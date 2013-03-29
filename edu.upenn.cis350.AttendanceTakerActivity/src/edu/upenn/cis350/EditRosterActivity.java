package edu.upenn.cis350;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.parse.FindCallback;
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
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.*;
import android.widget.AdapterView;

/**
 * Represents the roster in which the user can take attendance
 * for a particular activity.
 *
 */


public class EditRosterActivity extends Activity{
	
	//changed variable name to list of students
	private List<StudentObject> listOfStudents= new ArrayList<StudentObject>();
	private List<StudentObject> submitStudents= new ArrayList<StudentObject>();
	private List<StudentObject> actlist=new ArrayList<StudentObject>();
	private static final int ADD_STUDENT = 0;
	private String activityName;
	private String className;
	private ActivityObject currentActivity;
	private static String date = Utils.getTodaysDate();
	private static String time = Utils.getCurrentTime();
	public RosterAdapter adapter;
	
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.roster);
   
        Bundle extras = getIntent().getExtras();
        activityName = extras.getString("name");
        className = extras.getString("className");
        TextView activity_name;
        activity_name = (TextView) findViewById (R.id.roster_title);
	    activity_name.setText(activityName + " " + "Students");
	    
	    if(isOnline())
	    	currentActivity = ParseHandler.getActivityByName(className);
	    else
	    {
	    	
	    }
        System.out.println("On Create currentActivity"+currentActivity);
        createRoster(); 
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
 	 
    public void createRoster() {
    	if(isOnline())
    		populateRoster();
    	else
    		populateRosterOffline();
    	System.out.println("After populateRoster");
    	
    }
    
    // put the display here
    public void showRoster(){
    	
    	   ListView list = (ListView) findViewById(R.id.RosterList);
           
           //handles null case
     		if(list == null){
     			LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
     			list = (ListView) inflater.inflate(R.id.RosterList, null);
     		}
           
           //list.setClickable(true);

         adapter = new RosterAdapter(this, listOfStudents);
           
           //click listener for each list item
//           list.setOnItemClickListener(new OnItemClickListener() {
//             public void onItemClick(AdapterView<?> parent, View view,
//                 int position, long id) {
//           	  // get info for the student to be displayed
//           	  StudentObject student = listOfStudents.get(position);
//           	  String name = student.getName();
//           	  String idNumber = student.getStudentid();
//           	  int grade = student.getGradeLevel();
//           	  String phone = student.getPhone();
//           	  String address = student.getAddress();
//           	  String school = student.getSchool();
//           	  String site = student.getSite();
//           	  String program = student.getProgram();
//           	  String comment = student.getComment();
//           	  String contactName = student.getContactName();
//           	  String contactType = student.getContactType();
//           	  
//           	  Intent i = new Intent(view.getContext(), ProfileActivity.class);
//           	  
//           	  // put info for the student in intent
//           	  // defect log change "Name" -> "name"
//           	  i.putExtra("name", name);
//           	  i.putExtra("idNumber", idNumber);
//           	  i.putExtra("grade", grade+"");
//           	  i.putExtra("phone", phone);
//           	  i.putExtra("address", address);
//           	  i.putExtra("school", school);
//           	  i.putExtra("site", site);
//           	  i.putExtra("program", program);
//           	  i.putExtra("comment", comment);
//           	  i.putExtra("contactName", contactName);
//           	  i.putExtra("contactType", contactType);
//           	  startActivityForResult(i, AttendanceTakerActivity.ACTIVITY_ProfileActivity);
//             }
//           });
             list.setAdapter(adapter);
     		//list.setTextFilterEnabled(true);
    		//list.setChoiceMode(list.CHOICE_MODE_MULTIPLE);
     		
    }
    
    
 // selects all students
 	public void onSelectAllClick(View v) {
 		adapter.setAllChecked();
 		
 	}

 	// deselects all students
 	public void onDeselectAllClick(View v) {
 		adapter.setAllUnChecked();
 	}
    
    
    public List<StudentObject> populateRosterOffline(){
    	int id=0,name=0,school=0,site=0,program=0,comment=0,phone=0,grade=0,address=0,emrname=0,emrrel=0;
    	System.out.println("Offline");
    	String[] columns;
    	String fileName = "/mnt/sdcard/"+ className +".csv"; 	
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
		
		listOfStudents.clear();
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
			listOfStudents.add(new StudentObject(tokens[id],gradeValue,tokens[name],tokens[phone],tokens[address],tokens[school],tokens[site],tokens[program],tokens[comment],tokens[emrname],tokens[emrrel]));
			/*for(int i=0;i<tokens.length;i++)
				System.out.print(tokens[i]+"     ");
			System.out.println();*/
		}
		
		}
		br.close();
		showRoster();
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
    	return listOfStudents;
    }
    
    /** creates and populates roster **/
	public List<StudentObject> populateRoster(){
		
		//= ParseHandler.getStudentsForActivity(className);
		
	      System.out.println("Before getStudentForAct");
	      ParseQuery query1 = new ParseQuery("Student");
	      listOfStudents.clear();
	    	query1.whereEqualTo(className, 1);
	    	query1.findInBackground(new FindCallback() {
			     public void done(List<ParseObject> queryList, ParseException e) {
			         if (e == null) {
			        	 System.out.println("Done No Error");
			        	 System.out.println("The size of querylsit is "+queryList.size());
			        	 for(ParseObject student : queryList){
				     			System.out.println("5"); 
				     			listOfStudents.add(new StudentObject(student.getNumber("ID").toString(), student.getNumber("Parse_1112GradeLevel").intValue(),
							student.getString("Name"),student.getString("phoneNumber"),student.getString("Address"), student.getString("School"), student.getString("Site"),
							student.getString("Program"),student.getString("Comment"),
							student.getString("emergencyContactName"), student.getString("emergencyContactRelationship")));
				     	}
				        System.out.println("AGAIN!!The size of actlist is "+listOfStudents.size());
				   		
						Collections.sort(listOfStudents, new Comparator<StudentObject>(){
					   	     public int compare(StudentObject o1, StudentObject o2){
					   	         return o1.getName().compareToIgnoreCase(o2.getName());
					   	     }
					   	});
						System.out.println("In populateR before showR");
						showRoster();
						System.out.println("In populateR after showR");
			         } else {
			             //objectRetrievalFailed();
			        	 System.out.println("Error");
			     }
			         
			     }
			     
			    
			 });
		
		  System.out.println("After getStudentForAct");
	
		return listOfStudents;
	}
    
	/** handles click for Back button **/
	
	
	public void onSubmitButtonClick(View view) {
		submitStudents=adapter.getBox();
		if(listOfStudents == null){
			Toast.makeText(getApplicationContext(),
					"No roster student.", Toast.LENGTH_SHORT)
					.show();

		}
		else if(submitStudents.size()==0){
			Toast.makeText(getApplicationContext(),
					"Please select some students first.", Toast.LENGTH_SHORT)
					.show();

		}
		else{
			
			
			System.out.println(" CurrentActivity"+currentActivity);
			PopupMenu popup = new PopupMenu(this, view);
			popup.getMenuInflater().inflate(R.menu.studentselectionmenu,
					popup.getMenu());
			popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
				public boolean onMenuItemClick(MenuItem item) {
					switch (item.getItemId()) {
					case R.id.check_in_student:
						if(isOnline())
						//onCheckInStudents();
					{
						ParseHandler.submitActivityAttendance_In(currentActivity, submitStudents);
						System.out.println("CHECK_IN_STUDENT");
					}
						else submitOffline_In();
						return true;
					case R.id.check_out_student:
						//onCheckOutStudents();
						if(isOnline())
					{
						ParseHandler.submitActivityAttendance_Out(currentActivity, submitStudents);
						System.out.println("CHECK_OUT_STUDENT");
						
					}
						else submitOffline_Out();
						return true;
//					case R.id.leave_student_comment:
//						//onLeaveComment();
//						System.out.println("LEAVE COMMENT");
//						return true;
					case R.id.mark_absent_student:
						//onMarkAbsentStudents();
						if(isOnline())
					{
						ParseHandler.submitActivityAttendance_ab(currentActivity, submitStudents);
						System.out.println("CHECK_ABSENT_STUDENT");
						
					}
						else 
							submitOffline_ab();
						return true;
					default:
						Toast.makeText(getApplicationContext(),
								"Not Yet Implemented", Toast.LENGTH_SHORT)
								.show();
						return true;
					}
				}
			});
			popup.show();
		} 
			
			
			
			

		}
	

    @Override
	protected Dialog onCreateDialog(int id) {
    	if (id == ADD_STUDENT) {
	    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Enter student name");
            LinearLayout ll = new LinearLayout(this);
            ll.setOrientation(1); // suggestion was to change 1 to vertical, not sure if the enum is accepted
            // Edit text is final - Defect Log changes
            final EditText input = new EditText(this);
	        ll.addView(input);
	        builder.setView(ll);
	    	builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
	    		public void onClick(DialogInterface dialog, int id) {
	    			Editable activity = input.getText();
	    	        String activity_string = activity.toString();
	    	        boolean activity_already_exists = false;
	    	        
	    	        // changed to a for each lopp and added the break statement
	    	        for(StudentObject s : listOfStudents) {
	    	        	if(s.getName().equals(activity_string)) {
	    	        		activity_already_exists = true;
	    	        		break;
	    	        	}
	    	        }
	    	        
	    	        // changed the instantiation of the toast
	    	        
	    	        if(activity_already_exists) {
	    	        	Toast.makeText(getApplicationContext(), "Activity already exists",
	    						Toast.LENGTH_SHORT).show();
	    	        }
	    	        
	    	        else {
	    	        	ParseHandler.addStudent(new StudentObject(activity_string));
	    	        	dialog.cancel();
	    	        	createRoster();
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
    	else return null;
    }

    public ActivityObject getActivityByNameOffline(String class_name)
    {
    	ActivityObject a = new ActivityObject();
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
			if(tokens[classname].compareTo(class_name)==0)
				a =new ActivityObject(tokens[objid],tokens[display],tokens[classname],d,countValue);
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
    	return a;
		
	}
    
    public void submitOffline_In()
    {
    	System.out.println("Offline");
    	String fileName = className +".csv"; 	
    	File f = new File("/mnt/sdcard/",fileName);
		f.delete();
		try
		{
			String record,stat_str;
			StudentObject stud;
			FileOutputStream fos = new FileOutputStream(f);
			fos.write(new String("\"ID\",\"Parse_1112GradeLevel\",\"Name\",\"phoneNumber\",\"Address\",\"School\",\"Site\",\"Program\",\"Comment\",\"emergencyContactName\",\"emergencyContactRelationship\",\"Attendance\"\n").getBytes());
			for(int i=0;i<listOfStudents.size();i++)
			{
				stud = listOfStudents.get(i);
				stat_str = new String("In"+ "_"+Utils.getTodaysDate()+"="+Utils.getCurrentTime()); 
				record = new String("\""+stud.getStudentid()+"\",\""
						+stud.getGradeLevel()+"\",\""
						+stud.getName()+"\",\""
						+stud.getPhone()+"\",\""
						+stud.getAddress()+"\",\""
						+stud.getSchool()+"\",\""
						+stud.getSite()+"\",\""
						+stud.getProgram()+"\",\""
						+stud.getComments()+"\",\""
						+stud.getContactName()+"\",\""
						+stud.getContactType()+"\",\""
						+stat_str+"\"\n");
				fos.write(record.getBytes());
				System.out.println(i);
			}
			updateFileList();
		}
		 catch(Exception ex)
    	 {
    		 System.out.println("Exception"+ex.toString());
    		 ex.printStackTrace();
    	 }
    }
    
    
    public void submitOffline_Out()
    {
    	System.out.println("Offline");
    	String fileName = className +".csv"; 	
    	File f = new File("/mnt/sdcard/",fileName);
		f.delete();
		try
		{
			String record,stat_str;
			StudentObject stud;
			FileOutputStream fos = new FileOutputStream(f);
			fos.write(new String("\"ID\",\"Parse_1112GradeLevel\",\"Name\",\"phoneNumber\",\"Address\",\"School\",\"Site\",\"Program\",\"Comment\",\"emergencyContactName\",\"emergencyContactRelationship\",\"Attendance\"\n").getBytes());
			for(int i=0;i<listOfStudents.size();i++)
			{
				stud = listOfStudents.get(i);
				stat_str = new String("Out"+ "_"+Utils.getTodaysDate()+"="+Utils.getCurrentTime()); 
				record = new String("\""+stud.getStudentid()+"\",\""
						+stud.getGradeLevel()+"\",\""
						+stud.getName()+"\",\""
						+stud.getPhone()+"\",\""
						+stud.getAddress()+"\",\""
						+stud.getSchool()+"\",\""
						+stud.getSite()+"\",\""
						+stud.getProgram()+"\",\""
						+stud.getComments()+"\",\""
						+stud.getContactName()+"\",\""
						+stud.getContactType()+"\",\""
						+stat_str+"\"\n");
				fos.write(record.getBytes());
				System.out.println(i);
			}
			updateFileList();
		}
		 catch(Exception ex)
    	 {
    		 System.out.println("Exception"+ex.toString());
    		 ex.printStackTrace();
    	 }
    }
    
    public void submitOffline_ab()
    {
    	System.out.println("Offline");
    	String fileName = className +".csv"; 	
    	File f = new File("/mnt/sdcard/",fileName);
		f.delete();
		try
		{
			String record,stat_str;
			StudentObject stud;
			FileOutputStream fos = new FileOutputStream(f);
			fos.write(new String("\"ID\",\"Parse_1112GradeLevel\",\"Name\",\"phoneNumber\",\"Address\",\"School\",\"Site\",\"Program\",\"Comment\",\"emergencyContactName\",\"emergencyContactRelationship\",\"Attendance\"\n").getBytes());
			for(int i=0;i<listOfStudents.size();i++)
			{
				stud = listOfStudents.get(i);
				stat_str = new String("Absent"+ "_"+Utils.getTodaysDate()+"="+Utils.getCurrentTime()); 
				record = new String("\""+stud.getStudentid()+"\",\""
						+stud.getGradeLevel()+"\",\""
						+stud.getName()+"\",\""
						+stud.getPhone()+"\",\""
						+stud.getAddress()+"\",\""
						+stud.getSchool()+"\",\""
						+stud.getSite()+"\",\""
						+stud.getProgram()+"\",\""
						+stud.getComments()+"\",\""
						+stud.getContactName()+"\",\""
						+stud.getContactType()+"\",\""
						+stat_str+"\"\n");
				fos.write(record.getBytes());
				System.out.println(i);
			}
			updateFileList();
		}
		 catch(Exception ex)
    	 {
    		 System.out.println("Exception"+ex.toString());
    		 ex.printStackTrace();
    	 }
    }
    
    public void updateFileList()
    {
    	System.out.println("Offline");	
    	File f = new File("/mnt/sdcard/","changes.csv");
		try
		{
			FileOutputStream fos = new FileOutputStream(f, true);
			fos.write(className.getBytes());
			fos.write(",".getBytes());
			fos.close();
		}
		catch(Exception ex)
   	 	{
   		 System.out.println("Exception"+ex.toString());
   		 ex.printStackTrace();
   	 	}
    }


}
