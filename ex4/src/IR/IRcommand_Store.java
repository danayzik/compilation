
package IR;

import TEMP.*;

public class IRcommand_Store extends IRcommand
{
	String var_name;
	TEMP src;
	
	public IRcommand_Store(String var_name, TEMP src)
	{
		this.src      = src;
		this.var_name = var_name;
	}

	public void printMe(){
		System.out.printf("%s = %s\n", var_name, src);
	}
}
