package AST;
import TYPES.*;
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

	public AST_EXP_BINOP(int line, AST_EXP left,AST_EXP right,int OP)
	{
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.left = left;
		this.right = right;
		this.OP = OP;
		this.line = String.valueOf(line);
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

	public TYPE semantMe()
	{
		TYPE t1;
		TYPE t2;
		t1 = left.semantMe();
		t2 = right.semantMe();
		assert t1 != null;
		assert t2 != null;
		boolean bothInts = (t1 == TYPE_INT.getInstance()) && (t2 == TYPE_INT.getInstance());
		boolean bothStrings = (t1 == TYPE_STRING.getInstance()) && (t2 == TYPE_STRING.getInstance());
		boolean bothObjects = t1.isClass() && t2.isClass();
		boolean oneIsNil = t1.isNil() || t2.isNil();
		boolean oneIsClass = t1.isClass() || t2.isClass();
		boolean oneIsArray = t1.isArray() && t2.isArray();
		boolean comparable = oneIsNil && (oneIsArray || oneIsClass);
		switch (OP) {
			case 0:
				if(bothStrings){
					return TYPE_STRING.getInstance();
				}
				if(bothInts){
					return TYPE_INT.getInstance();
				}
				break;
			case 6:
				if(t1 == t2 || comparable){
					return TYPE_INT.getInstance();
				}
				if(bothObjects){
					assert t1 instanceof TYPE_CLASS;
					assert t2 instanceof TYPE_CLASS;
					if(((TYPE_CLASS)t1).isAncestor(((TYPE_CLASS)t2)) || ((TYPE_CLASS)t2).isAncestor(((TYPE_CLASS)t1))){
						return TYPE_INT.getInstance();
					}
				}
				break;

			case 3:
				if(bothInts){
					if (right instanceof AST_EXP_INT){
						if(((AST_EXP_INT) right).value == 0){
							throw new SemanticError(line);
						}
					}
				}
				//fall to default case

			default:
				if(bothInts){
					return TYPE_INT.getInstance();
				}
		}
		throw new SemanticError(line);

	}
}
