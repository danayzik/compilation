package AST;
import TYPES.*;
import IR.*;
import TEMP.*;


public class AST_VAR_SUBSCRIPT extends AST_VAR
{
	public AST_VAR var;
	public AST_EXP subscript;

	public AST_VAR_SUBSCRIPT(int line, AST_VAR var,AST_EXP subscript)
	{
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.var = var;
		this.subscript = subscript;
		this.line = String.valueOf(line);
	}

	public void PrintMe()
	{
		System.out.print("AST NODE SUBSCRIPT VAR\n");
		if (var != null) var.PrintMe();
		if (subscript != null) subscript.PrintMe();
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"SUBSCRIPT\nVAR\n...[...]");
		if (var != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,var.SerialNumber);
		if (subscript != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,subscript.SerialNumber);
	}

	public TYPE semantMe(){
		TYPE varType = var.semantMe();
		TYPE subscriptType = subscript.semantMe();
		if (!varType.isArray())
			throw new SemanticError(String.format("%s non array type variable can not be indexed", line));
		if (subscriptType != TYPE_INT.getInstance())
			throw new SemanticError(String.format("%s index expression must be type int", line));
		semanticType = ((TYPE_ARRAY)varType).arrayType;
		return semanticType;
	}

	public TEMP IRme(){
		IR instance = IR.getInstance();
		Address arrayAddr = var.getStoreAddr();
		TEMP index = subscript.IRme();
		TEMP arrayAddrTemp = TEMP_FACTORY.getInstance().getFreshTEMP();
		instance.Add_IRcommand(new IRcommand_Load(arrayAddrTemp, arrayAddr));
		TEMP arraySize = TEMP_FACTORY.getInstance().getFreshTEMP();
		instance.Add_IRcommand(new IRcommand_Array_Access(arraySize,
				arrayAddrTemp, index));
		TEMP indexOffset = TEMP_FACTORY.getInstance().getFreshTEMP();
		instance.Add_IRcommand(new IRcommand_SLL(indexOffset, index));
		TEMP finalAddr = TEMP_FACTORY.getInstance().getFreshTEMP();
		TEMP dst = TEMP_FACTORY.getInstance().getFreshTEMP();
		Address addr = new Address(arrayAddr.varName, 4, finalAddr);
		instance.Add_IRcommand(new IRcommand_Load(dst, addr));
		return dst;
	}

	@Override
	public Address getStoreAddr(){
		IR instance = IR.getInstance();
		Address arrayAddr = var.getStoreAddr();
		TEMP index = subscript.IRme();
		TEMP arrayAddressTemp = TEMP_FACTORY.getInstance().getFreshTEMP();
		instance.Add_IRcommand(new IRcommand_Load(arrayAddressTemp, arrayAddr));
		TEMP arraySize = TEMP_FACTORY.getInstance().getFreshTEMP();
		instance.Add_IRcommand(new IRcommand_Array_Access(arraySize,
				arrayAddressTemp, index));
		TEMP indexOffset = TEMP_FACTORY.getInstance().getFreshTEMP();
		instance.Add_IRcommand(new IRcommand_SLL(indexOffset, index));
		TEMP finalAddr = TEMP_FACTORY.getInstance().getFreshTEMP();
		instance.Add_IRcommand(new IRcommand_Binop(finalAddr, indexOffset, arrayAddressTemp,0));
        return new Address(arrayAddr.varName, 4, finalAddr);
	}
}
