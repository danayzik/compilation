
package IR;

import TEMP.TEMP;

import java.util.Set;

public class IRcommand_Load_Field_From_SelfObj extends IRcommand
{
	TEMP dst;
	String fieldName;
	int offset;

	public IRcommand_Load_Field_From_SelfObj(TEMP dst, String name, int offset)
	{
		this.dst      = dst;
		fieldName = name;
		this.offset = offset;
	}

	public void printMe(){
		super.printMe();
		System.out.printf("%s = this.%s (it's at offset %d)\n", dst, fieldName, offset);
	}


}
