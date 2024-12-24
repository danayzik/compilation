package AST;
import TYPES.*;
public class AST_EXP_STRING extends AST_EXP
{
	public String str;
	public AST_EXP_STRING(int line, String str)
	{
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.str = str;
		this.line = String.valueOf(line);
	}

	public void PrintMe()
	{
		System.out.format("AST NODE STRING( %s )\n",str);
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("STRING(%s)",str));
	}

	public TYPE semantMe()
	{
		return TYPE_STRING.getInstance();
	}
}
