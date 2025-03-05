
package IR;

import TEMP.TEMP;

public class IRcommand_Store_To_Stack extends IRcommand
{
	int offset;
	TEMP src;

	public IRcommand_Store_To_Stack(int offset, TEMP src)
	{
		this.src      = src;
		this.offset = offset;
	}

	public void printMe(){
		super.printMe();
		System.out.printf("SW From %s to FP+%d \n", src, offset);
	}


}
