package AST;

import SYMBOL_TABLE.*;
import TYPES.*;

public class AST_NEW_EXP_SIMPLE extends AST_NEW_EXP
{
	public AST_TYPE type;

	public AST_NEW_EXP_SIMPLE(AST_TYPE type)
	{
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.type = type;
	}


	public void PrintMe()
	{
		System.out.format("AST NODE SIMPLE NEW EXP\n");
		AST_GRAPHVIZ.getInstance().logNode(SerialNumber,"NEW\nEXP");
		type.PrintMe();
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,type.SerialNumber);
	}


	public TYPE semantMe() {
		TYPE t;
		t = TYPE_TABLE.getInstance().find(type.type);
		if (t == null)
			throw new SemanticError("");
		if(!t.isClass())
			throw new SemanticError("");
		return t;
	}
}
