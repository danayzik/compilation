package SYMBOL_TABLE;
import TYPES.*;

public class TYPE_TABLE {

	TYPE_TABLE_ENTRY head;
	TYPE_TABLE_ENTRY tail;
	private static TYPE_TABLE instance = null;


	protected TYPE_TABLE() {}

	public void enter(String name, TYPE t) {
		TYPE_TABLE_ENTRY e = new TYPE_TABLE_ENTRY(name, t);
		if (head == null) {
			head = e;
			tail = e;
		} else {
			tail.next = e;
			tail = e;
		}
	}

	public TYPE find(String name){
		TYPE_TABLE_ENTRY curr = head;
		while(curr != null){
			if(curr.name.equals(name))
				return curr.type;
			curr = curr.next;
		}
		return null;
	}
	public static TYPE_TABLE getInstance()
	{
		if (instance == null)
		{
			instance = new TYPE_TABLE();
			instance.enter("int", TYPE_INT.getInstance());
			instance.enter("string", TYPE_STRING.getInstance());
			instance.enter("void", TYPE_VOID.getInstance());
		}
		return instance;
	}
	// This function assumes that you're currently in the scope of a class.
	public TYPE_CLASS getCurrentClassType(){
		return (TYPE_CLASS)tail.type;
	}


}