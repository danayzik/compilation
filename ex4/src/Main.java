   
import java.io.*;
import java.io.PrintWriter;
import java.util.Set;

import java_cup.runtime.Symbol;
import AST.*;
import IR.*;

public class Main
{
	static public void main(String argv[])
	{
		Lexer l;
		Parser p;
		Symbol s;
		AST_PROGRAM AST;
		FileReader file_reader;
		PrintWriter file_writer;
		String inputFilename = argv[0];
		String outputFilename = argv[1];
		
		try
		{
			/********************************/
			/* [1] Initialize a file reader */
			/********************************/
			file_reader = new FileReader(inputFilename);

			/********************************/
			/* [2] Initialize a file writer */
			/********************************/
			file_writer = new PrintWriter(outputFilename);
			
			/******************************/
			/* [3] Initialize a new lexer */
			/******************************/
			l = new Lexer(file_reader);
			
			/*******************************/
			/* [4] Initialize a new parser */
			/*******************************/
			p = new Parser(l);

			/***********************************/
			/* [5] 3 ... 2 ... 1 ... Parse !!! */
			/***********************************/
			try {
				AST = (AST_PROGRAM) p.parse().value;
			}
			catch (SyntaxError se){
				file_writer.println(String.format("ERROR(%s)", se.getMessage()));
				System.out.println("Caught Syntax Error");
				file_writer.close();
				return;
			}
			catch (LexicalError le) {
				file_writer.println("ERROR");
				System.out.println("Caught Lexical Error");
				file_writer.close();
				return;
			}


			try{
				AST.semantMe();
			}
			catch (SemanticError se){
				String msg = se.getMessage();
				String line;
				int spaceIndex = msg.indexOf(' ');
				line = msg.substring(0, spaceIndex);
				String messagePart = msg.substring(spaceIndex + 1);
				file_writer.println(String.format("ERROR(%s)", line));
				System.out.format("Caught Semantic Error: %s", messagePart);
				file_writer.close();
				return;
			}
			AST.IRme();
			IR.getInstance().printMe();
			IR.getInstance().setupCFG();
			Set<String> varSet = IR.getInstance().dataFlowAnalysis();
			System.out.println(varSet);
			
			/*************************/
			/* [7] Close output file */
			/*************************/
			file_writer.println("OK");
			file_writer.close();
			
			/*************************************/
			/* [8] Finalize AST GRAPHIZ DOT file */
			/*************************************/

    	}
			     
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}


