package model;

public enum Part {
	FE,
	FD,
	SS,
	LSD,
	ONL,
	LSE;
	final public static Part parse(String str) {
		for (Part p : Part.values())
			if (p.name().equals(str))
				return p;
		return null;
	}
}
