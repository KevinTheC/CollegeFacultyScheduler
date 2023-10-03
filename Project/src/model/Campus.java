package model;

	public enum Campus {AMMERMAN, EAST, GRANT, ONLINE;
		public static final Campus parse(char c) throws IllegalArgumentException{
			if (c=='A') return AMMERMAN; else if (c=='E') return EAST; else if (c=='W'||c=='G') return GRANT; else if (c=='O') return ONLINE;
			else throw new IllegalArgumentException("Malformed char for parsing into Campus. Char: " + c);
		}};
