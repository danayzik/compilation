package AST;
import TYPES.*;
import static AST.SemanticUtils.isLegalAssignment;
import TEMP.*;
import IR.*;

public class AST_STMT_ASSIGN extends AST_STMT
{

	public AST_VAR var;
	public AST_EXP exp;

	public AST_STMT_ASSIGN(int line, AST_VAR var,AST_EXP exp)
	{
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.var = var;
		this.exp = exp;
		this.line = String.valueOf(line);
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
	public TYPE semantMe()
	{
		TYPE t1 = var.semantMe();
		TYPE t2 = exp.semantMe();
		if(!isLegalAssignment(t1, t2))
			throw new SemanticError(String.format("%s illegal assignment", line));
		return null;
	}

	public TEMP IRme()
	{
		Address storeAddr = var.getStoreAddr();
		TEMP src = exp.IRme();
		IR.getInstance().Add_IRcommand(new IRcommand_Store(storeAddr, src));
		return null;
	}
}
