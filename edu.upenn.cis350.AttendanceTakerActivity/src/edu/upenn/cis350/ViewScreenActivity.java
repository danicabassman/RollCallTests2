package edu.upenn.cis350;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.parse.ParseUser;

public class ViewScreenActivity extends Activity{
	
	public void onCreate(Bundle savedInstanceState) {
		System.out.println("Go To ViewScreen"); 
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_screen); 
        //Add code here to check if sync functionality should be available.
        System.out.println(isOnline());
        if(!isOnline())
        {
        	Button myButton = (Button) findViewById(R.id.Sync);
        	myButton.setVisibility(Button.INVISIBLE);
        	myButton = (Button) findViewById(R.id.email);
        	myButton.setVisibility(Button.INVISIBLE);
        }
        

	}
	
	 public void onViewActivitiesClick(View view) {
		 System.out.println("In Click");
	    	Intent i = new Intent(this, ActivitiesListActivity.class);
	    	startActivityForResult(i, AttendanceTakerActivity.ACTIVITY_ActivitiesList);
		 
	 }
	
	 public void onViewStudentsClick(View view) {
		 System.out.println("In Click");
	    	Intent i = new Intent(this, ViewAllStudents.class);
	    	startActivityForResult(i, AttendanceTakerActivity.ACTIVITY_ViewStudents);
	 }
	 
	 public void onEmailClick(View view) {
//		 StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//		 StrictMode.setThreadPolicy(policy); 
//		 try {   
//                GMailSender sender = new GMailSender("rollcall.developers@gmail.com","nettercenterapp");
//                sender.sendMail("Exported File",   
//                        "This worked!",   
//                        "rollcall.developers@gmail.com",   
//                       // "arvindisgr8@gmail.com,nirmal.utwani@gmail.com,ankit.khandelwal679@gmail.com"); 
//                        "rollcall.developers@gmail.com");
//                System.out.println("In try");
//                Toast.makeText(getApplicationContext(), 
//                        "Email sent!", Toast.LENGTH_LONG).show();
//            } catch (Exception e) {
//            	System.out.println("In catch");
//                System.out.println(e.getMessage());   
//            } 
		 
		 	Intent i = new Intent(this, SendEmailActivity.class);
	    	startActivityForResult(i, AttendanceTakerActivity.ACTIVITY_SendEmailActivity);
	 }
	    	
	//Added by Arvind 10/22/2012 - Sync button
	 public void onSyncClick(View view) {
		 System.out.println("In Click");
		 Intent i = new Intent(this, SyncActivity.class);
	     startActivityForResult(i, AttendanceTakerActivity.ACTIVITY_ViewStudents);
	 }
	 //Added by Arvind 11/14/2012 - Back button error
	
	 @Override 
	public void onBackPressed()
	{
		ParseUser.logOut();
		/*Calendar c = Calendar.getInstance(); 
		int now = c.get(Calendar.SECOND);
		for (int i=now;i<now+5;i++)*/
		finish();
	}
	 
    
	 
	//Added by Arvind 10/22/2012 - To Check Connectivity
	 public boolean isOnline() { 
		 boolean haveConnectedWifi = false;
		 boolean haveConnectedMobile = false;
 	     ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		 NetworkInfo[] netInfo = cm.getAllNetworkInfo();
		    for (NetworkInfo ni : netInfo) {
		        if (ni.getTypeName().equalsIgnoreCase("WIFI"))
		            if (ni.isConnected())
		                haveConnectedWifi = true;
		        if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
		            if (ni.isConnected())
		                haveConnectedMobile = true;
		    }
		    return haveConnectedWifi || haveConnectedMobile;
		} 
	

	 
}
