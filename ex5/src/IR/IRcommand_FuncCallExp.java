
package IR;

import TEMP.TEMP;
import TEMP.TEMP_LIST;

public class IRcommand_FuncCallExp extends IRcommand
{
	TEMP_LIST tempList;
	String funcName;
	TEMP dst;

	public IRcommand_FuncCallExp(String id, TEMP_LIST t, TEMP dst)
	{
		this.funcName = id;
		this.tempList = t;
		this.dst = dst;
	}

	public void printMe(){
		super.printMe();

		System.out.printf("%s = call %s ", dst, funcName);
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
