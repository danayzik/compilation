package AST;
import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_CLASS_DEC extends AST_DEC
{
    public String ID;
    public String parentID;
    public boolean inherits;
    public AST_CFIELD_LIST cfieldList;

    public AST_CLASS_DEC(String ID, AST_CFIELD_LIST cfieldList, String parentID)
    {
        SerialNumber = AST_Node_Serial_Number.getFresh();
        this.ID = ID;
        this.cfieldList = cfieldList;
        this.inherits = (parentID != null);
        this.parentID = parentID;
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
        if(inherits){
            father = (TYPE_CLASS) SYMBOL_TABLE.getInstance().find(parentID);
        }
        SYMBOL_TABLE.getInstance().beginScope();
        TYPE_CLASS t = new TYPE_CLASS(father,ID,cfieldList.semantMeList());
        SYMBOL_TABLE.getInstance().endScope();
        SYMBOL_TABLE.getInstance().enter(ID,t);
        return null;
    }
}