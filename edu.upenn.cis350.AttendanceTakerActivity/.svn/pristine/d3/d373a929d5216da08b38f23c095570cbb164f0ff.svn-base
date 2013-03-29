package edu.upenn.cis350;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Roster of students for a particular activity
 * and buttons to give the user options to add, edit,
 * or delete students from the roster.
 */
public class ViewAllStudents extends Activity{
	
	int id,name,school,site,program,comment,phone,grade,address,emrname,emrrel;
	private List<StudentObject> listOfItems =new ArrayList<StudentObject>();;
	private ArrayList<String> ids;
	private String activity;
	private String roster_title;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_roster);
		Parse.initialize(this,LoginHandler.link1 ,LoginHandler.link2);
		if(isOnline())
        	createList();
        else
        	createOfflineList();
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
    
    public void onAddStudentClick(View view) {
    	Intent i = new Intent(this, AddNewStudentActivity.class);

    	startActivityForResult(i, AttendanceTakerActivity.ACTIVITY_AddNewStudent);
    }
    
   
    public void createOfflineList()
    {
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
		
		listOfItems.clear();
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
			listOfItems.add(new StudentObject(tokens[id],gradeValue,tokens[name],tokens[phone],tokens[address],tokens[school],tokens[site],tokens[program],tokens[comment],tokens[emrname],tokens[emrrel]));
			/*for(int i=0;i<tokens.length;i++)
				System.out.print(tokens[i]+"     ");
			System.out.println();*/
		}
		
		}
		br.close();
		showList();
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

    
    public void createList(){
    	System.out.println("Online");
    	   System.out.println("Before getStudentForAct");
 	      ParseQuery query1 = new ParseQuery("Student");
 	     listOfItems.clear();
 	    	query1.findInBackground(new FindCallback() {
 			     public void done(List<ParseObject> queryList, ParseException e) {
 			         if (e == null) {
 			        	 System.out.println("Done No Error");
 			        	 System.out.println("The size of querylsit is "+queryList.size());
 			        	 for(ParseObject student : queryList){
 				     			System.out.println("5"); 
 				     			listOfItems.add(new StudentObject(student.getNumber("ID").toString(), student.getNumber("Parse_1112GradeLevel").intValue(),
 							student.getString("Name"),student.getString("phoneNumber"),student.getString("Address"), student.getString("School"), student.getString("Site"),
 							student.getString("Program"),student.getString("Comment"),
 							student.getString("emergencyContactName"), student.getString("emergencyContactRelationship")));
 				     	}
 				        System.out.println("AGAIN!!The size of listOfItems is "+listOfItems.size());
 				   		
 						Collections.sort(listOfItems, new Comparator<StudentObject>(){
 					   	     public int compare(StudentObject o1, StudentObject o2){
 					   	         return o1.getName().compareToIgnoreCase(o2.getName());
 					   	     }
 					   	});
 						System.out.println("In populateR before showR");
 						showList();
 						System.out.println("In populateR after showR");
 			         } else {
 			             //objectRetrievalFailed();
 			        	 System.out.println("Error");
 			     }
 			         
 			     }
 			     
 			    
 			 });
 		
 		  System.out.println("After getStudentForAct");
 	
    	
    }
    public void showList(){
    	
    	  ListView list = (ListView) findViewById(R.id.StudentList);
          
          //handles null case
    		if(list == null){
    			LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    			list = (ListView) inflater.inflate(R.id.RosterList, null);
    		}
          
          list.setClickable(true);
      	AddStudentAdapter adapter = new AddStudentAdapter(this, listOfItems);
          
          //click listener for each list item
          list.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                int position, long id) {
          	  // get info for the student to be displayed
          	  StudentObject student = listOfItems.get(position);
          	  String name = student.getName();
          	  String idNumber = student.getStudentid();
          	  int grade = student.getGradeLevel();
          	  String phone = student.getPhone();
          	  String address = student.getAddress();
          	  String school = student.getSchool();
          	  String program = student.getProgram();
          	  String comment = student.getComment();
          	  String site = student.getSite();
          	  String contactName = student.getContactName();
          	  String contactType = student.getContactType();
          	  
          	  Intent i = new Intent(view.getContext(), ProfileActivity.class);
          	  
          	  // put info for the student in intent
          	  // defect log change "Name" -> "name"
          	  i.putExtra("name", name);
          	  i.putExtra("idNumber", idNumber);
          	  i.putExtra("grade", grade+"");
          	  i.putExtra("phone", phone);
          	  i.putExtra("address", address);
          	  i.putExtra("school", school);
          	  i.putExtra("site", site);
          	  i.putExtra("program", program);
          	  i.putExtra("comment", comment);
          	  i.putExtra("contactName", contactName);
          	  i.putExtra("contactType", contactType);
          	  startActivityForResult(i, AttendanceTakerActivity.ACTIVITY_ProfileActivity);
            }
          });
          list.setAdapter(adapter);       
    	
    	
    }
    
    public void onRemoveStudentClick(View view) {
    	Intent i = new Intent(this, RemoveStudentMainActivity.class);
    	ids = new ArrayList<String>();
    	for(StudentObject s : listOfItems) {
    		ids.add(s.getStudentid());
    	}
    	i.putStringArrayListExtra("ids", ids);
//    	i.putExtra("activity_name", roster_title);
//    	i.putExtra("activity", activity);
    	System.out.println("Click Remove");
    	startActivityForResult(i, AttendanceTakerActivity.ACTIVITY_RemoveStudentMian);
    	System.out.println("After Send to RemoveMain");
    }
    
    public void onRefreshClick(View view) {
    	createList();
    }

    public void refreshIntent(){}
    
    
    
}