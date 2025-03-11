package AST;
import TYPES.*;
import TEMP.*;
import IR.*;
public class AST_EXP_LIST extends AST_Node
{

	public AST_EXP head;
	public AST_EXP_LIST tail;

	public AST_EXP_LIST(AST_EXP head,AST_EXP_LIST tail)
	{
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.head = head;
		this.tail = tail;
	}

	public void PrintMe()
	{
		System.out.print("AST NODE FUNC CALL ARG LIST\n");
		if (head != null) head.PrintMe();
		if (tail != null) tail.PrintMe();
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"EXP\nLIST\n");
		if (head != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,head.SerialNumber);
		if (tail != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,tail.SerialNumber);
	}
	public TYPE_LIST semantMeList(){
		if (tail == null)
		{
			return new TYPE_LIST(
					head.semantMe(),
					null);
		}
		else
		{
			return new TYPE_LIST(
					head.semantMe(),
					tail.semantMeList());
		}
	}
	public int getArgCount(){
		if (tail!= null)return 1+tail.getArgCount();
		return 1;
	}


	public TEMP_LIST IRmeList(){
		TEMP t = head.IRme();
		IR instance = IR.getInstance();
		Address storeAddr = new Address("");
		storeAddr.setAsSPAddr(0);
		instance.Add_IRcommand(new IRcommand_Offset_Stack(4));
		if(tail == null)
			return new TEMP_LIST(t, null);

		else
			return new TEMP_LIST(t, tail.IRmeList());
	}
	
}
