
package IR;

import AST.Address;
import TEMP.TEMP;

public class IRcommand_Load extends IRcommand
{
	Address addr;
	TEMP dst;

	public IRcommand_Load(TEMP dst, Address addr)
	{
		this.dst      = dst;
		this.addr = addr;
	}

	public void printMe(){
		super.printMe();
		System.out.printf("LW %s  %s\n", dst, addr);
	}


}
