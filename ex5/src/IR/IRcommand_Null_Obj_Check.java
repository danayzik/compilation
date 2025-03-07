
package IR;

import TEMP.TEMP;


public class IRcommand_Null_Obj_Check extends IRcommand
{

	TEMP objAddr;


	public IRcommand_Null_Obj_Check(TEMP objAddr)
	{
		this.objAddr = objAddr;
	}

	public void printMe(){
		super.printMe();
		System.out.printf("if %s == 0 abort\n", objAddr);
	}


}
