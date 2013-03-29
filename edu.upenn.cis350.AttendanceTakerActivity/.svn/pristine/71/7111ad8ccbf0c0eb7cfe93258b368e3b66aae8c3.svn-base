package edu.upenn.cis350;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Screen where user can edit the information pertaining
 * to a student.
 *
 */

public class EditProfileActivity extends Activity{
	Bundle extras;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_profile);
		extras = getIntent().getExtras();

		String studentName = extras.getString("name");
		EditText studentNameView = (EditText)findViewById(R.id.editProfileName);
		studentNameView.setText(studentName);

		String studentPhoneNumber = extras.getString("phone");
		EditText phoneNumberView = (EditText)findViewById(R.id.editProfilePhone);
		phoneNumberView.setText(studentPhoneNumber);

		String studentAddress = extras.getString("address");
		EditText studentAddressView = (EditText)findViewById(R.id.editProfileAddress);
		studentAddressView.setText(studentAddress);

		String studentIDNumber = extras.getString("idNumber");
		EditText studentIDNumberView = (EditText)findViewById(R.id.editProfileIDNumber);
		studentIDNumberView.setText(studentIDNumber);

		String studentContactName = extras.getString("contactName");
		EditText studentContactNameView = (EditText)findViewById(R.id.editProfileEmergContName);
		studentContactNameView.setText(studentContactName);

		String studentContactType = extras.getString("contactType");
		EditText studentContactTypeView = (EditText)findViewById(R.id.editProfileEmergContType);
		studentContactTypeView.setText(studentContactType);

		String studentSite = extras.getString("site");
		EditText studentSiteView = (EditText)findViewById(R.id.editProfileSite);
		studentSiteView.setText(studentSite);

		String studentGrade = extras.getString("grade");
		EditText studentGradeView = (EditText)findViewById(R.id.editProfileGrade);
		studentGradeView.setText(studentGrade);

		String studentSchool = extras.getString("school");
		EditText studentSchoolView = (EditText)findViewById(R.id.editProfileSchool);
		studentSchoolView.setText(studentSchool);
		
		String studentProgram = extras.getString("program");
        if (studentProgram== null || studentProgram.equals(""))
        	studentProgram=studentSite;
		EditText studentProgramView = (EditText)findViewById(R.id.editProfileProgram);
		studentProgramView.setText(studentProgram);
		
		String studentComment = extras.getString("comment");
		EditText studentCommentView = (EditText)findViewById(R.id.editProfileComment);
		studentCommentView.setText(studentComment);

		
	}

	public void onDoneButtonClick(View view){
		String nameEdit = ((EditText)findViewById(R.id.editProfileName)).getText().toString();
		String phoneEdit = ((EditText)findViewById(R.id.editProfilePhone)).getText().toString();	
		String addressEdit = ((EditText)findViewById(R.id.editProfileAddress)).getText().toString();
		String idEdit = ((EditText)findViewById(R.id.editProfileIDNumber)).getText().toString();
		String ecnEdit = ((EditText)findViewById(R.id.editProfileEmergContName)).getText().toString();
		String ectEdit = ((EditText)findViewById(R.id.editProfileEmergContType)).getText().toString();
		String siteEdit = ((EditText)findViewById(R.id.editProfileSite)).getText().toString();
		String gradeEdit = ((EditText)findViewById(R.id.editProfileGrade)).getText().toString();
		String programEdit = ((EditText)findViewById(R.id.editProfileProgram)).getText().toString();
		String commentEdit = ((EditText)findViewById(R.id.editProfileComment)).getText().toString();
		String schoolEdit = ((EditText)findViewById(R.id.editProfileSchool)).getText().toString();
		System.out.println("Before Click Edit");
		
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(idEdit.trim());
		Matcher isPhone =pattern.matcher(phoneEdit.trim());
		if (idEdit== null || idEdit.equals("")){
			Context context = getApplicationContext();
	 		CharSequence text = "You Must Input the ID";
			int duration = Toast.LENGTH_SHORT;
			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
		
		
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
		ParseHandler.editStudent(extras.getString("name"), nameEdit, phoneEdit, 
				addressEdit, idEdit, ecnEdit, ectEdit, siteEdit, gradeEdit, schoolEdit,programEdit,commentEdit);
		}
		
        Intent intent = new Intent();

      Bundle bundle = new Bundle();

      bundle.putString("nameEdit", nameEdit);
      bundle.putString("phoneEdit", phoneEdit);
      bundle.putString("addressEdit", addressEdit);
      bundle.putString("idEdit", idEdit);
      bundle.putString("ecnEdit", ecnEdit);
      bundle.putString("ectEdit", ectEdit);
      bundle.putString("siteEdit", siteEdit);
      bundle.putString("gradeEdit", gradeEdit);
      bundle.putString("schoolEdit", schoolEdit);
      bundle.putString("programEdit", programEdit);
      bundle.putString("commentEdit", commentEdit);

      intent.putExtras(bundle);
		
		setResult(RESULT_OK, intent);  
		System.out.println("After Click Edit");
		finish();
	}
}
