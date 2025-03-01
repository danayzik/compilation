
package IR;

import TEMP.TEMP;


public class IRcommand_Array_Access extends IRcommand
{
	TEMP index;
	TEMP src;
	TEMP dst;

	public IRcommand_Array_Access(TEMP dst, TEMP src, TEMP index)
	{
		this.index = index;
		this.src = src;
		this.dst = dst;
	}

	public void printMe(){
		super.printMe();
		System.out.printf("%s = Array access in %s index: %s\n", dst, src, index);
	}


}
