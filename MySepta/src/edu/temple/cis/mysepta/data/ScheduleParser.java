package edu.temple.cis.mysepta.data;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.tags.BodyTag;
import org.htmlparser.tags.Bullet;
import org.htmlparser.tags.BulletList;
import org.htmlparser.tags.CompositeTag;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.tags.ParagraphTag;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.ParserException;

import android.util.Log;

/**
 * ScheduleParser uses HTMLParser (third party library) to parse day of service, stop, and schedule for a specific route.
 */

public class ScheduleParser {
	private DBAdapter db = null;
	private long routeID = 0;
    
	public ScheduleParser(){}

	/**
	 * Retrieve all the schedule information of the route from the given URL.
     * @param url URL of the route to parse.
     * @param db Database to add schedule data.
     * @param id ID of the route to be parse.
     * @throws ParserException If the creation of the underlying Lexer cannot be performed.
	 */
    public void parseSchedule(String url, DBAdapter db, long id) throws ParserException{
    	if (url.contains("rail")){
    		rail = true;
    	}
    	Log.i(DBAdapter.TAG, "Parsing schedule of " + id + "(" + url + ")");
        this.db = db;
        this.routeID = id; 
        Parser parser = new Parser(url);
        NodeIterator itr = parser.elements();
        while (itr.hasMoreNodes()) {
            Node node = itr.nextNode();
            if (node instanceof CompositeTag) {
                if (node != null){
                    processCompositeTag(node);
                }
                break;
            }
        }
        HttpURLConnection connection = (HttpURLConnection) parser.getConnection();
        connection.disconnect();
        this.db = null;
        this.routeID = 0;
        rail = false;
    }

    /**
     * Process HTML body. If root is a body tag or any of the div wrapper, call itself to process the child.
     * @param root Subroot of the tree to containing the HTML body.
     */
    private void processCompositeTag(Node root){
        for (Node child = root.getFirstChild(); child != null; child = child.getNextSibling()){
            if (child instanceof BodyTag){
                processCompositeTag(child);
                break;
            } else if (child instanceof Div){
                Div div = (Div)child;
                String divId = div.getAttribute("id");
                String divClass = div.getAttribute("class");
                if (divId != null){
                     if (divId.equalsIgnoreCase("wrapper") || divId.equalsIgnoreCase("septa_main_content") || divId.equalsIgnoreCase("tab2")){
                        processCompositeTag(child);
                        break;
                     } else if (divId.equalsIgnoreCase("tabs")){
                         getDirection(child);
                         processCompositeTag(child);
                     } else if (divId.indexOf("tabs-") >= 0){
                    	 //Get Schedule
                    	 if (rail){
                             getRailSchedule(child);
                         } else {
                        	 getSchedule(child);
                         }
                     }
                } else if (divClass != null && (divClass.equalsIgnoreCase("full_col") || divClass.equalsIgnoreCase("col_content"))){
                    processCompositeTag(child);
                    break;
                }
            }
        }
    }

    /**
     * Find the root containing the individual direction and call getIndividualDirection to get the direction.
     * @param root Root node with the direction listing.
     */
    private void getDirection(Node root){
        Node parent = root.getFirstChild();
        //Get the direction listing tag
        while (parent != null && !(parent instanceof Bullet)){
            if (parent instanceof Div || parent instanceof BulletList){
                parent = parent.getFirstChild();
            } else {
                parent = parent.getNextSibling();
            }
        }

        //Get individual direction
        for (; parent != null; parent = parent.getNextSibling()){
            getIndividualDirection(parent);
        }        
    }

    /**
     * Parse HTML to get direction information.
     * @param root Root node with direction information.
     */
    private void getIndividualDirection(Node root){
        Node parent = root.getFirstChild();
        Node child;
        while (parent != null){
            if (parent instanceof ParagraphTag){
            	//Get direction information.
                child = parent.getFirstChild();
                String name = child.getText().replace("&amp;", "&").trim();
                name = name.replace("\"", "").replace("'", "");
                long dayID = db.insertDayOfService(routeID, name);
                day.add(dayID);
                Log.i(DBAdapter.TAG, routeID + " Direction: [" + dayID + "]" + name);
                while (child != null){
                    child = child.getNextSibling();
                }
            }
            parent = parent.getNextSibling();
        }
    }

    /**
     * Find the root that holds the stop and time information and call the appropriate function to parse each.
     * @param root Root node of the schedule listing.
     */
    private void getSchedule(Node root){
        Node child = root.getFirstChild();
        //Get to the row containing the schedule information.
        while (child != null && !(child instanceof TableRow)){
            if (child instanceof TableTag){
                child = child.getFirstChild();
            } else {
                child  = child.getNextSibling();
            }
        }

        //Find the row containing the stop and time, call the appropriate method.
        if (child instanceof TableRow){
            for (; child != null; child = child.getNextSibling()){
                if (child instanceof TableRow){
                    TableRow tr = (TableRow) child;
                    String align = tr.getAttribute("align");
                    if (align != null && (align.equalsIgnoreCase("middle") || align.equalsIgnoreCase("center"))){
                    	//Parse stop
                    	parseStop(child);
                    	s_dayID++;
                        if (s_dayID == day.size())
                        	s_dayID = 0;
                    } else {
                    	//Parse time
                    	parseTime(child);
                    	break;
                    }
                }
            }
        }
    }

    /**
     * From the parent root, parse the HTML to get stop information and add it
     * it to the stop ArrayList.
     * @param root Root node with stop listing.
     */
    private void parseStop(Node root){
    	Log.i(DBAdapter.TAG, "Parsing route " + routeID + " stops.");
    	stop = new ArrayList<Long>();
        for (Node child = root.getFirstChild(); child != null; child = child.getNextSibling()){
            Node grand = child.getFirstChild();
            while (grand != null){
                if (grand instanceof ImageTag){
                	//Get the stop name, which is in the alt attribute of the image.
                    ImageTag img = (ImageTag) grand;
                    String name = img.getAttribute("alt").replace("&amp;", "&").trim();
                    name = name.replace("\"", "").replace("'", "");
	                long stopID = db.insertStop(day.get(s_dayID), name);
	                stop.add(stopID);
	                Log.i(DBAdapter.TAG, "Route " + routeID + " | Day " + day.get(s_dayID) + " | Stop " + stopID + " | Name " + name);
                    break;
                } else if (grand instanceof TableColumn || grand instanceof Div){
                    grand = grand.getFirstChild();
                } else {
                    grand = grand.getNextSibling();
                }
            }
        }   
    }

    /**
     * Parse the schedule time.
     * @param root Root containing the time information.
     */
	private void parseTime(Node root){
		Log.i(DBAdapter.TAG, "Parsring route " + routeID + " time.");
    	int i = 0;
        float pm = 0;
        Node grand = null;
        for (Node child = root; child != null; child = child.getNextSibling()){
            grand = child.getFirstChild();
            while (grand != null){
            	if (i >= stop.size())
            		i = 0;
                if (grand instanceof TableColumn || grand instanceof Div){
                    grand = grand.getFirstChild();
                } else if (grand instanceof TextNode){
                	String text = grand.getText();
                    float x = 0;
                    try{
                    	//Get schedule
                        x = Float.parseFloat(text);
                        if (x < 25){
	                        if (x < 12){ // If x is less than 12, add pm.
	                            x = x + pm;
	                        }
                        	if (x >= 25){
                                pm = 0;
                                x = x - 12;
                            }
	                        db.insertSchedule(stop.get(i++), x);
                        }
                    } catch (NumberFormatException e){
                        if (text.equalsIgnoreCase("-")){
                        	db.insertSchedule(stop.get(i++), 0);
                        } else if (text.equalsIgnoreCase("PM Service")){
                            pm = 12;
                        }
                    }
                    grand = grand.getParent().getNextSibling();
                } else {
                    grand = grand.getNextSibling();
                }
            }
        }
		Log.i(DBAdapter.TAG, "Finish parsring route " + routeID + " time.");	
    }
	
    /**
     * Find the root that holds the stop and time information and call the appropriate function to parse each.
     * @param root Root node of the schedule listing.
     */
	private void getRailSchedule(Node root){
		stop = new ArrayList<Long>();
		Node child = root.getFirstChild();
        //Get to the row containing the schedule information.
        while (child != null){
            if (child instanceof TableTag){
                child = child.getFirstChild();
            } else if (child instanceof TableRow){
              TableRow r = (TableRow) child;
              String bg = r.getAttribute("bordercolor");
              if (bg != null && bg.equals("#000000")){
                  parseTime(child.getNextSibling());
                  break;
              } else {
                child = child.getFirstChild();
              }
            } else if (child instanceof TableColumn){
                TableColumn col = (TableColumn) child;
                String c = col.getAttribute("class");

                if (c == null) {
                    Node grand = child.getFirstChild();
                    if (grand instanceof ImageTag){
                        parseRailStop(child.getNextSibling());
                    	s_dayID++;
                        if (s_dayID == day.size())
                        	s_dayID = 0;

                    }
                    child = child.getParent().getNextSibling();
                } else {
                    child = child.getParent().getNextSibling();
                }
            } else {
                child  = child.getNextSibling();
            }
        }
    }

	/**
     * From the parent root, parse the HTML to get stop information and add it
     * it to the stop List.
     * @param root Root node with stop listing.
     */
    private void parseRailStop(Node root){
        Node child = root;
        while (child != null && !(child instanceof TableColumn)){
            child = child.getNextSibling();
        }

        for (; child != null; child = child.getNextSibling()){
            Node grand = child.getFirstChild();
            while (grand != null){
                if (grand instanceof ImageTag){
                    ImageTag img = (ImageTag) grand;
                    String name = img.getAttribute("alt").replace("&amp;", "&").trim();
                    name = name.replace("\"", "").replace("'", "");
                    long stopID = db.insertStop(day.get(s_dayID), name);
                    Log.i(DBAdapter.TAG, "Route " + routeID + " | Day " + day.get(s_dayID) + " | Stop " + stopID + " | Name " + name);
                    stop.add(stopID);
                    break;
                } else if (grand instanceof TableColumn || grand instanceof Div){
                    grand = grand.getFirstChild();
                } else {
                    grand = grand.getNextSibling();
                }
            }
        }
    }

    private static int s_dayID = 0;
	private boolean rail = false;
    private List<Long> day = new ArrayList<Long>(); //ID list for the days of service
    private List<Long> stop = null; //ID list of the stops
}