package edu.upenn.cis350;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;



public class AddNewStudentActivity extends Activity{
	Bundle extras;
	private List<StudentObject> studentList = new ArrayList<StudentObject>();
	private ArrayList<String> ids=new ArrayList<String>();
	private String idEdit,nameEdit,phoneEdit,addressEdit,ecnEdit,ectEdit,siteEdit,gradeEdit,schoolEdit,programEdit,commentEdit;
	private boolean already_in_roster = false;
	private boolean id_null=false;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_profile);
	}

	public void onDoneButtonClick(View view){
		 nameEdit = ((EditText)findViewById(R.id.editProfileName)).getText().toString();
		 phoneEdit = ((EditText)findViewById(R.id.editProfilePhone)).getText().toString();	
		 addressEdit = ((EditText)findViewById(R.id.editProfileAddress)).getText().toString();
		 idEdit = ((EditText)findViewById(R.id.editProfileIDNumber)).getText().toString();
		 ecnEdit = ((EditText)findViewById(R.id.editProfileEmergContName)).getText().toString();
		 ectEdit = ((EditText)findViewById(R.id.editProfileEmergContType)).getText().toString();
		 siteEdit = ((EditText)findViewById(R.id.editProfileSite)).getText().toString();
		 System.out.println("siteEdit"+siteEdit);
		 gradeEdit = ((EditText)findViewById(R.id.editProfileGrade)).getText().toString();
		 schoolEdit = ((EditText)findViewById(R.id.editProfileSchool)).getText().toString();
		 programEdit = ((EditText)findViewById(R.id.editProfileProgram)).getText().toString();
		 commentEdit = ((EditText)findViewById(R.id.editProfileComment)).getText().toString();
		System.out.println("Before addNewStudent");
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(idEdit.trim());
		Matcher isPhone =pattern.matcher(phoneEdit.trim());
		
		if (idEdit== null || idEdit.equals("")){
			Context context = getApplicationContext();
	 		CharSequence text = "You Must Input the ID";
			int duration = Toast.LENGTH_SHORT;
			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
			id_null=true;
		
		}
		else if (!isNum.matches()){
		
			Context context = getApplicationContext();
			CharSequence text = "ID Must be a Number";
			int duration = Toast.LENGTH_SHORT;
			Toast toast = Toast.makeText(context, text, duration);
			toast.show();}
		
		else if (!isPhone.matches()){
			
			Context context = getApplicationContext();
			CharSequence text = "Phone Must be a Number";
			int duration = Toast.LENGTH_SHORT;
			Toast toast = Toast.makeText(context, text, duration);
			toast.show();}
		
		else {
			if (gradeEdit== null || gradeEdit.equals("")){
				System.out.println("in gradeedit");
				gradeEdit="999";
				}
		
	      ParseQuery query1 = new ParseQuery("Student");
	    	studentList.clear();
	    	ids.clear();
	    	query1.findInBackground(new FindCallback() {
			     public void done(List<ParseObject> queryList, ParseException e) {
			         if (e == null) {
			        	 for(ParseObject student : queryList){
			        		 ids.add(student.getNumber("ID").toString());
				     	}
				        System.out.println("AGAIN!!The size of IDS is "+ids.size());
				        checkId();
			         } else {
			             //objectRetrievalFailed();
			        	 System.out.println("Error");
			     }
			         
			     }
			 });
		
	    

		
		System.out.println("After addNewStudent");

		}
		
	}
	
	public void checkId(){
		System.out.println("In Id Checking");
    	for (String ID : ids) {
    		if(idEdit.trim().equals(ID)){
    			already_in_roster = true;
    		System.out.println("ID already EXIST!!!");}
    	}
    	if(already_in_roster) {
  		Toast.makeText(getApplicationContext(), "That student ID is already Registed!",
	                Toast.LENGTH_LONG).show();
  		already_in_roster = false;

    	}
    	else{
			ParseHandler.addNewStudent( nameEdit, phoneEdit, 
			addressEdit, idEdit, ecnEdit, ectEdit, siteEdit, gradeEdit, schoolEdit,programEdit,commentEdit);
			Toast.makeText(getApplicationContext(), "Add Sucessfully!",
	                Toast.LENGTH_LONG).show();
			finish();
			}
		
	}
}
