import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

public class pdaAccept {
	
	static Queue<state> states = new LinkedList<state>();

	public static void main(String[] args) {
		File text = new File("/Users/dylandegrood/Documents/GitHub/CS357_Project/CS357_Project/CS352_Proj/src/input.txt");

		int totalStates;
		String[] alphabet;
		String[] stackAlphabet;
		int startState;
		String[] endStates;
		String[] tempTrans;
		ArrayList<transition> transitions = new ArrayList<>();
		String inputString;
		state tempState;
		
		
		Scanner scnr;
		try {
			scnr = new Scanner(text);
			//Reading each line of file using Scanner class
			
			totalStates = scnr.nextInt();
			System.out.println(totalStates);
			scnr.nextLine();
			
			alphabet = scnr.nextLine().split(" ");
			
			stackAlphabet = scnr.nextLine().split(" ");
			for(int i = 0; i < stackAlphabet.length; i++) {
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
            tempState = new state(transitions, inputString, startState);
            states.add(tempState);
	        
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

}
