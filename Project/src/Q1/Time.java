package Q1;

public enum Time {
	EarlyMorning("7AM-8AM"),
	Morning("8AM-12PM"),
	Afternoon("12PM-3PM"),
	LateAfternoon("3PM-4PM"),
	LaterAfternoon("4PM-6PM"),
	Evening("6PM-10PM"),
	Open("8AM-3PM");
	private final String range;
	private Time(String str) {
		this.range = str;
	}
	public String toString() {
		return range;
	}
}
