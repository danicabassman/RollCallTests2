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
 * Screen where user can remove a student from
 * a particular activity's roster.
 */
public class RemoveStudentActivity extends Activity{
	
	private List<StudentObject> listOfItems=new ArrayList<StudentObject>();
	private static final int REMOVE = 0;
	private StudentObject student;
	private String activity;
	private String classname;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_student);  
 	//	new AttendanceTakerActivity();
	//	int code=AttendanceTakerActivity.DatabaseCode;
      //  System.out.println("The code is"+ code);
      //  Parse.initialize(this, "cuoXWbqvBKs8SUrhnyKdyNWiMPZxuDBZ31ehltVI", "tl8VMcFHu7u3haym9KSbRKEP61MmxPDvmL06dxeo"); 
      
       // new LoginHandler().LoginDB(code);
       // new LoginHandler();
		Parse.initialize(this,LoginHandler.link1 ,LoginHandler.link2);
		
        Bundle extras = getIntent().getExtras();
        activity = extras.getString("activity_name");
        classname = extras.getString("activity");
        TextView activity_name;
        activity_name = (TextView) findViewById (R.id.title_thinger);
	    activity_name.setText(activity + " " + "Roster");
        createList();        
    }
    
    /*old version createlst
    public void createList() {
    	listOfItems = ParseHandler.getStudentsForActivity(classname);
    	AddStudentAdapter adapter = new AddStudentAdapter(this, listOfItems);
        ListView list = (ListView) findViewById(R.id.StudentList);
        list.setClickable(true);
        //click listener for each list item - on click, goes to Roster page
        list.setOnItemClickListener(new OnItemClickListener() {
          public void onItemClick(AdapterView<?> parent, View view,
              int position, long id) {  
        	student = listOfItems.get(position);
        	showDialog(REMOVE);
        	}
        });
        
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    */
    
    
    public void createList() {
    	
    	  ParseQuery query1 = new ParseQuery("Student");
    	  listOfItems.clear();
	    	query1.whereEqualTo(classname, 1);
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
						showRemove();
						System.out.println("In populateR after showR");
			         } else {
			             //objectRetrievalFailed();
			        	 System.out.println("Error");
			     }
			         
			     }
			     
			    
			 });
		
		  System.out.println("After getStudentForAct");
	
    }
    
    public void showRemove(){
    	
    	AddStudentAdapter adapter = new AddStudentAdapter(this, listOfItems);
        ListView list = (ListView) findViewById(R.id.StudentList);
        list.setClickable(true);
        //click listener for each list item - on click, goes to Roster page
        list.setOnItemClickListener(new OnItemClickListener() {
          public void onItemClick(AdapterView<?> parent, View view,
              int position, long id) {  
        	student = listOfItems.get(position);
        	showDialog(REMOVE);
        	}
        });
        
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    	
    }
    @Override
	protected Dialog onCreateDialog(int id) {
    	if (id == REMOVE) {
	    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to remove this student?");
            LinearLayout ll = new LinearLayout(this);
            ll.setOrientation(1);
	        builder.setView(ll);
	    	builder.setPositiveButton("Remove", new DialogInterface.OnClickListener() {
	    		public void onClick(DialogInterface dialog, int id) {
	    			Double studentID = Double.parseDouble(student.getStudentid());
	    			
	    			ParseHandler.removeStudentFromActivity(studentID, activity);
	    			
	    			dialog.cancel();
	        		Toast.makeText(getApplicationContext(), "Student successfully removed.",
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