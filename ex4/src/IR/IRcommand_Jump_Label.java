
package IR;

import TEMP.*;

public class IRcommand_Jump_Label extends IRcommand
{
	String label_name;
	
	public IRcommand_Jump_Label(String label_name)
	{
		this.label_name = label_name;
	}

	public void printMe(){
		System.out.printf("jump %s\n", label_name);
	}

	public boolean isJump(){return true;}
}
