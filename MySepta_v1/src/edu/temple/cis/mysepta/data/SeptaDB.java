package edu.temple.cis.mysepta.data;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;

public class SeptaDB extends DBAdapter{
	private static final String bus_s = "http://www.septa.org/schedules/bus/index.html";
	private static final String trolley_s = "http://www.septa.org/schedules/trolley/index.html";
	private static final String rail_s = "http://www.septa.org/schedules/rail/index.html";

	private RouteParser rp = null;
	private List<Route> train = null;
	private List<Route> bus = null;
	private List<Route> trolley = null;
	private List<Route> rail = null;
	
	public SeptaDB(Context ctx) {
		super(ctx);
	}

	public DBAdapter open() throws Exception{
		Log.i(nhuTag, "Opening database2");
		DBAdapter dbA = super.open();

		//if (this.isServiceEmpty()){
			Log.i(nhuTag, "db is empty, getting service");
			getService();

			rp = new RouteParser();
			getTrain();
			Log.i(nhuTag, "I'm calling rail");
			getRail();
			//getTrolley();
			//getBus();			
//		}
		return dbA;
	}
	
	private int rail_id, mfl_id, bsl_id, trolley_id, nhs_id, bus_id, cct_id;
	
	private void getService(){
		rail_id = (int)super.insertService("RR", "Regional Rail", "#477997");
		mfl_id = (int)super.insertService("MFL", "Market-Frankford Line", "#107DC1");
		bsl_id = (int)super.insertService("BSL", "Broad Street Line", "#F58220");
		trolley_id = (int)super.insertService("Trolley", "Trolley Lines", "#539442");
		nhs_id = (int)super.insertService("NHS", "Norristown High Speed Line", "#9E3E97");
		bus_id = (int)super.insertService("Buses", "Buses", "#41525C");
		cct_id = (int)super.insertService("CCT", "CCT Connect","#0055A5");
		Log.i(nhuTag, "got service");
	}
	
	/**
	 * Mannually entered data because structure is a little different and there are only 3 trains. 
	 * @return
	 * @throws Exception
	 */
	public List<Route> getTrain(){
		if (train == null){
			Log.i(nhuTag, " Inserting ");
			int mfl_route_id = (int) insertRoute(mfl_id, "MFL", "Market-Frankford Line", 0, "http://www.septa.org/schedules/transit/mfl.html");
			int bsl_route_id = (int) insertRoute(bsl_id, "BSL", "Broad Street Line", 0, "http://www.septa.org/schedules/transit/bsl.html");
			int nhs_route_id = (int) insertRoute(nhs_id, "NHS", "Norristown High Speed Line", 0, "http://www.septa.org/schedules/transit/nhsl.html");
			Log.i(nhuTag, " Inserting train " + mfl_route_id + " " + bsl_route_id + " " + nhs_route_id);
			train = new ArrayList<Route>();
			train.add(new Route(mfl_id, mfl_route_id, "MFL", "Market-Frankford Line", "http://www.septa.org/schedules/transit/mfl.html"));
			train.add(new Route(bsl_id, bsl_route_id, "BSL", "Broad Street Line", "http://www.septa.org/schedules/transit/bsl.html"));
			train.add(new Route(nhs_id, nhs_route_id, "NHS", "Norristown High Speed Line", "http://www.septa.org/schedules/transit/nhsl.html"));
		}
		return train;
	}

	public List<Route> getBus() throws Exception {
		if (bus == null){
			if (rp == null){rp = new RouteParser();}
			bus = rp.getRoute(bus_s, this, bus_id);
		}
		return bus;
	}

	public List<Route> getTrolley() throws Exception {
		if (trolley == null){
			if (rp == null) {rp = new RouteParser();}
			trolley = rp.getRoute(trolley_s, this, trolley_id);
		}
		return trolley;
	}

	public List<Route> getRail() throws Exception {
		if (rail == null){
			if (rp == null) { rp = new RouteParser();}
			Log.i(nhuTag, " Getting Rail info");
			rail = rp.getRoute(rail_s, this, rail_id);
		}
		return rail;
	}
}