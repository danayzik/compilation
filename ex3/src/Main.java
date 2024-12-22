   
import java.io.*;
import java.io.PrintWriter;
import java_cup.runtime.Symbol;
import AST.*;
// TODO
// Make sure variables can't be void, and array types cant be void

public class Main
{
	static public void main(String argv[])
	{
		Lexer l;
		Parser p;
		Symbol s;
		AST_DEC_LIST AST;
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
				AST = (AST_DEC_LIST) p.parse().value;
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

			/*************************/
			/* [6] Print the AST ... */
			/*************************/
			AST.PrintMe();
			
			/*************************/
			/* [7] Close output file */
			/*************************/
			file_writer.println("OK");
			file_writer.close();
			
			/*************************************/
			/* [8] Finalize AST GRAPHIZ DOT file */
			/*************************************/
			AST_GRAPHVIZ.getInstance().finalizeFile();
    	}
			     
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}


