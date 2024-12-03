package AST;
//not finished
public class AST_EXP_NIL extends AST_EXP
{
	public String str;
	public AST_EXP_INT()
	{
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.str = NIL;
	}

	/************************************************/
	/* The printing message for an INT EXP AST node */
	/************************************************/
	public void PrintMe()
	{
		/*******************************/
		/* AST NODE TYPE = AST INT EXP */
		/*******************************/
		System.out.format("AST NODE INT( %d )\n",value);

		/*********************************/
		/* Print to AST GRAPHIZ DOT file */
		/*********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("INT(%d)",value));
	}
}
