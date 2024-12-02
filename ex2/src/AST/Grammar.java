package AST;

public enum Grammar {
	//Non terminals:
	// Top-level grammar rule
	PROGRAM,

	// Declarations
	DEC, VAR_DEC, FUNC_DEC, CLASS_DEC, ARRAY_TYPEDEF,

	// Types
	TYPE, TYPE_INT, TYPE_STRING, TYPE_VOID, TYPE_ID,

	// Others
	EXP, NEWEXP, VAR, CFIELD, STMT, BINOP,

	//Terminals
	TIMES, RBRACK, SEMICOLON, PLUS, INT, RBRACE, RPAREN, WHILE, LBRACK, IF,
	LPAREN, LBRACE, ID, EOF, DIVIDE, MINUS, error, DOT, ASSIGN, EQ

	public boolean isTerminal() {
		return this.ordinal() >= TIMES.ordinal();
	}

}

