package AST;
import TEMP.*;

public class Address {
    public String varName;
    public int offset;
    public String label = null;
    public TEMP tempRegister = null;

    public boolean isOnStack = false;
    public boolean isFieldOfSelf= false;

    //Global var
    public Address(String varName, String label) {
        this.varName = varName;
        this.label = label;
    }

    //Local var and Implicit Field
    public Address(String varName, int offset, boolean isField){
        this.varName = varName;
        this.isOnStack = !isField;
        this.isFieldOfSelf = isField;
        this.offset = offset;
    }

    public Address(String varName, int offset, TEMP t){
        this.varName = varName;
        this.offset = offset;
        this.tempRegister = t;
    }


    @Override
    public String toString(){
        return String.format("Variable: %s, Implicit Field: %b, Local: %b", varName, isFieldOfSelf, isOnStack);
    }


}
