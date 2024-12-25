package TYPES;

import static AST.SemanticUtils.isLegalAssignment;

public class TYPE_LIST
{

	public TYPE head;
	public TYPE_LIST tail;


	public TYPE_LIST(TYPE head,TYPE_LIST tail)
	{
		this.head = head;
		this.tail = tail;
	}

	public boolean canAssignList(TYPE_LIST rightTypes){
		TYPE_LIST leftTypes = this;

		while (leftTypes != null && rightTypes != null) {

			if (!isLegalAssignment(leftTypes.head, rightTypes.head)) {
				return false;
			}
			leftTypes = leftTypes.tail;
			rightTypes = rightTypes.tail;
		}
		return leftTypes == null && rightTypes == null;
	}


}
