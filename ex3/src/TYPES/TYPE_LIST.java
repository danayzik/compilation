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
		if (leftType instanceof TYPE_CLASS_MEMBER)
			leftType = ((TYPE_CLASS_MEMBER) leftType).t;
		if (rightType instanceof TYPE_CLASS_MEMBER)
			rightType = ((TYPE_CLASS_MEMBER) rightType).t;
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
		if(leftType.isArray() && rightType.isNewArray()){
			if(((TYPE_ARRAY)leftType).arrayType == ((TYPE_NEW_ARRAY)rightType).arrayType)
				return true;
		}
		return false;
	}
	public boolean canAssignList(TYPE_LIST rightTypes){
		TYPE_LIST leftTypes = this;

		while (leftTypes != null && rightTypes != null) {

			if (!canAssignTypes(leftTypes.head, rightTypes.head)) {
				return false;
			}
			leftTypes = leftTypes.tail;
			rightTypes = rightTypes.tail;
		}
		return leftTypes == null && rightTypes == null;
	}


}
