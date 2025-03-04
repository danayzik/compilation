
package IR;

import TEMP.TEMP;

public class IRcommandConstString extends IRcommand
{
	String value;
	String label;

	public IRcommandConstString(String label, String value)
	{
		this.label = label;
		this.value = value;
	}

	public void printMe(){
		super.printMe();
		System.out.printf("%s: %s\n", label, value);
	}


}
