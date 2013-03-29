package edu.upenn.cis350;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;


import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Where user can add a student to the roster of
 * students for a particular activity in the database.
 *
 */
public class AddStudentActivity extends Activity{
	
	private List<StudentObject> listOfItems= new ArrayList<StudentObject>();
	private static final int ADD = 0;
	private ArrayList<String> ids;
	private StudentObject student;
	private String activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_student);   
        
		Parse.initialize(this,LoginHandler.link1 ,LoginHandler.link2);
		
        Bundle extras = getIntent().getExtras();
        ids = extras.getStringArrayList("ids");
        activity = extras.getString("activity_name");
        TextView activity_name;
        activity_name = (TextView) findViewById (R.id.title_thinger);
	    activity_name.setText(activity + " " + "Roster");
        createList();        
    }
  
   
    
    public void createList() {

        System.out.println("Before getStudentForAct");
	      ParseQuery query1 = new ParseQuery("Student");
	      listOfItems.clear();
	    	
	    	query1.findInBackground(new FindCallback() {
			     public void done(List<ParseObject> queryList, ParseException e) {
			         if (e == null) {
			        	 System.out.println("Done No Error");
			        	 System.out.println("The size of querylsit is "+queryList.size());
			        	 for(ParseObject student : queryList){
							 listOfItems.add(new StudentObject(student.getNumber("ID").toString(), student.getNumber("Parse_1112GradeLevel").intValue(),
											student.getString("Name"),student.getString("phoneNumber"),student.getString("Address"), student.getString("School"), student.getString("Site"),
											student.getString("Program"),student.getString("Comment"),
											student.getString("emergencyContactName"), student.getString("emergencyContactRelationship")));
								}
				        System.out.println("AGAIN!!The size of actlist is "+listOfItems.size());
				   		
						Collections.sort(listOfItems, new Comparator<StudentObject>(){
					   	     public int compare(StudentObject o1, StudentObject o2){
					   	         return o1.getName().compareToIgnoreCase(o2.getName());
					   	     }
					   	});
						showStudentList();
						
						System.out.println("In populateR after showR");
			         } else {
			             //objectRetrievalFailed();
			        	 System.out.println("Error");
			     }
			         
			     }
			     
			    
			 });
		
		  System.out.println("After getStudentForAct");
	

    	
    }
    
    
    public void showStudentList(){
    	
    	AddStudentAdapter adapter = new AddStudentAdapter(this, listOfItems);
        ListView list = (ListView) findViewById(R.id.StudentList);
        list.setClickable(true);
        //click listener for each list item - on click, goes to Roster page
        list.setOnItemClickListener(new OnItemClickListener() {
          public void onItemClick(AdapterView<?> parent, View view,
              int position, long id) {  
        	StudentObject currentStudent = listOfItems.get(position);
        	boolean already_in_roster = false;
        	for (String ID : ids) {
        		if(currentStudent.getStudentid().equals(ID))
        			already_in_roster = true;
        	}
        	if(already_in_roster) {
        		Toast.makeText(getApplicationContext(), "That student is already part of the activity",
    	                Toast.LENGTH_SHORT).show();
        	}
        	else {
        		student = currentStudent;
        		showDialog(ADD);
        	}
          }
        });
        
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    	
    }
    @Override
	protected Dialog onCreateDialog(int id) {
    	if (id == ADD) {
	    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to add this student?");
            LinearLayout ll = new LinearLayout(this);
            ll.setOrientation(1);
	        builder.setView(ll);
	    	builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
	    		public void onClick(DialogInterface dialog, int id) {
	    			Double studentID = Double.parseDouble(student.getStudentid());
	    			System.out.println("Before Add Student to Activity");
	    			ParseHandler.addStudentToActivity(studentID, activity);
	    			System.out.println("After Add Student to Activity");
	    			dialog.cancel();
	        		Toast.makeText(getApplicationContext(), "Student successfully added.",
	    	                Toast.LENGTH_SHORT).show();
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

}


