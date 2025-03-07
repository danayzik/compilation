
package IR;

import TEMP.TEMP;

public class IRcommand_Load_Global extends IRcommand
{
	TEMP dst;
	String label;

	public IRcommand_Load_Global(TEMP dst, String label)
	{
		this.dst      = dst;
		this.label = label;
	}

	public void printMe(){
		super.printMe();
		System.out.printf("%s = global %s\n", dst, label);
	}


}
