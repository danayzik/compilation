package AST;
import TYPES.*;

public class SemanticUtils {
    private SemanticUtils() {}

    public static void checkLegalAssignment(TYPE leftType, TYPE rightType, String line){
        if (leftType instanceof TYPE_CLASS_MEMBER)
            leftType = ((TYPE_CLASS_MEMBER) leftType).t;
        if (rightType instanceof TYPE_CLASS_MEMBER)
            rightType = ((TYPE_CLASS_MEMBER) rightType).t;
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
        if(leftType.isArray() && rightType.isNewArray()){
            if (((TYPE_ARRAY)leftType).arrayType == ((TYPE_NEW_ARRAY)rightType).arrayType)
                return;
        }
        throw new SemanticError(String.format("%s illegal assignment", line));
    }
}
