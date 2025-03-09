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
	public boolean isArg = false;
	public int indexInArgs = 0;

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
			isArg = entry.isLocalArg;
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
		Address varAddr = null;
		if(isField){
			offset = instance.activeClass.getFieldOffset(name);
			varAddr = new Address(name, offset, true);
		}
		else if (isLocal){
			if(isArg)
				offset = indexInArgs*4+8;
			else
				offset = -localIndexInFunc*4-4;
			varAddr = new Address(name, offset, false);
		} else if (isGlobal) {
			varAddr = new Address(name, name);
		}
		if(varAddr == null){
			throw new RuntimeException("You're stupid");
		}
		instance.Add_IRcommand(new IRcommand_Load(t, varAddr));
		return t;
	}

	@Override
	public Address getStoreAddr(){
		if(isLocal)
			return new Address(name, localIndexInFunc*4 ,false);
		if(isGlobal)
			return new Address(name, name);
		if(isField)
			return new Address(name, IR.getInstance().activeClass.getFieldOffset(name), true);
		throw new RuntimeException("You fucked up stupid");
	}
}
