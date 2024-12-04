package AST;

public class AST_NEW_EXP_ARRAY extends AST_NEW_EXP
{
	public AST_TYPE type;
	public AST_EXP sizeExp;
	public AST_NEW_EXP_ARRAY(AST_TYPE type, AST_EXP sizeExp)
	{
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.type = type;
		this.sizeExp = sizeExp;
	}


	public void PrintMe()
	{
		System.out.format("AST NODE NEW ARRAY EXP\n");
		AST_GRAPHVIZ.getInstance().logNode(SerialNumber,"NEW\nARRAY");
		type.PrintMe();
		sizeExp.PrintMe();
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,type.SerialNumber);
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,sizeExp.SerialNumber);
	}
}
