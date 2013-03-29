package edu.upenn.cis350;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * Representation of a profile for a student.
 * Every student profile has the student's name,
 * student ID, grade, site, school, address, and
 * emergency contact information displayed.
 */
public class ProfileActivity extends Activity{
	
	Bundle extras;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        extras = getIntent().getExtras();
        
        String studentName = extras.getString("name");
        TextView studentNameView = (TextView)findViewById(R.id.profileName);
        studentNameView.setText(studentName);
        
        String studentPhoneNumber = extras.getString("phone");
        TextView phoneNumberView = (TextView)findViewById(R.id.profilePhone);
        phoneNumberView.setText(studentPhoneNumber);
        
        String studentAddress = extras.getString("address");
        TextView studentAddressView = (TextView)findViewById(R.id.profileAddress);
        studentAddressView.setText(studentAddress);
        
        String studentIDNumber = extras.getString("idNumber");
        TextView studentIDNumberView = (TextView)findViewById(R.id.profileIDNumber);
        studentIDNumberView.setText(studentIDNumber);
        
        String studentContactName = extras.getString("contactName");
        TextView studentContactNameView = (TextView)findViewById(R.id.profileEmergContName);
        studentContactNameView.setText(studentContactName);
        
        String studentContactType = extras.getString("contactType");
        TextView studentContactTypeView = (TextView)findViewById(R.id.profileEmergContType);
        studentContactTypeView.setText(studentContactType);
        
        String studentSite = extras.getString("site");
        TextView studentSiteView = (TextView)findViewById(R.id.profileSite);
        studentSiteView.setText(studentSite);
        
        String studentGrade = extras.getString("grade");
        TextView studentGradeView = (TextView)findViewById(R.id.profileGrade);
        studentGradeView.setText(studentGrade);
        
        String studentSchool = extras.getString("school");
        TextView studentSchoolView = (TextView)findViewById(R.id.profileSchool);
        studentSchoolView.setText(studentSchool);
        System.out.println("studentSchool"+studentSchool);
        
        String studentProgram = extras.getString("program");
        if (studentProgram== null || studentProgram.equals(""))
        	studentProgram=studentSite;
        System.out.println("studentProgram"+studentProgram);
        	
        TextView studentProgramView = (TextView)findViewById(R.id.profileProgram);
        studentProgramView.setText(studentProgram);
        
        String studentComment = extras.getString("comment");
        TextView studentCommentView = (TextView)findViewById(R.id.profileComment);
        studentCommentView.setText(studentComment);
        
    }

	public void onEditButtonClick(View view) {
		Intent i = new Intent(view.getContext(), EditProfileActivity.class);
		i.putExtras(extras);
		startActivityForResult(i, AttendanceTakerActivity.ACTIVITY_EditProfile);
	}
	
	public void onRefreshClick(View view) {
		
		
	}
	
	protected void onActivityResult(int requestCode, int resultCode,Intent data) {  
        if (requestCode == AttendanceTakerActivity.ACTIVITY_EditProfile) {  
        	System.out.println("on Activity Result");
       	 setContentView(R.layout.profile);
         
         
         Bundle bundle = data.getExtras();  
         
         String studentName =  bundle.getString("nameEdit"); 
         TextView studentNameView = (TextView)findViewById(R.id.profileName);
         studentNameView.setText(studentName);
         
         String studentPhoneNumber =  bundle.getString("phoneEdit");  
         TextView phoneNumberView = (TextView)findViewById(R.id.profilePhone);
         phoneNumberView.setText(studentPhoneNumber);
         
         String studentAddress = bundle.getString("addressEdit");  
         TextView studentAddressView = (TextView)findViewById(R.id.profileAddress);
         studentAddressView.setText(studentAddress);
         
         String studentIDNumber =  bundle.getString("idEdit"); 
         TextView studentIDNumberView = (TextView)findViewById(R.id.profileIDNumber);
         studentIDNumberView.setText(studentIDNumber);
         
         String studentContactName = bundle.getString("ecnEdit"); 
         TextView studentContactNameView = (TextView)findViewById(R.id.profileEmergContName);
         studentContactNameView.setText(studentContactName);
         
         String studentContactType = bundle.getString("ectEdit"); 
         TextView studentContactTypeView = (TextView)findViewById(R.id.profileEmergContType);
         studentContactTypeView.setText(studentContactType);
         
         String studentSite = bundle.getString("siteEdit"); 
         TextView studentSiteView = (TextView)findViewById(R.id.profileSite);
         studentSiteView.setText(studentSite);
         
         String studentGrade =  bundle.getString("gradeEdit"); 
         TextView studentGradeView = (TextView)findViewById(R.id.profileGrade);
         studentGradeView.setText(studentGrade);
         
         String studentSchool = bundle.getString("schoolEdit"); 
         TextView studentSchoolView = (TextView)findViewById(R.id.profileSchool);
         studentSchoolView.setText(studentSchool);
         
         String studentProgram = bundle.getString("programEdit"); 
         TextView studentProgramView = (TextView)findViewById(R.id.profileProgram);
         studentProgramView.setText(studentProgram);
         System.out.println("studentProgram"+ studentProgram);
         
         String studentComment = bundle.getString("commentEdit"); 
         TextView studentCommentView = (TextView)findViewById(R.id.profileComment);
         studentCommentView.setText(studentComment);
        	
            if (resultCode == RESULT_CANCELED) {  
             //do something  
            
                 
            } else {  
             //do something  
          }  
     }  
}  

}