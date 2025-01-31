
package IR;


import java.util.HashSet;
import java.util.Set;

public abstract class IRcommand
{
	public IRcommand nextCmdInLine = null;
	public IRcommand jumpToCmd = null;
	protected static int label_counter=0;

	public int index;
	public static String getFreshLabel(String msg)
	{
		return String.format("Label_%d_%s",label_counter++,msg);
	}

	public void printMe(){
		System.out.printf("%d: ", index);}

	public boolean isJump(){return false;}

	public void inToOut(unInitSets setsObj){
		workedOn = true;
		setsObj.uninitVariablesOut = new HashSet<>(setsObj.uninitVariablesIn);
		setsObj.uninitTempsOut = new HashSet<>(setsObj.uninitTempsIn);
	}

	public void addUninitVariableUse(Set<String> varSet, unInitSets flowSets){}

	public boolean workedOn = false;
}
