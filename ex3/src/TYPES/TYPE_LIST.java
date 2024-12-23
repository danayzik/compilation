package TYPES;

public class TYPE_LIST
{

	public TYPE head;
	public TYPE_LIST tail;


	public TYPE_LIST(TYPE head,TYPE_LIST tail)
	{
		this.head = head;
		this.tail = tail;
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
