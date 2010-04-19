/**
 * 
 */
package edu.temple.cis.mysepta.favorite;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import android.content.Context;
import edu.temple.cis.mysepta.R;
import edu.temple.cis.mysepta.myclass.Route;

/**
 * @author Yu Liang
 *
 */
public class RouteHelper {
	
	private final static String SEP = ",";

	private Context ctx;
	
	Map<Integer, Route> routeMap;
	List<Route> routeList;
	Map<Integer, Route> favoriteRouteMap;
	List<Route> favoriteRouteList;
	
	public RouteHelper(Context ctx) {
		this.ctx = ctx;
	}
	
	private void init() {
		if (routeMap == null) {
			routeMap = new HashMap<Integer, Route>();
			routeList = new ArrayList<Route>();
			favoriteRouteMap = new HashMap<Integer, Route>();
			favoriteRouteList = new ArrayList<Route>();
			InputStream is = null;
			try {
				is = this.ctx.getResources().openRawResource(R.raw.routes);
				Scanner in = new Scanner(is);
				in.nextLine();
				int i = 0;
				while (in.hasNext()) {
					String s = in.nextLine();
					String[] sa = s.split(SEP);
					Route route = new Route();
					if (sa.length > 3) {
						route.setRoute_id(Integer.parseInt(sa[0]));
						//route.setRoute_short_name(sa[1]);
						route.setName(sa[2]);
						if (i++%10 == 1) {
							favoriteRouteMap.put((int)route.getRoute_id(), route);
							favoriteRouteList.add(route);
						} else {
							routeMap.put((int)route.getRoute_id(), route);
							routeList.add(route);
						}
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
		}
	}
	
	public Map<Integer, Route> getRoutesMap() {
		init();
		return routeMap;
	}

	public List<Route> getRoutesList() {
		init();
		return routeList;
	}
	
	public Map<Integer, Route> getFavoriteRoutesMap() {
		init();
		return favoriteRouteMap;
	}

	public List<Route> getFavoriteRoutesList() {
		init();
		return favoriteRouteList;
	}
	
	public Route getRoute(int routeID) {
		return (Route) routeMap.get(routeID);
	}
}
