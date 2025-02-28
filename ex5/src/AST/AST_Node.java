package AST;
import TYPES.*;
import TEMP.*;
public abstract class AST_Node
{
	public int SerialNumber;
	public TYPE semanticType = null;
	public TYPE semantMe(){return null;}
	public TEMP IRme(){return null;}
	public void PrintMe()
	{
		System.out.print("AST NODE UNKNOWN\n");
	}
	public String line;
}
