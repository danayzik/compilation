package AST;

public class AST_CFIELD_LIST extends AST_Node
{
	public AST_CFIELD head;
	public AST_CFIELD_LIST tail;


	public AST_CFIELD_LIST(AST_CFIELD head,AST_CFIELD_LIST tail)
	{
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.head = head;
		this.tail = tail;
	}


	public void PrintMe()
	{

		System.out.print("AST NODE CFIELD LIST\n");
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"CFIELD\nLIST\n");
		if (head != null) head.PrintMe();
		if (tail != null) tail.PrintMe();
		if (head != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,head.SerialNumber);
		if (tail != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,tail.SerialNumber);
	}
	
}
