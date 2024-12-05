package AST;
//not finished
public class AST_LOCAL_VAR_DEC extends AST_STMT
{
    public boolean assigned;
    public AST_EXP assignedExp;
    public AST_TYPE type;
    public String ID;

    public AST_LOCAL_VAR_DEC(AST_TYPE varType, String ID, AST_EXP assignedExp) {
        this.ID = ID;
        this.type = varType;
        this.assignedExp = assignedExp;
        this.assigned = (assignedExp != null);
        SerialNumber = AST_Node_Serial_Number.getFresh();
    }

    public AST_LOCAL_VAR_DEC(AST_TYPE varType, String ID) {
        this(varType, ID, null);
    }

    public void PrintMe()
    {
        System.out.print("AST NODE LOCAL VAR DEC\n");
        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                String.format("LOCAL VAR\nDEC\nID: %s\nAssigned: %b", ID, assigned));
        AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,type.SerialNumber);
        type.PrintMe();
        if (assignedExp != null) assignedExp.PrintMe();
        if (assignedExp != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,assignedExp.SerialNumber);
    }
}