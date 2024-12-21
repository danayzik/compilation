package AST;

public class AST_EXP_BINOP extends AST_EXP
{
	int OP;
	public AST_EXP left;
	public AST_EXP right;
	public static String mapOperator(int operator) {
		switch (operator) {
			case 0:
				return "PLUS";
			case 1:
				return "MINUS";
			case 2:
				return "TIMES";
			case 3:
				return "DIVIDE";
			case 4:
				return "LT";
			case 5:
				return "GT";
			case 6:
				return "EQ";
			default:
				throw new IllegalArgumentException("Invalid operator: " + operator);
		}
	}

	public AST_EXP_BINOP(AST_EXP left,AST_EXP right,int OP)
	{
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.left = left;
		this.right = right;
		this.OP = OP;
	}

	public void PrintMe()
	{

		String sOP=mapOperator(this.OP);
		System.out.print("AST NODE BINOP EXP\n");
		if (left != null) left.PrintMe();
		if (right != null) right.PrintMe();

		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("BINOP(%s)",sOP));
		if (left  != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,left.SerialNumber);
		if (right != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,right.SerialNumber);
	}
}
