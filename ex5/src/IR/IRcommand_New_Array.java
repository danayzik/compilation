
package IR;

import MIPS.MIPSGenerator;
import TEMP.*;
import TYPES.*;

public class IRcommand_New_Array extends IRcommand
{
	TEMP arraySize;
	TYPE type;
	TEMP dst;

	public IRcommand_New_Array(TEMP size, TYPE type, TEMP dst)
	{
		this.arraySize = size;
		this.type = type;
		this.dst = dst;
	}

	public void printMe(){
		super.printMe();
		System.out.printf("%s = New array of type: %s, size: %s\n", dst, type.name, arraySize);
	}
	public void inToOut(InOutSets setsObj){
		super.inToOut(setsObj);
		setsObj.tempsOut.remove(dst.getSerialNumber());
		setsObj.tempsOut.add(arraySize.getSerialNumber());

	}

	@Override
	public void mipsMe() {
		MIPSGenerator gen = MIPSGenerator.getInstance();
		TEMP_FACTORY fact = TEMP_FACTORY.getInstance();
		String dstReg = fact.tempToRegister(dst.getSerialNumber());
		String arraySizeReg = fact.tempToRegister(arraySize.getSerialNumber());

		gen.newArray(dstReg, arraySizeReg);
		super.mipsMe();
	}
}
