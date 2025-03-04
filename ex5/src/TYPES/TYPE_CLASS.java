package TYPES;

import java.util.ArrayList;
import java.util.List;

public class TYPE_CLASS extends TYPE
{

	public TYPE_CLASS_MEMBER head;
	public TYPE_CLASS_MEMBER tail;
	public TYPE_CLASS father;

	public int fieldCount = 0;
	public boolean hasMethod = false;

	public List<TYPE_CLASS_MEMBER> fieldList = new ArrayList<>();
	public List<String> methodNamesList = new ArrayList<>();

	public List<String> vtable = new ArrayList<>();
	public TYPE_CLASS(TYPE_CLASS father,String name)
	{
		this.name = name;
		head = null;
		tail = null;
		this.father = father;
	}
	public int getSize(){
		return fieldCount*4+(hasMethod ? 4 : 0);
	}
	public List<TYPE_CLASS_MEMBER> getFieldListCopy() {
		return new ArrayList<>(this.fieldList);
	}
	public List<String> getVTableCopy() {
		return new ArrayList<>(this.vtable);
	}
	public List<String> getMethodNamesListCopy(){return  new ArrayList<>(this.methodNamesList);}

	public void setupVTable() {
		if (father != null){
			vtable = father.getVTableCopy();
			methodNamesList = father.getMethodNamesListCopy();
		}
		TYPE_CLASS_MEMBER curr = head;
		while(curr != null){
			if(curr.isMethod()) {
				int index = methodNamesList.indexOf(curr.name);
				if (index >= 0) {
					vtable.set(index, String.format("%s_%s", name, curr.name));
				} else {

					vtable.add(String.format("%s_%s", name, curr.name));
					methodNamesList.add(curr.name);
				}
			}
			curr = curr.next;
		}

	}

	public void setupFieldList(){
		if(father != null)
			fieldList = father.getFieldListCopy();
		TYPE_CLASS_MEMBER curr = head;
		while(curr != null){
			if(curr.isField())
				fieldList.add(curr);
			curr = curr.next;
		}
		fieldCount = fieldList.size();
	}

	public void checkForMethod(){
		if(father!= null){
			if(father.hasMethod)
				hasMethod = true;
		}
	}

	public int getFieldOffset(String fieldName){
		for (int i = 0; i < fieldList.size(); i++) {
			if(fieldList.get(i).name.equals(fieldName))
				return i*4 + (hasMethod ? 4 : 0);
		}
		return -1;
	}

	public void addDataMember(TYPE_CLASS_MEMBER member){
		if(member.isMethod())
			hasMethod = true;
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
