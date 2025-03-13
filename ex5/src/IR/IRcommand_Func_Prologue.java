
package IR;

import AST.Address;
import MIPS.MIPSGenerator;
import TEMP.TEMP;
import TEMP.TEMP_FACTORY;

public class IRcommand_Func_Prologue extends IRcommand
{


	public IRcommand_Func_Prologue()
	{

	}

	public void printMe(){
		super.printMe();
		System.out.printf("Function Prologue\n");
	}
	@Override
	public void mipsMe() {

		MIPSGenerator gen = MIPSGenerator.getInstance();
		gen.functionPrologue();
		super.mipsMe();

	}


}
