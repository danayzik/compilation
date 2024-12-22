package AST;
import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_CLASS_VAR_DEC extends AST_CFIELD
{
    public boolean assigned;
    public AST_EXP assignedExp;
    public AST_TYPE type;
    public String ID;

    public AST_CLASS_VAR_DEC(AST_TYPE varType, String ID, AST_EXP assignedExp) {
        SerialNumber = AST_Node_Serial_Number.getFresh();
        this.ID = ID;
        this.type = varType;
        this.assignedExp = assignedExp;
        this.assigned = (assignedExp != null);
    }

    public AST_CLASS_VAR_DEC(AST_TYPE varType, String ID) {
        this(varType, ID, null);
    }

    public void PrintMe()
    {
        System.out.print("AST NODE CLASS FIELD DEC\n");
        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                String.format("CLASS\nDEC\nID: %s\nAssigned: %b", ID, assigned));
        AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,type.SerialNumber);
        type.PrintMe();
        if (assignedExp != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,assignedExp.SerialNumber);
    }

    public TYPE semantMe()
    {
        TYPE leftType;
        TYPE rightType = null;
        if (type.type.equals("void"))
            throw new SemanticError("");
        leftType = SYMBOL_TABLE.getInstance().findInInnerScope(type.type);

        if (leftType == null)
        {
            throw new SemanticError("");
        }
        if (SYMBOL_TABLE.getInstance().findInInnerScope(ID) != null)
        {
            throw new SemanticError("");
        }
        if(assigned) {
            rightType = assignedExp.semantMe();
            if (rightType.isNil() && (leftType.isArray() || leftType.isClass())
            return null;
            if(leftType != rightType){
                throw new SemanticError("");
            }
        }
        SYMBOL_TABLE.getInstance().enter(ID,leftType);
        return null;
    }

}