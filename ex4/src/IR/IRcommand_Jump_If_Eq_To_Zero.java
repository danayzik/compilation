
package IR;

import TEMP.*;

public class IRcommand_Jump_If_Eq_To_Zero extends IRcommand
{
	TEMP t;
	String label_name;
	
	public IRcommand_Jump_If_Eq_To_Zero(TEMP t, String label_name)
	{
		this.t          = t;
		this.label_name = label_name;
	}

	public void printMe(){
		super.printMe();
		System.out.printf("beq %s %s\n", t, label_name);
	}


}
