
package IR;

import MIPS.MIPSGenerator;
import TEMP.*;
import TEMP.TEMP_LIST;

public class IRcommand_PrintIntCall extends IRcommand
{
	TEMP_LIST tempList;


	public IRcommand_PrintIntCall(TEMP_LIST t)
	{

		this.tempList = t;
	}

	public void printMe(){
		super.printMe();
		System.out.print("call PrintInt ");
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
		MIPSGenerator.getInstance().print_int(argReg);
		super.mipsMe();
	}
}
