import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import java.util.Stack;


public class Main {

	static Stack<String> stack;
	static ArrayList<String> fin = new ArrayList<String>();
	static ArrayList<String> fin_time = new ArrayList<String>();
	public static void file_read() {
		BufferedReader br = null; 
		try {
			String sCurrentLine;
			br = new BufferedReader(new FileReader("data.txt"));
			while ((sCurrentLine = br.readLine()) != null) {
			
				long startTime = System.nanoTime();
				new Main(sCurrentLine);
				fin.add(stack.pop());
				long endTime = System.nanoTime();
				double time_s = (endTime - startTime)/1000000000.0;
				
				fin_time.add(time_s+"");
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public static void print_output(ArrayList<String> set) {
		try {
			/*File file = new File("src/out.txt");
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			String newLine = System.getProperty("line.separator");
			Random r = new Random();
			for (int i = 0; i <100000; i++) {
				String a1 = ""+r.nextInt(10000);
				String a2 = ""+r.nextInt(10000);
				String a3 = ""+r.nextInt(10000);
				String a4 = ""+r.nextInt(10000);
				String a = "( "+ "* "+a1+" ( " + "/ "+a2+" ( - "+ a4+" "+a3+" ) )";
				bw.write(a+newLine);	
			}	
			//bw.write(content);
			bw.close();
			System.out.println("Done");
			*/
			String[] content =set.toArray(new String[0]); 
			File file = new File("out.txt");
 
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
 
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			String newLine = System.getProperty("line.separator");
			bw.write("var data2 = new Array();"+newLine);	
			for (int i = 0; i < set.size(); i++) {
				bw.write("data2["+i+"]="+content[i]+";"+newLine);	
			}	
			//bw.write(content);
			bw.close();
			System.out.println("Done");
 
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	  public Main(String word){
		 //String word = "( + 2 ( / 3 20 ) )";
		 
		 String[] tokens = word.split(" ");
		 
		 
	    //String[] tokens = new String[]{"(","+","2","(","+","3","2",")",")"};
	    stack = new Stack<String>();
	    for (int i=0;i<tokens.length;i++){
	      stack.push(tokens[i]);
	      if(tokens[i].equals(")")) Interprete(); 
	    }
	  }
	  
	  public static void Interprete(){
	    String tok;
	    Stack<String> callStack = new Stack<String>();
	    tok = stack.pop(); /* This is the ) character */
	    while(!(tok=stack.pop()).equals("(")){
	      callStack.push(tok);
	    }
	    Call(callStack);
	  }
	  
	  public static void Call(Stack<String> callStack){
	    String func = callStack.pop(); /* This is the operator or function */
	    if(func.equals("+")) {
	      double result = Plus(callStack);
	      stack.push(String.valueOf(result));
	    }
	    if(func.equals("-")) {
	    	double result = Minus(callStack);
		    stack.push(String.valueOf(result));
	    }
	    if(func.equals("*")) {
	    	double result = Mul(callStack);
		    stack.push(String.valueOf(result));
	    }
	    if(func.equals("/")) {
	    	double result = Div(callStack);
		    stack.push(String.valueOf(result));
	    }
	  }
	  
	  private static double Div(Stack<String> callStack) {
		  double a = Double.parseDouble(callStack.pop());
		  double b = Double.parseDouble(callStack.pop());
		    //System.out.println("Answer is "+(a*b));
		    return(a/b);
	}

	private static double Mul(Stack<String> callStack) {
		  double a = Double.parseDouble(callStack.pop());
		  double b = Double.parseDouble(callStack.pop());
		    //System.out.println("Answer is "+(a*b));
		    return(a*b);
		
	}

	private static double Minus(Stack<String> callStack) {
		  double a = Double.parseDouble(callStack.pop());
		  double b = Double.parseDouble(callStack.pop());
		    //System.out.println("Answer is "+(a-b));
		    return(a-b);
		
	}

	public static double Plus(Stack<String> callStack){
	    double a = Double.parseDouble(callStack.pop());
	    double b = Double.parseDouble(callStack.pop());
	    //System.out.println("Answer is "+(a+b));
	    return(a+b);
	  } 
	
	public static void main(String[] args) {
		file_read();
		print_output(fin_time);
		//new Main();
		//System.out.println(stack.pop());
		

	}

}
