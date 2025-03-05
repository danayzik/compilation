package AST;
import TYPES.*;
import SYMBOL_TABLE.*;
import TEMP.*;
import IR.*;
public class AST_STMT_WHILE extends AST_STMT
{
	public AST_EXP cond;
	public AST_STMT_LIST body;

	public AST_STMT_WHILE(int line, AST_EXP cond,AST_STMT_LIST body)
	{
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.cond = cond;
		this.body = body;
		this.line = String.valueOf(line);
	}
	public void PrintMe()
	{
		System.out.print("AST NODE WHILE STMT\n");
		if (cond != null) cond.PrintMe();
		if (body != null) body.PrintMe();
		AST_GRAPHVIZ.getInstance().logNode(
				SerialNumber,
				"WHILE STATEMENT\n");
		if (cond != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,cond.SerialNumber);
		if (body != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,body.SerialNumber);
	}
	public TYPE semantMe()
	{
		if (cond.semantMe() != TYPE_INT.getInstance())
			throw new SemanticError(String.format("%s condition needs to be of type integer", line));

		SYMBOL_TABLE.getInstance().beginScope();
		SYMBOL_TABLE.getInstance().inheritVarCount();
		body.semantMe();
		SYMBOL_TABLE.getInstance().endScope();
		return null;
	}

	@Override
	public void matchReturnType(TYPE t) {
		body.matchReturnType(t);
	}

	public TEMP IRme(){
		IR IRInstance = IR.getInstance();
		String condLabel = IRcommand.getFreshLabel("");
		String falseLabel = IRcommand.getFreshLabel("");

		IRcommand condLabelJump = new IRcommand_Jump_Label(condLabel);
		IRcommand condLabelCmd = new IRcommand_Label(condLabel);
		IRcommand falseLabelCmd = new IRcommand_Label(falseLabel);
		condLabelJump.jumpToCmd = condLabelCmd;

		IRInstance.Add_IRcommand(condLabelCmd);
		TEMP condTemp = cond.IRme();
		IRcommand beqCmd = new IRcommand_Jump_If_Eq_To_Zero(condTemp, falseLabel);
		beqCmd.jumpToCmd = falseLabelCmd;
		IRInstance.Add_IRcommand(beqCmd);
		body.IRme();
		IRInstance.Add_IRcommand(condLabelJump);
		IRInstance.Add_IRcommand(falseLabelCmd);
		return null;
	}
}