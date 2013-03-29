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


public class EditFileActivity extends Activity{
	
	//changed variable name to list of students
	static public List<FileObject> listOffiles= new ArrayList<FileObject>();
	private List<FileObject> submitfiles= new ArrayList<FileObject>();

	public FileAdapter adapter;
	
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file);
        System.out.println("IN EDITFILE TO SHOW FILENAMES");
        for (FileObject f: listOffiles){
        	System.out.println(f.getName());
        }
        showFile();
    }
      
    // put the display here
    public void showFile(){
    	
 	   ListView list = (ListView) findViewById(R.id.FileList);
        
        //handles null case
  		if(list == null){
  			LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  			list = (ListView) inflater.inflate(R.id.FileList, null);
  		}
        
        //list.setClickable(true);

      adapter = new FileAdapter(this, listOffiles);
        

          list.setAdapter(adapter);
 }
    
    
 // selects all students
 	public void onSelectAllClick(View v) {
 		adapter.setAllChecked();
 		
 	}

 	// deselects all students
 	public void onDeselectAllClick(View v) {
 		adapter.setAllUnChecked();
 	}
    
    
    
    /** creates and populates roster **/
//	public List<StudentObject> populateRoster(){
//		
//		
//						System.out.println("In populateR before showR");
//						showRoster();
//						System.out.println("In populateR after showR");
//		
//	}
    
	/** handles click for Back button **/
	
	
	public void onSubmitAttachClick(View view) {
		submitfiles=adapter.getBox();
		System.out.println("Print BOX");
		for (FileObject ff: submitfiles){
			System.out.println(ff.getName());
			
		}
		if(listOffiles == null){
			Toast.makeText(getApplicationContext(),
					"No file.", Toast.LENGTH_SHORT)
					.show();

		}
		else if(submitfiles.size()==0){
			Toast.makeText(getApplicationContext(),
					"Please select some files first.", Toast.LENGTH_SHORT)
					.show();

		}
		else{
			ArrayList<String> filenames=new ArrayList<String>();
			for (FileObject f:submitfiles){
				filenames.add(f.getName());
				}
				SendEmailActivity.setAttachFiles(filenames);
				
				// Intent i = new Intent(this, SendEmailActivity.class);
			     //startActivityForResult(i, AttendanceTakerActivity.ACTIVITY_SendEmailActivity);
			     
			     
			     Intent intent = new Intent();  
		            setResult(AttendanceTakerActivity.ACTIVITY_SendEmailActivity, intent);  
		            finish();  
			}
			
		
			

		}
	
}