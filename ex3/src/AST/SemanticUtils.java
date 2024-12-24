package AST;
import TYPES.*;

public class SemanticUtils {
    private SemanticUtils() {}

    public static void checkLegalAssignment(AST_Node leftNode, AST_Node rightNode, String line){
        TYPE leftType = leftNode.semanticType;
        TYPE rightType = rightNode.semanticType;
        if(leftType == rightType){
            return;
        }
        boolean bothObjects = leftType.isClass() && rightType.isClass();
        if(bothObjects){
            if(((TYPE_CLASS)rightType).isAncestor(((TYPE_CLASS)leftType))){
                return;
            }
        }
        if (rightType.isNil() && (leftType.isArray() || leftType.isClass()))
            return;
        if(leftType.isArray()){
            if (((TYPE_ARRAY)leftType).arrayType == rightType && rightNode instanceof AST_NEW_EXP)
                return;
        }
        throw new SemanticError(line);
    }
}
