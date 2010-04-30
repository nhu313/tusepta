package edu.temple.cis.mysepta.data;

import java.util.GregorianCalendar;

public class MySeptaDataHelper {
	public static float getTime(){
		GregorianCalendar now = new GregorianCalendar();
		float x = now.get(GregorianCalendar.HOUR_OF_DAY);
		float y = now.get(GregorianCalendar.MINUTE);
		y = y / 100;
		x = x + y;
		return x;
	}
	
	public static float round2(float t){
		return (float) (Math.round(100.0 * t)/100.0);
	}

	public static String time2String(float time){
		String ret = "";
		if (time >= 12.0){
			if (time >= 24.0){
				ret = MySeptaDataHelper.round2(time - 12) + " AM";
			} else if (time < 13.0){
				ret = MySeptaDataHelper.round2(time) + " PM";
			} else {
				ret = MySeptaDataHelper.round2(time - 12) + " PM";
			}
		} else {
			ret = MySeptaDataHelper.round2(time) + " AM";
		}
		return ret;
	}
}
