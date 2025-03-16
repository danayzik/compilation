package AST;
import TYPES.*;
import static AST.SemanticUtils.isLegalAssignment;
import TEMP.*;
import IR.*;
public class AST_STMT_RETURN extends AST_STMT
{
	public AST_EXP returnExp;

	public AST_STMT_RETURN(int line, AST_EXP returnExp)
	{
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.returnExp = returnExp;
		this.line = String.valueOf(line);
	}
	public void PrintMe()
	{
		System.out.print("AST NODE RETURN STMT\n");
		if (returnExp != null) returnExp.PrintMe();
		AST_GRAPHVIZ.getInstance().logNode(
				SerialNumber,
				"(EXP)\n");
		if (returnExp != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,returnExp.SerialNumber);
	}
	public TYPE semantMe(){
		if (returnExp == null)	{
			semanticType = TYPE_VOID.getInstance();
			return semanticType;
		}
		semanticType = returnExp.semantMe();
		return semanticType;
	}
	public void matchReturnType(TYPE t){
		if (semanticType == TYPE_VOID.getInstance()){
			if (t != TYPE_VOID.getInstance())
				throw new SemanticError(String.format("%s Non matching return type", line));
			return;
		}
		if(!isLegalAssignment(t, semanticType))
			throw new SemanticError(String.format("%s Non matching return type", line));
	}

	public TEMP IRme(){
		TEMP t = null;
		if(returnExp!=null)t = returnExp.IRme();
		IR.getInstance().Add_IRcommand(new IRcommand_Return(t));
		IR.getInstance().Add_IRcommand(new IRcommand_Func_Epilogue());
		return t;
	}


}
