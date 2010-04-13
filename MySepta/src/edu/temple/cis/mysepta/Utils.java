/**
 * 
 */
package edu.temple.cis.mysepta;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * @author Yu Liang
 *
 */
public class Utils {

	public static void showMessage(Context ctx, String msg) {
		AlertDialog alert = new AlertDialog.Builder(ctx).create();
		alert.setMessage(msg);
		alert.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				return;
			}
		});
		alert.show();
	}
}
