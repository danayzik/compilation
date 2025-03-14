
package IR;

import MIPS.MIPSGenerator;
import TEMP.*;

import java.util.Set;

public class IRcommand_Load_Address extends IRcommand
{
	TEMP dst;
	String label;

	public IRcommand_Load_Address(TEMP dst, String label)
	{
		this.dst      = dst;
		this.label = label;
	}

	public void printMe(){
		super.printMe();
		System.out.printf("%s = %s\n", dst, label);
	}

	@Override
	public void mipsMe() {
		String reg = TEMP_FACTORY.getInstance().tempToRegister(dst.getSerialNumber());
		MIPSGenerator.getInstance().loadAddress(reg, label);
		super.mipsMe();
	}
	public void inToOut(InOutSets setsObj){
		super.inToOut(setsObj);
		setsObj.tempsOut.remove(dst.getSerialNumber());

	}
}
