package edu.upenn.cis350;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;



import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class ActivitiesListActivity extends Activity {

	private List<ActivityObject> listOfItems=new ArrayList<ActivityObject>();
	private static final int ADD_ACTIVITY = 0;
	int objid,display,classname,count,upd;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activities_list);
     
		Parse.initialize(this,LoginHandler.link1 ,LoginHandler.link2);
        if(isOnline())
        	createList();
        else
        	createOfflineList();
    }
    
    public void createList() {
    	System.out.println("In createList");
    	
    	
    	ParseQuery query = new ParseQuery("Activity");
    	listOfItems.clear();
    	query.findInBackground(new FindCallback() {
		     public void done(List<ParseObject> queryList, ParseException e) {
		         if (e == null) {
		        	
		        	 System.out.println("Done No Error");
		        	 System.out.println("The size of querylsit is "+queryList.size());
		        	 for(ParseObject p : queryList){
			     			System.out.println("5");
			     			listOfItems.add(new ActivityObject(p.getObjectId(), p.getString("DisplayName"),
			     					p.getString("ClassName"), p.getUpdatedAt(), p.getInt("count")));
			     	}
			        System.out.println("AGAIN!!The size of listOfItems is "+listOfItems.size());
			        System.out.println("Before ShowaActivity!");
			        showActivity();
			       
		         } else {
		           
		        	 System.out.println("Error");
		     }
		         
		     }
		     
		    
		 });
    	
    }
    
    
    public void createOfflineList()
    {
    	System.out.println("Offline");
    	String[] columns;
    	String fileName = "/mnt/sdcard/Activity.csv"; 	
    	try 
		{
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		if((fileName = br.readLine())!=null)
		{
			columns = fileName.split("\",\"");
		for(int i=0;i<columns.length;i++)
		{
			if(columns[i].compareTo("DisplayName")==0)
				display=i;
			else if(columns[i].compareTo("count")==0)
				count=i;
			else if(columns[i].compareTo("ClassName")==0)
				classname=i;
			else if(columns[i].compareTo("ObjectId")==0)
				objid=i;
			else if(columns[i].compareTo("UpdatedAt")==0)
				upd=i;
			}
		
		listOfItems.clear();
		while((fileName = br.readLine())!=null)
		{
			String[] tokens = fileName.split("\",\"");
			tokens[0]=tokens[0].replace('\"', ' ');
			tokens[0]=tokens[0].trim();
			int countValue=0;
			Date d =new Date(System.currentTimeMillis());
			try
			{
				countValue=Integer.parseInt(tokens[count]);
				d= Date.valueOf(tokens[upd]);
			}
			catch(NumberFormatException nfe)
			{
				countValue=0;
			}
			catch(IllegalArgumentException ie)
			{
				d= new Date(System.currentTimeMillis());
			}
			listOfItems.add(new ActivityObject(tokens[objid],tokens[display],tokens[classname],d,countValue));
			/*for(int i=0;i<tokens.length;i++)
				System.out.print(tokens[i]+"     ");
			System.out.println();*/
		}
		
		}
		br.close();
		showActivity();
		}
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
    
    
    public void showActivity(){
    	
    	 ActivitiesAdapter adapter = new ActivitiesAdapter(this, listOfItems);
	        
	        ListView list = (ListView) findViewById(R.id.ActivitiesList);
	        list.setVisibility(View.GONE);
	        list.setClickable(true);
	        //click listener for each list item - on click, goes to Roster page
	        System.out.println("Befor OnClick");
	        list.setOnItemClickListener(new OnItemClickListener() {
	          public void onItemClick(AdapterView<?> parent, View view,
	              int position, long id) {  
	        	String name = listOfItems.get(position).getName();
	        	String className = listOfItems.get(position).getClassName();

				Log.v("activity:", className);
				System.out.println("ClassName"+className);
				System.out.println("Name"+name);
				if(isOnline())
					ParseHandler.increaseActivityCount(className);
	        	
	        	System.out.println("1");
	            Intent i = new Intent(view.getContext(), ActivityHomeActivity.class);
	            System.out.println("2");
	            i.putExtra("name", name);
	      	  	i.putExtra("className", className);
	      	  System.out.println("3");
	        	startActivityForResult(i, AttendanceTakerActivity.ACTIVITY_ActivityHome);
	        	System.out.println("After send to Home");
	          }
	        });
	        
	        list.setAdapter(adapter);
	        
	        adapter.notifyDataSetChanged(); 
	        list.setVisibility(View.VISIBLE);
    	
    	
    	
    }

    public void onAddActivityClick(View view){
    	showDialog(ADD_ACTIVITY);
    }
    
    public void onRefreshClick(View view) {
    	createList();
    }
    
    @Override
	protected Dialog onCreateDialog(int id) {
    	if (id == ADD_ACTIVITY) {
	    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Enter activity name");
            LinearLayout ll = new LinearLayout(this);
            ll.setOrientation(1);
            final EditText input = new EditText(this);
	        ll.addView(input);
	        builder.setView(ll);
	    	builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
	    		public void onClick(DialogInterface dialog, int id) {
	    			System.out.println("In ADD on Click");
	    			Editable activity = input.getText();
	    	        String activity_string = activity.toString();
	    	        if (activity_string.length() == 0) {
	    				Toast.makeText(getApplicationContext(), "A required field is blank",
	    						Toast.LENGTH_SHORT).show();
	    				        return;
	    			}
	    	        boolean activity_already_exists = false;
	    	        
	    	        // changed to a for each loop - Defect log
	    	        for(ActivityObject a : listOfItems) {
	    	        	System.out.println("In loop !");
	    	        	if(a.getName().equals(activity_string)) {
	    	        		activity_already_exists = true;
	    	        		// added the break for the defect log to improve speed
	    	        		break;
	    	        	}
	    	        }
	  //changed the toast format - Defect Log       
	    	        if(activity_already_exists) {
	    	        	Toast.makeText(getApplicationContext(), "Activity already exists",
	    						Toast.LENGTH_SHORT).show();
	    	        }
	    	        else {
	    	        	ParseHandler.addActivity(activity_string);
	    	        	dialog.cancel();
	    	        	createList();
	    	        }
	    	    }
	    	 });
	    	builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	    		public void onClick(DialogInterface dialog, int id) {
	    			dialog.cancel();
	    		}
	    	});
	    	return builder.create();
    	}
    	 return null; //removed else - defect log fix
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