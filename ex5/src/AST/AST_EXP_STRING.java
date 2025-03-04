package AST;
import TYPES.*;
import TEMP.*;
import IR.*;

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
		System.out.println(str);
		System.out.format("AST NODE STRING( %s )\n",str);
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber, String.format("String:%s", str));
	}

	public TYPE semantMe()
	{
		semanticType = TYPE_STRING.getInstance();
		return semanticType;
	}
	@Override
	public TEMP IRme() {
		TEMP t = TEMP_FACTORY.getInstance().getFreshTEMP();
		String strLabel = IRcommand.getFreshStrLabel();
		IR.getInstance().activateDataSection();
		IR.getInstance().Add_IRcommand(new IRcommandConstString(strLabel, str));
		IR.getInstance().activateFunctionSection();
		IR.getInstance().Add_IRcommand(new IRcommand_Load_Address(t, strLabel));
		return t;
	}
}
