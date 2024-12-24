package AST;
import TYPES.*;
public class AST_EXP_VAR extends AST_EXP
{
	public AST_VAR var;

	public AST_EXP_VAR(int line, AST_VAR var)
	{
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.var = var;
		this.line = String.valueOf(line);
	}

	public void PrintMe()
	{
		System.out.print("AST NODE EXP VAR\n");
		if (var != null) var.PrintMe();
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"EXP\nVAR");
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,var.SerialNumber);
	}
	public TYPE semantMe()
	{
		return var.semantMe();
	}
}
