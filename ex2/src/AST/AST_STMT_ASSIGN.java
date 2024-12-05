package AST;

public class AST_STMT_ASSIGN extends AST_STMT
{

	public AST_VAR var;
	public AST_EXP exp;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_STMT_ASSIGN(AST_VAR var,AST_EXP exp)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();


		this.var = var;
		this.exp = exp;
	}

	public void PrintMe()
	{

		System.out.print("AST NODE ASSIGN STMT\n");

		if (var != null) var.PrintMe();
		if (exp != null) exp.PrintMe();

		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"ASSIGN\nleft := right\n");

		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,var.SerialNumber);
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,exp.SerialNumber);
	}
}
