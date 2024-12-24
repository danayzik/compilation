package TYPES;

import AST.SemanticError;

public class TYPE_LIST
{

	public TYPE head;
	public TYPE_LIST tail;


	public TYPE_LIST(TYPE head,TYPE_LIST tail)
	{
		this.head = head;
		this.tail = tail;
	}
	public static boolean canAssignTypes(TYPE leftType, TYPE rightType){
		if(leftType == rightType){
			return true;
		}
		boolean bothObjects = leftType.isClass() && rightType.isClass();
		if(bothObjects){
			if(((TYPE_CLASS)rightType).isAncestor(((TYPE_CLASS)leftType))){
				return true;
			}
		}
		if (rightType.isNil() && (leftType.isArray() || leftType.isClass()))
			return true;
		if(leftType.isArray()){
			if(((TYPE_ARRAY)leftType).arrayType == rightType)
				return true;
		}
		return false;
	}
	public boolean canAssignList(TYPE_LIST rightTypes){
		TYPE_LIST leftTypes = this;

		while (leftTypes != null && leftTypes != null) {

			if (!canAssignTypes(leftTypes.head, rightTypes.head)) {
				return false;
			}
			leftTypes = leftTypes.tail;
			rightTypes = rightTypes.tail;
		}
		return leftTypes == null && rightTypes == null;
	}

	public boolean matchingList(TYPE_LIST list2) {
		TYPE_LIST list1 = this;
		while (list1 != null && list2 != null) {

			if (list1.head != list2.head) {
				return false;
			}
			list1 = list1.tail;
			list2 = list2.tail;
		}
		return list1 == null && list2 == null;
	}
}
