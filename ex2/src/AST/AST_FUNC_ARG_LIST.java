package AST;

public class AST_FUNC_ARG_LIST extends AST_Node
{

	public AST_FUNC_ARG head;
	public AST_FUNC_ARG_LIST tail;


	public AST_FUNC_ARG_LIST(AST_FUNC_ARG head,AST_FUNC_ARG_LIST tail)
	{
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.head = head;
		this.tail = tail;
	}


	public void PrintMe()
	{
		System.out.print("AST NODE FUNC ARG LIST\n");
		if (head != null) head.PrintMe();
		if (tail != null) tail.PrintMe();

		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"FUNC ARG\nLIST\n");
		if (head != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,head.SerialNumber);
		if (tail != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,tail.SerialNumber);
	}
	
}
