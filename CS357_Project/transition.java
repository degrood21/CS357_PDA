/**
 * transition.java
 * 
 * Authors: Dylan DeGrood, Mikey Antkiewicz
 * 
 * Object class for each transition needed for a given PDA
 * Needs a current state, destination state, input for the char
 * needed to be read, and whether or not to pop or push a char
 * from/on the stack
 * 
 * "e" is used to denote nothing or empty
 * 
 */
public class transition {
	int currState;
	String input;
	String pop;
	int destState;
	String push;
	
	transition(){
		currState = -1;
		input = "";
		pop = "";
		destState = -1;
		push = "";
	}
	
	transition(int curr, String in, String po, int dest, String pu){
		currState = curr;
		input = in;
		pop = po;
		destState = dest;
		push = pu;
	}
	
	public int getCurr(){
		return this.currState;
	}
	public int getDest(){
		return this.destState;
	}
	public String getInput(){
		return this.input;
	}
	public String getPush(){
		return this.push;
	}
	public String getPop(){
		return this.pop;
	}

}
