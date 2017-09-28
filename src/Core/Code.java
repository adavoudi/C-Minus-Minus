package Core;

public class Code {

	private String part1 ,part2, part3;

	public Code(Object part1, Object part2, Object part3) {
		super();
		
		if(part1 != null){
			this.part1 = "" + part1;
		}
		if(part2 != null){
			this.part2 = "" + part2;
		}
		if(part3 != null){
			this.part3 = "" + part3;
		}
	}

	public String getPart1() {
		return part1;
	}

	public void setPart1(String part1) {
		this.part1 = part1;
	}

	public String getPart2() {
		return part2;
	}

	public void setPart2(String part2) {
		this.part2 = part2;
	}

	public String getPart3() {
		return part3;
	}

	public void setPart3(String part3) {
		this.part3 = part3;
	}

	@Override
	public String toString() {
		StringBuilder output = new StringBuilder();
//		output.append("[");
		
		if(part1 != null){
			output.append(part1);
		}
		if(part2 != null){
			output.append(" " + part2);
		}
		if(part3 != null){
			output.append(", " + part3);
		}
		
//		output.append("]");
		
		return output.toString();
	}

	
	
}
