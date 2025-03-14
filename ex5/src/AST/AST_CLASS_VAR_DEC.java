package AST;
import TEMP.TEMP;
import TYPES.*;
import SYMBOL_TABLE.*;
import static AST.SemanticUtils.isLegalAssignment;

public class AST_CLASS_VAR_DEC extends AST_CFIELD
{
    public boolean assigned;
    public AST_EXP assignedExp;
    public AST_TYPE type;
    public String ID;
    public int intValue;
    public String stringValue;
    public TYPE_CLASS ownerClass;

    public AST_CLASS_VAR_DEC(int line, AST_TYPE varType, String ID, AST_EXP assignedExp) {
        SerialNumber = AST_Node_Serial_Number.getFresh();
        this.ID = ID;
        this.type = varType;
        this.assignedExp = assignedExp;
        this.assigned = (assignedExp != null);
        this.line = String.valueOf(line);
    }

    public AST_CLASS_VAR_DEC(int line, AST_TYPE varType, String ID) {
        this(line, varType, ID, null);
    }

    public void PrintMe()
    {
        System.out.print("AST NODE CLASS FIELD DEC\n");
        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                String.format("CLASS\nDEC\nID: %s\nAssigned: %b", ID, assigned));
        AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,type.SerialNumber);
        type.PrintMe();
        if (assignedExp != null) assignedExp.PrintMe();
        if (assignedExp != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,assignedExp.SerialNumber);
    }
    private void transferInitialValue(TYPE_CLASS_FIELD tField){
        if(assigned){
            if(assignedExp instanceof AST_EXP_INT){
                int val = ((AST_EXP_INT) assignedExp).value;
                intValue = val;
                tField.setInitialInt(val);
            }
            if(assignedExp instanceof  AST_EXP_NIL){
                int val = 0;
                intValue = val;
                tField.setInitialInt(val);
            }
            if(assignedExp instanceof AST_EXP_STRING){
                String val = ((AST_EXP_STRING) assignedExp).strLabel;
                stringValue = val;
                tField.setInitialStringValue(val);
            }
        }

    }

    public TYPE semantMe()
    {
        TYPE leftType;
        TYPE rightType;
        TYPE_CLASS_FIELD field;
        leftType = type.semantMe();
        if (SYMBOL_TABLE.getInstance().findInInnerScope(ID) != null)
            throw new SemanticError(String.format("%s %s already exists in this scope", line, ID));
        TYPE_CLASS owner = TYPE_TABLE.getInstance().getCurrentClassType();
        ownerClass = owner;
        boolean shadowingError = owner.isShadowingError(ID);
        if (shadowingError)
            throw new SemanticError(String.format("%s shadowing error", line));
        SYMBOL_TABLE.getInstance().enter(ID, leftType);
        field = new TYPE_CLASS_FIELD(leftType, ID);
        transferInitialValue(field);
        owner.addDataMember(field);
        if(assigned) {
            if (!(assignedExp instanceof AST_EXP_INT || assignedExp instanceof AST_EXP_STRING || assignedExp instanceof AST_EXP_NIL))
                throw new SemanticError(String.format("%s Can only accept basic assignments for a field" , line));
            rightType = assignedExp.semantMe();
            if (!isLegalAssignment(leftType, rightType))
                throw new SemanticError(String.format("%s illegal assignment", line));
        }
        semanticType = field;
        return semanticType;
    }


}