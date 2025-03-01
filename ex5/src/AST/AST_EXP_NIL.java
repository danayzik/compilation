package AST;
import TYPES.*;
import IR.*;
import TEMP.*;
public class AST_EXP_NIL extends AST_EXP
{
	public String str;
	public AST_EXP_NIL(int line)
	{
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.str = "NIL";
		this.line = String.valueOf(line);
	}


	public void PrintMe()
	{
		System.out.format("AST NODE NIL");
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber, str);
	}

	public TYPE semantMe(){
		semanticType = TYPE_NIL.getInstance();
		return semanticType;
	}

	@Override
	public TEMP IRme() {
		TEMP t = TEMP_FACTORY.getInstance().getFreshTEMP();
		IR.getInstance().Add_IRcommand(new IRcommandConstNil(t));
		return t;
	}
}
