
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
		super.printMe();
		System.out.printf("%s = %s\n", var_name, src);
	}

	public void inToOut(unInitSets setsObj){
		super.inToOut(setsObj);
		if(setsObj.uninitTempsIn.contains(src.toString())){
			setsObj.uninitVariablesOut.add(var_name);
		}
		else{
			setsObj.uninitVariablesOut.remove(var_name);
		}
	}
}
