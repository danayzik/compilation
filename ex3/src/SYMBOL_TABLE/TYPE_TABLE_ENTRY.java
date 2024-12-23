package SYMBOL_TABLE;

import TYPES.*;

public class TYPE_TABLE_ENTRY {
	String name;
	TYPE type;
	TYPE_TABLE_ENTRY next;


	public TYPE_TABLE_ENTRY(String name, TYPE type) {
		this.name = name;
		this.type = type;
		this.next = null;
	}
}