
package IR;

import TEMP.TEMP;

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


}
