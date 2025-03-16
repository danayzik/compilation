package SYMBOL_TABLE;
import TYPES.TYPE;
import TYPES.TYPE_CLASS;

public class SYMBOL_TABLE_SCOPE {

	SYMBOL_TABLE_SCOPE prev;
	SYMBOL_TABLE_ENTRY head;
	SYMBOL_TABLE_ENTRY tail;


	public boolean isClassScope;

	public int varCountInFunc = 0;
	public int argCountInFunc = 0;


	public SYMBOL_TABLE_SCOPE(SYMBOL_TABLE_SCOPE parent) {
		this.prev = parent;
		this.head = null;
		this.tail = null;
		this.isClassScope = false;
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
	public void inheritVarCount(){
		varCountInFunc = prev.varCountInFunc;
		argCountInFunc = prev.argCountInFunc;
	}

	public TYPE findInScope(String name){
		SYMBOL_TABLE_ENTRY curr = head;
		while(curr != null){
			if(curr.name.equals(name)) {

				SYMBOL_TABLE.getInstance().lastSearched = curr;
				return curr.type;
			}
			curr = curr.next;
		}
		return null;
	}
	public TYPE findInClass(String name){
		TYPE_CLASS owner = TYPE_TABLE.getInstance().getCurrentClassType();
		return owner.findMemberType(name);
	}

	public TYPE findInAllScopes(String name){
		SYMBOL_TABLE instance = SYMBOL_TABLE.getInstance();
		instance.lastSearched = null;
		instance.lastSearchedIsMethod = false;
		TYPE target;
		SYMBOL_TABLE_SCOPE currScope = this;
		while (currScope != null){
			target = currScope.findInScope(name);
			if (target != null) {
				if(currScope.isClassScope)
					instance.lastSearchedIsMethod = true;
				return target;
			}
			if (currScope.isClassScope) {

				target = findInClass(name);
			}
			if (target != null) {
				instance.lastSearchedIsMethod = true;
				return target;
			}
			currScope = currScope.prev;
		}
		return null;
	}

	public void setAsClassScope(){
		isClassScope = true;
	}
}