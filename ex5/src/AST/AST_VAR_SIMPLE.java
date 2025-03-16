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
			localIndexInFunc = entry.indexInFunc - SYMBOL_TABLE.getInstance().getArgCount();
			isArg = entry.isLocalArg;
			if(isArg){
				indexInArgs = entry.indexInArg;
			}
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
		Address varAddr = new Address(name);
		if(isField){
			offset = instance.activeClass.getFieldOffset(name);
			varAddr.setAsImplicitField(offset);
		}
		else if (isLocal){
			if(isArg)
				offset = indexInArgs*4+52;
			else
				offset = -localIndexInFunc*4-4;
			varAddr.setAsFPAddr(offset);
		} else if (isGlobal) {
			varAddr.setAsLabelAddr(name);
		}

		instance.Add_IRcommand(new IRcommand_Load(t, varAddr));
		return t;
	}

	@Override
	public Address getStoreAddr(){
		Address addr = new Address(name);
		if(isLocal) {
			int offset;
			if(isArg){
				offset = indexInArgs*4+52;
			}
			else{
				offset = -localIndexInFunc*4-4;
			}
			addr.setAsFPAddr(offset);

		}
		if(isGlobal)
			addr.setAsLabelAddr(name);
		if(isField)
			addr.setAsImplicitField(IR.getInstance().activeClass.getFieldOffset(name));
		return addr;

	}
}
