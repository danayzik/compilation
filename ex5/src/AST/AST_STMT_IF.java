package AST;
import TYPES.*;
import SYMBOL_TABLE.*;
import TEMP.*;
import IR.*;
public class AST_STMT_IF extends AST_STMT
{
	public AST_EXP cond;
	public AST_STMT_LIST body;

	public AST_STMT_IF(int line, AST_EXP cond,AST_STMT_LIST body)
	{
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.cond = cond;
		this.body = body;
		this.line = String.valueOf(line);
	}
	public void PrintMe()
	{
		System.out.print("AST NODE IF STMT\n");
		if (cond != null) cond.PrintMe();
		if (body != null) body.PrintMe();
		AST_GRAPHVIZ.getInstance().logNode(
				SerialNumber,
				"IF STATEMENT\n");
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
		String beqLabel = IRcommand.getFreshLabel("");
		IR IRInstance = IR.getInstance();
		IRcommand_Label labelCmd= new IRcommand_Label(beqLabel);
		TEMP condTemp = cond.IRme();
		IRcommand_Jump_If_Eq_To_Zero beqCmd = new IRcommand_Jump_If_Eq_To_Zero(condTemp, beqLabel);
		beqCmd.jumpToCmd = labelCmd;
		labelCmd.jumpPrev = beqCmd;
		IRInstance.Add_IRcommand(beqCmd);
		body.IRme();
		IRInstance.Add_IRcommand(labelCmd);
		return null;
	}

	@Override
	public void initDeclarations() {
		if(body!=null)body.initDeclarations();
	}
}