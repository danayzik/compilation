
package IR;

import TEMP.TEMP;
import TYPES.*;


public class IRcommand_Array_Typedef extends IRcommand
{
	String typeName;
	TYPE type;


	public IRcommand_Array_Typedef(String name, TYPE t)
	{
		this.typeName = name;
		this.type = t;
	}

	public void printMe(){
		super.printMe();
		System.out.printf("Array typedef of type: %s name: %s\n", type.name, typeName);
	}


}
