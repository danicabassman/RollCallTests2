package edu.upenn.cis350;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutionException;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


/***
 * Handles interaction with online Parse database.
 * In general, methods convert to ActivityObject or StudentObject
 *
 */
public class ParseHandler {

	/**
	 * given a name, adds an activity to Parse datastore
	 * @param name
	 */
	
	static List<ActivityObject> activityList = new ArrayList<ActivityObject>();
	private static String date = Utils.getTodaysDate();
	private static String time = Utils.getCurrentTime();
	private static String stuName;
	
	
	
	
	
	
	public static void addActivity(String name){
        ParseObject activity = new ParseObject("Activity");
        ParseQuery query = new ParseQuery("Activity");
        int id = 0;
//        try {
//			id = query.count()+1;
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
        System.out.println("In addActi 1");
        StringTokenizer tokenizer = new StringTokenizer(name);
        String className = tokenizer.nextToken();
        activity.put("ClassName", className);
        activity.put("DisplayName", name);
        activity.put("ID", id);
        activity.put("count", 0);
        activity.saveInBackground();
        
        System.out.println("In addActi 2");
        ParseObject newActivity = new ParseObject(className);
        newActivity.put("schoolYear", "2011-2012");
        newActivity.put("program", name);
        newActivity.saveInBackground();
	}
	

	public static void addStudentToActivity(Double studentID, String activity) {
		try {
			new RetriveStudent().execute(String.valueOf(studentID), activity).get();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	}
	

	
	public static void removeStudentFromActivity(Double studentID, String activity) {
		 System.out.println("in rmSFA");
		try {
			System.out.println("Before RSE");
			new RetriveRemoveStudent().execute(String.valueOf(studentID), activity).get();
			System.out.println("After RSE");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	}
	
	public static void removeStudentFromActivityMain(Double studentID) {
		 System.out.println("in rmSFA");
		try {
			System.out.println("Before RSE");
			new RetriveRemoveStudentMain().execute(String.valueOf(studentID)).get();
			System.out.println("After RSE");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	}

	
	public static ActivityObject getActivityByName(String name){
		ActivityObject activity = new ActivityObject();

		
		try {
			ParseObject a = new RetriveQuery().execute(name, "Activity").get();

			activity = new ActivityObject(a.getObjectId(),a.getString("DisplayName"),
					a.getString("ClassName"), a.getUpdatedAt(), a.getInt("count"));
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return activity;
	}
	
	
	public static List<ActivityObject> getAllActivities(){
		System.out.println("1");
		ParseQuery query = new ParseQuery("Activity");
		System.out.println("2");
		//List<ParseObject> queryList = new ArrayList<ParseObject>();
		//List<ActivityObject> activityList = new ArrayList<ActivityObject>();
		System.out.println("3");
		//queryList = query.find();
//	query.findInBackground(queryList);
		
		query.findInBackground(new FindCallback() {
	
		     public void done(List<ParseObject> queryList, ParseException e) {
		         if (e == null) {
		             //objectsWereRetrievedSuccessfully(queryList);
		        	 System.out.println("Done No Error");
		        	 System.out.println("The size of querylsit is "+queryList.size());
		        	 for(ParseObject p : queryList){
			     			System.out.println("5");
			     			activityList.add(new ActivityObject(p.getObjectId(), p.getString("DisplayName"),
			     					p.getString("ClassName"), p.getUpdatedAt(), p.getInt("count")));
			     		}
			        	 System.out.println("AGAIN!!The size of activity is "+activityList.size());
		         } else {
		             //objectRetrievalFailed();
		        	 System.out.println("Error");
		     }
		         
		     }
		     
		    
		 }
		);
		System.out.println("4");
		

		System.out.println("6");
		Collections.sort(activityList);
		System.out.println("7");
		return activityList;

	}
	
	public static void increaseActivityCount(String activity){
		try {
			ParseObject a = new RetriveQuery().execute(activity, "Activity").get();
			int count = a.getInt("count");
			count++;
			Log.v("count", count+"");
			a.put("count", count);
			a.saveInBackground();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	
	public static void addStudent(StudentObject s){
		ParseObject student = new ParseObject("Student");
		student.put("Name", s.getName());
        student.saveInBackground();
	}
	
	
	public static void addNewStudent(String... arg){
		ParseObject student = new ParseObject("Student");
		student.put("Name", arg[0]);
		student.put("phoneNumber", arg[1]);
		student.put("Address", arg[2]);
		student.put("ID", Integer.parseInt(arg[3]));
		student.put("emergencyContactName", arg[4]);
		student.put("emergencyContactRelationship", arg[5]);
		student.put("Site", arg[6]);
		//student.put("gradeLevel", arg[7]);Parse_1112GradeLevel
		student.put("Parse_1112GradeLevel", Integer.parseInt(arg[7]));
		student.put("School", arg[8]);
		student.put("Program", arg[9]);
		student.put("Comment", arg[10]);
	
        student.saveInBackground();
	}

	
	
	public static List<StudentObject> getAllStudents(){
		ParseQuery query = new ParseQuery("Student");
		List<ParseObject> queryList = new ArrayList<ParseObject>();
		List<StudentObject> studentList = new ArrayList<StudentObject>();
		try{
			queryList = query.find();
			Log.v("hello", queryList.size()+"");
			for(ParseObject student : queryList){
				studentList.add(new StudentObject(student.getNumber("ID").toString(), student.getNumber("Parse_1112GradeLevel").intValue(),
						student.getString("Name"),student.getString("phoneNumber"),student.getString("Address"), student.getString("School"), student.getString("Site"),
						student.getString("Program"),student.getString("Comment"),
						student.getString("emergencyContactName"), student.getString("emergencyContactRelationship")));
			}
		}
		catch(ParseException e){
			e.printStackTrace();
		}
		return studentList;
	}
	
	public static List<StudentObject> getStudentsForActivity(String activity) {
		ParseQuery query = new ParseQuery("Student");
		query.whereEqualTo(activity, 1);
		List<ParseObject> queryList = new ArrayList<ParseObject>();
		List<StudentObject> studentList = new ArrayList<StudentObject>();
		
		try{
			queryList = query.find();
			Log.v("hello", queryList.size()+"");
			for(ParseObject student : queryList){
				studentList.add(new StudentObject(student.getNumber("ID").toString(), student.getNumber("Parse_1112GradeLevel").intValue(),
						student.getString("Name"),student.getString("phoneNumber"),student.getString("Address"), student.getString("School"), student.getString("Site"),
						student.getString("Program"),student.getString("Comment"),
						student.getString("emergencyContactName"), student.getString("emergencyContactRelationship")));
			}
		}
		catch(ParseException e){
			e.printStackTrace();
		}
		return studentList;
		
	}
	
	
	
	public static void renameActivity (String className, String newName) {
		 System.out.println("in rmSFA");
		try {
			System.out.println("Before RSE");
			new RetriveOldActName().execute(className, newName).get();
			System.out.println("After RSE");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	}
	
	
	
	
	
	public static void submitActivityAttendance_In(ActivityObject a, final List<StudentObject> studentList){

		 System.out.println("The size of STUDENT is "+studentList.size());

		ParseQuery query2 = new ParseQuery(a.getClassName());
		System.out.println("In Handler submitAc a.getClassName"+a.getClassName());

		query2.findInBackground(new FindCallback() {
		     public void done(List<ParseObject> rows, ParseException e) {
		         if (e == null) {

		        	 System.out.println("The size of querylsit is "+rows.size());
				        for(ParseObject row: rows){
				        	System.out.println("rows!!!");
							String name = row.getString("Name");
							System.out.println("Name!!!"+name);
							if(Utils.containsName(studentList, name)){
								StudentObject stud = Utils.getStudentFromList(studentList, name);
								if(stud!=null){
									//String studentStatus = stud.getStatus();
									String studentStatus="In";
									String studentComments = stud.getComments();
									//if student is absent, don't log anything
								//	if(!studentStatus.equals("Absent")){
										System.out.println("In Save Row");
										String columnName = studentStatus + "_" + date;
										System.out.println(columnName);
										//if(!row.containsKey(columnName)){
											row.put(columnName, Utils.getCurrentTime()+date+"_"+studentStatus);	
											row.put("Comment_"+date, studentComments);
											row.saveInBackground();
											Log.v("Attendance", "Attendance taken for " + stud.getName() + " " + columnName + " " + Utils.getCurrentTime());
								//		}
									//}
								}
							}
						}

			   			
		         } else {
		             //objectRetrievalFailed();
		        	 System.out.println("Error");
		     }
		         
		     }
		     
		    
		 });

}

	
	
	public static void submitActivityAttendance_Out(ActivityObject a, final List<StudentObject> studentList){

		 System.out.println("The size of STUDENT is "+studentList.size());

		ParseQuery query2 = new ParseQuery(a.getClassName());
		System.out.println("In Handler submitAc a.getClassName"+a.getClassName());

		query2.findInBackground(new FindCallback() {
		     public void done(List<ParseObject> rows, ParseException e) {
		         if (e == null) {

		        	 System.out.println("The size of querylsit is "+rows.size());
				        for(ParseObject row: rows){
				        	System.out.println("rows!!!");
							String name = row.getString("Name");
							System.out.println("Name!!!"+name);
							if(Utils.containsName(studentList, name)){
								StudentObject stud = Utils.getStudentFromList(studentList, name);
								if(stud!=null){
									//String studentStatus = stud.getStatus();
									String studentStatus="Out";
									String studentComments = stud.getComments();
									//if student is absent, don't log anything
								//	if(!studentStatus.equals("Absent")){
										System.out.println("In Save Row");
										String columnName = studentStatus + "_" + date;
										System.out.println(columnName);
										//if(!row.containsKey(columnName)){
											row.put(columnName, Utils.getCurrentTime());	
											row.put("Comment_"+date, studentComments);
											row.saveInBackground();
											Log.v("Attendance", "Attendance taken for " + stud.getName() + " " + columnName + " " + Utils.getCurrentTime());
								//		}
									//}
								}
							}
						}

			   			
		         } else {
		             //objectRetrievalFailed();
		        	 System.out.println("Error");
		     }
		         
		     }
		     
		    
		 });

}
	
	public static void submitActivityAttendance_ab(ActivityObject a, final List<StudentObject> studentList){

		 System.out.println("The size of STUDENT is "+studentList.size());

		ParseQuery query2 = new ParseQuery(a.getClassName());
		System.out.println("In Handler submitAc a.getClassName"+a.getClassName());

		query2.findInBackground(new FindCallback() {
		     public void done(List<ParseObject> rows, ParseException e) {
		         if (e == null) {

		        	 System.out.println("The size of querylsit is "+rows.size());
				        for(ParseObject row: rows){
				        	System.out.println("rows!!!");
							String name = row.getString("Name");
							System.out.println("Name!!!"+name);
							if(Utils.containsName(studentList, name)){
								StudentObject stud = Utils.getStudentFromList(studentList, name);
								if(stud!=null){
									//String studentStatus = stud.getStatus();
									String studentStatus="Absent";
									String studentComments = stud.getComments();
									//if student is absent, don't log anything
								//	if(!studentStatus.equals("Absent")){
										System.out.println("In Save Row");
										String columnName = studentStatus + "_" + date;
										System.out.println(columnName);
										//if(!row.containsKey(columnName)){
											row.put(columnName, Utils.getCurrentTime());	
											row.put("Comment_"+date, studentComments);
											row.saveInBackground();
											Log.v("Attendance", "Attendance taken for " + stud.getName() + " " + columnName + " " + Utils.getCurrentTime());
								//		}
									//}
								}
							}
						}

			   			
		         } else {
		             //objectRetrievalFailed();
		        	 System.out.println("Error");
		     }
		         
		     }
		     
		    
		 });

}
	
	public static void signUpUser(String username, /*String email,*/ String password, final Context context) {
				
			System.out.println("ENTER Flag 1");
			ParseUser user = new ParseUser();
			user.setUsername(username);
			user.setPassword(password);
			System.out.println(username);
			System.out.println(password);
		
			
				System.out.println("Before Sign up");
			

				user.signUpInBackground(new SignUpCallback() {
					CharSequence text = "";
				   public void done(ParseException e) {
				     if (e == null) {
				       System.out.println("signUpUser YES!");
				    text = "Registration successful!";
				     } else {
				       System.out.println("signUpUser failed");
			         text = "Already have one";
				     }
				     int duration = Toast.LENGTH_SHORT;
			    		Toast toast = Toast.makeText(context, text, duration);
			    		toast.show();
				   
				   }
				 });
			
	
	}
	

	
	public static ParseUser checkLogin(String username, String password){
	//	try {
//			ParseUser user = Parseser.logIn(username, password);
//			if(user!=null){
//				return user;
			System.out.println(username+" "+password);
		    System.out.print("Check Begin!!!!!!!");
			ParseUser.logInInBackground(username, password, null);
					/*new LogInCallback() {
				
				@Override
				public void done(ParseUser arg0, ParseException arg1) {
					// TODO Auto-generated method stub
					
					ParseUser user =ParseUser.getCurrentUser();
					//System.out.println(user);
					
					if (user==null){
						System.out.print("Void Done - user is empty");
					}
					System.out.println(user.isAuthenticated());
					System.out.flush();
				}
			});*/
			ParseUser user = ParseUser.getCurrentUser();
			System.out.print("Check Finished");
			/*try{
					user = user.fetch();
			}
			catch(ParseException pe)
			{
				System.out.println("Parse Exception");
			}
			if (user==null){
				System.out.print("User is Empty");
			}*/
			System.out.flush();
			return user;
//		}
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		return null;
	}
	
	
	public static void editStudent(String name, String nameEdit, String phoneEdit, 
			String addressEdit, String idEdit, String ecnEdit,
			String ecrEdit, String siteEdit, String gradeEdit, 
			String schoolEdit,String programEdit,String commentEdit){
		try {
			System.out.println("Before RSE");
			new RetriveEditStudent().execute(name, nameEdit, phoneEdit, 
					addressEdit, idEdit,  ecnEdit, 
					 ecrEdit,  siteEdit,  gradeEdit, 
					schoolEdit, programEdit,  commentEdit ).get();
			
			System.out.println("After RSE");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	static class RetriveQuery extends AsyncTask<String, Void, ParseObject>{

		@Override
		protected ParseObject doInBackground(String... arg) {
			ParseQuery query = new ParseQuery(arg[1]);
			query.whereEqualTo("ClassName", arg[0]);
			try {
				ParseObject a = query.getFirst();
				return a;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
	}
	
	
	static class RetriveStudent extends AsyncTask<String, Void, ParseObject>{

		@Override
		protected ParseObject doInBackground(String... arg) {
			ParseQuery query = new ParseQuery("Student");
			ParseObject actitem = new ParseObject(arg[1]);
			//ParseQuery query1 = new ParseQuery(arg[1]);
			query.whereEqualTo("ID", Double.parseDouble(arg[0]));
			try {
				ParseObject a = query.getFirst();
				a.put(arg[1], 1);
				stuName=a.getString("Name");
				a.saveInBackground();
				// add to specific activity class
				System.out.println("To Save to the activity class");
			//	ParseObject b = query1.getFirst();
				actitem.put("Name", a.getString("Name"));
				actitem.saveInBackground();
				
		return a;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

			return null;
		}
		
	}
	
	static class RetriveRemoveStudent extends AsyncTask<String, Void, ParseObject>{

		@Override
		protected ParseObject doInBackground(String... arg) {
			ParseQuery query = new ParseQuery("Student");
			query.whereEqualTo("ID", Double.parseDouble(arg[0]));
			try {
				ParseObject a = query.getFirst();
				a.put(arg[1], 0);
				a.saveInBackground();
				return a;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
	}
	
	static class RetriveRemoveStudentMain extends AsyncTask<String, Void, ParseObject>{

		@Override
		protected ParseObject doInBackground(String... arg) {
			ParseQuery query = new ParseQuery("Student");
			query.whereEqualTo("ID", Double.parseDouble(arg[0]));
			try {
				ParseObject a = query.getFirst();
				a.delete();
				return a;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
	}
	
	static class RetriveOldActName extends AsyncTask<String, Void, ParseObject>{

	@Override
	protected ParseObject doInBackground(String... arg) {
		ParseQuery query = new ParseQuery("Activity");
		query.whereEqualTo("ClassName", arg[0]);
		try {
			ParseObject a = query.getFirst();
			a.put("DisplayName", arg[1]);
			a.saveInBackground();
			return a;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
	static class RetriveEditStudent extends AsyncTask<String, Void, ParseObject>{

		@Override
		protected ParseObject doInBackground(String... arg) {
			ParseQuery query = new ParseQuery("Student");
			query.whereEqualTo("Name", arg[0]);
			try {
				ParseObject a = query.getFirst();
				String a1=a.get("Name").toString();
				String a2=a.get("phoneNumber").toString();
				String a3=a.get("emergencyContactRelationship").toString();				
				String a4= a.get("emergencyContactName").toString();
				String a8=a.get("Address").toString();
				String a5=a.get("ID").toString();
				String a6=a.get("School").toString();
				String a7=a.get("Site").toString();
				String a9=a.get("Parse_1112GradeLevel").toString();
				String a10=a.get("Program").toString();
				String a11=a.get("Comment").toString();
				
				
			if(!arg[1].equals("")){
					
					a.put("Name", arg[1]);
			
					if((a1.equals(arg[1]))==false)
					{
					System.out.println(a1);
					System.out.println(a.getObjectId());
	    			System.out.println((ParseUser.getCurrentUser().getUsername()));
	    			System.out.println(arg[1]);
					System.out.println(ParseUser.getCurrentUser());
					System.out.println(a.getUpdatedAt());
					
					
					//ParseQuery query1 = new ParseQuery("Log_Table"); 	
					
					ParseObject query1 = new ParseObject("Log_Table");
					query1.put("newValue", arg[1]);
					query1.put("modifiedObjId", a.getObjectId().toString());
					query1.put("oldValue", a1);
					query1.put("userName", ParseUser.getCurrentUser().getUsername());
					query1.put("userId", ParseUser.getCurrentUser().getObjectId().toString());
					query1.put("changedAt", a.getUpdatedAt().toString());	
					query1.put("fieldName", "Student Name");	
			        query1.saveEventually();
	    		}
			}
			
				
				a.put("phoneNumber", arg[2]);	
			if((a2.equals(arg[2]))==false)
				{
				System.out.println(a2);
				System.out.println(a.getObjectId());
    			System.out.println((ParseUser.getCurrentUser().getUsername()));
    			System.out.println(arg[2]);
				System.out.println(ParseUser.getCurrentUser());
				System.out.println(a.getUpdatedAt());
				
				//ParseQuery query1 = new ParseQuery("Log_Table"); 	
				
				ParseObject query1 = new ParseObject("Log_Table");
				query1.put("newValue", arg[2]);
				query1.put("modifiedObjId", a.getObjectId().toString());
				query1.put("oldValue", a2);
				query1.put("userName", ParseUser.getCurrentUser().getUsername());
				query1.put("userId", ParseUser.getCurrentUser().getObjectId().toString());
				query1.put("changedAt", a.getUpdatedAt().toString());	
				query1.put("fieldName", "Contact Phone");
		        query1.saveEventually();
    		}
			
			a.put("emergencyContactRelationship", arg[6]);	
			if((a3.equals(arg[6]))==false)
			{
			System.out.println(a2);
			System.out.println(a.getObjectId());
			System.out.println((ParseUser.getCurrentUser().getUsername()));
			System.out.println(arg[2]);
			System.out.println(ParseUser.getCurrentUser());
			System.out.println(a.getUpdatedAt());
			
			//ParseQuery query1 = new ParseQuery("Log_Table"); 	
			
			ParseObject query1 = new ParseObject("Log_Table");
			query1.put("newValue", arg[6]);
			query1.put("modifiedObjId", a.getObjectId().toString());
			query1.put("oldValue", a3);
			query1.put("userName", ParseUser.getCurrentUser().getUsername());
			query1.put("userId", ParseUser.getCurrentUser().getObjectId().toString());
			query1.put("changedAt", a.getUpdatedAt().toString());	
			query1.put("fieldName", "Contact Relationship");
	        query1.saveEventually();
		}
				
				a.put("emergencyContactName", arg[5]);
				if((a4.equals(arg[5]))==false)
				{
				System.out.println(a2);
				System.out.println(a.getObjectId());
				System.out.println((ParseUser.getCurrentUser().getUsername()));
				System.out.println(arg[2]);
				System.out.println(ParseUser.getCurrentUser());
				System.out.println(a.getUpdatedAt());
				
				//ParseQuery query1 = new ParseQuery("Log_Table"); 	
				
				ParseObject query1 = new ParseObject("Log_Table");
				query1.put("newValue", arg[5]);
				query1.put("modifiedObjId", a.getObjectId().toString());
				query1.put("oldValue", a4);
				query1.put("userName", ParseUser.getCurrentUser().getUsername());
				query1.put("userId", ParseUser.getCurrentUser().getObjectId().toString());
				query1.put("changedAt", a.getUpdatedAt().toString());	
				query1.put("fieldName", "Contact Name");
		        query1.saveEventually();
			}
				a.put("Address", arg[3]);
				if((a8.equals(arg[3]))==false)
				{
				System.out.println(a2);
				System.out.println(a.getObjectId());
				System.out.println((ParseUser.getCurrentUser().getUsername()));
				System.out.println(arg[2]);
				System.out.println(ParseUser.getCurrentUser());
				System.out.println(a.getUpdatedAt());
				
				//ParseQuery query1 = new ParseQuery("Log_Table"); 	
				
				ParseObject query1 = new ParseObject("Log_Table");
				query1.put("newValue", arg[3]);
				query1.put("modifiedObjId", a.getObjectId().toString());
				query1.put("oldValue", a8);
				query1.put("userName", ParseUser.getCurrentUser().getUsername());
				query1.put("userId", ParseUser.getCurrentUser().getObjectId().toString());
				query1.put("changedAt", a.getUpdatedAt().toString());	
				query1.put("fieldName", "Address");
		        query1.saveEventually();
			}
				a.put("ID", Double.parseDouble(arg[4]));
				if((a5.equals(arg[4]))==false)
				{
				System.out.println(a2);
				System.out.println(a.getObjectId());
				System.out.println((ParseUser.getCurrentUser().getUsername()));
				System.out.println(arg[2]);
				System.out.println(ParseUser.getCurrentUser());
				System.out.println(a.getUpdatedAt());
				
				//ParseQuery query1 = new ParseQuery("Log_Table"); 	
				
				ParseObject query1 = new ParseObject("Log_Table");
				query1.put("newValue", arg[4]);
				query1.put("modifiedObjId", a.getObjectId().toString());
				query1.put("oldValue", a5);
				query1.put("userName", ParseUser.getCurrentUser().getUsername());
				query1.put("userId", ParseUser.getCurrentUser().getObjectId().toString());
				query1.put("changedAt", a.getUpdatedAt().toString());	
				query1.put("fieldName", "Student Id");
		        query1.saveEventually();
			}
				a.put("School", arg[9]);
				if((a6.equals(arg[9]))==false)
				{
				System.out.println(a2);
				System.out.println(a.getObjectId());
				System.out.println((ParseUser.getCurrentUser().getUsername()));
				System.out.println(arg[2]);
				System.out.println(ParseUser.getCurrentUser());
				System.out.println(a.getUpdatedAt());
				
				//ParseQuery query1 = new ParseQuery("Log_Table"); 	
				
				ParseObject query1 = new ParseObject("Log_Table");
				query1.put("newValue", arg[9]);
				query1.put("modifiedObjId", a.getObjectId().toString());
				query1.put("oldValue", a6);
				query1.put("userName", ParseUser.getCurrentUser().getUsername());
				query1.put("userId", ParseUser.getCurrentUser().getObjectId().toString());
				query1.put("changedAt", a.getUpdatedAt().toString());	
				query1.put("fieldName", "School");
		        query1.saveEventually();
			}
				a.put("Site", arg[7]);
				if((a7.equals(arg[7]))==false)
				{
				System.out.println(a2);
				System.out.println(a.getObjectId());
				System.out.println((ParseUser.getCurrentUser().getUsername()));
				System.out.println(arg[2]);
				System.out.println(ParseUser.getCurrentUser());
				System.out.println(a.getUpdatedAt());
				
				//ParseQuery query1 = new ParseQuery("Log_Table"); 	
				
				ParseObject query1 = new ParseObject("Log_Table");
				query1.put("newValue", arg[7]);
				query1.put("modifiedObjId", a.getObjectId().toString());
				query1.put("oldValue", a7);
				query1.put("userName", ParseUser.getCurrentUser().getUsername());
				query1.put("userId", ParseUser.getCurrentUser().getObjectId().toString());
				query1.put("changedAt", a.getUpdatedAt().toString());		
				query1.put("fieldName", "Site");
		        query1.saveEventually();
			}
				//a.put("Address", arg[3]);
				a.put("Parse_1112GradeLevel", Integer.parseInt(arg[8]));
				if((a9.equals(arg[8]))==false)
				{
				System.out.println(a2);
				System.out.println(a.getObjectId());
				System.out.println((ParseUser.getCurrentUser().getUsername()));
				System.out.println(arg[2]);
				System.out.println(ParseUser.getCurrentUser());
				System.out.println(a.getUpdatedAt());
				
				//ParseQuery query1 = new ParseQuery("Log_Table"); 	
				
				ParseObject query1 = new ParseObject("Log_Table");
				query1.put("newValue", arg[8]);
				query1.put("modifiedObjId", a.getObjectId().toString());
				query1.put("oldValue", a9);
				query1.put("userName", ParseUser.getCurrentUser().getUsername());
				query1.put("userId", ParseUser.getCurrentUser().getObjectId().toString());
				query1.put("changedAt", a.getUpdatedAt().toString());
				query1.put("fieldName", "Grade");
		        query1.saveEventually();
			}
				a.put("Program", arg[10]);
				if((a10.equals(arg[10]))==false)
				{
				System.out.println(a2);
				System.out.println(a.getObjectId());
				System.out.println((ParseUser.getCurrentUser().getUsername()));
				System.out.println(arg[2]);
				System.out.println(ParseUser.getCurrentUser());
				System.out.println(a.getUpdatedAt());
				
				//ParseQuery query1 = new ParseQuery("Log_Table"); 	
				
				ParseObject query1 = new ParseObject("Log_Table");
				query1.put("newValue", arg[10]);
				query1.put("modifiedObjId", a.getObjectId().toString());
				query1.put("oldValue", a10);
				query1.put("userName", ParseUser.getCurrentUser().getUsername());
				query1.put("userId", ParseUser.getCurrentUser().getObjectId().toString());
				query1.put("changedAt", a.getUpdatedAt().toString());
				query1.put("fieldName", "Program");
		        query1.saveEventually();
			}
				a.put("Comment", arg[11]);
				if((a11.equals(arg[11]))==false)
				{
				System.out.println(a2);
				System.out.println(a.getObjectId());
				System.out.println((ParseUser.getCurrentUser().getUsername()));
				System.out.println(arg[2]);
				System.out.println(ParseUser.getCurrentUser());
				System.out.println(a.getUpdatedAt());
				
				//ParseQuery query1 = new ParseQuery("Log_Table"); 	
				
				ParseObject query1 = new ParseObject("Log_Table");
				query1.put("newValue", arg[11]);
				query1.put("modifiedObjId", a.getObjectId().toString());
				query1.put("oldValue", a11);
				query1.put("userName", ParseUser.getCurrentUser().getUsername());
				query1.put("userId", ParseUser.getCurrentUser().getObjectId().toString());
				query1.put("changedAt", a.getUpdatedAt().toString());	
				query1.put("fieldName", "Comment");
		        query1.saveEventually();
			}
				a.saveEventually();
				//saveInBackground();
				Log.v("asdf", "edit successful " + arg[3]);
				return a;
			
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null; 
		}
		
	}
}


