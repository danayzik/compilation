package AST;
import TYPES.*;
import SYMBOL_TABLE.*;
import TEMP.*;
import IR.*;
public class AST_VAR_SIMPLE extends AST_VAR
{

	public String name;

	public AST_VAR_SIMPLE(int line, String name)
	{
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.name = name;
		this.line = String.valueOf(line);
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
		TYPE t = SYMBOL_TABLE.getInstance().findInAllScopes(name);
		if(t == null)
			throw new SemanticError(String.format("%s can not find %s", line, name));
		semanticType = t;
		return t;
	}
	public TEMP IRme()
	{
		TEMP t = TEMP_FACTORY.getInstance().getFreshTEMP();
		IR.getInstance().Add_IRcommand(new IRcommand_Load(t,name));
		return t;
	}
}
