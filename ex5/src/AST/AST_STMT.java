package AST;
import TYPES.*;
public abstract class AST_STMT extends AST_Node
{

	public void PrintMe()
	{
		System.out.print("UNKNOWN AST STATEMENT NODE");
	}
	public void matchReturnType(TYPE t){
		return;
	}
	public void initDeclarations(){}
}
