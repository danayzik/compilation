
package IR;

import TEMP.*;

public class IRcommand_Store extends IRcommand
{
	TEMP addr;
	TEMP src;
	
	public IRcommand_Store(TEMP addr, TEMP src)
	{
		this.src      = src;
		this.addr = addr;
	}

	public void printMe(){
		super.printMe();
		System.out.printf("SW from %s to %s\n", addr, src);
	}


}
