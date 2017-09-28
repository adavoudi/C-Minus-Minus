package Core;

import java.util.Hashtable;

public class Scope {

	private static int currentAddr;
	private Scope parent;
	private Hashtable<String, Integer> vars = new Hashtable<String, Integer>();

	static {
		currentAddr = 0;
	}

	public Scope(Scope parent) {
		this.parent = parent;
	}

	public int findAddr(String input) throws ParseException {

		Scope t = this;
		while (t != null) {
			if (t.getVars().containsKey(input)) {
				return t.getVars().get(input);
			}
			t = t.getParent();
		}

		throw new ParseException("Unknown variable '" + input
				+ "'.define it first.");
	}

	public int makeAddr(String input) throws ParseException {

		Scope t = this;
		while (t != null) {
			if (t.getVars().containsKey(input)) {
				throw new ParseException("Variable '" + input
						+ "' has already been defined.");
			}
			t = t.getParent();
		}

		vars.put(input, currentAddr);

		int temp = currentAddr;
		// 2 bytes for every variable
		currentAddr += 2;

		return temp;
	}

	public static int getCurrentAddr() {
		return currentAddr;
	}

	public static void setCurrentAddr(int currentAddr) {
		Scope.currentAddr = currentAddr;
	}

	public Scope getParent() {
		return parent;
	}

	public void setParent(Scope parent) {
		this.parent = parent;
	}

	public Hashtable<String, Integer> getVars() {
		return vars;
	}

	public void setVars(Hashtable<String, Integer> vars) {
		this.vars = vars;
	}

}
