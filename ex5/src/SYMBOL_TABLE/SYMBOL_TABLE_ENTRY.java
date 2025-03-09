package SYMBOL_TABLE;

import TYPES.*;

public class SYMBOL_TABLE_ENTRY {
	String name;
	TYPE type;
	SYMBOL_TABLE_ENTRY next;
	public int indexInFunc = -1;
	public boolean isLocal = false;
	public boolean isField = false;
	public boolean isGlobal = false;
	public boolean isLocalArg = false;
	public int indexInArg = -1;


	public SYMBOL_TABLE_ENTRY(String name, TYPE type, int index) {
		this.name = name;
		this.type = type;
		this.indexInFunc = index;
		this.next = null;
	}

	public void setAsLocal(){
		this.isLocal = true;
	}
	public void setAsGlobal(){
		this.isGlobal = true;
	}
	public void setAsField(){
		this.isField = true;
	}
	public void setAsArg(int indexInArg){
		this.indexInArg = indexInArg;
		this.isLocalArg = true;}

	public String toString(){
		return String.format("Entry: %s %s", type, name);
	}
}