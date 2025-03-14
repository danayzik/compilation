
package IR;

import java.util.HashSet;
import java.util.Set;

public abstract class IRcommand
{
	public IRcommand next = null;
	public IRcommand prev = null;
	public IRcommand jumpPrev = null;
	public IRcommand jumpToCmd = null;
	protected static int label_counter=0;
	protected static int strLabelCount=0;

	public int index;
	public static String getFreshLabel(String msg)
	{
		return String.format("Label_%d_%s",label_counter++,msg);
	}

	public static String getFreshStrLabel(){return String.format("strLabel%d", strLabelCount++);}

	public void printMe(){
		System.out.printf("%d: ", index);}

	public boolean isJump(){return false;}

	public boolean isReturn(){return false;}

	public boolean isLabel(){return false;}

	public void inToOut(InOutSets setsObj){
		workedOn = true;
		setsObj.tempsOut = new HashSet<>(setsObj.tempsIn);
	}



	public boolean workedOn = false;
	public void mipsMe(){
		if(next!=null)next.mipsMe();
	}

}
