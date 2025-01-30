
package IR;
import TEMP.*;

public class IRcommand_Return extends IRcommand
{
	TEMP returnSrc;

	public IRcommand_Return(TEMP returnReg)
	{
		returnSrc = returnReg;
	}

	public void printMe(){
		System.out.printf("return value = %s\njump ra\n", returnSrc);
	}
}
