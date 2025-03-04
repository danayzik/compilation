package AST;
import TYPES.*;
import SYMBOL_TABLE.*;
import static AST.SemanticUtils.isLegalAssignment;
import TEMP.*;
import IR.*;
public class AST_GLOBAL_VAR_DEC extends AST_DEC
{
    public boolean assigned;
    public AST_EXP assignedExp;
    public AST_TYPE type;
    public String ID;

    public AST_GLOBAL_VAR_DEC(int line, AST_TYPE varType, String ID, AST_EXP assignedExp) {
        SerialNumber = AST_Node_Serial_Number.getFresh();
        this.ID = ID;
        this.type = varType;
        this.assignedExp = assignedExp;
        this.assigned = (assignedExp != null);
        this.line = String.valueOf(line);
    }

    public AST_GLOBAL_VAR_DEC(int line, AST_TYPE varType, String ID) {
        this(line, varType, ID, null);
    }

    public void PrintMe()
    {
        System.out.print("AST NODE GLOBAL VAR DEC\n");
        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                String.format("VAR\nDEC\nID: %s\nAssigned: %b", ID, assigned));
        AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,type.SerialNumber);
        type.PrintMe();
        if (assignedExp != null) assignedExp.PrintMe();
        if (assignedExp != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,assignedExp.SerialNumber);
    }
    public TYPE semantMe()
    {
        TYPE leftType;
        TYPE rightType;
        leftType = type.semantMe();
        if (SYMBOL_TABLE.getInstance().findInInnerScope(ID) != null)
            throw new SemanticError(String.format("%s %s already exists in this scope", line, ID));
        SYMBOL_TABLE.getInstance().enter(ID, leftType);
        if(assigned) {
            rightType = assignedExp.semantMe();
            if(!isLegalAssignment(leftType, rightType))
                throw new SemanticError(String.format("%s illegal assignment", line));
        }
        return null;
    }

    public TEMP IRme()
    {
        IR IRInstance = IR.getInstance();
        IRInstance.activateDataSection();
        IRcommand_Global_Var_Dec cmd = new IRcommand_Global_Var_Dec(ID);
        if (assigned)
        {
            if(assignedExp instanceof AST_EXP_INT){
                cmd.setIntInitVal(((AST_EXP_INT) assignedExp).value);
            }
            if(assignedExp instanceof AST_EXP_STRING){
                String initialVal = ((AST_EXP_STRING) assignedExp).str;
                String label = IRcommand.getFreshStrLabel();
                cmd.setStrInitVal(label);
                IR.getInstance().Add_IRcommand(new IRcommandConstString(label, initialVal));
            }
        }
        IRInstance.Add_IRcommand(cmd);


        return null;
    }

}