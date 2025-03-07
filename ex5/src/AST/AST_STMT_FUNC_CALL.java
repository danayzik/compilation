package AST;
import TYPES.*;
import SYMBOL_TABLE.*;
import TEMP.*;
import IR.*;

public class AST_STMT_FUNC_CALL extends AST_STMT
{
	public AST_EXP_LIST argList;
	public boolean classMethodCall;
	public AST_VAR ownerVar;
	public String funcID;

	public AST_STMT_FUNC_CALL(int line, AST_VAR ownerVar, String funcID, AST_EXP_LIST argList)
	{
		this.ownerVar = ownerVar;
		this.funcID = funcID;
		this.argList = argList;
		this.classMethodCall = (ownerVar != null);
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.line = String.valueOf(line);
	}

	public AST_STMT_FUNC_CALL(int line, String funcID, AST_EXP_LIST argList)
	{
		this(line, null, funcID, argList);
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
	public TYPE semantMe(){
		TYPE funcType;
		TYPE owner;
		TYPE finalType;
		TYPE_LIST argTypes = null;
		TYPE_CLASS_MEMBER method;
		if (argList != null)
			argTypes = argList.semantMeList();
		if (!classMethodCall){
			funcType = SYMBOL_TABLE.getInstance().findInAllScopes(funcID);
			if (funcType == null)
				throw new SemanticError(String.format("%s can not find %s", line, funcID));
			if (funcType instanceof TYPE_FUNCTION) {
				if (!((TYPE_FUNCTION)funcType).canAssignToArgs(argTypes))
					throw new SemanticError(String.format("%s Invalid function arguments", line));
				finalType = ((TYPE_FUNCTION)funcType).returnType;
			} else if (funcType instanceof TYPE_CLASS_METHOD) {
				if (!((TYPE_CLASS_METHOD)funcType).canAssignToArgs(argTypes))
					throw new SemanticError(String.format("%s Invalid function arguments", line));
				finalType = ((TYPE_CLASS_METHOD)funcType).t;
			}
			else {
				throw new SemanticError(String.format("%s %s is not callable", line, funcID));
			}
		}
		else {
			owner = ownerVar.semantMe();
			if (owner == null)
				throw new SemanticError(String.format("%s can not find owner", line));
			if (!owner.isClass())
				throw new SemanticError(String.format("%s owner is not of type class", line));
			method = ((TYPE_CLASS)owner).findMember(funcID);
			if (method == null)
				throw new SemanticError(String.format("%s can not find %s in class", line, funcID));
			if (!method.isMethod())
				throw new SemanticError(String.format("%s %s is not callable", line, funcID));
			if (!((TYPE_CLASS_METHOD)method).canAssignToArgs(argTypes))
				throw new SemanticError(String.format("%s Invalid function arguments", line));
			finalType = method.t;
		}
		semanticType = finalType;
		return finalType;
	}


	public TEMP IRme(){
		TEMP_LIST tempList = argList.IRmeList();
		if(!classMethodCall) {
			if(funcID.equals("PrintString")) {
				IR.getInstance().Add_IRcommand(new IRcommand_PrintStringCall(funcID, tempList));
			}
			else if (funcID.equals("PrintInt")) {
				IR.getInstance().Add_IRcommand(new IRcommand_PrintIntCall(funcID, tempList));
			}
			else {
				IR.getInstance().Add_IRcommand(new IRcommand_FuncCall(funcID, tempList));
			}
		}
		else{
			IR.getInstance().Add_IRcommand(new IRcommand_MethodCall(funcID, tempList, ownerVar.IRme()));
		}
		return null;
	}
}
