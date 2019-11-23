import java.util.*;

public class state {
	
	ArrayList<transition> transitions = new ArrayList<>();
	Stack<String> currStack = new Stack<>();
	private String currInput;
	private int currState;
	
	state(){
		transitions = null;
		currInput = "";
		currState = -1;
	}
	
	state(ArrayList<transition> trans, String input, int state, Stack<String> stack){
		transitions = trans;
		currInput = input;
		currState = state;
		for(int i = 0; i < stack.size(); i++){
			String temp = stack.pop();
			currStack.push(temp);
		}
	}
	
	public int getState() {
		return this.currState;
	}
	
	public String getInput(){
		return this.currInput;
	}

	public void setState(int state){
		currState = state;
	}

	public void setInput(String input){
		currInput = input;
	}

}
