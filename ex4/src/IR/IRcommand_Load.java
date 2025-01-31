
package IR;

import TEMP.*;

import java.util.Set;

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
		super.printMe();
		System.out.printf("%s = %s\n", dst, var_name);
	}

	public void inToOut(unInitSets setsObj){
		super.inToOut(setsObj);
		if(setsObj.uninitVariablesIn.contains(var_name)){
			setsObj.uninitTempsOut.add(dst.toString());
		}
		else{
			setsObj.uninitTempsOut.remove(dst.toString());
		}
	}

	public void addUninitVariableUse(Set<String> varSet, unInitSets flowSets){
		if(flowSets.uninitVariablesIn.contains(var_name))
			varSet.add(var_name);
	}
}
