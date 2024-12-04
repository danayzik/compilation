package AST;

public class AST_VAR_SUBSCRIPT extends AST_VAR
{
	public AST_VAR var;
	public AST_EXP subscript;

	public AST_VAR_SUBSCRIPT(AST_VAR var,AST_EXP subscript)
	{
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.var = var;
		this.subscript = subscript;
	}

	public void PrintMe()
	{
		System.out.print("AST NODE SUBSCRIPT VAR\n");
		if (var != null) var.PrintMe();
		if (subscript != null) subscript.PrintMe();
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"SUBSCRIPT\nVAR\n...[...]");
		if (var       != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,var.SerialNumber);
		if (subscript != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,subscript.SerialNumber);
	}
}
