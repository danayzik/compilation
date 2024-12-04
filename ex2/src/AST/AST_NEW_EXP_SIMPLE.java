package AST;

public class AST_NEW_EXP_SIMPLE extends AST_NEW_EXP
{
	public AST_TYPE type;

	public AST_NEW_EXP_SIMPLE(AST_TYPE type)
	{
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.type = type;
	}


	public void PrintMe()
	{
		System.out.format("AST NODE SIMEPL NEW EXP\n");
		AST_GRAPHVIZ.getInstance().logNode(SerialNumber,"NEW\nEXP");
		type.PrintMe();
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,type.SerialNumber);

	}
}
