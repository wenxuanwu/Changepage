package com.example.wuwenxuan.changepage;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class BapToast {
	private static Toast toast = null;
	private static String contextName;

	public static void show(Context context, String message){
		String _contextName=context.getClass().getName();
		if(toast==null||!contextName.equals(_contextName)){
			toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
			toast.setGravity(Gravity.CENTER, 0, 0);
			contextName=_contextName;
		}else{
			toast.setText(message);
		}
		toast.show();
	}
	public static void shortShow(Context context, String message){
		String _contextName=context.getClass().getName();
		if(toast==null||!contextName.equals(_contextName)){
			toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER, 0, 0);
			contextName=_contextName;
		}else{
			toast.setText(message);
		}
		toast.show();
	}
}
