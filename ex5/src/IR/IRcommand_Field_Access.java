
package IR;

import TEMP.TEMP;


public class IRcommand_Field_Access extends IRcommand
{
	String fieldName;
	TEMP src;
	TEMP dst;

	public IRcommand_Field_Access(TEMP dst, TEMP src, String fieldName)
	{
		this.fieldName = fieldName;
		this.src = src;
		this.dst = dst;
	}

	public void printMe(){
		super.printMe();
		System.out.printf("%s = Field access in %s field: %s\n", dst, src, fieldName);
	}


}
