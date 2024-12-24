package AST;

import SYMBOL_TABLE.TYPE_TABLE;
import TYPES.TYPE;


public class AST_TYPE extends AST_Node
{
    String type;
    public boolean isFuncType;
    public AST_TYPE(int line, String type)
    {
        SerialNumber = AST_Node_Serial_Number.getFresh();
        this.type = type;
        isFuncType = false;
        this.line = String.valueOf(line);
    }
    public void PrintMe()
    {
        System.out.print("AST NODE TYPE\n");
        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber, String.format("Type:%s",type));
    }


    public TYPE semantMe() {
        if (!isFuncType){
            if (type.equals("void"))
                throw new SemanticError(line);
        }
        TYPE t = TYPE_TABLE.getInstance().find(type);
        if (t == null)
            throw new SemanticError(line);
        return t;
    }
}