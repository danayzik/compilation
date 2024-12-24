package AST;
import TYPES.*;
public abstract class AST_Node
{
	public int SerialNumber;

	public TYPE semantMe(){return null;}
	public void PrintMe()
	{
		System.out.print("AST NODE UNKNOWN\n");
	}
	public String line;
}
