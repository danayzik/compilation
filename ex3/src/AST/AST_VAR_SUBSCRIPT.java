package AST;
import TYPES.*;


public class AST_VAR_SUBSCRIPT extends AST_VAR
{
	public AST_VAR var;
	public AST_EXP subscript;

	public AST_VAR_SUBSCRIPT(int line, AST_VAR var,AST_EXP subscript)
	{
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.var = var;
		this.subscript = subscript;
		this.line = String.valueOf(line);
	}

	public void PrintMe()
	{
		System.out.print("AST NODE SUBSCRIPT VAR\n");
		if (var != null) var.PrintMe();
		if (subscript != null) subscript.PrintMe();
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"SUBSCRIPT\nVAR\n...[...]");
		if (var != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,var.SerialNumber);
		if (subscript != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,subscript.SerialNumber);
	}

	public TYPE semantMe(){
		TYPE varType = var.semantMe();
		TYPE subscriptType = subscript.semantMe();
		if (!varType.isArray())
			throw new SemanticError(line);
		if (subscriptType != TYPE_INT.getInstance())
			throw new SemanticError(line);

		return ((TYPE_ARRAY)varType).arrayType;
	}
}
