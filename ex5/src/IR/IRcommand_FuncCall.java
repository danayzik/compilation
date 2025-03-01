
package IR;

import TEMP.*;

public class IRcommand_FuncCall extends IRcommand
{
	TEMP_LIST tempList;
	String funcName;

	public IRcommand_FuncCall(String id, TEMP_LIST t)
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
