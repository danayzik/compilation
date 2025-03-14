
package IR;

import TEMP.*;
import MIPS.MIPSGenerator;

public class IRcommandConstInt extends IRcommand
{
	TEMP t;
	int value;
	
	public IRcommandConstInt(TEMP t, int value)
	{
		this.t = t;
		this.value = value;
	}

	public void printMe(){
		super.printMe();
		System.out.printf("%s = %d\n", t, value);
	}


	@Override
	public void mipsMe() {
		MIPSGenerator gen = MIPSGenerator.getInstance();
		TEMP_FACTORY fact = TEMP_FACTORY.getInstance();
		String dstReg = fact.tempToRegister(t.getSerialNumber());
		gen.loadImmediate(dstReg, String.valueOf(value));
		super.mipsMe();
	}
	public void inToOut(InOutSets setsObj){
		super.inToOut(setsObj);
		setsObj.tempsOut.remove(t.getSerialNumber());


	}
}
