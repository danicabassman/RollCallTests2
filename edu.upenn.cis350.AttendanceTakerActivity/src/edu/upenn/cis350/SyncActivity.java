package edu.upenn.cis350;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class SyncActivity extends Activity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.syncactivity);
        Parse.initialize(this,LoginHandler.link1 ,LoginHandler.link2);
        upload();
        
        createList();
        createListActivity();
        
    }

	public void onBackButtonClick(View view) {
		Intent i = new Intent();
		setResult(RESULT_OK, i);
		finish();	
	}
	
	public void createList(){
    	
 	   System.out.println("Before getStudentForAct");
	     ParseQuery query1 = new ParseQuery("Student");
	        	query1.findInBackground(new FindCallback() {
			     public void done(List<ParseObject> queryList, ParseException e) {
			         if (e == null) {
			        	 try
			        	 {
			        	
			        	 System.out.println("Done No Error");
			        	 System.out.println("The size of querylsit is "+queryList.size());
			        	 Set<String> s = queryList.get(0).keySet();
			        	 String keys[]= new String[s.size()+2];
			        	 s.toArray(keys);
			        	 keys[s.size()]="ObjectId";
			        	 keys[s.size()+1]="UpdatedAt";
			        	 //File f = new File("Students.csv");
			        	 File f = new File("/mnt/sdcard/","Students.csv");
			        	 f.delete();
			        	 FileOutputStream fos = new FileOutputStream(f);
			        	 System.out.println(f.getAbsolutePath());
			        	 String name;
			        	 for(int i=0;i<keys.length;i++)
				        	 {
				        		 System.out.println(keys[i]);
				        	   	 name=new String("\""+keys[i]+"\"");		        		 
						         fos.write(name.getBytes());
						         if(i!=keys.length-1)
						        		 fos.write(",".getBytes());			        	 
				        	 }
			        	
			        	   	 fos.write("\n".getBytes());
			        	   	 System.out.println("new line");		        	   	 
			        	 
			        	 
			        	 for(ParseObject student : queryList){
				     		//System.out.println("5"); 
			        		 String value;
			        		 for(int i=0;i<keys.length-2;i++)
				        	 {
			        			 System.out.println("for");
			        			 if(student.containsKey(keys[i]))			        			 
			        				{
			        				 value= new String("\""+student.get(keys[i]).toString()+"\"");
			        				}
			        			 else
			        			 {
			        				 value = new String("\""+ " "+ "\"");
			        				 System.out.println("false");
			        			 }
			        				 //fos.write(student.get(keys[i]).toString().getBytes());
			        				 fos.write(value.getBytes());	
			        		   		 fos.write(",".getBytes());
				        	 }
			        		 String temp =new String("\""+student.getObjectId().toString()+"\",");
			        		 fos.write(temp.getBytes());
			        		 temp =new String("\""+student.getUpdatedAt().toString()+"\"");
			        		 fos.write(temp.getBytes());
			        		 fos.write("\n".getBytes()); 
			        				        		 
			        	 }
			        	 	fos.close();
			        	 	System.out.println("Complete");
			         }
			         catch(Exception ex)
		        	 {
		        		 System.out.println("Exception"+ex.toString());
		        		 ex.printStackTrace();
		        		 
		        	 }
			     
			     } else {
			             //objectRetrievalFailed();
			        	 System.out.println("Error");
			     }
			         
			     }
			     
			    
			 });
		
		  System.out.println("After getStudentForAct");
	
 	
 }
	
	public void createListActivity(){
		 
	 	   System.out.println("Before getStudentForAct");
		     ParseQuery query1 = new ParseQuery("Activity");
		        	query1.findInBackground(new FindCallback() {
				     public void done(List<ParseObject> queryList, ParseException e) {
				         if (e == null) {
				        	 try
				        	 {
				        	 System.out.println("Done No Error");
				        	 System.out.println("The size of querylsit is "+queryList.size());
				        	 Set<String> s = queryList.get(0).keySet();
				        	 String keys[]= new String[s.size()+2];
				        	 s.toArray(keys);
				        	 keys[s.size()]="ObjectId";
				        	 keys[s.size()+1]="UpdatedAt";
				        	 System.out.println("this worked");
				           	 File f = new File("/mnt/sdcard/","Activity.csv");
				        	 f.delete();
				        	 FileOutputStream fos = new FileOutputStream(f);
				        	 System.out.println(f.getAbsolutePath());
				        	 String name;
				        	 
					        	
				        	 for(int i=0;i<keys.length;i++)
					        	 {
					        		 System.out.println(keys[i]);
					        	   	 name=new String("\""+keys[i]+"\"");
					        	   	 fos.write(name.getBytes());
							         if(i!=keys.length-1)
							        		 fos.write(",".getBytes());			        	 
					        	 }
				        	
				        	   	 fos.write("\n".getBytes());
				        	   	 System.out.println("new line");
				        	
				        	   	 
				        	 
				        	 
				        	 for(ParseObject activity : queryList){
					     		//System.out.println("5"); 
				        		 String value;
				        		 for(int i=0;i<keys.length-2;i++)
					        	 {
				        			 
				        			 System.out.println("for");
				        			 if(activity.containsKey(keys[i]))			        			 
				        				{
				        				 value= new String("\""+activity.get(keys[i]).toString()+"\"");
				        				 if(i==0) populateRoster(activity.get(keys[i]).toString());
				        				}
				        			 else
				        			 {
				        				 value = new String("\""+ " "+ "\"");
				        				 System.out.println("false");
				        			 }
				        				 //fos.write(student.get(keys[i]).toString().getBytes());
				        				 fos.write(value.getBytes());
				        				 fos.write(",".getBytes());
					        	 }
				        		 String temp =new String("\""+activity.getObjectId().toString()+"\",");
				        		 fos.write(temp.getBytes());
				        		 temp =new String("\""+activity.getUpdatedAt().toString()+"\"");
				        		 fos.write(temp.getBytes());
				        		 fos.write("\n".getBytes()); 
				        				        		 
				        	 }
				        	 	fos.close();
				        	 	System.out.println("Complete");
				         }
				         catch(Exception ex)
			        	 {
			        		 System.out.println("Exception"+ex.toString());
			        		 ex.printStackTrace();
			        		 
			        	 } 
				     
				     } else {
				             //objectRetrievalFailed();
				        	 System.out.println("Error");
				     }
				         
				     }
				     
				    
				 });
			
			  System.out.println("After getStudentForAct");
		
	 	
	 }
	
	
	public void createListActivityList(String classname){
		final String filename = new String(classname+".csv");
	 	   System.out.println("Filename");
	 	  System.out.println(filename);
		     ParseQuery query1 = new ParseQuery(classname);
		        	query1.findInBackground(new FindCallback() {
				     public void done(List<ParseObject> queryList, ParseException e) {
				         if (e == null) {
				        	 try
				        	 {
				        	 System.out.println("Done No Error");
				        	 System.out.println("The size of querylsit is "+queryList.size());
				        	 Set<String> s = queryList.get(0).keySet();
				        	 String keys[]= new String[s.size()+2];
				        	 s.toArray(keys);
				        	 keys[s.size()]="ObjectId";
				        	 keys[s.size()+1]="UpdatedAt";
				        	 System.out.println("this worked");
				        	 File f = new File("/mnt/sdcard/",filename);
				        	 f.delete();
				           	 FileOutputStream fos = new FileOutputStream(f);
				        	 System.out.println(f.getAbsolutePath());
				        	 String name;
				        	
					        	for(int i=0;i<keys.length;i++)
					        	 {
					        		 System.out.println(keys[i]);
					        	   	 name=new String("\""+keys[i]+"\"");		        		 
							         fos.write(name.getBytes());
							         if(i!=keys.length-1)
							        		 fos.write(",".getBytes());			        	 
					        	 }
				        	
				        	   	 fos.write("\n".getBytes());
				        	   	 System.out.println("new line");
				        	 
				        	   	 
				        	 
				        	 
				        	 for(ParseObject activity : queryList){
					     		//System.out.println("5"); 
				        		 String value;
				        		 for(int i=0;i<keys.length-2;i++)
					        	 {
				        			 System.out.println("for");
				        			 if(activity.containsKey(keys[i]))			        			 
				        				{
				        				 value= new String("\""+activity.get(keys[i]).toString()+"\"");
				        				}
				        			 else
				        			 {
				        				 value = new String("\""+ " "+ "\"");
				        				 System.out.println("false");
				        			 }
				        				 //fos.write(student.get(keys[i]).toString().getBytes());
				        				 fos.write(value.getBytes());
				        				 fos.write(",".getBytes());
					        	 }
				        		 String temp =new String("\""+activity.getObjectId().toString()+"\",");
				        		 fos.write(temp.getBytes());
				        		 temp =new String("\""+activity.getUpdatedAt().toString()+"\"");
				        		 fos.write(temp.getBytes());
				        		 fos.write("\n".getBytes()); 
				        				        		 
				        	 }
				        	 	fos.close();
				        	 	System.out.println("Complete");
				        	 }
				        
				         catch(Exception ex)
			        	 {
			        		 System.out.println("Exception"+ex.toString());
			        		 ex.printStackTrace();
			        		 
			        	 } 
				     
				        	 } else {
				             //objectRetrievalFailed();
				        	 System.out.println("Error");
				     }
				         
				     }
				     
				    
				 });
			
			  System.out.println("After getStudentForAct");
		
	 	
	 }
	
	
	public void populateRoster(String classname)
	{
		
		final String filename = new String(classname+".csv");
		System.out.println("Filename");
		System.out.println(filename);
		System.out.println("Before getStudentForAct");
	    ParseQuery query1 = new ParseQuery("Student");
	    query1.whereEqualTo(classname, 1);
	    query1.findInBackground(new FindCallback() {
		    public void done(List<ParseObject> queryList, ParseException e) {
			         if (e == null) {
			        	 try
			        	 {
			        		 String keys[] = {"ID","Parse_1112GradeLevel","Name","phoneNumber","Address","School","Site","Program","Comment","emergencyContactName","emergencyContactRelationship"};
			        		 File f = new File("/mnt/sdcard/",filename);
			        		 f.delete();
			        		 FileOutputStream fos = new FileOutputStream(f);
			        		 System.out.println("Done No Error");
				        	 System.out.println("The size of querylsit is "+queryList.size());
				        	 if(queryList.size()==0)			        		 
					        	 fos.write(new String("\"ID\",\"Parse_1112GradeLevel\",\"Name\",\"phoneNumber\",\"Address\",\"School\",\"Site\",\"Program\",\"Comment\",\"emergencyContactName\",\"emergencyContactRelationship\"").getBytes());
				        	 else
				        	 {
				        	 System.out.println(f.getAbsolutePath());
				        	 fos.write(new String("\"ID\",\"Parse_1112GradeLevel\",\"Name\",\"phoneNumber\",\"Address\",\"School\",\"Site\",\"Program\",\"Comment\",\"emergencyContactName\",\"emergencyContactRelationship\"").getBytes());
				        	   	 fos.write("\n".getBytes());
				        	   	 System.out.println("new line");
					        	 for(ParseObject student : queryList){
					        		 String value;
					        		 for(int i=0;i<keys.length;i++)
						        	 {
					        			 System.out.println("for");
					        			 if(student.containsKey(keys[i]))			        			 
					        				{
					        				 value= new String("\""+student.get(keys[i]).toString()+"\"");
					        				}
					        			 else
					        			 {
					        				 value = new String("\""+ " "+ "\"");
					        				 System.out.println("false");
					        			 }
					        				 //fos.write(student.get(keys[i]).toString().getBytes());
					        				 fos.write(value.getBytes());	
					        			 if(i!=keys.length-1)
							        		 fos.write(",".getBytes());
						        	 }
					        		 fos.write("\n".getBytes()); 
					        				        		 
					        	 }
					        	 	fos.close();
					        	 	System.out.println("Complete");
					         }
			        	 }
					         catch(Exception ex)
				        	 {
				        		 System.out.println("Exception"+ex.toString());
				        		 ex.printStackTrace();
				        		 
				        	 }
					     
					     } else {
					             //objectRetrievalFailed();
					        	 System.out.println("Error");
					     }
					         
					     }
					     
					    
					 });
				
				  System.out.println("After getStudentForAct");
			
				
			}
	
	
	public void upload()
	{
		try 
		{
		BufferedReader brd = new BufferedReader(new FileReader("/mnt/sdcard/changes.csv"));
		String line;
		String files[];
		System.out.println("In Upload");
		if((line = brd.readLine())!=null)
		{
			files=line.split(",");
		for(int j=0;j<files.length;j++)
		{
		System.out.println(files[j]);
		List<StudentObject> studentList =new ArrayList<StudentObject>();
		StudentObject s;
		int id=0,name=0,school=0,site=0,program=0,comment=0,phone=0,grade=0,address=0,emrname=0,emrrel=0,att=0;	
		 System.out.println("Offline");
	    	String[] columns;
	    	String fileName = "/mnt/sdcard/"+files[j]+".csv"; 
	    	System.out.println(fileName);
	    	try 
			{
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			if((fileName = br.readLine())!=null)
			{
				columns = fileName.split("\",\"");
			for(int i=0;i<columns.length;i++)
			{
				System.out.println(columns[i]);
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
				else if(columns[i].compareTo( "Attendance\"")==0)
					att=i;
			}
			
			while((fileName = br.readLine())!=null)
			{
				String[] tokens = fileName.split("\",\"");
				tokens[0]=tokens[0].replace('\"', ' ');
				tokens[0]=tokens[0].trim();
				tokens[att]=tokens[att].replace('\"',' ');
				tokens[att]=tokens[att].trim();
				String one[] = tokens[att].split("=");
				for(int i=0;i<one.length;i++)
				System.out.println("Attendance is" + one[i]);
				int gradeValue;
				String[] attend;
				try
				{
					gradeValue=Integer.parseInt(tokens[grade]);
					attend=tokens[att].split("=");
					
				}
				catch(NumberFormatException nfe)
				{
					gradeValue=0;
				}
				catch(Exception e)
				{
					gradeValue=0;
				}
				
				s= new StudentObject(tokens[id],gradeValue,tokens[name],tokens[phone],tokens[address],tokens[school],tokens[site],tokens[program],tokens[comment],tokens[emrname],tokens[emrrel]);
				s.setStatus(tokens[att]);
				studentList.add(s);
				
				
				for(int i=0;i<tokens.length;i++)
					System.out.print(tokens[i]+"     ");
				System.out.println();
			}
			}
			}
			 catch(Exception ex)
        	 {
        		 System.out.println("Exception"+ex.toString());
        		 ex.printStackTrace();
        		 
        	 }
			
	
			ParseQuery query2 = new ParseQuery(files[j]);
			final List<StudentObject> studList =studentList;
			for(int i=0; i<studList.size();i++)
				System.out.println(studList.get(i).getName());
		
			query2.findInBackground(new FindCallback() {
		     public void done(List<ParseObject> rows, ParseException e) {
		         if (e == null) {

		        	 System.out.println("The size of querylsit is "+rows.size());
				        for(ParseObject row: rows){
							String name = row.getString("Name");
							//System.out.println(name);
							if(Utils.containsName(studList, name)){
								StudentObject stud = Utils.getStudentFromList(studList, name);
								if(stud!=null){
									String[] studentStatus = stud.getStatus().split("=");
									String studentComments = stud.getComments();
									System.out.println(stud.getName());
									//if student is absent, don't log anything
								//	if(!studentStatus.equals("Absent")){
										System.out.println("In Save Row");
										String columnName = studentStatus[0];
										System.out.println(columnName);
										//if(!row.containsKey(columnName)){
											row.put(columnName,studentStatus[1]);
											row.put("Comment_"+Utils.getTodaysDate(), studentComments);
											row.saveInBackground();
											Log.v("Attendance", "Attendance taken for " + stud.getName() + " " + columnName + " " + Utils.getCurrentTime());
											
								//		}
									//}
								}
								else
									System.out.println("studlist=null");
							
							}
							
							else
								System.out.println("does not contain name");
						}

			   			
		         } else {
		             //objectRetrievalFailed();
		        	 System.out.println("Error");
		     }
		         
		     }
		     
		    
		 });

			}
		}
		}
		catch(Exception ex)
   	 	{
   		 System.out.println("Exception"+ex.toString());
   		 ex.printStackTrace();
   		 
   	 	}
		finally
		{
			File f = new File("/mnt/sdcard/","changes.csv");
	    	f.delete();
		}
		
}
}
