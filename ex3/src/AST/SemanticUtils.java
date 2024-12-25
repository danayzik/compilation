package AST;
import TYPES.*;

public class SemanticUtils {
    private SemanticUtils() {}

    public static boolean isLegalAssignment(TYPE leftType, TYPE rightType){
        System.out.println(leftType);
        System.out.println(rightType);
        if(leftType == rightType){
            return true;
        }
        boolean bothObjects = leftType.isClass() && rightType.isClass();
        if(bothObjects){
            if(((TYPE_CLASS)rightType).isAncestor(((TYPE_CLASS)leftType))){
                return true;
            }
        }
        if (rightType.isNil() && (leftType.isArray() || leftType.isClass()))
            return true;
        if(leftType.isArray() && rightType.isNewArray()){
            if (((TYPE_ARRAY)leftType).arrayType == ((TYPE_NEW_ARRAY)rightType).arrayType)
                return true;
        }
        return false;
    }
}
