/**
 * Represents a student. Holds the students name,
 * student ID, grade, phone number, address, school,
 * site, program, and emergency contact information.
 */

package edu.upenn.cis350;

public class StudentObject {

	private String ID;
	private int Parse_1112GradeLevel;
	private String Name;
	private String phoneNumber;
	private String Address;
	private String School;
	private String Site;
	private String Program;
	private String Comment;
	private String emergencyContactName;
	private String emergencyContactRelationship;
	private String attendance_status;
	private String comments;
	boolean box=false;
	
	public StudentObject(){
		
	}
	public StudentObject(String studentid, int gradeLevel, String name,
			String phone, String address, String school, String site,
			String program,String comment, String contactName, String contactType) {
		this.ID = studentid;
		this.Parse_1112GradeLevel = gradeLevel;
		this.Name = name;
		this.phoneNumber = phone;
		this.Address = address;
		this.School = school;
		this.Site = site;
		this.Program = program;
		this.Comment = comment;
		this.emergencyContactName = contactName;
		this.emergencyContactRelationship = contactType;
		this.attendance_status = "In";
		this.setComments("");
	}
	
	public StudentObject(String studentid, String name){
		this.ID = studentid;
		this.Name = name;
	}
	
	public StudentObject(String name){
		this.Name = name;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		this.Name = name;
	}

	public String getPhone() {
		return phoneNumber;
	}

	public void setPhone(String phone) {
		this.phoneNumber = phone;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		this.Address = address;
	}

	public String getStudentid() {
		return ID;
	}

	public void setStudentid(String studentid) {
		this.ID = studentid;
	}

	public String getSite() {
		return Site;
	}

	public void setSite(String site) {
		this.Site = site;
	}

	public int getGradeLevel() {
		return Parse_1112GradeLevel;
	}

	public void setGradeLevel(int gradeLevel) {
		this.Parse_1112GradeLevel = gradeLevel;
	}

	public String getSchool() {
		return School;
	}

	public void setSchool(String school) {
		this.School = school;
	}

	public String getProgram() {
		return Program;
	}
	
	public void setProgram(String program) {
		this.Program = program;
	}
	
	public String getComment() {
		return Comment;
	}

	public void setComment(String comment) {
		this.Comment = comment;
	}

	public String getContactName() {
		return emergencyContactName;
	}

	public void setContactName(String contactName) {
		this.emergencyContactName = contactName;
	}

	public String getContactType() {
		return emergencyContactRelationship;
	}

	public void setContactType(String contactType) {
		this.emergencyContactRelationship = contactType;
	}

	public void setStatus(String status) {
		this.attendance_status = status;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	public String getStatus() {
		return attendance_status;
	}
}
