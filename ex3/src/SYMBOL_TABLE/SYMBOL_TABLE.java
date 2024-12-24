
package SYMBOL_TABLE;

import java.io.PrintWriter;

import TYPES.*;


public class SYMBOL_TABLE
{

	private SYMBOL_TABLE_SCOPE headScope;
	private SYMBOL_TABLE_SCOPE tailScope;
	private static SYMBOL_TABLE instance = null;





	public void enter(String name,TYPE type)
	{
		SYMBOL_TABLE_ENTRY newEntry = new SYMBOL_TABLE_ENTRY(name, type);
		if (tailScope != null) {
			tailScope.addEntry(newEntry);
		}
	}



	public TYPE findInInnerScope(String name)
	{
		return tailScope.findInScope(name);
	}

	public TYPE findInAllScopes(String name) {

		return tailScope.findInAllScopes(name);
	}


	public void beginScope() {
		SYMBOL_TABLE_SCOPE newScope = new SYMBOL_TABLE_SCOPE(tailScope);
		tailScope = newScope;
		if (headScope == null) {
			headScope = newScope;
		}
	}


	public void endScope()
	{
		if (tailScope != null) {
			tailScope = tailScope.prev;
		}

	}

	protected SYMBOL_TABLE() {}


	public static SYMBOL_TABLE getInstance()
	{
		if (instance == null)
		{
			instance = new SYMBOL_TABLE();
			instance.beginScope();
			TYPE_FUNCTION PrintInt = new TYPE_FUNCTION(TYPE_VOID.getInstance(), "PrintInt");
			TYPE_LIST PrintIntParams = new TYPE_LIST(TYPE_INT.getInstance(), null);
			TYPE_FUNCTION PrintString = new TYPE_FUNCTION(TYPE_VOID.getInstance(), "PrintString");
			TYPE_LIST PrintStringParams = new TYPE_LIST(TYPE_STRING.getInstance(), null);
			PrintInt.setParams(PrintIntParams);
			PrintString.setParams(PrintStringParams);
			instance.enter("PrintInt", PrintInt);
			instance.enter("PrintString", PrintString);
		}
		return instance;
	}
	public void setAsClassScope(){
		tailScope.setAsClassScope();
	}

	public boolean isClassScope(){
		return tailScope.isClassScope;
	}

}
