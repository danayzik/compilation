package AST;
// not finished
public class AST_TYPE extends AST_Node
{
    String type;
    public AST_TYPE(String type)
    {
        SerialNumber = AST_Node_Serial_Number.getFresh();
        this.type = type;
    }
    public void PrintMe()
    {
        System.out.print("AST NODE TYPE\n");
        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber, type);
    }
}