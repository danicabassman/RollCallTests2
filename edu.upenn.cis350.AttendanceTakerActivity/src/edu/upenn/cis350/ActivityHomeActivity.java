package edu.upenn.cis350;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

/**
 * Represents home screen for when an activity is clicked;
 * gives the user a choice between editing the roster for
 * that activity and taking attendance for that activity
 */
public class ActivityHomeActivity extends Activity{
	private List<ActivityObject> listOfItems=new ArrayList<ActivityObject>();
	private List<StudentObject> studentList = new ArrayList<StudentObject>();
	private static final int RENAME_ACTIVITY = 0;
	private String activityName;
	private String className;
	private ArrayList<String> ids=new ArrayList<String>();


	 @Override
	 public void onCreate(Bundle savedInstanceState) {
		 setContentView(R.layout.activity_home);
		 TextView registration_name;
		 registration_name = (TextView) findViewById (R.id.titlething);
		 
	
	     
		 super.onCreate(savedInstanceState);
		 
	     Bundle extras = getIntent().getExtras();
	     activityName = extras.getString("name");
	     className = extras.getString("className"); 
	     registration_name.setText(activityName);
	      System.out.println("Home before query");
	      if(isOnline())    	  
	      {  
	    	ParseQuery query = new ParseQuery("Activity");
	    	listOfItems.clear();
	    	query.findInBackground(new FindCallback() {
			     public void done(List<ParseObject> queryList, ParseException e) {
			         if (e == null) {
			        	 System.out.println("HOME!!!The size of querylsit is "+queryList.size());
			        	 for(ParseObject p : queryList){
				     			System.out.println("5");
				     			listOfItems.add(new ActivityObject(p.getObjectId(), p.getString("DisplayName"),
				     					p.getString("ClassName"), p.getUpdatedAt(), p.getInt("count")));
				     	}
				        System.out.println("HOME!!!AGAIN!!The size of listOfItems is "+listOfItems.size());
				       
			         } else {
			             //objectRetrievalFailed();
			        	 System.out.println("Error");
			         		}
			     }
			     
			 });
	     
/*old veriosn getStudentsForActivity
	     System.out.println("Before getStudentForAct");
	     List<StudentObject> list = ParseHandler.getStudentsForActivity(className);
	     ids = new ArrayList<String>();
	     for(StudentObject s : list) {
	    	 ids.add(s.getStudentid());
	     }
*/	     
	     
	      System.out.println("Before getStudentForAct");
	      ParseQuery query1 = new ParseQuery("Student");
	    	studentList.clear();
	    	ids.clear();
	    	query1.whereEqualTo(className, 1);
	    	query1.findInBackground(new FindCallback() {
			     public void done(List<ParseObject> queryList, ParseException e) {
			         if (e == null) {

			        	 System.out.println("Done No Error");
			        	 System.out.println("The size of querylsit is "+queryList.size());
			        	 for(ParseObject student : queryList){
				     			System.out.println("5"); 
				     		studentList.add(new StudentObject(student.getNumber("ID").toString(), student.getNumber("Parse_1112GradeLevel").intValue(),
							student.getString("Name"),student.getString("phoneNumber"),student.getString("Address"), student.getString("School"), student.getString("Site"),
							student.getString("Program"),student.getString("Comment"),
							student.getString("emergencyContactName"), student.getString("emergencyContactRelationship")));
				     	}
				        System.out.println("AGAIN!!The size of studentList is "+studentList.size());
				   			
				          for(StudentObject s : studentList) {
		    	 				ids.add(s.getStudentid());
		     				}

			         } else {
			             //objectRetrievalFailed();
			        	 System.out.println("Error");
			     }
			         
			     }
			     
			    
			 });
		      System.out.println("After getStudentForAct");
	      }
	      else
	      {
	    	  createOfflineActivityList();
	    	  createOfflineStudentList();   	  
	      }
	      
	     
	 }

	 public void onViewRosterClick(View view) {
        Intent i = new Intent(view.getContext(), StudentRosterActivity.class);
  	  	i.putExtra("name", activityName);
  	  	i.putExtra("className", className);
  	  	i.putStringArrayListExtra("ids", ids);
     	startActivityForResult(i, AttendanceTakerActivity.ACTIVITY_StudentRoster);
	 }
	 
	 public void onTakeAttendanceClick(View view) {
        Intent i = new Intent(view.getContext(), EditRosterActivity.class);
  	  	i.putExtra("name", activityName);
  	  	i.putExtra("className", className);
  	  	i.putStringArrayListExtra("ids", ids);
     	startActivityForResult(i, AttendanceTakerActivity.ACTIVITY_Roster);
	 }
	 
	 public void onRenameActivityClick (View view) {
		 showDialog(RENAME_ACTIVITY);
	 }
	 
	 protected Dialog onCreateDialog(int id) {
	    	if (id == RENAME_ACTIVITY) {
		    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
	            builder.setMessage("Enter new activity name");
	            LinearLayout ll = new LinearLayout(this);
	            ll.setOrientation(1);
	            final EditText input = new EditText(this);
		        ll.addView(input);
		        builder.setView(ll);
		    	builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
		    		public void onClick(DialogInterface dialog, int id) {
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
		    	        	Log.v(activity_string, "this is the new name");
		    	            ParseHandler.renameActivity(className, activity_string);
		    	            Toast.makeText(getApplicationContext(), "Activity name change successful, please click back and refresh to update the names",
		    						Toast.LENGTH_LONG).show();
//		    	        	ParseHandler.addActivity(activity_string);
		    	        	dialog.cancel();
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
		 
		 public void createOfflineActivityList()
		    {
			 	int objid=0,display=0,classname=0,count=0,upd=0;
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
		 
		 public void createOfflineStudentList()
		    {
			 int id=0,name=0,school=0,site=0,program=0,comment=0,phone=0,grade=0,address=0,emrname=0,emrrel=0;	
			 System.out.println("Offline");
		    	String[] columns;
		    	String fileName = "/mnt/sdcard/Students.csv"; 	
		    	try 
				{
				BufferedReader br = new BufferedReader(new FileReader(fileName));
				if((fileName = br.readLine())!=null)
				{
					columns = fileName.split("\",\"");
				for(int i=0;i<columns.length;i++)
				{
					if(columns[i].compareTo("ID")==0)
						id=i;
					else if(columns[i].compareTo("Parse_1112GradeLevel")==0)
						grade=i;
					else if(columns[i].compareTo("Name")==0)
						name=i;
					else if(columns[i].compareTo("phoneNumber")==0)
						phone=i;
					else if(columns[i].compareTo("Address")==0)
						address=i;
					else if(columns[i].compareTo("School")==0)
						school=i;
					else if(columns[i].compareTo("Site")==0)
						site=i;
					else if(columns[i].compareTo("Program")==0)
						program=i;
					else if(columns[i].compareTo("Comment")==0)
						comment=i;
					else if(columns[i].compareTo("emergencyContactName")==0)
						emrname=i;
					else if(columns[i].compareTo( "emergencyContactRelationship")==0)
						emrrel=i;
					}
				
				studentList.clear();
				while((fileName = br.readLine())!=null)
				{
					String[] tokens = fileName.split("\",\"");
					tokens[0]=tokens[0].replace('\"', ' ');
					tokens[0]=tokens[0].trim();
					int gradeValue;
					try
					{
						gradeValue=Integer.parseInt(tokens[grade]);
					}
					catch(NumberFormatException nfe)
					{
						gradeValue=0;
					}
					studentList.add(new StudentObject(tokens[id],gradeValue,tokens[name],tokens[phone],tokens[address],tokens[school],tokens[site],tokens[program],tokens[comment],tokens[emrname],tokens[emrrel]));
					/*for(int i=0;i<tokens.length;i++)
						System.out.print(tokens[i]+"     ");
					System.out.println();*/
				}
				
				}
				br.close();
				for(StudentObject s : studentList) {
	 				ids.add(s.getStudentid());
 				}
				
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
		 
		 
		 
		
	}
	 
