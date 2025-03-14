
package IR;

import TEMP.TEMP;
import TEMP.TEMP_FACTORY;
import MIPS.MIPSGenerator;

public class IRcommand_Array_Access extends IRcommand
{
	TEMP index;
	TEMP firstElemAddr;


	public IRcommand_Array_Access(TEMP firstElemAddr, TEMP index)
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

	@Override
	public void mipsMe() {
		MIPSGenerator gen = MIPSGenerator.getInstance();
		TEMP_FACTORY fact = TEMP_FACTORY.getInstance();
		String arrayAddrReg = fact.tempToRegister(firstElemAddr.getSerialNumber());
		String indexReg = fact.tempToRegister(index.getSerialNumber());
		gen.checkForNullDeref(arrayAddrReg);
		gen.outOfBoundsCheck(arrayAddrReg, indexReg);
		super.mipsMe();
	}
}
