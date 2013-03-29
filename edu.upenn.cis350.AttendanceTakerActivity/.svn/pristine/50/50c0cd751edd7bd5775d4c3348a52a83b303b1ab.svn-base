package edu.upenn.cis350;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

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
public class StudentRosterActivity extends Activity{
	
	private List<StudentObject> listOfItems =new ArrayList<StudentObject>();;
	private ArrayList<String> ids;
	private String activity;
	private String roster_title;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_roster);
        Parse.initialize(this,LoginHandler.link1 ,LoginHandler.link2);
        Bundle extras = getIntent().getExtras();
        activity = extras.getString("className");
        roster_title = extras.getString("name");
        TextView activity_name;
        activity_name = (TextView) findViewById (R.id.title_stud);
	    activity_name.setText(roster_title + " " + "Roster");
        createList();        
    }
    
    public void onAddStudentClick(View view) {
    	Intent i = new Intent(this, AddStudentActivity.class);
    	ids = new ArrayList<String>();
    	for(StudentObject s : listOfItems) {
    		ids.add(s.getStudentid());
    	}
    	i.putStringArrayListExtra("ids", ids);
    	i.putExtra("activity", activity);
    	i.putExtra("activity_name", roster_title);

    	startActivityForResult(i, AttendanceTakerActivity.ACTIVITY_AddStudent);
    }
    
    public void createList(){
    	
    	   System.out.println("Before getStudentForAct");
 	      ParseQuery query1 = new ParseQuery("Student");
 	     listOfItems.clear();
 	    	query1.whereEqualTo(activity, 1);
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
    	Intent i = new Intent(this, RemoveStudentActivity.class);
    	ids = new ArrayList<String>();
    	for(StudentObject s : listOfItems) {
    		ids.add(s.getStudentid());
    	}
    	i.putStringArrayListExtra("ids", ids);
    	i.putExtra("activity_name", roster_title);
    	i.putExtra("activity", activity);
    	startActivityForResult(i, AttendanceTakerActivity.ACTIVITY_RemoveStudent);
    }
    
    public void onRefreshClick(View view) {
    	createList();
    }

}