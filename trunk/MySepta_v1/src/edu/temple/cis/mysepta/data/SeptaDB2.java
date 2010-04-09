package edu.temple.cis.mysepta.data;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

public class SeptaDB2 extends DBAdapter2{
	private static final String bus_s = "http://www.septa.org/schedules/bus/index.html";
	private static final String trolley_s = "http://www.septa.org/schedules/trolley/index.html";
	private static final String rail_s = "http://www.septa.org/schedules/rail/index.html";

	private RouteParser rp = null;
	private List<Route> bus = null;
	private List<Route> trolley = null;
	private List<Route> rail = null;
	private Service[] service = null;
	
	private int rail_id, mfl_id, bsl_id, trolley_id, nhs_id, bus_id;
	
	public SeptaDB2(Context ctx) {
		super(ctx);
	}

	public DBAdapter2 open() throws Exception{
		Log.i(nhuTag, "Opening database2");
		DBAdapter2 dbA = super.open();
		insertService();
		getTrain();
		return dbA;
	}
	
	public List<Route> getRoute(int serviceID) throws Exception{
		List<Route> route = null;
		if (serviceID == rail_id){
			route = getRail();
		} else if (serviceID == trolley_id){
			route = getTrolley();
		} else if (serviceID == bus_id){
			route = getBus();
		} else {
			throw new Exception("Cannot find service ID");
		}
		return route;
	}
	
	//Insert service to get ids
	private void insertService(){
		rail_id = (int)super.insertService("RR", "Regional Rail", "#477997");
		mfl_id = (int)super.insertService("MFL", "Market-Frankford Line", "#107DC1");
		bsl_id = (int)super.insertService("BSL", "Broad Street Line", "#F58220");
		trolley_id = (int)super.insertService("Trolley", "Trolley Lines", "#539442");
		nhs_id = (int)super.insertService("NHS", "Norristown High Speed Line", "#9E3E97");
		bus_id = (int)super.insertService("Buses", "Buses", "#41525C");
	}
	
	//Get list of service
	public Service[] getService(){
		if (service == null){
			Cursor c = super.getAllServices();
			if (c.moveToFirst()){
				service = new Service[c.getCount()];
				for (int i = 0; i < c.getCount(); i++){
					service[i] = new Service(c.getInt(0), c.getString(1), c.getString(2), c.getString(3));
					c.moveToNext();
				}
			} else {
				insertService();
				getService();
			}
			c.close();
		}
		return service;
	}
	
	/**
	 * Mannually entered data because structure is a little different and there are only 3 trains. 
	 * @return
	 * @throws Exception
	 */
	public void getTrain(){
		int mfl_route_id = (int) insertRoute(mfl_id, "MFL", "Market-Frankford Line", 0, "http://www.septa.org/schedules/transit/mfl.html");
		int bsl_route_id = (int) insertRoute(bsl_id, "BSL", "Broad Street Line", 0, "http://www.septa.org/schedules/transit/bsl.html");
		int nhs_route_id = (int) insertRoute(nhs_id, "NHS", "Norristown High Speed Line", 0, "http://www.septa.org/schedules/transit/nhsl.html");
	}

	public List<Route> getBus() throws Exception {
		if (bus == null){
			Cursor c = super.getRouteByService(bus_id);
			if (c.moveToFirst()){
				if (rp == null){rp = new RouteParser();}
				bus = rp.getRoute(bus_s, this, bus_id);
			} else {
				Log.i(nhuTag, "It's not empty.");
				bus = getRouteByService(c);
			}
			c.close();
		}
		Log.i(nhuTag, bus.get(bus.size()-1).toString());
		return bus;
	}
	
	public List<Route> getTrolley() throws Exception {
		if (trolley == null){
			Cursor c = super.getRouteByService(trolley_id);
			if (c.moveToFirst()){
				if (rp == null){rp = new RouteParser();}
				trolley = rp.getRoute(trolley_s, this, trolley_id);
			} else {
				//Log.i(nhuTag, "It's not empty.");
				trolley = getRouteByService(c);
			}
			c.close();
		}
		//Log.i(nhuTag, bus.get(bus.size()-1).toString());
		return trolley;
	}

	public List<Route> getRail() throws Exception {
		if (rail == null){
			Cursor c = super.getRouteByService(rail_id);
			if (c.moveToFirst()){
				if (rp == null){rp = new RouteParser();}
				rail = rp.getRoute(rail_s, this, rail_id);
			} else {
				Log.i(nhuTag, "It's not empty.");
				rail = getRouteByService(c);
			}
			c.close();
		}
	//	Log.i(nhuTag, bus.get(bus.size()-1).toString());
		return rail;
	}
	
	private List<Route> getRouteByService(Cursor c) throws Exception{
		Log.i(nhuTag, "cursor count " + c.getCount());
		List<Route> list = new ArrayList<Route>();
		for (int i = 0; i < c.getCount(); i++){
			int f = c.getInt(4);
			boolean fav = (f == 1) ? true:false;
			list.add(new Route(c.getInt(0), c.getInt(1), c.getString(2), 
					c.getString(3), fav, c.getString(5)));
			Log.i(nhuTag, "Array size after adding rail" + list.size());
			c.moveToNext();
		}
		Log.i(nhuTag + "getroute", list.get(list.size()-1).toString());
		return list; 
	}

}