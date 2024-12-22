package SYMBOL_TABLE;

import TYPES.*;

public class SYMBOL_TABLE_ENTRY {
	String name;
	TYPE type;
	SYMBOL_TABLE_ENTRY next;


	public SYMBOL_TABLE_ENTRY(String name, TYPE type) {
		this.name = name;
		this.type = type;
		this.next = null;
	}
}