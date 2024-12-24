package AST;
import TYPES.*;
public class AST_EXP_NIL extends AST_EXP
{
	public String str;
	public AST_EXP_NIL(int line)
	{
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.str = "NIL";
		this.line = String.valueOf(line);
	}


	public void PrintMe()
	{
		System.out.format("AST NODE NIL");
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber, str);
	}

	public TYPE semantMe(){
		semanticType = TYPE_NIL.getInstance();
		return semanticType;
	}
}
