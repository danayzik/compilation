
package IR;

import TEMP.TEMP;

public class IRcommand_Store_To_Stack extends IRcommand
{

	TEMP src;

	public IRcommand_Store_To_Stack(TEMP src)
	{
		this.src      = src;

	}

	public void printMe(){
		super.printMe();
		System.out.printf("SW  %s 0($sp) \n", src);
	}


}
