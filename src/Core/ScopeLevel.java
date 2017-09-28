package Core;

import java.util.Stack;

public class ScopeLevel {
	private Stack<Scope> currentScope = new Stack<Scope>();

	public ScopeLevel() {
	}

	public static int getCurrentAddr(){
		return Scope.getCurrentAddr();
	}
	
	public static void setCurrentAddr(int currentAddr){
		Scope.setCurrentAddr(currentAddr);
	}
	
	public void addScope() {
		Scope s = null;
		if(currentScope.isEmpty()){
			s =  new Scope(null);
		}else{
			s =  new Scope(currentScope.peek());
		}
		currentScope.add(s);
	}

	public void deleteScope() {
		currentScope.pop();
	}

	public int addVariable(String input) throws ParseException {
		return currentScope.peek().makeAddr(input);
	}

	public int findVariable(String input) throws ParseException {
		return currentScope.peek().findAddr(input);
	}
}
