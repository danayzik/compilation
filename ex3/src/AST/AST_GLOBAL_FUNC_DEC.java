package AST;
import SYMBOL_TABLE.*;
import TYPES.*;

public class AST_GLOBAL_FUNC_DEC extends AST_DEC {
    public AST_TYPE type;
    public String ID;
    public AST_STMT_LIST body;
    public AST_FUNC_ARG_LIST argList;

    public AST_GLOBAL_FUNC_DEC(AST_TYPE type,String id,AST_STMT_LIST stLst, AST_FUNC_ARG_LIST argList)
    {
        SerialNumber = AST_Node_Serial_Number.getFresh();
        this.type = type;
        this.ID = id;
        this.body = stLst;
        this.argList = argList;
    }
    public void PrintMe()
    {
        System.out.print("AST NODE GLOBAL FUNC DEC\n");
        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                String.format("FUNC\nDEC\nID: %s", ID));
        if (argList != null) argList.PrintMe();
        if (body != null) body.PrintMe();
        AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,type.SerialNumber);
        type.PrintMe();
        if (argList != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,argList.SerialNumber);
        if (body != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,body.SerialNumber);
    }

    public TYPE semantMe(){
        TYPE t = TYPE_TABLE.getInstance().find(type.type);
        if(t == null)
            throw new SemanticError("");
        if(SYMBOL_TABLE.getInstance().findInInnerScope(ID) != null)
            throw new SemanticError("");
        TYPE_FUNCTION func = new TYPE_FUNCTION(t, ID);
        SYMBOL_TABLE.getInstance().enter(ID, func);
        SYMBOL_TABLE.getInstance().beginScope();
        func.setParams(argList.semantMeList());
        body.semantMe();
        body.matchReturnType(t);
        SYMBOL_TABLE.getInstance().endScope();
        return null;
    }

}