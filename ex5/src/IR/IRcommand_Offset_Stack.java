
package IR;

import MIPS.MIPSGenerator;


public class IRcommand_Offset_Stack extends IRcommand
{
	int offset;

	public IRcommand_Offset_Stack(int offset)
	{
		this.offset = offset;
	}

	public void printMe(){
		super.printMe();
		System.out.printf("addi $sp $sp %d\n", offset);
	}

	@Override
	public void mipsMe() {
		MIPSGenerator.getInstance().addToStack(offset);
		super.mipsMe();
	}
}
