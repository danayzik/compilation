
package IR;

import MIPS.MIPSGenerator;
import TEMP.*;
import TEMP.TEMP_LIST;

public class IRcommand_PrintStringCall extends IRcommand
{
	TEMP_LIST tempList;


	public IRcommand_PrintStringCall(TEMP_LIST t)
	{

		this.tempList = t;
	}

	public void printMe(){
		super.printMe();
		System.out.print("call PrintString ");
		TEMP curr;
		TEMP_LIST currTail = tempList;
		while (currTail != null){
			curr = currTail.head;
			if(curr!=null) System.out.printf("%s ", curr);
			currTail = currTail.tail;
		}
		System.out.println();
	}
	@Override
	public void mipsMe() {
		TEMP arg = tempList.head;
		String argReg = TEMP_FACTORY.getInstance().tempToRegister(arg.getSerialNumber());
		MIPSGenerator.getInstance().printString(argReg);
		super.mipsMe();
	}
}
