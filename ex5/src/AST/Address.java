package AST;
import TEMP.*;

public class Address {
    public String varName;
    public int offset =0;
    public String label = null;
    public TEMP tempRegister = null;

    public boolean isLabel = false;
    public boolean isCustomReg = false;
    public boolean framePointerAddr = false;

    public boolean stackPointerAddr = false;
    public boolean isFieldOfSelf= false;


    public Address(String varName) {
        this.varName = varName;
    }
    public void setAsLabelAddr(String label){
        this.isLabel = true;
        this.label = label;
    }

    public void setAsFPAddr(int offset){
        this.offset = offset;
        this.framePointerAddr = true;
    }
    public void setAsSPAddr(int offset){
        this.offset = offset;
        this.stackPointerAddr = true;
    }
    public void setAsImplicitField(int offset){
        this.offset = offset;
        this.isFieldOfSelf = true;
    }
    public void setCustomReg(int offset, TEMP reg){
        this.isCustomReg = true;
        this.tempRegister = reg;
    }




    @Override
    public String toString(){
        String reg = "error";
        if(isLabel) return label;
        if(isCustomReg)reg = TEMP_FACTORY.getInstance().tempToRegister(tempRegister.getSerialNumber());
        if(framePointerAddr)reg = "$fp";
        if(stackPointerAddr)reg = "$sp";
        if(isFieldOfSelf)reg = "a3";
        return String.format("%d(%s)", offset, reg);
    }


}
