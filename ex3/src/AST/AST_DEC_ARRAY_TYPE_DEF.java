package AST;

import SYMBOL_TABLE.*;
import TYPES.*;



public class AST_DEC_ARRAY_TYPE_DEF extends AST_DEC
{
    public String ID;
    public AST_TYPE type;
    public AST_DEC_ARRAY_TYPE_DEF(int line, String ID, AST_TYPE type)
    {
        SerialNumber = AST_Node_Serial_Number.getFresh();
        this.ID = ID;
        this.type = type;
        this.line = String.valueOf(line);
    }
    public void PrintMe()
    {
        System.out.print("AST NODE ARRAY TYPE DEF\n");
        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                String.format("ARRAY\nDEC\nID: %s", ID));
        AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,type.SerialNumber);
    }

    public TYPE semantMe()
    {
        TYPE arrayType;
        if (type.type.equals("void"))
            throw new SemanticError(line);
        if (SYMBOL_TABLE.getInstance().findInInnerScope(ID) != null)
        {
            throw new SemanticError(line);
        }
        arrayType = TYPE_TABLE.getInstance().find(type.type);
        if (arrayType == null)
        {
            throw new SemanticError(line);
        }

        TYPE_TABLE.getInstance().enter(ID, new TYPE_ARRAY(arrayType, ID));
        return null;
    }
}