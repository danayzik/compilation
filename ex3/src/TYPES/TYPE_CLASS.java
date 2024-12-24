package TYPES;

public class TYPE_CLASS extends TYPE
{

	public TYPE_CLASS father;

	public TYPE_CLASS_MEMBER_LIST data_members;


	public TYPE_CLASS(TYPE_CLASS father,String name)
	{
		this.name = name;
		this.father = father;
		this.data_members = null;
	}
	public void setDataMembers(TYPE_CLASS_MEMBER_LIST data_members){
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

	//ugliest function i ever made
	public boolean isOverrideError(TYPE funcType,String ID, TYPE_LIST funcParams){
		TYPE_CLASS ancestor = this.father;
		TYPE_CLASS_MEMBER_LIST ancestorMembers;
		TYPE_CLASS_MEMBER currMember;
		TYPE superType;
		TYPE_LIST superParams;
		while(ancestor != null){
			ancestorMembers = ancestor.data_members;
			while(ancestorMembers != null) {
				currMember = ancestorMembers.head;
				if (currMember.name.equals(ID)) {
					if (currMember.isField())
						return true;
					superType = currMember.t;
					superParams = ((TYPE_CLASS_METHOD) currMember).args;
					if (funcType != superType)
						return true;
					if (!superParams.matchingList(funcParams))
						return true;
				}
				ancestorMembers = ancestorMembers.tail;
			}
			ancestor = ancestor.father;
		}
		return false;
	}

	public boolean isShadowingError(String ID){
		TYPE_CLASS ancestor = this.father;
		TYPE_CLASS_MEMBER_LIST ancestorMembers;
		TYPE_CLASS_MEMBER currMember;
		while(ancestor != null){
			ancestorMembers = ancestor.data_members;
			while(ancestorMembers != null) {
				currMember = ancestorMembers.head;
				if (currMember.name.equals(ID))
					return true;
				ancestorMembers = ancestorMembers.tail;
			}
			ancestor = ancestor.father;
		}
		return false;
	}

	public TYPE_CLASS_MEMBER findMember(String ID){

		TYPE_CLASS ancestor = this;
		TYPE_CLASS_MEMBER_LIST currMembers;
		TYPE_CLASS_MEMBER currMember;
		while(ancestor != null){
			currMembers = ancestor.data_members;
			while(currMembers != null){
				currMember = currMembers.head;
				if (currMember != null) {
					if (currMember.name.equals(ID))
						return currMember;
				}
				currMembers = currMembers.tail;
			}
			ancestor = ancestor.father;
		}
		return null;
	}


}
