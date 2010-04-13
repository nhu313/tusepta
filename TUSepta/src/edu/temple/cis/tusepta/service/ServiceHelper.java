/**
 * 
 */
package edu.temple.cis.mysepta.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import android.content.Context;
import edu.temple.cis.mysepta.R;

/**
 * @author Yu Liang
 *
 */
public class ServiceHelper {
	
	private final static String SEP = ",";
	
	public static List<Service> getRoutesList(Context ctx) {
		List<Service> services = new ArrayList<Service>();
		InputStream is = null;
		try {
			is = ctx.getResources().openRawResource(R.raw.services);
			Scanner in = new Scanner(is);
			while (in.hasNext()) {
				String s = in.nextLine();
				String[] sa = s.split(SEP);
				Service service = new Service();
				if (sa.length > 3) {
					service.setId(Integer.parseInt(sa[0]));
					service.setShortName(sa[1]);
					service.setLongName(sa[2]);
					service.setColor(sa[3]);
					services.add(service);
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
		return services;
	}
}
