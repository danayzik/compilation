package AST;
//not finished
public class AST_DEC_ARRAY_TYPE_DEF extends AST_DEC
{
    public String ID;
    public AST_TYPE type;
    public AST_DEC_ARRAY_TYPE_DEF(String ID, AST_TYPE type)
    {
        this.ID = ID;
        this.type = type;
    }
}