package AST;
//not finished
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
}