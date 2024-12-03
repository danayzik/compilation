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
        this.ID = ID;
        this.cfieldList = cfieldList;
        this.inherits = (parentID != null);
        this.parentID = parentID;
    }
}