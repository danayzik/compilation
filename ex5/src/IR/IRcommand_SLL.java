
package IR;
import MIPS.MIPSGenerator;
import TEMP.TEMP_FACTORY;

import TEMP.TEMP;

public class IRcommand_SLL extends IRcommand
{
	TEMP indexReg;
	TEMP dst;

	public IRcommand_SLL(TEMP dst, TEMP indexReg)
	{
		this.dst      = dst;
		this.indexReg = indexReg;
	}

	public void printMe(){
		super.printMe();
		System.out.printf("SLL %s %s 2\n", dst, indexReg);
	}

	@Override
	public void mipsMe() {
		MIPSGenerator gen = MIPSGenerator.getInstance();
		TEMP_FACTORY fact = TEMP_FACTORY.getInstance();
		String dstStr = fact.tempToRegister(dst.getSerialNumber());
		String index = fact.tempToRegister(indexReg.getSerialNumber());
		gen.shiftLeft(dstStr, index, 2);
		super.mipsMe();
	}
	public void inToOut(InOutSets setsObj){
		super.inToOut(setsObj);
		setsObj.tempsOut.remove(dst.getSerialNumber());
		setsObj.tempsOut.add(indexReg.getSerialNumber());

	}
}
