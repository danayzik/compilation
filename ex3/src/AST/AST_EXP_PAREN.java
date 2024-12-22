package AST;
import TYPES.*;
public class AST_EXP_PAREN extends AST_EXP
{
    public AST_EXP childExp;
    public AST_EXP_PAREN(AST_EXP child)
    {
        SerialNumber = AST_Node_Serial_Number.getFresh();
        this.childExp = child;
    }
    public void PrintMe()
    {
        System.out.print("AST NODE EXP IN PARENTHESIS\n");
        if (childExp != null) childExp.PrintMe();
        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                "(EXP)\n");
        if (childExp != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,childExp.SerialNumber);
    }
    public TYPE semantMe()
    {
        return childExp.semantMe();
    }
}