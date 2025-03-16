package AST;
import TYPES.*;
import TEMP.*;

public class AST_STMT_LIST extends AST_Node
{

	public AST_STMT head;
	public AST_STMT_LIST tail;


	public AST_STMT_LIST(AST_STMT head,AST_STMT_LIST tail)
	{
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.head = head;
		this.tail = tail;
	}


	public void PrintMe()
	{
		System.out.print("AST NODE STMT LIST\n");
		if (head != null) head.PrintMe();
		if (tail != null) tail.PrintMe();

		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"STMT\nLIST\n");
		if (head != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,head.SerialNumber);
		if (tail != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,tail.SerialNumber);
	}
	public TYPE semantMe(){
		if(head != null) head.semantMe();
		if(tail != null) tail.semantMe();
		return null;
	}
	public void matchReturnType(TYPE t){
		if(head != null) head.matchReturnType(t);
		if(tail != null) tail.matchReturnType(t);
	}

	public TEMP IRme(){
		if(head!=null) head.IRme();
		if(tail!=null) tail.IRme();
		return null;
	}

	public void initDeclarations(){
		if(head != null) head.initDeclarations();
		if(tail != null) tail.initDeclarations();
	}
	
}
