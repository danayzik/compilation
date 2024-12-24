package AST;
import SYMBOL_TABLE.*;
import TYPES.*;

public class AST_FUNC_ARG extends AST_Node
{
    AST_TYPE type;
    String ID;

    public AST_FUNC_ARG(int line, AST_TYPE type, String ID)
    {
        SerialNumber = AST_Node_Serial_Number.getFresh();
        this.type = type;
        this.ID = ID;
        this.line = String.valueOf(line);
    }
    public void PrintMe()
    {
        System.out.print("AST NODE FUNC_ARG\n");
        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,String.format("FUNC\nARG\nID:\n%s",ID));
        if (type != null) type.PrintMe();
        if (type != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,type.SerialNumber);
    }
    public TYPE semantMe(){
        if(type.type.equals("void"))
            throw new SemanticError(line);
        TYPE t = TYPE_TABLE.getInstance().find(type.type);
        if(t == null)
            throw new SemanticError(line);
        if(SYMBOL_TABLE.getInstance().findInInnerScope(ID) != null)
            throw new SemanticError(line);
        SYMBOL_TABLE.getInstance().enter(ID, t);
        return t;
    }
}