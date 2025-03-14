
package IR;
import MIPS.MIPSGenerator;

import TEMP.*;
import TYPES.TYPE;
import TYPES.TYPE_CLASS;
import TYPES.TYPE_CLASS_FIELD;
import TYPES.TYPE_CLASS_MEMBER;

public class IRcommand_New_Class_Object extends IRcommand
{

	TYPE_CLASS type;
	TEMP dst;
	int size;

	public IRcommand_New_Class_Object(TYPE type, TEMP dst)
	{
		this.type = (TYPE_CLASS) type;
		this.dst = dst;
		this.size = ((TYPE_CLASS) type).getSize();
	}

	public void printMe(){
		super.printMe();
		System.out.printf("%s = New object of class %s allocate %d bytes\n", dst, type.name, size);
	}
	public void inToOut(InOutSets setsObj){
		super.inToOut(setsObj);
		setsObj.tempsOut.remove(dst.getSerialNumber());

	}

	@Override
	public void mipsMe() {
		MIPSGenerator gen = MIPSGenerator.getInstance();
		String dstReg = TEMP_FACTORY.getInstance().tempToRegister(dst.getSerialNumber());
		String vtableLabel = null;
		boolean hasMethod = type.hasMethod;
		if(hasMethod){
			vtableLabel = String.format("vtable_%s", type.name);
		}
		gen.newClassObject(dstReg, size, hasMethod, vtableLabel);
		TYPE_CLASS_FIELD currField;
		for(TYPE_CLASS_MEMBER currMember : type.fieldList){
			currField = (TYPE_CLASS_FIELD) currMember;
			String val = currField.getInitialValue();
			if(currField.isString){
				gen.loadAddress("$s0", val);
			}
			else{
				gen.loadImmediate(dstReg, val);
			}
			int offset = type.getFieldOffset(currField.name);
			String address = String.format("%d(%s)", offset, dstReg);
			gen.storeToAddress("$s0", address);
		}
		super.mipsMe();
	}
}
