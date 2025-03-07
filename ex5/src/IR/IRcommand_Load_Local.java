
package IR;

import TEMP.TEMP;

public class IRcommand_Load_Local extends IRcommand
{
	TEMP dst;
	int offset;
	String varName;

	public IRcommand_Load_Local(TEMP dst, int offset, String name)
	{
		this.dst      = dst;
		this.offset = offset;
		varName = name;
	}

	public void printMe(){
		super.printMe();
		System.out.printf("%s = %d($sp)\n", dst, offset);
	}


}
