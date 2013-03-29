package edu.upenn.cis350;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SendEmailActivity extends Activity{
	static private ArrayList <String>filenames= new ArrayList<String>();
	
	
	public void onCreate(Bundle savedInstanceState) {
		System.out.println("Go To send email"); 
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_email); 
        readAllFiles();
		//public ArrayList<String> readFiles(){
			
			//	return csvfiles;
		//	}
  
	}
	
	
	public static void readAllFiles(){
		File file=new File("/mnt/sdcard/");
		File[]  files = file.listFiles();
		ArrayList<FileObject> csvfiles=new ArrayList<FileObject>();
		for (File f:files){
			String fileName = f.getName();
			if (fileName.endsWith(".csv"))
				csvfiles.add(new FileObject(fileName));
			}
		
		 for (FileObject s :csvfiles){
			 System.out.println(s.getName());
		 }
		
		 EditFileActivity.listOffiles=csvfiles;
		
		
	}
	public static void setAttachFiles(ArrayList<String> fs){
		filenames=fs;
	}
	
	 public void onSendClick(View view) {
	    	EditText r = (EditText) findViewById (R.id.reciever);
	    	EditText t = (EditText) findViewById (R.id.emailBody);
	    	EditText s = (EditText) findViewById (R.id.subject);
	    	String receiver = r.getText().toString();
	    	String textBody = t.getText().toString();
	    	String subject = s.getText().toString();
	    	System.out.println("receiver:"+receiver);
	    	System.out.println("textBody:"+textBody);
	    	System.out.println("subject:"+subject);
		 
			 StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			 StrictMode.setThreadPolicy(policy); 
			 try {   
	                GMailSender sender = new GMailSender("rollcall.developers@gmail.com","nettercenterapp");
	                sender.sendMail(subject,   
	                		textBody,   
	                        "rollcall.developers@gmail.com",   
	                       // "arvindisgr8@gmail.com,nirmal.utwani@gmail.com,ankit.khandelwal679@gmail.com"); 
	                        //"rollcall.developers@gmail.com"
	                        receiver, filenames);
	                System.out.println("In try");
	                Toast.makeText(getApplicationContext(), 
	                        "Email sent!", Toast.LENGTH_LONG).show();
	            } catch (Exception e) {
	            	System.out.println("In catch");
	                System.out.println(e.getMessage());   
	            } 
			 
			 
			 Intent i = new Intent(this, ViewScreenActivity.class);
		     startActivityForResult(i, AttendanceTakerActivity.ACTIVITY_ViewScreen);
	 }
	 
	 public void onAttachClick(View view) {
		 
		 Intent i = new Intent(this, EditFileActivity.class);
	     startActivityForResult(i, AttendanceTakerActivity.ACTIVITY_EditFileActivity);
	
	 }
			
	 protected void onActivityResult(int requestCode, int resultCode, Intent data)  
	    {  
	        if(requestCode==AttendanceTakerActivity.ACTIVITY_SendEmailActivity)  
	        {  
	            if(resultCode==AttendanceTakerActivity.ACTIVITY_EditFileActivity)  
	            {  
	                
	                Toast.makeText(SendEmailActivity.this, "Files Attached!!!", Toast.LENGTH_LONG).show();  
	            }  
	        }  
	    }  
	 
}
