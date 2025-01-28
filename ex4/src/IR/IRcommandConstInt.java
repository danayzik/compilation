
package IR;

import TEMP.*;

public class IRcommandConstInt extends IRcommand
{
	TEMP t;
	int value;
	
	public IRcommandConstInt(TEMP t,int value)
	{
		this.t = t;
		this.value = value;
	}
}
