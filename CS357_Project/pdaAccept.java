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
			stack.push("$");
            beginState = new state(transitions, inputString, startState,stack);
			states.add(beginState);

			scanner.close();
	        
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
		
	}

	public boolean checkString() {
		
		while(!done){
			state toLook = states.remove();

			if(toLook.getInput().isEmpty()){
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

			//for loop through the transitions
			//for each transition we can do based off of toLook state
			//create new state and add to queue
			for (transition currTransition : toLook.transitions) {
				if(toLook.getState() == currTransition.getCurr()){
					if(currTransition.getInput() == toLook.getInput().substring(0, 1)){
						
					}
					
					//state tempState = new state(toLook.transitions, input, state, stack)
				}
			}

		}

		return accept;
	}

}
