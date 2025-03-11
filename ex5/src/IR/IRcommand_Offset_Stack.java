
package IR;

import TEMP.TEMP;

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


}
