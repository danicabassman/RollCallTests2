package edu.upenn.cis350;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class SyncHandler {
	static List<ActivityObject> activityList = new ArrayList<ActivityObject>();
	static List<StudentObject> studentList = new ArrayList<StudentObject>();
	
	public static boolean getStudentData()
	{
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
		catch(ParseException e)
		{
			e.printStackTrace();
		}
		
		return true;
		
	}
	
	
	
	
}
