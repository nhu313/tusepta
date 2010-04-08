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

import android.util.Log;

/**
 *
 * @author Orange
 */
public class RouteParser {
    private boolean rail = false;
    public RouteParser(){}

    public List<Route> getRoute(String url, SeptaDB2 septaDB2, int id) throws Exception{

        if (url.contains("rail"))
            rail = true;

        this.db = septaDB2;
        serviceID = id;
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
        return (List<Route>)routes;
    }

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
     * Read through each route and call getIndividualRoute to parse route.
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
    private void getIndividualTrain(Node root){
        String link = "";
        String className = "";
        Node child = root.getFirstChild();
        while (child != null){
            if (child instanceof Bullet){
                Bullet b = (Bullet)child;
                className = b.getAttribute("class");
                if (className != null){
                    if (className.equals("train_route")){
                        child = child.getFirstChild();
                    } else if (className.equals("train_schedule")){
                        link = getLink(child);
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

    private void getTrainName(Node root, String link){
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
                Node h4 = child.getFirstChild();
                route = h4.getText().trim();
                route = route.replaceAll("\'", "").replaceAll("\"", "");
                child = child.getNextSibling();
            } else if (child instanceof ParagraphTag){
                child = child.getFirstChild();
                name = child.getText().trim();
                name = name.replaceAll("\'", "").replaceAll("\"", "");
                int id = (int)db.insertRoute(serviceID, route, name, 0, link);
                routes.add(new Route(serviceID, id, route, name, link));
                Log.i(db.nhuTag + "In Route Parser:", routes.get(routes.size()-1).toString());
                break;
            } else {
                child = child.getNextSibling();
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
                        int id = (int)db.insertRoute(serviceID, route, name, 0, link);
                        routes.add(new Route(serviceID, id, route, name, link));
                        Log.i(db.nhuTag + "In Route Parser:", routes.get(routes.size()-1).toString());
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

    private ArrayList<Route> routes = null;
    private SeptaDB2 db = null;
    private int serviceID;
}