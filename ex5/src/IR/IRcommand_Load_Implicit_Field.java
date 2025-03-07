
package IR;

import TEMP.TEMP;

public class IRcommand_Load_Implicit_Field extends IRcommand
{
	TEMP dst;
	int offset;
	String fieldName;

	public IRcommand_Load_Implicit_Field(TEMP dst, int offset, String fieldName)
	{
		this.dst      = dst;
		this.offset = offset;
		this.fieldName = fieldName;

	}

	public void printMe(){
		super.printMe();
		System.out.printf("%s = this.%s\n", dst, fieldName);
	}


}
