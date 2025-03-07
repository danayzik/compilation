
package IR;

import AST.Address;
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


}
