
package IR;

import TEMP.TEMP;
import TYPES.TYPE;
import TYPES.TYPE_CLASS;

public class IRcommand_New_Class_Object extends IRcommand
{

	TYPE_CLASS type;
	TEMP dst;
	int size;

	public IRcommand_New_Class_Object(TYPE type, TEMP dst)
	{
		this.type = (TYPE_CLASS) type;
		this.dst = dst;
		this.size = ((TYPE_CLASS) type).getSize();
	}

	public void printMe(){
		super.printMe();
		System.out.printf("%s = New object of class %s allocate %d bytes\n", dst, type.name, size);
	}


}
