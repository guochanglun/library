package com.gcl.util;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetState {
	
	private NetState(){}
	
	private static Context context;
	
	public static NetState with(Context _context) {
		context = _context;
		return new NetState();
	}
	
	public boolean detectNetState() {
		ConnectivityManager connectivityManager = 
				(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		
		NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();
		for(NetworkInfo info : networkInfo) {
			if (info.getState() == NetworkInfo.State.CONNECTED){
			      return true;
			}
		}
		return false;
	}
}
