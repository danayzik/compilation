
package IR;

import TEMP.TEMP;
import TEMP.TEMP_LIST;

public class IRcommand_PrintStringCall extends IRcommand
{
	TEMP_LIST tempList;
	String funcName;

	public IRcommand_PrintStringCall(String id, TEMP_LIST t)
	{
		this.funcName = id;
		this.tempList = t;
	}

	public void printMe(){
		super.printMe();
		System.out.printf("call %s ", funcName);
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
