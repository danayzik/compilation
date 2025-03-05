package AST;

import SYMBOL_TABLE.*;
import TYPES.*;
import TEMP.*;
import IR.*;



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
        if (SYMBOL_TABLE.getInstance().findInInnerScope(ID) != null)
        {
            throw new SemanticError(String.format("%s %s already exists in this scope", line, ID));
        }
        arrayType = type.semantMe();
        TYPE_TABLE.getInstance().enter(ID, new TYPE_ARRAY(arrayType, ID));
        return null;
    }


}