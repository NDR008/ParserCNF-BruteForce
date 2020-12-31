/*
 *
 * This script demos some of the features of the skeleton code. Click run to begin.
 *
 * If you are viewing this on Engage, click the 'open on repl.it' button in the top right.
 *
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import computation.contextfreegrammar.*;
import computation.parser.*;
import computation.parsetree.*;

class Main {

	// Change this to true to skip straight to the tests
	// - useful if you understand the code and are just testing your parser.
	private final static boolean SKIP_TO_TESTS = false;

	// The parser we will test. 
	// If you create a new class, add your constructor here instead e.g.
	// private static IParser parser = new MyParser();
	private static IParser parser = new Parser();

	public static void customCode() {
		// You can write your own custom code here and run it with option 3.
		// Good for testing your code works!
		// Any code here is totally informal and does not count towards your submission.

		// Below is the kind of code you might want to write to test your parser


		ContextFreeGrammar cfg = MyGrammar.simpleGrammar();

		//System.out.println(cfg);
		/*
		boolean checkOfGrammer = cfg.isInChomskyNormalForm();
		System.out.println("this grammar is in CNF : " + checkOfGrammer);
		System.out.println("--------------");
		*/

		Parser p1 = new Parser();

		ArrayList<Word> words = new ArrayList<>();
		words.add(new Word("0011"));
		words.add(new Word("000111"));
		words.add(new Word("10"));
		words.add(new Word("1011"));
		words.add(new Word(""));

		cfg = ContextFreeGrammar.simpleCNF();
		System.out.println("****************************************************************");
		System.out.println("Simple CNF");
		for (Word w : words){
			System.out.println("-----------------------------------------------------------------");
			boolean flag = p1.isInLanguage(cfg, w);
			System.out.println("Check if '" + w.toString() + "' is in the language- result: " + flag);
			System.out.println();
			if (flag) { p1.generateParseTree(cfg, w).print(); }
			System.out.println("-----------------------------------------------------------------");
		}

		boolean CYK = false;
		words.clear();
		words.add(new Word("1+0"));
		words.add(new Word("01"));
		words.add(new Word("*-*"));
		words.add(new Word("1*-1+0*-1"));
		if(CYK) {words.add(new Word("0+-1*-0+1*1+x+1*-x"));}
		if(CYK) {words.add(new Word("1*0+--1+0*1"));}
		if(CYK) {words.add(new Word("0+-1*-0+1*1+x+1*-x+0*1*-0+x*-x+1"));}
		if(CYK) {words.add(new Word("1*0+0-1+1*0+-0"));}

		cfg = MyGrammar.courseworkCNF();
		System.out.println("****************************************************************");
		System.out.println("Provided CNF");
		for (Word w : words){
			System.out.println("-----------------------------------------------------------------");
			boolean flag = p1.isInLanguage(cfg, w);
			System.out.println("Check if '" + w.toString() + "' is in the language- result: " + flag);
			System.out.println();
			if (flag) { p1.generateParseTree(cfg, w).print(); }
		}

		cfg = MyGrammar.makeGrammar();
		System.out.println("****************************************************************");
		System.out.println("Self converted CFG to CNF");
		for (Word w : words){
			System.out.println("-----------------------------------------------------------------");
			boolean flag = p1.isInLanguage(cfg, w);
			System.out.println("Check if '" + w.toString() + "' is in the language- result: " + flag);
			System.out.println();
			if (flag) { p1.generateParseTree(cfg, w).print(); }
		}
	}



	/* ******************************************************************************************************
	 * You should not need to edit anything below this line.
	 *
	 * There is nothing stopping you from editing it if you want, but make sure you know what you are doing.
	 * ******************************************************************************************************
	 */

	private static Scanner userin = new Scanner(System.in);

	public static void main(String[] args) {
		if(!SKIP_TO_TESTS) {
			System.out.println("|------- Welcome to the demo script. I recommend turning on word wrap in your console. -------|\n"
					+          "|------- Make the width of the window at least this wide without wrapping if possible. -------|\n");
			int input = -1;
			do {
				System.out.println("What would you like to do?");
				System.out.println("0 – Explain the assignment (start here if brand new)");
				System.out.println("1 – Demo the code");
				System.out.println("2 – Run simple tests on Parser.java");
				System.out.println("3 - Run custom code (see the code for Main.java)");
				System.out.println("9 – Quit");

				try {
					input = userin.nextInt();
				} catch(Exception e) {
					userin.nextLine();
					System.out.println("Invalid choice...");
					continue;
				}

				userin.nextLine();
				System.out.println();

				if(input == 0) {
					explainAssignment();
				} else if(input == 1) {
					demoCode();
				} else if(input == 2) {
					runTests();
				} else if(input == 3) {
					customCode();
				} else if(input != 9) {
					System.out.println("Invalid choice...");
				}

				if(input != 9) pause();
			} while(input != 9);
		}
		else {
			runTests();
		}
	}

	public static void explainAssignment() {
		System.out.println("The very first part of this assignment is to convert a grammar into Chomsky Normal form. "
				+ "You will want to do this on paper. As you are looking at the code, we assume you have completed this step!\n");

		pause();

		System.out.println("The next and main part of the assignment is to write a parser, then apply it to the grammar you created.\n");

		System.out.println("Creating the parser means writing a class. When given a context free "
				+ "grammar in Chomsky Normal form, and a string, your class will:");
		System.out.println("\t1 - Find whether this string is in the langauge generated by this grammar, and");
		System.out.println("\t2 - if so, produce a parse tree for the string.");

		pause();

		System.out.println("But you may wish to start by writing your Chomsky Normal Form grammar in Java. "
				+ "In other words, write some code which creates an object of the ContextFreeGrammar class. "
				+ "You can do this in the skeleton class called MyGrammar.java. "
				+ "This should help familiarise you with how the code works, and then you can write your parser, "
				+ "and apply it to the grammar."
				+ "\n\n"
				+ "Before that, choose option 1 from the menu to get an explanation of the code.\n");

		System.out.println("For more information see the full assignment text on Engage.\n");

	}

	private static void demoCode() {
		System.out.println("Each class has more information written in comments, but this is most "
				+ "clearly readable in the Javadoc documentation that has been provided in HTML form, "
				+ "available via Engage. (Or, if you have downloaded the code directly, open "
				+ "index.html in the doc folder.)");

		pause();

		System.out.println("First, we'll explain the Parser class. This is a blank template for your own code. "
				+ "You could also create your own class from scratch, so long as it implements IParser. "
				+ "Right now the methods in Parser do nothing, you must fill them out.\n");

		System.out.println("If you go back to the main menu and select option 2, it will run some basic tests "
				+ "on the parser code. These are not thorough! But they will demonstrate how it should work. "
				+ "Once you have written some parser code, try running them again. ");

		pause();

		System.out.println("The class ContextFreeGrammar is used to represent a grammar. If you look in the "
				+ "/computation/contextfreegrammar/ folder you will see all the required classes.\n");

		System.out.println("Terminal and Variable are subclasses of Symbol, and represent a single letter.");

		System.out.println("You can create a Terminal or a Variable object using their constructors.");
		System.out.println("\te.g. new Variable('S'); \t\t-- this produces: " + new Variable('S'));
		System.out.println("\t     new Terminal('a'); \t\t-- this produces: " + new Terminal('a'));
		System.out.println("\t     new Terminal('0'); \t\t-- this produces: " + new Terminal('0') + "\n");


		System.out.println("Variables can also have a subscript number from 0 to 9\n"
				+ "\te.g. new Variable(\"A0\"); \t-- this produces: " + new Variable("A0") + "\n");

		System.out.println("And there is a helper method to create an array of subscripted variables:\n"
				+ "\te.g. Variable.subscriptedVariables('S', 4); \t-- this produces: " + Arrays.toString(Variable.subscriptedVariables('S', 4)));

		pause();

		System.out.println("The class Word represents strings. We use the name Word because String is already taken.\n");

		System.out.println("One of the constructors for Word takes a Java String. It assumes upper case letters "
				+ "are variables, and everything else is a terminal. So you can't use subscript variables! "
				+ "I recommend mostly using it for strings of terminals.");

		System.out.println("\te.g. new Word(\"000111\"); \t-- this produces: " + new Word("000111"));
		System.out.println("\t     new Word(\"A0\"); \t\t-- this produces: " + new Word("A0") + " (variable followed by terminal)");
		System.out.println();

		System.out.println("The other way to construct a Word is with multiple symbols:");
		System.out.println("\te.g. new Word(new Variable(\"A0\"), new Terminal('0')); \t-- this produces: " + new Word(new Variable("A0"), new Terminal('0')));
		System.out.println();

		System.out.println("Or this syntax also allows you to pass in an array of symbols:");
		Symbol[] array = {new Variable("A0"),new Terminal('0')};
		System.out.println("\te.g. Symbol[] array = {new Variable(\"A0\"),new Terminal(\"0\")};");
		System.out.println("\t     new Word(array); \t\t-- this produces: " + new Word(array));

		pause();

		System.out.println("To create a grammar, create multiple Rule objects, add them to a list, then "
				+ "call the ContextFreeGrammar constructor.\n");

		System.out.println("Look at the code in ContextFreeGrammar.simpleCNF(). It produces this grammar:");
		System.out.println(ContextFreeGrammar.simpleCNF());

		pause();

		System.out.println("This should be enough information to start writing your CNF grammar in Java, "
				+ "and also writing the first part of the parser which checks whether the string is in the language.");

		pause();

		System.out.println("Once you are ready to make a parse tree, you will need to use the class ParseTreeNode.\n");

		System.out.println("The constructor takes a symbol object, plus zero, one, or two ParseTreeNodes as children.\n");

		System.out.println("Have a look at the code in the main method of ParseTreeNode. It produces this tree:");
		ParseTreeNode.main("");

		pause();

		System.out.println("This is the end of the demo. Dig into the code, look at the documentation, and good luck!");

	}



	public static void runTests() {
		ContextFreeGrammar cfg = ContextFreeGrammar.simpleCNF();
		Word test;

		if(!SKIP_TO_TESTS) {
			System.out.println("This script runs some very basic tests on your parser.");

			System.out.println("We will use a grammar that generates the language {0ⁿ1ⁿ where n ≥ 0}");
			System.out.println("The grammar is already in Chomsky Normal Form. Here are the rules:");

			System.out.println(cfg);
			pause();
		}

		System.out.println("Testing parser with " + parser.getClass());

		int success = 0, total = 0;

		test = new Word("0011");

		System.out.println(LINE+"\nTest 1:\n" + LINE + "\n");
		System.out.println("Does in-built grammar generate the string " + test);
		System.out.println("Expected result: true\nTest result:");
		total++;

		if(parser.isInLanguage(cfg, test)){
			System.out.println("\tPASS -- parser returned true");
			success++;
		} else{
			System.out.println("\tFAIL -- parser returned false");
		}

		pause();
		test = new Word("1011");

		System.out.println("Test 2:\n" + LINE + "\n");
		System.out.println("Does in-built grammar generate the string " + test);
		System.out.println("Expected result: false\nTest result:");
		total++;

		if(!parser.isInLanguage(cfg, test)){
			System.out.println("\tPASS -- parser returned false");
			success++;
		} else{
			System.out.println("\tFAIL -- parser returned true");
		}

		pause();

		test = new Word("0011");
		System.out.println("Test 3:\n" + LINE + "\n");
		System.out.println("Parse tree for string " + test);
		System.out.println("Expected result:\n");

		ParseTreeNode tree = new ParseTreeNode(new Variable("A0"), new ParseTreeNode(new Variable('Z'), new ParseTreeNode(new Terminal('0'))),new ParseTreeNode(new Variable('B'), new ParseTreeNode(new Variable('A'), new ParseTreeNode(new Variable('Z'), new ParseTreeNode(new Terminal('0'))), new ParseTreeNode(new Variable('Y'), new ParseTreeNode(new Terminal('1')))), new ParseTreeNode(new Variable('Y'), new ParseTreeNode(new Terminal('1')))));
		tree.print();

		ParseTreeNode result = parser.generateParseTree(cfg, test);
		if(result == null) {
			System.out.println("Actual result: null");
		} else {
			System.out.println("Actual result:\n");

			result.print();
		}

		total++;

		if(result != null && result.equals(tree)) {
			System.out.println("PASS -- trees match");
			success++;
		} else if (result == null) {
			System.out.println("FAIL -- result was null");
		} else {
			System.out.println("FAIL -- trees do not match");
		}

		pause();

		System.out.println("Total tests passed: " + success + " out of " + total);
	}

	private static void pause() {
		System.out.println("\n\n(Press enter to continue...)");
		userin.nextLine();
		System.out.println("\n" + "------------------------");
	}

	private static final String LINE = "-------------";

}

