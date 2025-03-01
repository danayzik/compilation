
package IR;

import TEMP.TEMP;
import TYPES.*;

import java.util.Set;

public class IRcommand_New_Array extends IRcommand
{
	TEMP size;
	TYPE type;
	TEMP dst;

	public IRcommand_New_Array(TEMP size, TYPE type, TEMP dst)
	{
		this.size = size;
		this.type = type;
		this.dst = dst;
	}

	public void printMe(){
		super.printMe();
		System.out.printf("%s = New array of type: %s, size: %s\n", dst, type.name, size);
	}


}
