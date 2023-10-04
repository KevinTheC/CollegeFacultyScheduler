package model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;
public class Instructor {
	
	public static class InstructorFactory{
		private final static TreeMap<String,Instructor> byName = new TreeMap<>();
		/**
		 * 
		 * @param ID
		 * @param subject
		 * @return
		 */
		public static Instructor getInstance(String name){
			if (!byName.containsKey(name))
				byName.put(name, new Instructor(name));
			return byName.get(name);
		}
		public static Instructor getInstance(Parser<Instructor> parser, List<String> strings) {
			Instructor ints = new Instructor(parser,strings);
			if (byName.containsKey(ints.name))
				return byName.get(ints.name);
			byName.put(ints.name, ints);
			return ints;
		}
		public static Instructor getInstance(Parser<Instructor> parser, String string) {
			ArrayList<String> strings = new ArrayList<>();
			strings.add(string);
			return getInstance(parser,strings);
		}
	}
	
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
	private List<Course> courses;
	private Rank ranking;
	private boolean online;
	private List<Campus> preferred;
	private final static Comparator<Course> comp = new Comparator<>() {
		@Override
		public int compare(Course o1, Course o2) {
			return Integer.compare(o1.hashCode(), o2.hashCode());
		}};
	private TreeMap<Course,Integer> weights;
	private int courseCount;
	private Availability avl;
	/**
	 * Lazy Constructor, defaults all values to null
	 */
	private Instructor(String name) {
		this.name = name;
		this.ID = -1;
		this.online = false;
		this.courseCount = -1;
		courses = new LinkedList<>();
		preferred = new LinkedList<>();
		weights = new TreeMap<>(comp);
		avl = new Availability();
	}
	private Instructor(Parser<Instructor> parser, List<String> strings) {
		courses = new LinkedList<>();
		preferred = new LinkedList<>();
		weights = new TreeMap<>(comp);
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
	public List<Course> getCourses(){
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
	public List<Campus> getPreferences(){
		return preferred;
	}
	public TreeMap<Course,Integer> getWeights(){
		return weights;
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