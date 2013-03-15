package com.example.project3.test;

import org.junit.Before;
import org.junit.Test;

import android.app.Activity;
import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;
import com.example.project3.MenuActivity;


public class MenuActivityTest extends
		ActivityInstrumentationTestCase2<MenuActivity> {
	
	public MenuActivityTest() {
		super("com.example.project3", MenuActivity.class);
	}

	Activity a;
	
	@Before
	public void setUp() {
		  try {
			super.setUp();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  a = getActivity();
	}
	
	@Test
	public void Test() {
		a.runOnUiThread(new Runnable() {
			public void run() {
				
			}
		});
		a.onCreate(new Bundle());
		
//		 for (String s : a.listview_array) {
//			 System.out.println(s);
//		 }
//		 
//		 System.out.println("LIST 2");
//		 
//		 for (String s : a.listview_array) {
//			 System.out.println(s);
//		 }
//		 
//		 System.out.println("SORTED ARRAY");
//		 for(String s : a.array_sort) {
//			 System.out.println(s);
//		 }
//		 
//		 System.out.println("textlength = " + a.textlength);
		
	}


}
