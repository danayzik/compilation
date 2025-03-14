package AST;

import SYMBOL_TABLE.*;
import TYPES.*;
import TEMP.*;
import IR.*;
public class AST_NEW_EXP_ARRAY extends AST_NEW_EXP
{
	public AST_TYPE type;
	public AST_EXP sizeExp;
	public AST_NEW_EXP_ARRAY(int line, AST_TYPE type, AST_EXP sizeExp)
	{
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.type = type;
		this.sizeExp = sizeExp;
		this.line = String.valueOf(line);
	}


	public void PrintMe()
	{
		System.out.format("AST NODE NEW ARRAY EXP\n");
		AST_GRAPHVIZ.getInstance().logNode(SerialNumber,"NEW\nARRAY");
		type.PrintMe();
		sizeExp.PrintMe();
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,type.SerialNumber);
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,sizeExp.SerialNumber);
	}

	public TYPE semantMe() {
		TYPE arrayType;
		arrayType = type.semantMe();
		TYPE_NEW_ARRAY t = new TYPE_NEW_ARRAY(arrayType);
		TYPE sizeExpType = sizeExp.semantMe();
		if(sizeExpType != TYPE_INT.getInstance())
			throw new SemanticError(String.format("%s size expression is not an integer", line));
		if (sizeExp instanceof AST_EXP_INT){
			if(((AST_EXP_INT) sizeExp).value <= 0){
				throw new SemanticError(String.format("%s constant size expression can't be less than or equal to zero", line));
			}
		}
		semanticType = t;
		return t;
	}

	public TEMP IRme(){
		TEMP size = sizeExp.IRme();
		TEMP dst = TEMP_FACTORY.getInstance().getFreshTEMP();
		IR.getInstance().Add_IRcommand(new IRcommand_New_Array(size, semanticType, dst));
		return dst;
	}
}
