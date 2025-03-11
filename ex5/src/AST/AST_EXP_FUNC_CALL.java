package AST;
import TYPES.*;
import SYMBOL_TABLE.*;
import IR.*;
import TEMP.*;
public class AST_EXP_FUNC_CALL extends AST_EXP
{
	public AST_EXP_LIST argList;
	public boolean classMethodCall;
	public AST_VAR ownerVar;
	public String funcID;
	public boolean isImplicitMethodCall;
	public int argCount = 0;

	public AST_EXP_FUNC_CALL(int line, AST_VAR ownerVar, String funcID, AST_EXP_LIST argList)
	{
		this.ownerVar = ownerVar;
		this.funcID = funcID;
		this.argList = argList;
		this.classMethodCall = (ownerVar != null);
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.line = String.valueOf(line);
	}

	public AST_EXP_FUNC_CALL(int line, String funcID, AST_EXP_LIST argList)
	{
		this(line, null, funcID, argList);
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
			isImplicitMethodCall = SYMBOL_TABLE.getInstance().lastSearchedIsMethod;
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
	public TEMP_LIST setupArgsOnStack(){
		IR instance = IR.getInstance();
		if (argList!=null)argCount = argList.getArgCount();
		instance.Add_IRcommand(new IRcommand_Offset_Stack(-argCount*4));
		TEMP_LIST tempList = argList.IRmeList();
		instance.Add_IRcommand(new IRcommand_Offset_Stack(argCount*4));
		return tempList;
	}
	public TEMP IRme(){
		IR instance = IR.getInstance();
		TEMP_LIST tempList = setupArgsOnStack();
		TEMP dst = TEMP_FACTORY.getInstance().getFreshTEMP();
		IRcommand callCommand;
		if(!classMethodCall) {
			if (isImplicitMethodCall) {
				TEMP vtableAddrTemp = TEMP_FACTORY.getInstance().getFreshTEMP();
				Address vtableAddr = new Address(funcID);
				vtableAddr.setAsImplicitField(0);
				instance.Add_IRcommand(new IRcommand_Load(vtableAddrTemp, vtableAddr));
				callCommand = new IRcommand_FunctionCall(funcID, tempList, vtableAddrTemp, instance.activeClass.getMethodOffset(funcID), dst);
			}
			else {
				callCommand = new IRcommand_FunctionCall(funcID, tempList, dst);
			}
		}
		else{
			TYPE_CLASS ownerClass = (TYPE_CLASS) ownerVar.semanticType;
			int offset = ownerClass.getMethodOffset(funcID);
			TEMP ownerObj = ownerVar.IRme();
			TEMP vtableAddrTemp = TEMP_FACTORY.getInstance().getFreshTEMP();
			Address vtableAddr = new Address(funcID);
			vtableAddr.setCustomReg(0, ownerObj);
			instance.Add_IRcommand(new IRcommand_Load(vtableAddrTemp, vtableAddr));
			callCommand = new IRcommand_FunctionCall(funcID, tempList, dst, ownerObj, vtableAddrTemp, offset);
		}
		instance.Add_IRcommand(callCommand);
		return dst;
	}

}
