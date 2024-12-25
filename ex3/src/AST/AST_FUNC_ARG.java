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
        TYPE t = type.semantMe();
        if (type.type.equals("void"))
            throw new SemanticError(String.format("%s argument type can't be void", line));
        if(SYMBOL_TABLE.getInstance().findInInnerScope(ID) != null)
            throw new SemanticError(String.format("%s Type %s not defined", line, type.type));
        SYMBOL_TABLE.getInstance().enter(ID, t);
        semanticType = t;
        return t;
    }
}