package AST;
import TYPES.*;

public class SemanticUtils {
    private SemanticUtils() {}

    public static void checkLegalAssignment(TYPE leftType, TYPE rightType, String line){
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
            if (((TYPE_ARRAY)leftType).arrayType == rightType)
                return;
        }
        throw new SemanticError(line);
    }
}
