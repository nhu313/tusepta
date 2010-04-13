/**
 * 
 */
package edu.temple.cis.mysepta.favorite;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author Yu Liang 
 *
 */
public class FavoriteRouteHelper {

	private final static String FAVORITE_ROUTES = "FAVORITE_ROUTES";
	
	private final static String SEP = ",";
	
	public static List<Route> getFavoriteRoutes(Context ctx) {
		List<Route> routes = new ArrayList<Route>();
		SharedPreferences prefsFavoriteRoutes = ctx.getSharedPreferences(
				FAVORITE_ROUTES, Context.MODE_WORLD_READABLE);
		String fr = prefsFavoriteRoutes.getString(FAVORITE_ROUTES, null);
		if (fr != null) {
			/*
			String[] fra = fr.split(SEP);
			RouteHelper routeHelper = new RouteHelper(ctx);
			Map<Integer, Route> routesMap = RouteHelper.getRoutesMap(ctx);
			for (int i = 0; i < fra.length; i++) {
				if (routesMap.containsKey(fra[i])) {
					routes.add((Route) routesMap.get(fra[i]));
				}
			}
			*/
		}

		return routes;
	}
}
