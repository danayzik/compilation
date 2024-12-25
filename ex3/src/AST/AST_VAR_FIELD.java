package AST;
import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_VAR_FIELD extends AST_VAR
{
	public AST_VAR var;
	public String fieldName;

	public AST_VAR_FIELD(int line, AST_VAR var,String fieldName)
	{
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.var = var;
		this.fieldName = fieldName;
		this.line = String.valueOf(line);
	}

	public void PrintMe()
	{
		System.out.print("AST NODE FIELD VAR\n");
		if (var != null) var.PrintMe();
		System.out.format("FIELD NAME( %s )\n",fieldName);

		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("FIELD\nVAR\n...->%s",fieldName));

		if (var != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,var.SerialNumber);
	}
	public TYPE semantMe(){
		TYPE owner = var.semantMe();
		TYPE_CLASS_MEMBER field;
		if(owner == null)
			throw new SemanticError(String.format("%s can not find owner", line));
		if (!owner.isClass())
			throw new SemanticError(String.format("%s owner is not of type class", line));
		field = ((TYPE_CLASS)owner).findMember(fieldName);
		if (field == null)
			throw new SemanticError(String.format("%s can not find member %s", line, fieldName));;
		TYPE t = field.t;
		if (t == null)
			throw new SemanticError(String.format("%s undefined type for member %s", line, fieldName));
		semanticType = t;
		return t;
	}
}
