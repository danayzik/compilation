
package SYMBOL_TABLE;

import java.io.PrintWriter;

import TYPES.*;


public class SYMBOL_TABLE
{

	private SYMBOL_TABLE_SCOPE headScope;
	private SYMBOL_TABLE_SCOPE tailScope;
	private static SYMBOL_TABLE instance = null;

	public SYMBOL_TABLE_ENTRY lastSearched = null;





	public int enter(String name,TYPE type) {
		int index = tailScope.varCountInFunc;
		SYMBOL_TABLE_ENTRY newEntry = new SYMBOL_TABLE_ENTRY(name, type, index);
		if (tailScope.isClassScope){
			newEntry.setAsField();

		}
		else if (tailScope == headScope) {
			newEntry.setAsGlobal();

		}
		else{
			newEntry.setAsLocal();

		}
		if (tailScope != null) {
			tailScope.varCountInFunc++;
			tailScope.addEntry(newEntry);
		}
		return index;
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
	public void inheritVarCount(){
		tailScope.inheritVarCount();
	}


	public void endScope()
	{
		if (tailScope != null) {
			tailScope.prev.varCountInFunc = tailScope.varCountInFunc;
			tailScope = tailScope.prev;
		}

	}

	public SYMBOL_TABLE_ENTRY getLastSearchedEntry(){
		return lastSearched;
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


}
