
package IR;

import TEMP.TEMP;
import TYPES.TYPE;

public class IRcommand_New_Class_Object extends IRcommand
{

	TYPE type;
	TEMP dst;

	public IRcommand_New_Class_Object(TYPE type, TEMP dst)
	{
		this.type = type;
		this.dst = dst;
	}

	public void printMe(){
		super.printMe();
		System.out.printf("%s = New class %s\n", dst, type.name);
	}


}
