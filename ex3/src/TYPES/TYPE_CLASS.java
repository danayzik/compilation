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


}
