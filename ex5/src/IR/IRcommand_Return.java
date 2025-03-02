
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
		super.printMe();
		System.out.printf("return %s\n", returnSrc);
	}
	//No return value for ex4

	public boolean isReturn(){return true;}


}
