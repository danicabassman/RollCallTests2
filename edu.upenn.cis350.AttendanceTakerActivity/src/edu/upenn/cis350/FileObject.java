/**
 * Represents a student. Holds the students name,
 * student ID, grade, phone number, address, school,
 * site, program, and emergency contact information.
 */

package edu.upenn.cis350;

public class FileObject {

	private String filename;
	boolean box=false;
	
	public FileObject(){
		
	}
	public FileObject(String name) {
		this.filename = name;
	
	}


	public String getName() {
		return filename;
	}

	public void setName(String name) {
		this.filename = name;
	}


}
