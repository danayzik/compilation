package AST;
import TYPES.*;
import TEMP.*;
import IR.*;
public class AST_EXP_INT extends AST_EXP
{
	public int value;

	public AST_EXP_INT(int line, int value)
	{
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.value = value;
		this.line = String.valueOf(line);
	}

	public void PrintMe()
	{
		System.out.format("AST NODE INT( %d )\n",value);
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("INT(%d)",value));
	}
	public TYPE semantMe()
	{
		semanticType = TYPE_INT.getInstance();
		return semanticType;
	}

	@Override
	public TEMP IRme() {
		TEMP t = TEMP_FACTORY.getInstance().getFreshTEMP();
		IR.getInstance().Add_IRcommand(new IRcommandConstInt(t, value));
		return t;
	}
}
