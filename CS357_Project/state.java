import java.util.*;

public class state {
	
	ArrayList<transition> transitions = new ArrayList<>();
	private String currInput;
	private int currState;
	
	state(){
		transitions = null;
		currInput = "";
		currState = -1;
	}
	
	state(ArrayList<transition> trans, String input, int state){
		transitions = trans;
		currInput = input;
		currState = state;
	}
	
	public int getState() {
		return this.currState;
	}
	
	public String getInput() {
		return this.currInput;
	}

}
