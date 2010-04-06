package edu.temple.cis.mysepta.data;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.text.DecimalFormat;

import org.htmlparser.tags.ImageTag;
import org.htmlparser.tags.CompositeTag;
import org.htmlparser.tags.BodyTag;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.TableTag;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.Bullet;
import org.htmlparser.tags.BulletList;
import org.htmlparser.tags.ParagraphTag;

import org.htmlparser.nodes.TextNode;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.Parser;
import org.htmlparser.Node;

public class ScheduleParser {
    public ScheduleParser(){}

    public void parseSchedule(String url) throws Exception{
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
                direction.add(child.getText());
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
        ArrayList<String> s = new ArrayList<String>();
        for (Node child = root.getFirstChild(); child != null; child = child.getNextSibling()){
            Node grand = child.getFirstChild();
            while (grand != null){
                if (grand instanceof ImageTag){
                    ImageTag img = (ImageTag) grand;
                    s.add(img.getAttribute("alt"));
                    break;
                } else if (grand instanceof TableColumn || grand instanceof Div){
                    grand = grand.getFirstChild();
                } else {
                    grand = grand.getNextSibling();
                }
            }
        }
        stop.add(s);
        printArrayList(s);
    }

    /**
     * Parse the schedule time.
     * @param root Root containing the time information.
     */
    @SuppressWarnings("unchecked")
	private void parseTime(Node root){
        ArrayList<ArrayList> ta = new ArrayList<ArrayList>();
        double pm = 0.0;
        Node grand = null;
        for (Node child = root; child != null; child = child.getNextSibling()){
            grand = child.getFirstChild();
            ArrayList<Double> t = new ArrayList<Double>();
            while (grand != null){
                if (grand instanceof TableColumn || grand instanceof Div){
                    grand = grand.getFirstChild();
                } else if (grand instanceof TextNode){
                    double x = 0.0;
                    String text = grand.getText();
                    try{
                        x = Double.parseDouble(text);
                        if (x < 12.0){ // If x is less than 12, add pm.
                            x = x + pm;
                        }
                        t.add(roundTwoDecimals(x));
                    } catch (NumberFormatException e){
                        if (text.equals("-")){
                            t.add(0.0);
                        } else if (text.equals("PM Service")){
                            pm = 12.0;
                        }
                    }
                    grand = grand.getParent().getNextSibling();
                } else {
                    grand = grand.getNextSibling();
                }
            }
            if (t.size() > 0){
                ta.add(t);
                printArrayList(t);
            }
        }
        sched.add(ta);
    }

    /**
     * Round the double to display two significant number after the decimal.
     * @param d Double to round off.
     * @return Double with rounded to two places.
     */
    private double roundTwoDecimals(double d) {
        DecimalFormat twoDForm = new DecimalFormat("#.##");
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

    private ArrayList<String> direction = new ArrayList<String>();
    @SuppressWarnings("unchecked")
	private ArrayList<ArrayList> stop = new ArrayList<ArrayList>();
    @SuppressWarnings("unchecked")
	private ArrayList<ArrayList> sched = new ArrayList<ArrayList>();
}