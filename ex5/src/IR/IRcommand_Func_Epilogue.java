
package IR;

import MIPS.MIPSGenerator;

public class IRcommand_Func_Epilogue extends IRcommand
{


	public IRcommand_Func_Epilogue()
	{

	}

	public void printMe(){
		super.printMe();
		System.out.printf("Function Epilogue\n");
	}
	@Override
	public void mipsMe() {

		MIPSGenerator gen = MIPSGenerator.getInstance();
		gen.functionEpilogue();
		super.mipsMe();

	}

	@Override
	public boolean isReturn() {
		return true;
	}
}
