package AST;
//not finished
public class AST_GLOBAL_VAR_DEC extends AST_DEC
{
    public boolean assigned;
    public AST_EXP assignedExp;
    public AST_TYPE type;
    public String ID;

    public AST_GLOBAL_VAR_DEC(AST_TYPE varType, String ID, AST_EXP assignedExp) {
        this.ID = ID;
        this.type = varType;
        this.assignedExp = assignedExp;
        this.assigned = (assignedExp != null);
    }

    public AST_GLOBAL_VAR_DEC(AST_TYPE varType, String ID) {
        this(varType, ID, null);
    }


}