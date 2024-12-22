package SYMBOL_TABLE;
import TYPES.TYPE;

public class SYMBOL_TABLE_SCOPE {

	SYMBOL_TABLE_SCOPE prev;
	SYMBOL_TABLE_ENTRY head;
	SYMBOL_TABLE_ENTRY tail;


	public SYMBOL_TABLE_SCOPE(SYMBOL_TABLE_SCOPE parent) {
		this.prev = parent;
		this.head = null;
		this.tail = null;
	}

	public void addEntry(SYMBOL_TABLE_ENTRY e) {
		if (head == null) {
			head = e;
			tail = e;
		} else {
			tail.next = e;
			tail = e;
		}
	}

	public TYPE findInScope(String name){
		SYMBOL_TABLE_ENTRY curr = head;
		while(curr != null){
			if(curr.name.equals(name))
				return curr.type;
			curr = curr.next;
		}
		return null;
	}

	public TYPE findInAllScopes(String name){
		TYPE target;
		SYMBOL_TABLE_SCOPE currScope = this;
		while (currScope != null){
			target = currScope.findInScope(name);
			if (target != null)
				return target;
			currScope = currScope.prev;
		}
		return null;

	}
}