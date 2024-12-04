package AST;

public class AST_EXP_STRING extends AST_EXP
{
	public String str;
	public AST_EXP_STRING(String str)
	{
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.str = str;
	}

	public void PrintMe()
	{
		System.out.format("AST NODE STRING( %s )\n",str);
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("STRING(%s)",str));
	}
}
