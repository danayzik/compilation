package AST;
import TYPES.*;
import SYMBOL_TABLE.*;
import static AST.SemanticUtils.checkLegalAssignment;
public class AST_GLOBAL_VAR_DEC extends AST_DEC
{
    public boolean assigned;
    public AST_EXP assignedExp;
    public AST_TYPE type;
    public String ID;

    public AST_GLOBAL_VAR_DEC(AST_TYPE varType, String ID, AST_EXP assignedExp) {
        SerialNumber = AST_Node_Serial_Number.getFresh();
        this.ID = ID;
        this.type = varType;
        this.assignedExp = assignedExp;
        this.assigned = (assignedExp != null);
    }

    public AST_GLOBAL_VAR_DEC(AST_TYPE varType, String ID) {
        this(varType, ID, null);
    }

    public void PrintMe()
    {
        System.out.print("AST NODE GLOBAL VAR DEC\n");
        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                String.format("VAR\nDEC\nID: %s\nAssigned: %b", ID, assigned));
        AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,type.SerialNumber);
        type.PrintMe();
        if (assignedExp != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,assignedExp.SerialNumber);
    }
    public TYPE semantMe()
    {
        TYPE leftType;
        TYPE rightType;
        if (type.type.equals("void"))
            throw new SemanticError("");
        leftType = TYPE_TABLE.getInstance().find(type.type);
        if (leftType == null)
        {
            throw new SemanticError("");
        }
        if (SYMBOL_TABLE.getInstance().findInInnerScope(ID) != null)
        {
            throw new SemanticError("");
        }
        SYMBOL_TABLE.getInstance().enter(ID, leftType);
        if(assigned) {
            rightType = assignedExp.semantMe();
            checkLegalAssignment(leftType, rightType, "");
        }
        return null;
    }

}