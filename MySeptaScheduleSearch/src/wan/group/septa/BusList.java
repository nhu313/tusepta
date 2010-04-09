package wan.group.septa;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.app.ListActivity; 
import android.content.Intent;

public class BusList extends ListActivity{
	
	String[] bus = {
			"Route 1",
			"Route 2",
			"Route 3",
			"Route 5",
			"Route 6",
			"Route 7",
			"Route 8",
			"Route 9",
			"Route 12",
			"Route 14",
			"Route 17",
			"Route 18",
			"Route 19",
			"Route 20",
			"Route 21",
			"Route 22",
			"Route 23",
			"Route 24",
			"Route 25",
			"Route 26",
			"Route 27",
			"Route 28",
			"Route 29",
			"Route 30",
			"Route 31",
			"Route 32",
			"Route 35",
			"Route 37",
			"Route 38",
			"Route 39",
			"Route 40",
			"Route 42",
			"Route 43",
			"Route 46",
			"Route 47",
			"Route 47m",
			"Route 48",
			"Route 50",
			"Route 52",
			"Route 53",
			"Route 54",
			"Route 55",
			"Route 56",
			"Route 57",
			"Route 58",
			"Route 59",
			"Route 60",
			"Route 61",
			"Route 62",
			"Route 64",
			"Route 65",
			"Route 66",
			"Route 67",
			"Route 68",
			"Route 70",
			"Route 71",
			"Route 73",
			"Route 75",
			"Route 77",
			"Route 79",
			"Route 80",
			"Route 84",
			"Route 88",
			"Route 89",
			"Route C",
			"Route G",
			"Route HXH",
			"Route J",
			"Route K",
			"Route L",
			"Route R",
			"Route LUCY",
			"Route 90",
			"Route 91",
			"Route 92",
			"Route 93",
			"Route 94",
			"Route 95",
			"Route 96",
			"Route 97",
			"Route 98",
			"Route 99",
			"Route 103",
			"Route 104",
			"Route 105",
			"Route 106",
			"Route 107",
			"Route 108",
			"Route 109",
			"Route 110",
			"Route 111",
			"Route 112",
			"Route 113",
			"Route 114",
			"Route 115",
			"Route 116",
			"Route 117",
			"Route 118",
			"Route 119",
			"Route 120",
			"Route 123",
			"Route 124",
			"Route 125",
			"Route 127",
			"Route 128",
			"Route 129",
			"Route 130",
			"Route 131",
			"Route 132",
			"Route 134",
			"Route 139",
			"Route 150",
			"Route 201",
			"Route 204",
			"Route 205",
			"Route 206",
			"Route 304",
			"Route 306",
			"Route 310",
			"Route 314"	
    };
	
	@Override  
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.bus);
        setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, bus));
     }    
 
    public void onListItemClick(ListView parent, View v, int position, long id) 
    {   
        Toast.makeText(this,"You have selected " + bus[position],Toast.LENGTH_SHORT).show();
        
        switch (position) {
		case 0: {//Route 1
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 1: {//Route 2
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 2: {//Route 3
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 3: {//Route 5
			Intent intent = new Intent(this, wan.group.septa.TrolleyList.class);
			startActivity(intent);
			break;
		}
		case 4: {//Route 6
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 5: {//Route 7
			Intent intent = new Intent(this, wan.group.septa.BusList.class);
		    startActivity(intent);
			break;
		}
		case 6: {//Route 8
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 7: {//Route 9
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 8: {//Route 12
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 9: {//Route 14
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 10: {//Route 17
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 11: {//Route 19
			Intent intent = new Intent(this, wan.group.septa.TrolleyList.class);
			startActivity(intent);
			break;
		}
		case 12: {//Route 20
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 13: {//Route 21
			Intent intent = new Intent(this, wan.group.septa.BusList.class);
		    startActivity(intent);
			break;
		}
		case 14: {//Route 22
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 15: {//Route 23
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 16: {//Route 24
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 17: {//Route 25
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 18: {//Route 26
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 19: {//Route 27
			Intent intent = new Intent(this, wan.group.septa.TrolleyList.class);
			startActivity(intent);
			break;
		}
		case 20: {//Route 28
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 21: {//Route 29
			Intent intent = new Intent(this, wan.group.septa.BusList.class);
		    startActivity(intent);
			break;
		}
		case 22: {//Route 30
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 23: {//Route 102
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 24: {//Route 1
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 25: {//Route 2
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 26: {//Route 13
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 27: {//Route 15
			Intent intent = new Intent(this, wan.group.septa.TrolleyList.class);
			startActivity(intent);
			break;
		}
		case 28: {//Route 34
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 29: {//Route 36
			Intent intent = new Intent(this, wan.group.septa.BusList.class);
		    startActivity(intent);
			break;
		}
		case 30: {//Route 101
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 31: {//Route 102
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 32: {//Route 1
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 33: {//Route 2
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 34: {//Route 13
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 35: {//Route 15
			Intent intent = new Intent(this, wan.group.septa.TrolleyList.class);
			startActivity(intent);
			break;
		}
		case 36: {//Route 34
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 37: {//Route 36
			Intent intent = new Intent(this, wan.group.septa.BusList.class);
		    startActivity(intent);
			break;
		}
		case 38: {//Route 101
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 39: {//Route 102
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 40: {//Route 1
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 41: {//Route 2
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 42: {//Route 13
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 43: {//Route 15
			Intent intent = new Intent(this, wan.group.septa.TrolleyList.class);
			startActivity(intent);
			break;
		}
		case 44: {//Route 34
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 45: {//Route 36
			Intent intent = new Intent(this, wan.group.septa.BusList.class);
		    startActivity(intent);
			break;
		}
		case 46: {//Route 101
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 47: {//Route 102
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 48: {//Route 1
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 49: {//Route 2
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 50: {//Route 13
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 51: {//Route 15
			Intent intent = new Intent(this, wan.group.septa.TrolleyList.class);
			startActivity(intent);
			break;
		}
		case 52: {//Route 34
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 53: {//Route 36
			Intent intent = new Intent(this, wan.group.septa.BusList.class);
		    startActivity(intent);
			break;
		}
		case 54: {//Route 101
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 55: {//Route 102
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 56: {//Route 1
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 57: {//Route 2
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 58: {//Route 13
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 59: {//Route 15
			Intent intent = new Intent(this, wan.group.septa.TrolleyList.class);
			startActivity(intent);
			break;
		}
		case 60: {//Route 34
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 61: {//Route 36
			Intent intent = new Intent(this, wan.group.septa.BusList.class);
		    startActivity(intent);
			break;
		}
		case 62: {//Route 101
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 63: {//Route 102
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 64: {//Route 1
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 65: {//Route 2
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 66: {//Route 13
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 67: {//Route 15
			Intent intent = new Intent(this, wan.group.septa.TrolleyList.class);
			startActivity(intent);
			break;
		}
		case 68: {//Route 34
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 69: {//Route 36
			Intent intent = new Intent(this, wan.group.septa.BusList.class);
		    startActivity(intent);
			break;
		}
		case 70: {//Route 101
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 71: {//Route 102
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 72: {//Route 1
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 73: {//Route 2
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 74: {//Route 13
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 75: {//Route 15
			Intent intent = new Intent(this, wan.group.septa.TrolleyList.class);
			startActivity(intent);
			break;
		}
		case 76: {//Route 34
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 77: {//Route 36
			Intent intent = new Intent(this, wan.group.septa.BusList.class);
		    startActivity(intent);
			break;
		}
		case 78: {//Route 101
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 79: {//Route 102
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 80: {//Route 1
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 81: {//Route 2
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 82: {//Route 13
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 83: {//Route 15
			Intent intent = new Intent(this, wan.group.septa.TrolleyList.class);
			startActivity(intent);
			break;
		}
		case 84: {//Route 34
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 85: {//Route 36
			Intent intent = new Intent(this, wan.group.septa.BusList.class);
		    startActivity(intent);
			break;
		}
		case 86: {//Route 101
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 87: {//Route 102
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 88: {//Route 1
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 89: {//Route 2
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 90: {//Route 13
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 91: {//Route 15
			Intent intent = new Intent(this, wan.group.septa.TrolleyList.class);
			startActivity(intent);
			break;
		}
		case 92: {//Route 34
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 93: {//Route 36
			Intent intent = new Intent(this, wan.group.septa.BusList.class);
		    startActivity(intent);
			break;
		}
		case 94: {//Route 101
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 95: {//Route 102
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 96: {//Route 1
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 97: {//Route 2
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 98: {//Route 13
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 99: {//Route 15
			Intent intent = new Intent(this, wan.group.septa.TrolleyList.class);
			startActivity(intent);
			break;
		}
		case 100: {//Route 34
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 101: {//Route 36
			Intent intent = new Intent(this, wan.group.septa.BusList.class);
		    startActivity(intent);
			break;
		}
		case 102: {//Route 101
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 103: {//Route 102
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 104: {//Route 1
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 105: {//Route 2
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 106: {//Route 13
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 107: {//Route 15
			Intent intent = new Intent(this, wan.group.septa.TrolleyList.class);
			startActivity(intent);
			break;
		}
		case 108: {//Route 34
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 109: {//Route 36
			Intent intent = new Intent(this, wan.group.septa.BusList.class);
		    startActivity(intent);
			break;
		}
		case 110: {//Route 101
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 111: {//Route 102
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 112: {//Route 1
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 113: {//Route 2
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 114: {//Route 13
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 115: {//Route 15
			Intent intent = new Intent(this, wan.group.septa.TrolleyList.class);
			startActivity(intent);
			break;
		}
		case 116: {//Route 34
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 117: {//Route 36
			Intent intent = new Intent(this, wan.group.septa.BusList.class);
		    startActivity(intent);
			break;
		}
		case 118: {//Route 101
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 119: {//Route 102
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		
    }//End switch
        
    }

}
