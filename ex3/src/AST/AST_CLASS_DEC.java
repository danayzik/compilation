package AST;
import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_CLASS_DEC extends AST_DEC
{
    public String ID;
    public String parentID;
    public boolean inherits;
    public AST_CFIELD_LIST cfieldList;

    public AST_CLASS_DEC(int line, String ID, AST_CFIELD_LIST cfieldList, String parentID)
    {
        SerialNumber = AST_Node_Serial_Number.getFresh();
        this.ID = ID;
        this.cfieldList = cfieldList;
        this.parentID = parentID;
        this.inherits = (parentID != null);
        this.line = String.valueOf(line);
    }

    public void PrintMe()
    {
        System.out.print("AST NODE CLASS DEC\n");
        if(cfieldList != null) cfieldList.PrintMe();
        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                String.format("CLASS\nDEC\nID: %s\nParentID: %s\nInherits: %b", ID, parentID, inherits));
        if (cfieldList != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,cfieldList.SerialNumber);
    }
    public TYPE semantMe()
    {
        TYPE_CLASS father = null;
        SYMBOL_TABLE inst = SYMBOL_TABLE.getInstance();
        if(inst.findInInnerScope(ID) != null)
            throw new SemanticError(line);
        if(inherits){
            father = (TYPE_CLASS) TYPE_TABLE.getInstance().find(parentID);
            if(father == null)
                throw new SemanticError(String.format("%s couldn't find %s", line, parentID));
            if (!father.isClass())
                throw new SemanticError(String.format("%s %s Can not be extended, it is not a class", line, parentID));
        }
        TYPE_CLASS t = new TYPE_CLASS(father, ID);
        TYPE_TABLE.getInstance().enter(ID, t);
        SYMBOL_TABLE.getInstance().beginScope();
        SYMBOL_TABLE.getInstance().setAsClassScope();
        t.setDataMembers(cfieldList.semantMeList());
        SYMBOL_TABLE.getInstance().endScope();
        return null;
    }
}