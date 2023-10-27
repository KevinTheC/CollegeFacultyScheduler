package model;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Stream;

import model.IO.GenericReader;
import model.IO.SVReader;
public class Instructor implements Externalizable{
	private static final long serialVersionUID = -8265275038000714197L;
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
		public static Instructor getInstance(GenericReader<Instructor> parser, List<String> strings) {
			Instructor ints = new Instructor(parser,strings);
			if (byName.containsKey(ints.name))
				return byName.get(ints.name);
			byName.put(ints.name, ints);
			return ints;
		}
		public static Instructor getInstance(SVReader<Instructor> parser, String string) {
			ArrayList<String> strings = new ArrayList<>();
			strings.add(string);
			return getInstance(parser,strings);
		}
		public static Stream<Instructor> getStream(){
			List<Instructor> list = new LinkedList<>();
			byName.entrySet().forEach((i)->{list.add(i.getValue());});
			return list.stream();
		}
	}
	
	public static enum Rank implements Comparable<Rank>{A3,A2,A1;
		public static final Rank parse(String str) throws IllegalArgumentException{
			if (str.equals("A1")) return A1; else if (str.equals("A2")) return A2; else if (str.equals("A3")) return A3;
			else throw new IllegalArgumentException("Malformed string for parsing into ranking. String: " + str);
		}};
	private static class MyComparator implements Comparator<Course>, Serializable{
		private static final long serialVersionUID = 5480471523903517484L;
		@Override
		public int compare(Course o1, Course o2) {
			return Integer.compare(o1.hashCode(), o2.hashCode());
		}
	}
	private int ID;
	private Campus home;
	private String cell;
	private String name;
	private String homePhone;
	private String address;
	private String hireDate;
	private Set<Course> courses;
	private Set<Section> sections;
	private Rank ranking;
	private boolean online;
	private Set<Campus> preferred;
	private TreeMap<Course,Integer> weights;
	private int courseCount;
	private Availability<Section> avl;
	/**
	 * Exists because of externalizable implementation
	 */
	public Instructor() {}
	private Instructor(String name) {
		this.name = name;
		this.ID = -1;
		this.online = false;
		this.courseCount = -1;
		courses = new HashSet<>();
		preferred = new HashSet<>();
		sections = new HashSet<>();
		weights = new TreeMap<>(new MyComparator());
		avl = new Availability<>();
	}
	private Instructor(GenericReader<Instructor> parser, List<String> strings) {
		courses = new HashSet<>();
		preferred = new HashSet<>();
		sections = new HashSet<>();
		weights = new TreeMap<>(new MyComparator());
		avl = new Availability<>();
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
	public Set<Course> getCourses(){
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
	public Set<Campus> getPreferences(){
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
	public Availability<Section> getAvailability() {
		return avl;
	}
	public void addSection(Section src) {
		Day[] arr = (Day[])src.getDays().toArray();
		avl.put(src, src.getBegin(), src.getEnd(), arr);
		sections.add(src);
	}
	public void removeSection(Section src) {
		Day[] arr = (Day[])src.getDays().toArray();
		avl.remove(src, src.getBegin(), src.getEnd(), arr);
		sections.remove(src);
	}
	public Stream<Section> getSections(){
		return sections.stream();
	}
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeInt(ID);
		out.writeObject(home);
		String[] arr = {cell,name,homePhone,address,hireDate};
		for (String str : arr)
			if (str==null)
				out.writeUTF("");
			else
				out.writeUTF(str);
		out.writeObject(courses);
		out.writeObject(sections);
		out.writeObject(ranking);
		out.writeBoolean(online);
		out.writeObject(preferred);
		out.writeObject(weights);
		out.writeInt(courseCount);
		out.writeObject(avl);
	}
	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		
	}
}