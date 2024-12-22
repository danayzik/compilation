package AST;
import TYPES.*;
public class AST_VAR_SIMPLE extends AST_VAR
{

	public String name;

	public AST_VAR_SIMPLE(String name)
	{
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.name = name;
	}


	public void PrintMe()
	{
		System.out.format("AST NODE SIMPLE VAR( %s )\n",name);
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("SIMPLE\nVAR\n(%s)",name));
	}
	public TYPE semantMe()
	{
		return null;
	}
}
