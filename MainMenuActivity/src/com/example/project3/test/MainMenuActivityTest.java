package com.example.project3.test;


import com.example.project3.*;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

public class MainMenuActivityTest extends
		ActivityInstrumentationTestCase2<MainMenuActivity> {
			private Activity activity;
			private Button studentButton;
			private Button activityButton;
			private Button aggregateButton;

	public MainMenuActivityTest() {
		super("com.example.project3", MainMenuActivity.class);	
	}

public void setUp() {
	try {
		super.setUp();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	activity = this.getActivity();
	studentButton = (Button)activity.findViewById(com.example.project3.R.id.studentButton);
	activityButton = (Button)activity.findViewById(com.example.project3.R.id.activityButton);
	aggregateButton = (Button)activity.findViewById(com.example.project3.R.id.aggregateButton);
}

public void testStudentButton() {
	activity.runOnUiThread(new Runnable() {
		public void run() {
			studentButton.performClick();
		}
	});
	//getInstrumentation().waitForIdleSync();
}

public void testActivityButton() {
	activity.runOnUiThread(new Runnable() {
		public void run() {
			activityButton.performClick();
		}
	});
	//getInstrumentation().waitForIdleSync();
}

public void testAggregateButton() {
	activity.runOnUiThread(new Runnable() {
		public void run() {
			aggregateButton.performClick();
		}
	});
	getInstrumentation().waitForIdleSync();
}
}
