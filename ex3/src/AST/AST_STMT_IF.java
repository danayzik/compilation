package AST;
import TYPES.*;
public class AST_STMT_IF extends AST_STMT
{
	public AST_EXP cond;
	public AST_STMT_LIST body;

	public AST_STMT_IF(AST_EXP cond,AST_STMT_LIST body)
	{
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.cond = cond;
		this.body = body;
	}
	public void PrintMe()
	{
		System.out.print("AST NODE IF STMT\n");
		if (cond != null) cond.PrintMe();
		if (body != null) body.PrintMe();
		AST_GRAPHVIZ.getInstance().logNode(
				SerialNumber,
				"IF STATEMENT\n");
		if (cond != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,cond.SerialNumber);
		if (body != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,body.SerialNumber);
	}
	public TYPE semantMe()
	{
		if (cond.semantMe() != TYPE_INT.getInstance())
		{
			throw new SemanticError("");
		}
		SYMBOL_TABLE.getInstance().beginScope();
		body.semantMe();
		SYMBOL_TABLE.getInstance().endScope();
		return null;
	}
}