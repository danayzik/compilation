package AST;
import IR.IR;
import TEMP.TEMP;
import TYPES.*;
import SYMBOL_TABLE.*;
import static AST.SemanticUtils.isLegalAssignment;
import TEMP.*;
import IR.*;
public class AST_LOCAL_VAR_DEC extends AST_STMT
{
    public boolean assigned;
    public AST_EXP assignedExp;
    public AST_TYPE type;
    public String ID;
    public int indexInFunc;

    public AST_LOCAL_VAR_DEC(int line, AST_TYPE varType, String ID, AST_EXP assignedExp) {
        this.ID = ID;
        this.type = varType;
        this.assignedExp = assignedExp;
        this.assigned = (assignedExp != null);
        SerialNumber = AST_Node_Serial_Number.getFresh();
        this.line = String.valueOf(line);
    }

    public AST_LOCAL_VAR_DEC(int line, AST_TYPE varType, String ID) {
        this(line, varType, ID, null);
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

    public TYPE semantMe()
    {
        TYPE leftType;
        TYPE rightType;
        leftType = type.semantMe();
        if (SYMBOL_TABLE.getInstance().findInInnerScope(ID) != null)
            throw new SemanticError(String.format("%s %s already exists in this scope", line, ID));

        indexInFunc = SYMBOL_TABLE.getInstance().enter(ID, leftType);
        if(assigned) {
            rightType = assignedExp.semantMe();
            if(!isLegalAssignment(leftType, rightType))
                throw new SemanticError(String.format("%s illegal assignment", line));
        }
        return null;
    }

    public TEMP IRme()
    {
        IR.getInstance().Add_IRcommand(new IRcommand_Allocate(ID));

        if (assigned)
        {
            Address storeAddr = new Address(ID);
            storeAddr.setAsSPAddr(0);
            IR.getInstance().Add_IRcommand(new IRcommand_Store(storeAddr, assignedExp.IRme()));
        }
        return null;
    }
}