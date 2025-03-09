package AST;
import IR.*;
import TEMP.TEMP;
import TYPES.*;
import SYMBOL_TABLE.*;
public class AST_CLASS_FUNC_DEC extends AST_CFIELD
{
    public AST_TYPE type;
    public String ID;
    public AST_STMT_LIST body;
    public AST_FUNC_ARG_LIST argList;
    public TYPE_CLASS ownerClass;

    public AST_CLASS_FUNC_DEC(int line, AST_TYPE type,String id,AST_STMT_LIST stLst, AST_FUNC_ARG_LIST argList)
    {
        SerialNumber = AST_Node_Serial_Number.getFresh();
        this.type = type;
        this.ID = id;
        this.body = stLst;
        this.argList = argList;
        this.line = String.valueOf(line);
        type.isFuncType = true;
    }
    public void PrintMe()
    {
        System.out.print("AST NODE CLASS METHOD DEC\n");
        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                String.format("METHOD\nDEC\nID: %s", ID));
        if (argList != null) argList.PrintMe();
        AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,type.SerialNumber);
        type.PrintMe();
        if (argList != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,argList.SerialNumber);
        if (body != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,body.SerialNumber);
    }

    public TYPE semantMe(){

        TYPE t = type.semantMe();
        if(SYMBOL_TABLE.getInstance().findInInnerScope(ID) != null)
            throw new SemanticError(String.format("%s %s already exists in this scope", line, ID));
        TYPE_CLASS owner = TYPE_TABLE.getInstance().getCurrentClassType();
        ownerClass = owner;
        TYPE_CLASS_METHOD func = new TYPE_CLASS_METHOD(t, ID);
        SYMBOL_TABLE.getInstance().enter(ID, func);
        SYMBOL_TABLE.getInstance().beginScope();
        if (argList != null)
            func.setParams(argList.semantMeList(0));
        body.semantMe();
        body.matchReturnType(t);
        boolean overrideError = owner.isOverrideError(t, ID, func.args);
        if (overrideError)
            throw new SemanticError(String.format("%s override error", line));
        owner.addDataMember(func);
        SYMBOL_TABLE.getInstance().endScope();
        semanticType = func;
        return func;
    }

    public TEMP IRme()
    {
        IR IRInstance = IR.getInstance();
        IRInstance.activateFunctionSection();
        IRInstance.declareNewFunc();
        IR.getInstance().Add_IRcommand(new IRcommand_Label(String.format("%s_%s", ownerClass.name, ID)));
        if (body != null) body.IRme();
        return null;
    }
}