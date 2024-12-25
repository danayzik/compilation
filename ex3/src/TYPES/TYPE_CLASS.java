package TYPES;

public class TYPE_CLASS extends TYPE
{

	public TYPE_CLASS_MEMBER head;
	public TYPE_CLASS_MEMBER tail;
	public TYPE_CLASS father;

	public TYPE_CLASS(TYPE_CLASS father,String name)
	{
		this.name = name;
		head = null;
		tail = null;
		this.father = father;

	}
	public void addDataMember(TYPE_CLASS_MEMBER member){
		if (head == null) {
			head = member;
			tail = member;
		} else {
			tail.next = member;
			tail = member;
		}
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
		TYPE_CLASS_MEMBER currMember;
		TYPE superType;
		while(ancestor != null){
			currMember = ancestor.head;
			while(currMember != null) {
				if (currMember.name.equals(ID)) {
					if (currMember.isField())
						return true;
					superType = currMember.t;
					if (funcType != superType)
						return true;
					if (!((TYPE_CLASS_METHOD) currMember).canAssignToArgs(funcParams))
						return true;
				}
				currMember = currMember.next;
			}
			ancestor = ancestor.father;
		}
		return false;
	}

	public boolean isShadowingError(String ID){
		TYPE_CLASS ancestor = this.father;
		TYPE_CLASS_MEMBER currMember;
		while(ancestor != null){
			currMember = ancestor.head;
			while(currMember != null) {
				if (currMember.name.equals(ID))
					return true;
				currMember = currMember.next;
			}
			ancestor = ancestor.father;
		}
		return false;
	}

	public TYPE_CLASS_MEMBER findMember(String ID){
		TYPE_CLASS ancestor = this;
		TYPE_CLASS_MEMBER currMember;
		while(ancestor != null){
			currMember = ancestor.head;
			while(currMember != null){
				if (currMember.name.equals(ID))
					return currMember;
				currMember = currMember.next;
			}
			ancestor = ancestor.father;
		}
		return null;
	}
	public TYPE findMemberType(String ID){
		TYPE_CLASS_MEMBER member = findMember(ID);
		if (member == null)
			return null;
		return member.t;
	}


}
