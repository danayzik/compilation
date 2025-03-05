
package IR;

import TEMP.TEMP;

import java.util.Set;

public class IRcommand_Load_From_Stack extends IRcommand
{
	TEMP dst;
	int offset;

	public IRcommand_Load_From_Stack(TEMP dst, int offset)
	{
		this.dst      = dst;
		this.offset = offset;
	}

	public void printMe(){
		super.printMe();
		System.out.printf("%s = sp+%d\n", dst, offset);
	}


}
