
package IR;
import TEMP.*;
import MIPS.MIPSGenerator;

public class IRcommand_Return extends IRcommand
{
	TEMP returnSrc;

	public IRcommand_Return(TEMP returnReg)
	{
		returnSrc = returnReg;
	}

	public void printMe(){
		super.printMe();
		System.out.printf("return %s\n", returnSrc);
	}


	public boolean isReturn(){return true;}

	public void mipsMe(){
		MIPSGenerator gen = MIPSGenerator.getInstance();
		if(returnSrc!=null)
			gen.returnValue(TEMP_FACTORY.getInstance().tempToRegister(returnSrc.getSerialNumber()));
		gen.functionEpilogue();
		super.mipsMe();
	}

}
