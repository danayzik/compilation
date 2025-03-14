
package IR;

import MIPS.MIPSGenerator;
import TEMP.*;

public class IRcommand_Jump_If_Eq_To_Zero extends IRcommand
{
	TEMP t;
	String label_name;
	
	public IRcommand_Jump_If_Eq_To_Zero(TEMP t, String label_name)
	{
		this.t          = t;
		this.label_name = label_name;
	}

	public void printMe(){
		super.printMe();
		System.out.printf("beq %s %s\n", t, label_name);
	}

	@Override
	public void mipsMe() {
		String reg1 = TEMP_FACTORY.getInstance().tempToRegister(t.getSerialNumber());
		String reg2 = "$zero";
		MIPSGenerator.getInstance().beq(reg1, reg2, label_name);
		super.mipsMe();
	}
	public void inToOut(InOutSets setsObj){
		super.inToOut(setsObj);
		setsObj.tempsOut.add(t.getSerialNumber());

	}
}
