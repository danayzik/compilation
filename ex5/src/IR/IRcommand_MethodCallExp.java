
package IR;

import TEMP.TEMP;
import TEMP.TEMP_LIST;

public class IRcommand_MethodCallExp extends IRcommand
{
	TEMP_LIST tempList;
	String funcName;
	TEMP dst;
	TEMP ownerObj;

	public IRcommand_MethodCallExp(String id, TEMP_LIST t, TEMP dst, TEMP ownerObj)
	{
		this.funcName = id;
		this.tempList = t;
		this.dst = dst;
		this.ownerObj = ownerObj;
	}

	public void printMe(){
		super.printMe();

		System.out.printf("%s = virtual call %s %s ", dst, ownerObj, funcName);
		TEMP curr;
		TEMP_LIST currTail = tempList;
		while (currTail != null){
			curr = currTail.head;
			if(curr!=null) System.out.printf("%s ", curr);
			currTail = currTail.tail;
		}
		System.out.println();
	}
}
