package model;

public enum Time {
	EarlyMorning("7AM-8AM",1),
	Morning("8AM-12PM",4),
	Afternoon("12PM-3PM",3),
	LateAfternoon("3PM-4PM",1),
	LaterAfternoon("4PM-6PM",2),
	Evening("6PM-10PM",4),
	Open("8AM-3PM",7);
	private final String range;
	private final int length;
	private Time(String str,int length) {
		this.range = str;
		this.length = length;
	}
	public String toString() {
		return range;
	}
	public int getLength() {
		return length;
	}
}

