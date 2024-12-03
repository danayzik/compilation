package AST;
// not finished
public class AST_EXP_PAREN extends AST_EXP
{
    public AST_EXP childExp;

    public AST_EXP_PAREN(AST_EXP child)
    {
        SerialNumber = AST_Node_Serial_Number.getFresh();
        this.childExp = child;
    }
}