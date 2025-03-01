
package IR;

import TEMP.TEMP;

public class IRcommandConstString extends IRcommand
{
	TEMP t;
	String value;

	public IRcommandConstString(TEMP t, String value)
	{
		this.t = t;
		this.value = value;
	}

	public void printMe(){
		super.printMe();
		System.out.printf("%s = %s\n", t, value);
	}


}
