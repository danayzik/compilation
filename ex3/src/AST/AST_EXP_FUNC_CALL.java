package AST;
import TYPES.*;
import SYMBOL_TABLE.*;
public class AST_EXP_FUNC_CALL extends AST_EXP
{
	public AST_EXP_LIST argList;
	public boolean classMethodCall;
	public AST_VAR ownerVar;
	public String funcID;

	public AST_EXP_FUNC_CALL(AST_VAR ownerVar, String funcID, AST_EXP_LIST argList)
	{
		this.ownerVar = ownerVar;
		this.funcID = funcID;
		this.argList = argList;
		this.classMethodCall = (ownerVar != null);
		SerialNumber = AST_Node_Serial_Number.getFresh();
	}

	public AST_EXP_FUNC_CALL(String funcID, AST_EXP_LIST argList)
	{
		this(null, funcID, argList);
	}

	public void PrintMe()
	{
		System.out.format("AST NODE EXP FUNC CALL\n");
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("ID(%s)\nisMethod: %b",funcID,classMethodCall));
		if (ownerVar != null) ownerVar.PrintMe();
		if (argList != null) argList.PrintMe();
		if (ownerVar != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,ownerVar.SerialNumber);
		if (argList != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,argList.SerialNumber);
	}
	public TYPE semantMe(){
		TYPE_FUNCTION funcType;
		TYPE owner;
		TYPE finalType;
		TYPE_LIST argTypes;
		TYPE_LIST paramTypes;
		TYPE_CLASS_MEMBER method;
		argTypes = argList.semantMeList();
		if (!classMethodCall){
			funcType = (TYPE_FUNCTION) SYMBOL_TABLE.getInstance().findInAllScopes(funcID);
			if (funcType == null)
				throw new SemanticError("");
			paramTypes = funcType.params;
			if(!paramTypes.canAssignList(argTypes))
				throw new SemanticError("");
			finalType = funcType.returnType;
		}
		else {
			owner = ownerVar.semantMe();
			if (owner == null)
				throw new SemanticError("");
			if (!owner.isClass())
				throw new SemanticError("");
			method = ((TYPE_CLASS)owner).findMember(funcID);
			if (method == null)
				throw new SemanticError("");
			if (!method.isMethod())
				throw new SemanticError("");
			paramTypes = ((TYPE_CLASS_METHOD)method).args;
			if (!paramTypes.canAssignList(argTypes))
				throw new SemanticError("");
			finalType = method.t;
		}
		return finalType;
	}
}
