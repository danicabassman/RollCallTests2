package edu.upenn.cis350;

import com.parse.Parse;
import com.parse.ParseException;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;


/**
 * Screen where user is prompted to register a new
 * username and password for the application.
 */
public class RegistrationScreenActivity extends Activity {
	private int logincode;
	private String creatcode;
	@Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate (savedInstanceState);
    	setContentView (R.layout.resgistration);
    	
	}

	public void onRegisterAccountButtonClick (View view) throws ParseException {
		EditText registration_name = (EditText) findViewById (R.id.reg_username);
    	// EditText email = (EditText) findViewById (R.id.reg_email);
    	EditText password = (EditText) findViewById (R.id.reg_password);
    	EditText password_confirm = (EditText) findViewById (R.id.reg_password_confirm);
    	EditText creation_code = (EditText) findViewById (R.id.reg_createcode);
    	  creatcode=creation_code.getText().toString();
    	 //  Integer.parseInt(creation_code.getText().toString())
    	  regDB(creatcode);
		  System.out.println("LoginCode  "+logincode);
	      new LoginHandler().LoginDB(logincode);
		  Parse.initialize(this,LoginHandler.link1 ,LoginHandler.link2);
		  System.out.println("link1"+LoginHandler.link1);
		  System.out.println("link2"+LoginHandler.link2);
		  System.out.println("After Connect TO Database");
		  
    	String nameString = registration_name.getText().toString();
    	// String emailString = email.getText().toString();
    	String passwordString  = password.getText().toString();
    	String passConfirmString = password_confirm.getText().toString();
    	

    	if ((nameString.length() == 0) /*|| (emailString.length() == 0)*/ || (passwordString.length() == 0) || (passConfirmString.length() == 0)) {
    		Context context = getApplicationContext();
    		CharSequence text = "Some required field(s) are blank";
    		int duration = Toast.LENGTH_SHORT;
    		Toast toast = Toast.makeText(context, text, duration);
    		toast.show();
    		
    	}
    	
    	else if (!(passwordString.equals(passConfirmString))) {
    		Context context = getApplicationContext();
    		CharSequence text = "Incorrect password, please try again";
    		int duration = Toast.LENGTH_SHORT;
    		Toast toast = Toast.makeText(context, text, duration);
    		toast.show();
    		
    	}
    	
    	else{
    		  
    		
    		ParseHandler.signUpUser(nameString, passwordString, getApplicationContext());
    		System.out.println("After Handler SignUp");
    		Intent i = new Intent(this, AttendanceTakerActivity.class);
	    	startActivityForResult(i, AttendanceTakerActivity.ACTIVITY_RegistrationActivity);

    	}
		
	}
	public void regDB(String creatcode){
		System.out.println("The Creation Code is "+ creatcode);
		if (creatcode.equals("OCWD")) //Comegys
			logincode=1;
		else if (creatcode.equals("PIEB")) //Wilson
			logincode=2;
		else if (creatcode.equals("NVSO")) //Lea
			logincode=3;
		else if (creatcode.equals("REVH")) //Sayre
			logincode=4;
		else if (creatcode.equals("MJOG")) //UCHS
			logincode=5;
		else if (creatcode.equals("KEVJ")) //Huey
			logincode=6;
		else if (creatcode.equals("LPRE")) //West
			logincode=7;
		else if (creatcode.equals("MHFD")) //Bartrams
			logincode=8;
		else if (creatcode.equals("XCZE")) //SOTF
			logincode=9;
		else if (creatcode.equals("POFW")) //New 1
			logincode=10;
		else if (creatcode.equals("PRED")) //New 2
			logincode=11;
		else if (creatcode.equals("MNJT")) //New 3
			logincode=12;
		else if (creatcode.equals("EDPJ")) //New 4
			logincode=13;
		else if (creatcode.equals("LWQK")) //New 4
			logincode=14;
			
	}
}
