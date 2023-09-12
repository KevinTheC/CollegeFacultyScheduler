package Q1;

import java.util.ArrayList;
import java.util.function.Function;
public class Instructor {
	private enum cols{
		A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P;
	}
	public enum Campus {AMMERMAN, EAST, GRANT, ONLINE;
		public static final Campus parse(char c) throws IllegalArgumentException{
			if (c=='A') return AMMERMAN; else if (c=='E') return EAST; else if (c=='W'||c=='G') return GRANT; else if (c=='O') return ONLINE;
			else throw new IllegalArgumentException("Malformed char for parsing into Campus. String: " + c);
		}};
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
	private Address address;
	private String hireDate;
	private ArrayList<Course> courses;
	private Rank ranking;
	private boolean online;
	private ArrayList<Campus> preferred;
	private int courseCount;
	private Availability avl;
	public Instructor(ArrayList<String> strs, char seperator){
		String[][] lines = new String[strs.size()][];
		for (int i=0;i<lines.length;i++)
			lines[i]=strs.get(i).split(seperator+"(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
		for (int i = 0;i<lines.length;i++)
			for (int j = 0;j<lines[i].length;j++)
				lines[i][j] = lines[i][j].replaceAll("(\")", "");
		
		ID = Integer.parseInt(lines[0][cols.A.ordinal()]);
		home = Campus.parse(lines[1][cols.A.ordinal()].charAt(0));
		cell = lines[2][cols.A.ordinal()];
		
		
		name = lines[0][cols.B.ordinal()];
		address = new Address(lines[1][cols.B.ordinal()],lines[2][cols.B.ordinal()]);
		
		
		homePhone = lines[0][cols.C.ordinal()];
		hireDate = lines[1][cols.C.ordinal()];
		courses = new ArrayList<>();
		String[] crsstr = lines[2][cols.C.ordinal()].split("[ ]");
		for (String str : crsstr)
			courses.add(new Course(str));
		if (lines.length==4&&lines[3].length>2) {
			crsstr = lines[3][cols.C.ordinal()].split("[ ]");
			for (String str : crsstr)
				courses.add(new Course(str));
		}
		
		ranking = Rank.parse(lines[0][cols.D.ordinal()]);
		
		if (lines[0][cols.E.ordinal()].equals("Y"))
			online = true;
		else
			online = false;
		
		preferred = new ArrayList<>();
		String[] arr = lines[0][cols.F.ordinal()].split("[ ]");
		for (String str : arr) {
			Campus c = Campus.parse(str.charAt(0));
			if (!preferred.contains(c))
				preferred.add(c);
		}
		
		courseCount = 1;
		if (lines[0][cols.G.ordinal()].equals("Y"))
			++courseCount;
		if (lines[1][cols.G.ordinal()].equals("Y"))
			++courseCount;
		
		avl = new Availability();
		loopThruLines(lines,cols.I.ordinal(),(String str)->{if (str.charAt(0)=='*') return Time.EarlyMorning; return Time.Morning;});
		loopThruLines(lines,cols.J.ordinal(),(String str)->{if (str.charAt(0)=='*') return Time.LateAfternoon; return Time.Afternoon;});
		loopThruLines(lines,cols.K.ordinal(),(String str)->{return Time.Open;});
		loopThruLines(lines,cols.L.ordinal(),(String str)->{return Time.LaterAfternoon;});
		loopThruLines(lines,cols.M.ordinal(),(String str)->{return Time.Evening;});
	}
	private void loopThruLines(String[][] lines,int index,Function<String,Time> f) {
		for (int row = 0;row<lines.length;row++) {
			if (lines[row].length>index
					&&lines[row][index].length()>0) {
				parseAvailability(lines[row][index],
					f.apply(lines[row][index]));
			}
		}
	}
	private void parseAvailability(String str,Time t) {
		for (int i=0;i<str.length();i++) {
			if (str.charAt(i)==' '||str.charAt(i)=='*')
				continue;
			else if (str.charAt(i)=='S') {
				avl.put(t, Day.parse(str.substring(i,i+3)));
				i+=2;
			} else if (str.charAt(i)=='T'){
				if (i>2) {
					avl.put(t, Day.T);
				}
				else
					avl.put(t, Day.R);
			} else {
				avl.put(t, Day.parse(str.charAt(i)+""));
			}
		}
	}
	public String toString() {
		return "Information: [ID: "+ID+", Cell: "+cell+", Home Phone: "+homePhone+", Name: "
				+name+", Address: "+address+"]"+
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
	public void getHomeCampus(Campus homeCampus) {
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
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
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
	public boolean isOnline() {
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
