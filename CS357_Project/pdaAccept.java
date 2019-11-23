import java.io.*;
import java.util.*;

public class pdaAccept {
	
	static Queue<state> states = new LinkedList<state>();
	static Boolean done = false;
	static Boolean accept = false;
	static String[] endStates;

	public static void main(String[] args) {
		File text = new File("/Users/dylandegrood/Documents/GitHub/CS357_PDA/CS357_Project/input.txt");

		int totalStates;
		String[] alphabet;
		String[] stackAlphabet;
		int startState;
		String[] tempTrans;
		ArrayList<transition> transitions = new ArrayList<>();
		String inputString;
		state beginState;
		Stack<String> stack = new Stack<>();

		Scanner scnr;
		try {
			scnr = new Scanner(text);
			// Reading each line of file using Scanner class

			totalStates = scnr.nextInt();
			System.out.println(totalStates);
			scnr.nextLine();

			alphabet = scnr.nextLine().split(" ");

			stackAlphabet = scnr.nextLine().split(" ");
			for (int i = 0; i < stackAlphabet.length; i++) {
				System.out.println("" + stackAlphabet[i]);
			}

			startState = scnr.nextInt();
			scnr.nextLine();

			endStates = scnr.nextLine().split(" ");
			for(int i = 0; i < endStates.length; i++) {
				System.out.println("" + endStates[i]);
			}
						
			
	        int lineNumber = 1;
	        while(scnr.hasNextLine()){
	        	
	            String line = scnr.nextLine();
	            System.out.println("line " + lineNumber + " :" + line);
	            lineNumber++;
	            
	            tempTrans = line.split(" ");
	        
	            transition temp = new transition(Integer.parseInt(tempTrans[0]), tempTrans[1], tempTrans[2], Integer.parseInt(tempTrans[3]), tempTrans[4]);
	            
				transitions.add(temp);
	          
	        }
	        
	        scnr.close();

			Scanner scanner = new Scanner(System.in);
			System.out.println("Please Input String to Test: ");
            inputString = scanner.nextLine();
			//Setting up first state
			//stack.push("$");
            beginState = new state(transitions, inputString, startState,stack);
			states.add(beginState);

			scanner.close();
	        
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		checkString();
		if(accept){
			System.out.println("String was Accepted!");
		}
		else {
			System.out.println("String was NOT Accepted!");
		}

	}

	public static void checkString() {
		
		while(!done){
			state toLook = states.remove();

			/**
			 * BUG:
			 * BREAKS IF MORE 1's than 0's afterwards
			 * So like 00111 should not accept, but it breaks because it continues
			 * And if 1's are before 0's so probably also if its a mix of 1's and 0's
			 * The language of the current PDA is 0^n 1^n
			 */

			//for loop through the transitions
			//for each transition we can do based off of toLook state
			//create new state and add to queue
			for (transition currTransition : toLook.transitions) {
				if(toLook.getState() == currTransition.getCurr()){
					//will be in here if current state matches current state
					//of transition

					if(toLook.getInput().matches("")){
						if(currTransition.getInput().matches("e")){
							if(currTransition.getPop().matches("e")){
								String tempInput = toLook.getInput();
								state tempState = new state();
								tempState.currStack = toLook.currStack;
								tempState.currStack.push(currTransition.getPush());
								tempState.setInput(tempInput);
								tempState.setState(currTransition.getDest());
								tempState.transitions = toLook.transitions;
								states.add(tempState);
							}
							else if(currTransition.getPush().matches("e")){
								if(currTransition.getPop().equals(toLook.currStack.peek())){
									String tempInput = toLook.getInput();
									state tempState = new state();
									tempState.currStack = toLook.currStack;
									tempState.currStack.pop();
									tempState.setInput(tempInput);
									tempState.setState(currTransition.getDest());
									tempState.transitions = toLook.transitions;
									states.add(tempState);
								}
							}
						}
						else{
							continue;
						}
						
					}
					else if(currTransition.getInput().matches(toLook.getInput().substring(0, 1))){
						//will be here is for example read a '0' and input for
						//transition is '0'
						if(currTransition.getPop().matches("e")){
							String tempInput = toLook.getInput().substring(1,toLook.getInput().length());
							state tempState = new state();
							tempState.currStack = toLook.currStack;
							tempState.currStack.push(currTransition.getPush());
							tempState.setInput(tempInput);
							tempState.setState(currTransition.getDest());
							tempState.transitions = toLook.transitions;
							states.add(tempState);
						}
						else if(currTransition.getPush().matches("e")){
							if(currTransition.getPop().matches(toLook.currStack.peek())){
								String tempInput = toLook.getInput().substring(1,toLook.getInput().length());
								state tempState = new state();
								tempState.currStack = toLook.currStack;
								tempState.currStack.pop();
								tempState.setInput(tempInput);
								tempState.setState(currTransition.getDest());
								tempState.transitions = toLook.transitions;
								states.add(tempState);
							}
						}
						
					}
					else if(currTransition.getInput().matches("e")){
						//will be here if the transition is empty
						//check pop or push and do that
						//set currState
						//add that new state to queue
						if(currTransition.getPop().matches("e")){
							String tempInput = toLook.getInput();
							state tempState = new state();
							tempState.currStack = toLook.currStack;
							tempState.currStack.push(currTransition.getPush());
							tempState.setInput(tempInput);
							tempState.setState(currTransition.getDest());
							tempState.transitions = toLook.transitions;
							states.add(tempState);
						}
						else if(currTransition.getPush().matches("e")){
							if(currTransition.getPop().matches(toLook.currStack.peek())){
								String tempInput = toLook.getInput();
								state tempState = new state();
								tempState.currStack = toLook.currStack;
								tempState.currStack.pop();
								tempState.setInput(tempInput);
								tempState.setState(currTransition.getDest());
								tempState.transitions = toLook.transitions;
								states.add(tempState);
							}
						}

					}
					
				}
			}

			if(toLook.getInput().matches("")){
				//check to see if in finished state, set accept to true if yes
				//and done to true
				for(int i = 0; i < endStates.length; i++){
					if(toLook.getState() == Integer.parseInt(endStates[i])){
						accept = true;
						done = true;
					}
				}
				//check to see if stack is empty, set accept to true if yes
				//and done to true
				if(toLook.currStack.empty() && !accept){
					accept = true;
					done = true;
				}

				//otherwise leave accept as is and set done to true
				done = true;
			}

		}

		//return accept;
	}

}
