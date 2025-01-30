
package IR;

import TEMP.*;

public class IRcommand_Load extends IRcommand
{
	TEMP dst;
	String var_name;
	
	public IRcommand_Load(TEMP dst,String var_name)
	{
		this.dst      = dst;
		this.var_name = var_name;
	}

	public void printMe(){
		System.out.printf("%s = %s\n", dst, var_name);
	}
}
