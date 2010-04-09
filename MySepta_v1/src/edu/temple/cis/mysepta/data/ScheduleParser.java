package edu.temple.cis.mysepta.data;
import java.net.HttpURLConnection;
import java.text.DecimalFormat;
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

import android.util.Log;

import currentlyUnused.SeptaDB;

public class ScheduleParser {
	private SeptaDB2 db = null;
	private int routeID = 0;
	private String url = null;
    
	public ScheduleParser(){}

    public void parseSchedule(String url, SeptaDB2 db, int routeID) throws Exception{
        this.db = db;
        this.routeID = routeID; 
    	if (url.contains("rail")){
            rail = true;
        }
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
        rail = false;
        this.db = null;
        this.routeID = 0;
    }

    /**
     * Process HTML body. If root is a body tag or andy of the div wrapper,
     * call itself to process the child.
     * @param root Subroot of the tree.
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
                     if (divId.equals("wrapper") || divId.equals("septa_main_content") || divId.equals("tab2")){
                        processCompositeTag(child);
                        break;
                     } else if (divId.equals("tabs")){
                         getDirection(child);
                         processCompositeTag(child);
                     } else if (divId.indexOf("tabs-") >= 0){
                         getSchedule(child);
                     }
                } else if (divClass != null && (divClass.equals("full_col") || divClass.equals("col_content"))){
                    processCompositeTag(child);
                    break;
                }
            }
        }
    }

    /**
     * Find the root containing the individual direction and call
     * getIndividualDirection to get the direction.
     * @param root Root node with direction listing.
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
        printArrayList(direction);
    }

    /**
     * Parse HTML to get direction.
     * @param root Root node with direction information.
     */
    private void getIndividualDirection(Node root){
        Node parent = root.getFirstChild();
        Node child;
        while (parent != null){
            if (parent instanceof ParagraphTag){
                child = parent.getFirstChild();
                String name = child.getText().replace("|", "").trim();
                long dayID = db.insertDayOfService(routeID, name);
                day.add(dayID);
                Log.i(db.nhuTag, "day id " + dayID + " route id " + routeID + " direction " + name);
                Log.i(db.nhuTag, "direction " + name + " " + dayID);
                while (child != null){
                    child = child.getNextSibling();
                }
            }
            parent = parent.getNextSibling();
        }
    }

    /**
     * Find the root that holds the stop and time information and call the
     * appropriate function to parse each.
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
                    if (align != null && align.equals("middle")){
                        parseStop(child);
                    } else {
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
    	stop = new ArrayList<Long>();
    	int dayID = 0;
        for (Node child = root.getFirstChild(); child != null; child = child.getNextSibling()){
            Node grand = child.getFirstChild();
            while (grand != null){
                if (grand instanceof ImageTag){
                    ImageTag img = (ImageTag) grand;
                    String name = img.getAttribute("alt");
                    long stopID = db.insertStop(day.get(dayID++), name);
                    //Log.i(db.nhuTag, "Day id " + day.get(dayID-1) + " " + name + " " + stopID);
                    Log.i(db.nhuTag, "Stop name " + name);
                    //stop.add(stopID);
                    break;
                } else if (grand instanceof TableColumn || grand instanceof Div){
                    grand = grand.getFirstChild();
                } else {
                    grand = grand.getNextSibling();
                }
            }
        }
    }

    private List<Long> stop = null;
    /**
     * Parse the schedule time.
     * @param root Root containing the time information.
     */
	private void parseTime(Node root){
		Log.i(db.nhuTag, "Parsring time");
		Log.i(db.nhuTag, root.getText());
    	int i = 0;
        double pm = 0.0;
        Node grand = null;
        for (Node child = root; child != null; child = child.getNextSibling()){
            grand = child.getFirstChild();
            ArrayList<Double> t = new ArrayList<Double>();
            while (grand != null){
            	if (i >= stop.size())
            		i = 0;
                if (grand instanceof TableColumn || grand instanceof Div){
                    grand = grand.getFirstChild();
                } else if (grand instanceof TextNode){
                    double x = 0.0;
                    String text = grand.getText();
                    try{
                    	//Log.i(db.nhuTag, child.getText());
                        x = Double.parseDouble(text);
                        if (x < 12.0){ // If x is less than 12, add pm.
                            x = x + pm;
                        }
                        long timeID = db.insertTime(stop.get(i++), (float)roundTwoDecimals(x));
                        Log.i(db.nhuTag, "Stop id " + stop.get(i-1) + " " + x);
                        Log.i(db.nhuTag, "Time " + x);
                        i++;
                    } catch (NumberFormatException e){
                        if (text.equals("-")){
                        	Log.i(db.nhuTag, child.getText());
                        	db.insertTime(stop.get(i++), (float)0.0);
                        	Log.i(db.nhuTag, "Time " + 0.0);
                        	i++;
                        } else if (text.equals("PM Service")){
                            pm = 12.0;
                        }
                    }
                    grand = grand.getParent().getNextSibling();
                } else {
                    grand = grand.getNextSibling();
                }
            }
        }
    }

    /**
     * Round the double to display two significant number after the decimal.
     * @param d Double to round off.
     * @return Double with rounded to two places.
     */
    private double roundTwoDecimals(double d) {
        DecimalFormat twoDForm = new DecimalFormat("00.00");
        return Double.valueOf(twoDForm.format(d));
    }

    @SuppressWarnings("unchecked")
	private void printArrayList(ArrayList list){
        System.out.println("Printing List");
        for (int i = 0; i < list.size(); i++){
            System.out.print(list.get(i).toString() + "\t");
        }
        System.out.println();
    }

    private List<Long> day = new ArrayList<Long>();
    private ArrayList<String> direction = new ArrayList<String>();
    private boolean rail = false;
}