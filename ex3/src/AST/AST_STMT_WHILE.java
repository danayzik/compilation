package AST;
import TYPES.*;
import SYMBOL_TABLE.*;
public class AST_STMT_WHILE extends AST_STMT
{
	public AST_EXP cond;
	public AST_STMT_LIST body;

	public AST_STMT_WHILE(int line, AST_EXP cond,AST_STMT_LIST body)
	{
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.cond = cond;
		this.body = body;
		this.line = String.valueOf(line);
	}
	public void PrintMe()
	{
		System.out.print("AST NODE WHILE STMT\n");
		if (cond != null) cond.PrintMe();
		if (body != null) body.PrintMe();
		AST_GRAPHVIZ.getInstance().logNode(
				SerialNumber,
				"WHILE STATEMENT\n");
		if (cond != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,cond.SerialNumber);
		if (body != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,body.SerialNumber);
	}
	public TYPE semantMe()
	{
		if (cond.semantMe() != TYPE_INT.getInstance())
		{
			throw new SemanticError(line);
		}
		SYMBOL_TABLE.getInstance().beginScope();
		body.semantMe();
		SYMBOL_TABLE.getInstance().endScope();
		return null;
	}

	@Override
	public void matchReturnType(TYPE t) {
		body.matchReturnType(t);
	}
}