package AST;
import TYPES.*;
import SYMBOL_TABLE.*;
import TEMP.*;
import IR.*;
public class AST_VAR_SIMPLE extends AST_VAR
{

	public String name;
	public boolean isLocal = false;
	public boolean isGlobal = false;
	public boolean isField = false;
	public int localIndexInFunc = 0;

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

		SYMBOL_TABLE_ENTRY entry = SYMBOL_TABLE.getInstance().getLastSearchedEntry();

		if(entry != null) {
			isLocal = entry.isLocal;
			isGlobal = entry.isGlobal;
			localIndexInFunc = entry.indexInFunc;
		}
		isField = (!isGlobal) && (!isLocal);

		semanticType = t;
		return t;
	}
	public TEMP IRme()
	{
		IR instance = IR.getInstance();
		TEMP t = TEMP_FACTORY.getInstance().getFreshTEMP();
		int offset;
		if(isField){
			offset = instance.activeClass.getFieldOffset(name);
			instance.Add_IRcommand(new IRcommand_Load_FieldAddr_From_SelfObj(t, name, offset));
		}
		else if (isLocal){
			offset = localIndexInFunc*4;
			instance.Add_IRcommand(new IRcommand_Load_Stack_Offset(t, offset));
		} else if (isGlobal) {
			instance.Add_IRcommand(new IRcommand_Load_Address(t, name));
		}
		return t;
	}
}
