package AST;
// not finished
public class AST_STMT_FUNC_CALL extends AST_STMT
{
	public AST_EXP_LIST argList;
	public boolean classMethodCall;
	public AST_VAR ownerVar;
	public String funcID;

	public AST_STMT_FUNC_CALL(AST_VAR ownerVar, String funcID, AST_EXP_LIST argList)
	{
		this.ownerVar = ownerVar;
		this.funcID = funcID;
		this.argList = argList;
		this.classMethodCall = (ownerVar != null);
		SerialNumber = AST_Node_Serial_Number.getFresh();

	}
	public AST_STMT_FUNC_CALL(String funcID, AST_EXP_LIST argList)
	{
		this(null, funcID, argList);
	}


	public void PrintMe()
	{
		System.out.format("AST NODE FUNC CALL");
		if(ownerVar != null) ownerVar.PrintMe();
		if(argList != null) argList.PrintMe();
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("FUNC NAME: %s\nIs class method: %b",funcID, classMethodCall));
		if (ownerVar != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,ownerVar.SerialNumber);
		if (argList != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,argList.SerialNumber);
	}
}
