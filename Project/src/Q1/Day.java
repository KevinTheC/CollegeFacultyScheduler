package Q1;

public enum Day {
	M,
	T,
	W,
	R,
	F,
	S,
	U;
	public final static Day parse(String str) throws IllegalArgumentException{
		if (str.equals("Sun"))
			return U;
		else if (str.equals("Sat"))
			return S;
		switch (str.charAt(0)) {
		case 'M':	return M;
		case 'T':	return T;
		case 'W':	return W;
		case 'R':	return R;
		case 'F':	return F;
		default:	throw new IllegalArgumentException("Malformed string for parsing into Day. String: " + str);
		}
	}
};