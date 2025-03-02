
package IR;

import TEMP.TEMP;
import TEMP.TEMP_LIST;

public class IRcommand_MethodCall extends IRcommand
{
	TEMP_LIST tempList;
	String funcName;
	TEMP ownerObj;

	public IRcommand_MethodCall(String id, TEMP_LIST t, TEMP ownerObj)
	{
		this.funcName = id;
		this.tempList = t;
		this.ownerObj = ownerObj;
	}

	public void printMe(){
		super.printMe();
		System.out.printf("virtual call %s %s ",ownerObj, funcName);
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
