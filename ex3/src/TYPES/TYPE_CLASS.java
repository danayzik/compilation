package TYPES;

public class TYPE_CLASS extends TYPE
{

	public TYPE_CLASS father;

	public TYPE_LIST data_members;


	public TYPE_CLASS(TYPE_CLASS father,String name)
	{
		this.name = name;
		this.father = father;
		this.data_members = null;
	}
	public void setDataMembers(TYPE_LIST data_members){
		this.data_members = data_members;
	}
	public boolean isClass(){return true;}

	public boolean isAncestor(TYPE_CLASS potentialAncestor){
		TYPE_CLASS ancestor = father;
		while (ancestor != null){
			if(potentialAncestor == ancestor)
				return true;
			ancestor = ancestor.father;
		}
		return false;
	}
	public boolean isOverrideError(TYPE funcType,String ID, TYPE_LIST funcParams){
		TYPE_CLASS ancestor = this.father;
		TYPE_LIST ancestorFields;
		TYPE currField;
		TYPE superType;
		String superID;
		TYPE_LIST superParams;
		while(ancestor != null){
			ancestorFields = ancestor.data_members;
			currField = ancestorFields.head;
			if(currField instanceof TYPE_FUNCTION){
				if (currField.name.equals(ID)){
					if (((TYPE_FUNCTION) currField).matchingTypes(funcType))


				}
			}
		}
	}


}
