/**
 * pdaAccept.java
 * 
 * Authors: Dylan DeGrood, Mikey Antkiewicz
 * 
 * Takes in an input file with a PDA description
 * and asks user for input string and runs that string
 * with the PDA description and returns whether or not
 * it accepts or rejects
 * 
 */

import java.io.*;
import java.util.*;

public class pdaAccept {

	static Queue<state> states = new LinkedList<state>();
	static Boolean done = false;
	static Boolean accept = false;
	static String[] endStates;

	public static void main(String[] args) {
		File text = new File("/Users/dylandegrood/Documents/GitHub/CS357_PDA/CS357_Project/input3.txt");

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

			scnr.nextLine();

			alphabet = scnr.nextLine().split(" ");

			stackAlphabet = scnr.nextLine().split(" ");

			startState = scnr.nextInt();
			// Error check the input of start state
			// Throws Exception for strings at declaration above
			// otherwise if its not a number in scope throw our own error
			if(startState > totalStates-1 || startState < 0){
				System.out.println();
				System.out.println("ERROR:");
				System.out.println("Start State is Incorrect");
				System.out.println("See Format of Input File and Reformat");
				System.out.println();
				scnr.close();
				return;
			}
			scnr.nextLine();

			endStates = scnr.nextLine().split(" ");
			// Error check the end states
			// Throws Exception for strings at declaration above
			// otherwise if its not a number in scope throw our own error
			for(int i = 0; i < endStates.length; i++){
				if(Integer.parseInt(endStates[i]) > totalStates-1 || Integer.parseInt(endStates[i]) < 0){
					System.out.println();
					System.out.println("ERROR:");
					System.out.println("Incorrect End States");
					System.out.println("See Format of Input File and Reformat");
					System.out.println();
					scnr.close();
					return;
				}
			}

			while (scnr.hasNextLine()) {

				String line = scnr.nextLine();

				tempTrans = line.split(" ");
				// Test check to see if the transition line
				// has all needed characters
				if(tempTrans.length != 5){
					System.out.println();
					System.out.println("ERROR:");
					System.out.println("Incorrect Length of Transition");
					System.out.println("See Format of Input File and Reformat");
					System.out.println();
					scnr.close();
					return;
				}
				// Next 2 for loops check whether input transition
				// char is a valid alphabet char and the push and
				// pop char are a valid stack alphabet all according
				// to the language
				int notAlphabetFault = 0;
				int notStackFault = 0;
				for(int i = 0; i < alphabet.length; i++){
					if(alphabet[i].equals(tempTrans[1])){
						notAlphabetFault++;
					}
					else if(tempTrans[1].equals("e") && notAlphabetFault != 1){
						notAlphabetFault++;
					}
				}
				for(int i = 0; i < stackAlphabet.length; i++){
					if (stackAlphabet[i].equals(tempTrans[2])) {
						notStackFault++;
					} 
					else if (tempTrans[2].equals("e") && notStackFault != 1) {
						notStackFault++;
					}
					if (stackAlphabet[i].equals(tempTrans[4])) {
						notStackFault++;
					} 
					else if (tempTrans[4].equals("e") && notAlphabetFault != 2) {
						notStackFault++;
					}
				}
				// throws the error if transition alphabet or stack fault
				if(notAlphabetFault == 0 || notStackFault < 2){
					System.out.println();
					System.out.println("ERROR:");
					System.out.println("Incorrect Alphabet/Stack Character in Transition");
					System.out.println("See Format of Input File and Reformat");
					System.out.println();
					scnr.close();
					return;
				}
				// Checking to see if the currState/destState of the transition is
				// within the total amount of states allowed
				if (Integer.parseInt(tempTrans[0]) > totalStates - 1 || Integer.parseInt(tempTrans[0]) < 0
						|| Integer.parseInt(tempTrans[3]) > totalStates - 1 || Integer.parseInt(tempTrans[3]) < 0) {
					System.out.println();
					System.out.println("ERROR:");
					System.out.println("Incorrect Current State or Destination State in Transition");
					System.out.println("See Format of Input File and Reformat");
					System.out.println();
					scnr.close();
					return;
				}
				transition temp = new transition(Integer.parseInt(tempTrans[0]), tempTrans[1], tempTrans[2],
						Integer.parseInt(tempTrans[3]), tempTrans[4]);
				// tempTrans[0] currState, tempTrans[1] input, tempTrans[2] Pop char
				// tempTrans[3] destState, tempTrans[4] Push char

				transitions.add(temp);

			}

			scnr.close();

			Scanner scanner = new Scanner(System.in);
			System.out.println("Please Input String to Test: ");
			inputString = scanner.nextLine();
			// Setting up first state
			beginState = new state(transitions, inputString, startState, stack);
			states.add(beginState);// adds the first state

			scanner.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// Calls the method to check the string by going through
		// the transitions and the stack nondeterministically
		// choosing all paths to see if one accepts
		checkString();
		if (accept) {
			System.out.println("String was ACCEPTED!");
		} else {
			System.out.println("String was REJECTED!");
		}

	}

	// Sets accept true if there was one path that accepted
	public static void checkString() {

		while (!done) {
			state toLook = states.remove();

			// for loop through the transitions
			// for each transition we can do based off of toLook state
			// create new state and add to queue
			for (transition currTransition : toLook.transitions) {
				if (toLook.getState() == currTransition.getCurr()) {

					if (toLook.getInput().matches("")) {
						if (currTransition.getInput().matches("e")) {
							if (currTransition.getPop().matches("e")) {
								String tempInput = toLook.getInput();
								state tempState = new state();
								tempState.currStack = (Stack<String>) toLook.currStack.clone();
								tempState.currStack.push(currTransition.getPush());
								tempState.setInput(tempInput);
								tempState.setState(currTransition.getDest());
								tempState.transitions = toLook.transitions;
								states.add(tempState);
							} else if (currTransition.getPush().matches("e")) {
								if (currTransition.getPop().equals(toLook.currStack.peek())) {
									String tempInput = toLook.getInput();
									state tempState = new state();
									tempState.currStack = (Stack<String>) toLook.currStack.clone();
									tempState.currStack.pop();
									tempState.setInput(tempInput);
									tempState.setState(currTransition.getDest());
									tempState.transitions = toLook.transitions;
									states.add(tempState);
								}
							}
						} else {
							continue;
						}

					} else if (currTransition.getInput().matches(toLook.getInput().substring(0, 1))) {
						// will be here is for example read a '0' and input for
						// transition is '0'
						if (currTransition.getPop().matches("e") && !currTransition.getPush().matches("e")) {
							String tempInput = toLook.getInput().substring(1, toLook.getInput().length());
							state tempState = new state();
							tempState.currStack = (Stack<String>) toLook.currStack.clone();
							tempState.currStack.push(currTransition.getPush());
							tempState.setInput(tempInput);
							tempState.setState(currTransition.getDest());
							tempState.transitions = toLook.transitions;
							states.add(tempState);
						} else if (currTransition.getPush().matches("e") && !currTransition.getPop().matches("e")) {
							if (currTransition.getPop().matches(toLook.currStack.peek())) {
								String tempInput = toLook.getInput().substring(1, toLook.getInput().length());
								state tempState = new state();
								tempState.currStack = (Stack<String>) toLook.currStack.clone();
								tempState.currStack.pop();
								tempState.setInput(tempInput);
								tempState.setState(currTransition.getDest());
								tempState.transitions = toLook.transitions;
								states.add(tempState);
							}
						} else if (currTransition.getPush().matches("e") && currTransition.getPop().matches("e")){
							String tempInput = toLook.getInput().substring(1, toLook.getInput().length());
							state tempState = new state();
							tempState.currStack = (Stack<String>) toLook.currStack.clone();
							tempState.setInput(tempInput);
							tempState.setState(currTransition.getDest());
							tempState.transitions = toLook.transitions;
							states.add(tempState);
						}

					} else if (currTransition.getInput().matches("e")) {
						// will be here if the transition is empty
						// check pop or push and do that
						// set currState
						// add that new state to queue
						if (currTransition.getPop().matches("e") && !currTransition.getPush().matches("e")) {
							String tempInput = toLook.getInput();
							state tempState = new state();
							tempState.currStack = (Stack<String>) toLook.currStack.clone();
							tempState.currStack.push(currTransition.getPush());
							tempState.setInput(tempInput);
							tempState.setState(currTransition.getDest());
							tempState.transitions = toLook.transitions;
							states.add(tempState);
						} else if (currTransition.getPush().matches("e") && !currTransition.getPop().matches("e")) {
							if (currTransition.getPop().matches(toLook.currStack.peek())) {
								String tempInput = toLook.getInput();
								state tempState = new state();
								tempState.currStack = (Stack<String>) toLook.currStack.clone();
								tempState.currStack.pop();
								tempState.setInput(tempInput);
								tempState.setState(currTransition.getDest());
								tempState.transitions = toLook.transitions;
								states.add(tempState);
							}
						} else if (currTransition.getPush().matches("e") && currTransition.getPop().matches("e")){
							String tempInput = toLook.getInput();
							state tempState = new state();
							tempState.currStack = (Stack<String>) toLook.currStack.clone();
							tempState.setInput(tempInput);
							tempState.setState(currTransition.getDest());
							tempState.transitions = toLook.transitions;
							states.add(tempState);
						}

					}

				}
			}

			if (toLook.getInput().matches("")) {
				// check to see if in finished state, set accept to true if yes
				// and done to true
				for (int i = 0; i < endStates.length; i++) {
					if (toLook.getState() == Integer.parseInt(endStates[i])) {
						accept = true;
						done = true;
					}
				}
				// check to see if stack is empty, set accept to true if yes
				// and done to true
				if (toLook.currStack.empty() && !accept) {
					accept = true;
					done = true;
				}
			}

			if (states.isEmpty()) {
				done = true;
			}

		}

	}

}
