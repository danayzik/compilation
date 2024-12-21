package AST;

public class AST_VAR_FIELD extends AST_VAR
{
	public AST_VAR var;
	public String fieldName;

	public AST_VAR_FIELD(AST_VAR var,String fieldName)
	{
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.var = var;
		this.fieldName = fieldName;
	}


	public void PrintMe()
	{

		System.out.print("AST NODE FIELD VAR\n");

		if (var != null) var.PrintMe();
		System.out.format("FIELD NAME( %s )\n",fieldName);

		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("FIELD\nVAR\n...->%s",fieldName));

		if (var != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,var.SerialNumber);
	}
}
