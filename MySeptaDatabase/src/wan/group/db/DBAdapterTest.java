package wan.group.db;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

public class DBAdapterTest extends Activity {
	
	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        DBAdapter db = new DBAdapter(this);
        
        db.open();        
        db.insertService("RR","Regional Rail","#477997");        
        db.insertService("MFL","Market-Frankford Line","#107DC1");
        db.insertService("BSL","Broad Street Line","#F58220");
        db.insertService("TRL","Trolley Lines","#539442");
        db.insertService("NHS","Norristown High Speed Line","#9E3E97");
        db.insertService("BUS","Bus Line","#41525C");
        db.insertService("CCT","CCT Connect","#0055A5");
        db.close();
	
        //---get a Service---
        db.open();
        Cursor c = db.getService(2);
        if (c.moveToFirst())        
            DisplayService(c);
        else
            Toast.makeText(this, "No title found", 
            		Toast.LENGTH_LONG).show();
        db.close();
    
     /*   //---get all Services---
        db.open();
        Cursor c2 = db.getAllServices();
        if (c2.moveToFirst())
        {
            do {          
                DisplayService(c2);
            } while (c2.moveToNext());
        }
        db.close();
        
      //---delete a Service---
        db.open();
        if (db.deleteService(1))
            Toast.makeText(this, "Delete successful.", 
                Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Delete failed.", 
                Toast.LENGTH_LONG).show();            
        db.close();
        
      //After deleted one record ---get all Services---
        db.open();
        Cursor c3 = db.getAllServices();
        if (c3.moveToFirst())
        {
            do {          
                DisplayService(c3);
            } while (c3.moveToNext());
        }
        db.close();
        
     */
    
    }
	
	public void DisplayService(Cursor c)
    {
        Toast.makeText(this, 
                "ServiceID: " + c.getString(0) + "\n" +
                "ShortName: " + c.getString(1) + "\n" +
                "LongName: " + c.getString(2) + "\n" +
                "Color:  " + c.getString(3),
                Toast.LENGTH_LONG).show();        
    } 
	
	public void DisplayRoute(Cursor c)
    {
        Toast.makeText(this, 
                "RouteID: " + c.getString(0) + "\n" +
                "ServiceID: " + c.getString(1) + "\n" +
                "RouteNumer: " + c.getString(2) + "\n" +
                "RouteName: " + c.getString(3) + "\n" +
                "FavoriteRoute: " + c.getString(4) + "\n" +
                "URL:  " + c.getString(5),
                Toast.LENGTH_LONG).show();        
    } 
	
	public void DisplayStop(Cursor c)
    {
        Toast.makeText(this, 
                "StopID: " + c.getString(0) + "\n" +
                "RouteID: " + c.getString(1) + "\n" +
                "Stop:  " + c.getString(2),
                Toast.LENGTH_LONG).show();        
    } 
	
	public void DisplaySchedule(Cursor c)
    {
        Toast.makeText(this, 
                "ScheduleID: " + c.getString(0) + "\n" +
                "StopID: " + c.getString(1) + "\n" +
                "DayOfService: " + c.getString(2) + "\n" +
                "Direction: " + c.getString(3) + "\n" +
                "Time:  " + c.getString(4),
                Toast.LENGTH_LONG).show();        
    } 
	
	public void DisplayBackupStop(Cursor c)
    {
        Toast.makeText(this, 
                "StopID: " + c.getString(0) + "\n" +
                "RouteID: " + c.getString(1) + "\n" +
                "Stop:  " + c.getString(2),
                Toast.LENGTH_LONG).show();        
    } 
	
	public void DisplayBackupSchedule(Cursor c)
    {
        Toast.makeText(this, 
                "ScheduleID: " + c.getString(0) + "\n" +
                "StopID: " + c.getString(1) + "\n" +
                "DayOfService: " + c.getString(2) + "\n" +
                "Direction: " + c.getString(3) + "\n" +
                "Time:  " + c.getString(4),
                Toast.LENGTH_LONG).show();        
    } 

}
