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
			instance.Add_IRcommand(new IRcommand_Load_Implicit_Field(t, offset, name));
		}
		else if (isLocal){
			offset = localIndexInFunc*4;
			instance.Add_IRcommand(new IRcommand_Load_Local(t, offset, name));
		} else if (isGlobal) {
			instance.Add_IRcommand(new IRcommand_Load_Global(t, name));
		}
		return t;
	}

	@Override
	public Triplet<String, String, Integer> getNameAddrOffset(){
		if(isLocal)
			return new Triplet<>(name, "$sp", localIndexInFunc*4);
		if(isGlobal)
			return new Triplet<>(name, name, 0);
		if(isField)
			return new Triplet<>(name, "$a0", IR.getInstance().activeClass.getFieldOffset(name));
		throw new RuntimeException("You fucked up stupid");
	}
}
