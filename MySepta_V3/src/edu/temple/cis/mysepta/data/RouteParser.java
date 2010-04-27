package edu.temple.cis.mysepta.data;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.tags.BodyTag;
import org.htmlparser.tags.Bullet;
import org.htmlparser.tags.BulletList;
import org.htmlparser.tags.CompositeTag;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.HeadingTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.ParagraphTag;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.ParserException;

import android.util.Log;
import edu.temple.cis.mysepta.myclass.Route;

public class RouteParser {
    private List<Route> routes = null;
    private DBAdapter db = null;
    private long serviceID;
	private boolean rail = false;
    public RouteParser(){}

    /**
     * Retrieve all the routes information of the service from the given URL.
     * @param url URL of the service to parse.
     * @param db Database to add route data.
     * @param serviceID Service ID of the route to be parse (i.e. Trolley Service ID).
     * @return List of routes.
     * @throws ParserException If the creation of the underlying Lexer cannot be performed.
     */
    public List<Route> getRoute(String url, DBAdapter db, long serviceID) throws ParserException{
    	Log.i(DBAdapter.TAG, "Parsing routes of : " + url);
        if (url.contains("rail"))
            rail = true;

        this.db = db;
        this.serviceID = serviceID;
    	routes = new ArrayList<Route>();

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
        Log.i(DBAdapter.TAG, "Finish parsing routes of : " + url);
        return routes;
    }

    /**
     * Parse the given node to find the node containing the route's information (i.e. Node with the class "half_col").
     * @param root Root of the node to parse.
     */
    private void processCompositeTag(Node root) {
        for (Node child = root.getFirstChild(); child != null; child = child.getNextSibling()) {
            if (child instanceof BodyTag){
                processCompositeTag(child);
                break;
            } else if (child instanceof Div){
                Div div = (Div)child;
                String divId = div.getAttribute("id");
                String divClass = div.getAttribute("class");
                if (divId != null){
                     if (divId.equals("wrapper") || divId.equals("septa_main_content")){
                        processCompositeTag(child);
                        break;
                     }
                } else if (divClass != null){
                    if (divClass.equals("full_col")){
                        processCompositeTag(child);
                        break;
                    } else if (divClass.equals("half_col")){
                        processRoute(child);
                    }
                }
            }
        }
    }

    /**
     * Read through each route and call getIndividualRoute to parse route information.
     * @param root Root node containing all the root information.
     */
    private void processRoute(Node root){
        for (Node child = root.getFirstChild(); child != null; child = child.getNextSibling()){
            if (child instanceof BulletList){
                String name = child.getText();
                if (name.indexOf("routes_list") > -1){
                    if (rail){
                        getIndividualTrain(child);
                    } else {
                        getIndividualRoute(child);
                    }
                }
            }
        }
    }

    /**
     * Parse individual route to get the route short name, long name, and link.
     * @param root Root node containing all the route information.
     */
    private void getIndividualRoute(Node root){
        String route = "", name = "", link = "";
        String className = "";
        Node value = null;
        Node child = root.getFirstChild();
        while (child != null){
            if (child instanceof Bullet){
                Bullet b = (Bullet)child;
                className = b.getAttribute("class");
                if (className != null){
                    if (className.equals("route_no")){
                        value = b.getFirstChild();
                        route = value.getText().trim();
                        route = route.replaceAll("\'", "").replaceAll("\"", "");
                    } else if (className.equals("route_destination")){
                        value = b.getFirstChild();
                        name = value.getText().trim();
                        name = name.replaceAll("\'", "").replaceAll("\"", "");
                    } else if (className.equals("route_schedule")){
                        link = getLink(child);
                        int id = (int)db.insertRoute(serviceID, route, name, link);
                        routes.add(new Route(serviceID, id, route, name, link));
                        break;
                    }
                } else {
                    child = child.getFirstChild();
                }
            } else if (child instanceof BulletList){
                child = child.getFirstChild();
            }
            child = child.getNextSibling();
        }
    }

/**
 * Get the HTML link for the route schedule.
 * @param root Root node that contains the route's schedule links.
 * @return The link to the route schedule.
 */
    private String getLink(Node root){
        String link = null;
        Node child = root.getFirstChild();
        while (child != null){
            if(child instanceof BulletList){
                child = child.getFirstChild();
            } else if (child instanceof Bullet) {
                child = child.getFirstChild();
            } else if (child instanceof LinkTag) {
                LinkTag l = (LinkTag) child;
                String lname = l.getLink();
                if (lname.indexOf(".html") < 0){ //Is not html link
                    child = child.getParent().getNextSibling();
                } else { //Html link
                    link = lname.trim();
                    break;
                }
            } else {
                child = child.getNextSibling();
            }
        }
        return link;
    }


    /**
     * Parse individual train to get the train's schedule link and call getTrainName to get the train name.
     * @param root Root node containing all the route information.
     */
    private void getIndividualTrain(Node root){
        Node child = root.getFirstChild();
        while (child != null){
            if (child instanceof Bullet){
                Bullet b = (Bullet)child;
                String className = b.getAttribute("class");
                if (className != null){
                    if (className.equals("train_route")){
                        child = child.getFirstChild();
                    } else if (className.equals("train_schedule")){
                        String link = getLink(child);
                        getTrainName(root, link);
                        break;
                    }
                } else {
                    child = child.getFirstChild();
                }
            } else if (child instanceof BulletList){
                child = child.getFirstChild();
            }
            child = child.getNextSibling();
        }
    }

    /**
     * Get train long name and short name and insert it into the database.
     * @param root Root containing all the train information.
     * @param url URL to the train's schedule.
     */
    private void getTrainName(Node root, String url){
        Node child = root.getFirstChild();
        String route = "", name = "";
        while (child != null){
            if (child instanceof BulletList){
                child = child.getFirstChild();
            } else if (child instanceof Bullet){
                Bullet b = (Bullet)child;
                String n = b.getAttribute("class");
                if (n != null){
                    if (n.equals("train_schedule")){
                        child = child.getNextSibling();
                    } else{
                        child = child.getFirstChild();
                    }
                } else {
                    child = child.getFirstChild();
                }
            } else if (child instanceof HeadingTag){
            	//Node containing train's short name.
                Node h4 = child.getFirstChild();
                route = h4.getText().trim();
                route = route.replaceAll("\'", "").replaceAll("\"", "");
                child = child.getNextSibling();
            } else if (child instanceof ParagraphTag){
            	//Node containing train's long name.
                child = child.getFirstChild();
                name = child.getText().trim();
                name = name.replaceAll("\'", "").replaceAll("\"", "");
                int id = (int)db.insertRoute(serviceID, route, name, url);
                //routes.add(new Route(serviceID, id, route, name, url));
                break;
            } else {
                child = child.getNextSibling();
            }
        }
    }
}