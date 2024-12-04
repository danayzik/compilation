/***************************/
/* FILE NAME: LEX_FILE.lex */
/***************************/

/*************/
/* USER CODE */
/*************/
import java_cup.runtime.*;

/******************************/
/* DOLAR DOLAR - DON'T TOUCH! */
/******************************/

%%

/************************************/
/* OPTIONS AND DECLARATIONS SECTION */
/************************************/
   
/*****************************************************/ 
/* Lexer is the name of the class JFlex will create. */
/* The code will be written to the file Lexer.java.  */
/*****************************************************/ 
%class Lexer
%states MULTI_COMMENT ASTERISK_IN_COMMENT ONE_LINE_COMMENT
/********************************************************************/
/* The current line number can be accessed with the variable yyline */
/* and the current column number with the variable yycolumn.        */
/********************************************************************/
%line
%column

/*******************************************************************************/
/* Note that this has to be the EXACT same name of the class the CUP generates */
/*******************************************************************************/
%cupsym TokenNames

/******************************************************************/
/* CUP compatibility mode interfaces with a CUP generated parser. */
/******************************************************************/
%cup

/****************/
/* DECLARATIONS */
/****************/
/*****************************************************************************/   
/* Code between %{ and %}, both of which must be at the beginning of a line, */
/* will be copied verbatim (letter to letter) into the Lexer class code.     */
/* Here you declare member variables and functions that are used inside the  */
/* scanner actions.                                                          */  
/*****************************************************************************/   
%{
	/*********************************************************************************/
	/* Create a new java_cup.runtime.Symbol with information about the current token */
	/*********************************************************************************/
	private Symbol symbol(int type)               {return new Symbol(type, yyline, yycolumn);}
	private Symbol symbol(int type, Object value) {return new Symbol(type, yyline, yycolumn, value);}

	/*******************************************/
	/* Enable line number extraction from main */
	/*******************************************/
	public int getLine() { return yyline + 1; } 

	/**********************************************/
	/* Enable token position extraction from main */
	/**********************************************/
	public int getTokenStartPosition() { return yycolumn + 1; } 
%}

/***********************/
/* MACRO DECALARATIONS */
/***********************/
LineTerminator	= \r|\n|\r\n
WhiteSpace		= {LineTerminator} | [ \t]
INTEGER			= 0 | [1-9][0-9]*
LEADING_ZEROES_INT = 0[1-9][0-9]*
ID				= [a-zA-Z][a-zA-Z0-9]*
STRING          = \"([a-zA-Z]*)\"
ONE_LINE_COMMENT_START = \/\/
ALLOWED_ONE_LINE_COMMENT_CHARS = [a-zA-Z0-9 \t(){}\[\]\?!\+\-\*\/\.;]+
MULTI_COMMENT_START   = \/\*
ALLOWED_MULTI_COMMENT_CHARS = ([a-zA-Z0-9\(\){}\[\]\?\!\+\-\.;]|{WhiteSpace})+
SLASH = \/
/******************************/
/* DOLAR DOLAR - DON'T TOUCH! */
/******************************/

%%

/************************************************************/
/* LEXER matches regular expressions to actions (Java code) */
/************************************************************/

/**************************************************************/
/* YYINITIAL is the state at which the lexer begins scanning. */
/* So these regular expressions will only be matched if the   */
/* scanner is in the start state YYINITIAL.                   */
/**************************************************************/

<YYINITIAL> {

{MULTI_COMMENT_START}       {yybegin(MULTI_COMMENT);}
{ONE_LINE_COMMENT_START}    {yybegin(ONE_LINE_COMMENT);}
{LEADING_ZEROES_INT}        {return symbol(TokenNames.ERROR);}
"nil"               { return symbol(TokenNames.NIL, "NIL");}
"array"             { return symbol(TokenNames.ARRAY, "ARRAY");}
"class"             { return symbol(TokenNames.CLASS, "CLASS");}
"extends"           { return symbol(TokenNames.EXTENDS, "EXTENDS");}
"return"            { return symbol(TokenNames.RETURN, "RETURN");}
"while"             { return symbol(TokenNames.WHILE, "WHILE");}
"if"                { return symbol(TokenNames.IF, "IF");}
"new"               { return symbol(TokenNames.NEW, "NEW");}
"int"               { return symbol(TokenNames.TYPE_INT, "TYPE_INT");}
"void"              { return symbol(TokenNames.TYPE_VOID, "TYPE_VOID");}
"string"            { return symbol(TokenNames.TYPE_STRING,"TYPE_STRING");}
"+"					{ return symbol(TokenNames.PLUS, "PLUS");}
"-"					{ return symbol(TokenNames.MINUS, "MINUS");}
"*"				    { return symbol(TokenNames.TIMES, "TIMES");}
"/"					{ return symbol(TokenNames.DIVIDE, "DIVIDE");}
"("					{ return symbol(TokenNames.LPAREN, "LPAREN");}
")"					{ return symbol(TokenNames.RPAREN, "RPAREN");}
{INTEGER}			{ if(Integer.valueOf(yytext()) > 32767){return symbol(TokenNames.ERROR);}return symbol(TokenNames.NUMBER, String.format("INT(%s)", yytext()));}
{STRING}            { return symbol(TokenNames.STRING, String.format("STRING(%s)", yytext()));}
{ID}				{ return symbol(TokenNames.ID, String.format("ID(%s)", yytext()));}
{WhiteSpace}		{ /* just skip what was found, do nothing */ }
"["                 { return symbol(TokenNames.LBRACK, "LBRACK");}
"]"                 { return symbol(TokenNames.RBRACK, "RBRACK");}
"{"                 { return symbol(TokenNames.LBRACE, "LBRACE");}
"}"                 { return symbol(TokenNames.RBRACE, "RBRACE");}
","                 { return symbol(TokenNames.COMMA, "COMMA");}
"."                 { return symbol(TokenNames.DOT, "DOT");}
";"                 { return symbol(TokenNames.SEMICOLON, "SEMICOLON");}
":="                { return symbol(TokenNames.ASSIGN, "ASSIGN");}
"="                 { return symbol(TokenNames.EQ, "EQ");}
"<"                 { return symbol(TokenNames.LT, "LT");}
">"                 { return symbol(TokenNames.GT, "GT");}
<<EOF>>				{ return symbol(TokenNames.EOF);}
.                   {return symbol(TokenNames.ERROR);}
/**************************************************************/
/* YYINITIAL is the state at which the lexer begins scanning. */
/* So these regular expressions will only be matched if the   */
/* .+   // lexical ERROR caught, add this. also, add it to too large of an int*/
/**************************************************************/

}
<MULTI_COMMENT> {
"*"        { yybegin(ASTERISK_IN_COMMENT); }
{ALLOWED_MULTI_COMMENT_CHARS}|\/      { }
<<EOF>>				{return symbol(TokenNames.ERROR);}
.                   {return symbol(TokenNames.ERROR);}
}
<ASTERISK_IN_COMMENT>{
"*"   {}
{SLASH}   {yybegin(YYINITIAL);}
{ALLOWED_MULTI_COMMENT_CHARS}  {yybegin(MULTI_COMMENT);}
<<EOF>>				{return symbol(TokenNames.ERROR);}
.                   {return symbol(TokenNames.ERROR);}
}
<ONE_LINE_COMMENT> {
{ALLOWED_ONE_LINE_COMMENT_CHARS}      { }
{LineTerminator}              {yybegin(YYINITIAL);}
<<EOF>>				{return symbol(TokenNames.EOF);}
.                   {return symbol(TokenNames.ERROR);}
}