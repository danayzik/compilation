
package IR;

import TEMP.TEMP;

public class IRcommandConstNil extends IRcommand
{
	TEMP t;

	public IRcommandConstNil(TEMP t)
	{
		this.t = t;

	}

	public void printMe(){
		super.printMe();
		System.out.printf("%s = nil\n", t);
	}


}
