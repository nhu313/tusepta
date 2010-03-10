/**
 * 
 */
package edu.temple.cis.tusepta.favorite;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import android.content.Context;
import edu.temple.cis.tusepta.R;

/**
 * @author Yu Liang
 *
 */
public class RouteHelper {
	
	private final static String SEP = ",";

	public static Map<Integer, Route> getRoutesMap(Context ctx) {
		Map<Integer, Route> routes = new HashMap<Integer, Route>();
		InputStream is = null;
		try {
			is = ctx.getResources().openRawResource(R.raw.routes);
			Scanner in = new Scanner(is);
			in.nextLine();
			while (in.hasNext()) {
				String s = in.nextLine();
				String[] sa = s.split(SEP);
				Route route = new Route();
				if (sa.length > 3) {
					route.setRoute_id(Integer.parseInt(sa[0]));
					route.setRoute_short_name(sa[1]);
					route.setRoute_long_name(sa[2]);
					route.setRoute_type(Integer.parseInt(sa[3]));
					if (sa.length > 4) {
						route.setRoute_url(sa[4]);
					}
					routes.put(route.getRoute_id(), route);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (Exception e) {
				}
			}
		}
		return routes;
	}

	public static List<Route> getRoutesList(Context ctx) {
		List<Route> routes = new ArrayList<Route>();
		InputStream is = null;
		try {
			is = ctx.getResources().openRawResource(R.raw.routes);
			Scanner in = new Scanner(is);
			in.nextLine();
			while (in.hasNext()) {
				String s = in.nextLine();
				String[] sa = s.split(SEP);
				Route route = new Route();
				if (sa.length > 3) {
					route.setRoute_id(Integer.parseInt(sa[0]));
					route.setRoute_short_name(sa[1]);
					route.setRoute_long_name(sa[2]);
					route.setRoute_type(Integer.parseInt(sa[3]));
					if (sa.length > 4) {
						route.setRoute_url(sa[4]);
					}
					routes.add(route);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (Exception e) {
				}
			}
		}
		return routes;
	}
	
	public static Route getRoute(Map<Integer, Route> routes, int routeID) {
		return (Route) routes.get(routeID);
	}
}
