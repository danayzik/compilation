package AST;
import TYPES.*;
public class AST_STMT_RETURN extends AST_STMT
{
	public AST_EXP returnExp;

	public AST_STMT_RETURN(AST_EXP returnExp)
	{
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.returnExp = returnExp;
	}
	public void PrintMe()
	{
		System.out.print("AST NODE RETURN STMT\n");
		if (returnExp != null) returnExp.PrintMe();
		AST_GRAPHVIZ.getInstance().logNode(
				SerialNumber,
				"(EXP)\n");
		if (returnExp != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,returnExp.SerialNumber);
	}
	public TYPE semantMe(){
		if (returnExp == null)	{
			return null;
		}
		return returnExp.semantMe();

	}


}
