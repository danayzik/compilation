package AST;
//not finished
public class AST_DEC_ARRAY_TYPE_DEF extends AST_DEC
{
    public String ID;
    public AST_TYPE type;
    public AST_DEC_ARRAY_TYPE_DEF(String ID, AST_TYPE type)
    {
        SerialNumber = AST_Node_Serial_Number.getFresh();
        this.ID = ID;
        this.type = type;
    }
    public void PrintMe()
    {
        System.out.print("AST NODE ARRAY TYPE DEF\n");
        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                String.format("ARRAY\nDEC\nID: %s", ID));
        AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,type.SerialNumber);
    }
}