
package IR;

import TEMP.TEMP;

public class IRcommand_Load_Stack_Offset extends IRcommand
{
	TEMP dst;
	int offset;

	public IRcommand_Load_Stack_Offset(TEMP dst, int offset)
	{
		this.dst      = dst;
		this.offset = offset;
	}

	public void printMe(){
		super.printMe();
		System.out.printf("%s = Address(sp+%d)\n", dst, offset);
	}


}
