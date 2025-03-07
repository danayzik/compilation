
package IR;

import TEMP.TEMP;


public class IRcommand_Array_Access extends IRcommand
{
	TEMP index;
	TEMP firstElemAddr;
	TEMP sizeTemp;

	public IRcommand_Array_Access(TEMP sizeTemp, TEMP firstElemAddr, TEMP index)
	{
		this.index = index;
		this.firstElemAddr = firstElemAddr;
	}

	/* This needs to check if firstElemAddr is 0(null),
	and then load size then check access
	*/
	public void printMe(){
		super.printMe();
		System.out.printf("Array access, first element at: %s index: %s\n", firstElemAddr, index);
	}


}
