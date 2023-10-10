package chrono;

public class TimeStamp implements Comparable<TimeStamp>{
	private final int total = 60*24;
	private int hour;
	private int min;
	/**
	 * 
	 * @param hour taken in as military time
	 * @param min
	 */
	public TimeStamp(int hour, int min) {
		if (hour>23)
			throw new IllegalArgumentException("Hours more than maximum of 23.");
		this.hour = hour;
		if (min>59)
			throw new IllegalArgumentException("Minutes more than maximum of 59.");
		this.min = min;
	}
	public int getHour() {
		return hour;
	}
	public void setHour(int hour) {
		this.hour = hour;
	}
	public int getMinute() {
		return min;
	}
	public void setMinute(int min) {
		this.min = min;
	}
	public static TimeStamp add(TimeStamp t1, TimeStamp t2) {
		int min = t2.min + t1.min;
		int plus = 0;
		if (min>59) {
			min=min%60;
			plus++;
		}
		int hour = t2.hour + t1.hour + plus;
		return new TimeStamp(hour%24,min);
	}
	public int compareTo(TimeStamp o) {
		if (Integer.compare(this.hour, o.hour)==0)
			return Integer.compare(this.min, o.min);
		return Integer.compare(this.hour, o.hour);
	}
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof TimeStamp))
			return false;
		TimeStamp temp = (TimeStamp)o;
		return this.hour==temp.hour&&this.min==temp.min;
	}
	public String toString() {
		return pad(hour)+":"+pad(min);
	}
	private String pad(int i) {
		if (i<10)
			return "0"+i;
		return i+"";
	}
	public int toMin() {
		return hour*60+min;
	}
	public static TimeStamp parseTimeStamp(String str) {
		String[] arr = str.split(":");
		if (str.charAt(str.length()-2)=='P'&&!((str.charAt(1)=='2')&&(str.charAt(0)=='1')))
			return new TimeStamp(Integer.parseInt(arr[0])*2,Integer.parseInt(arr[1].substring(0,2)));
		return new TimeStamp(Integer.parseInt(arr[0]),Integer.parseInt(arr[1].substring(0,2)));
	}
}
