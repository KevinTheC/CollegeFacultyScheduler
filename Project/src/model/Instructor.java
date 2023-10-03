package model;

import java.util.ArrayList;
import java.util.List;
public class Instructor {
	public enum Rank implements Comparable<Rank>{A3,A2,A1;
		public static final Rank parse(String str) throws IllegalArgumentException{
			if (str.equals("A1")) return A1; else if (str.equals("A2")) return A2; else if (str.equals("A3")) return A3;
			else throw new IllegalArgumentException("Malformed string for parsing into ranking. String: " + str);
		}};
	private int ID;
	private Campus home;
	private String cell;
	private String name;
	private String homePhone;
	private String address;
	private String hireDate;
	private ArrayList<Course> courses;
	private Rank ranking;
	private boolean online;
	private ArrayList<Campus> preferred;
	private int courseCount;
	private Availability avl;
	/**
	 * Lazy Constructor, defaults all values to null
	 */
	public Instructor(Parser<Instructor> parser,List<String> strings) {
		courses = new ArrayList<Course>();
		preferred = new ArrayList<Campus>();
		avl = new Availability();
		parser.apply(this,strings);
	}
	public String toString() {
		return "Information: [ID: "+ID+", Cell: "+cell+", Home Phone: "+homePhone+", Name: \""
				+name+"\", Address: \""+address+"\"]"+
				"\nSchool Info: [Home Campus: "+home+", Hire Date: "+hireDate+", Ranking: "+ranking+", Course Count: "+courseCount+"]"+
				"\nPreferred Campus: "+preferred+
				"\nCourses: "+courses+
				"\nAvailability: "+avl;
	}
	public int getID() {
		return ID;
	}
	public void setID(int ID) {
		this.ID = ID;
	}
	public Campus getHomeCampus() {
		return home;
	}
	public void setHomeCampus(Campus homeCampus) {
		this.home = homeCampus;
	}
	public String getCell() {
		return cell;
	}
	public void setCell(String cell) {
		this.cell = cell;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHomePhone() {
		return homePhone;
	}
	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getHireDate() {
		return hireDate;
	}
	public void setHireDate(String hireDate) {
		this.hireDate = hireDate;
	}
	public ArrayList<Course> getCourses(){
		return courses;
	}
	public Rank getRank() {
		return ranking;
	}
	public void setRank(Rank rank) {
		this.ranking = rank;
	}
	public boolean getOnline() {
		return online;
	}
	public void setOnline(boolean online) {
		this.online = online;
	}
	public ArrayList<Campus> getPreferences(){
		return preferred;
	}
	public int getCourseCount() {
		return courseCount;
	}
	public void setCourseCount(int courseCount) {
		this.courseCount = courseCount;
	}
	public Availability getAvailability() {
		return avl;
	}
}