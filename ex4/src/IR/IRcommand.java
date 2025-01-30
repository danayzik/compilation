
package IR;



public abstract class IRcommand
{
	public IRcommand nextCmdInLine;
	public IRcommand jumpToCmd;
	protected static int label_counter=0;
	public static String getFreshLabel(String msg)
	{
		return String.format("Label_%d_%s",label_counter++,msg);
	}

	public void printMe(){return;}

	public boolean isJump(){return false;}
}
