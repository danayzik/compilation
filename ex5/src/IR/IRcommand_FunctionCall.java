
package IR;
import TEMP.TEMP_FACTORY;
import MIPS.MIPSGenerator;
import TEMP.TEMP;
import TEMP.TEMP_LIST;

public class IRcommand_FunctionCall extends IRcommand
{
	TEMP_LIST argListValues;
	String funcName;
	TEMP ownerObj;
	boolean isGlobal;

	public TEMP dst;
	public TEMP vtableAddr;
	public int offset;
	public boolean saveReturnValue;

	//Global function with return value
	public IRcommand_FunctionCall(String id, TEMP_LIST argList, TEMP dst)
	{
		this.saveReturnValue = dst != null;
		this.funcName = id;
		this.dst = dst;
		this.isGlobal = true;
		this.argListValues = argList;
	}

	//Global function discard return
	public IRcommand_FunctionCall(String id, TEMP_LIST argList){
		this(id, argList, null);
	}

	//Explicit method call save return value
	public IRcommand_FunctionCall(String id, TEMP_LIST argList,
								  TEMP dst, TEMP ownerObj,
								  TEMP vtableAddr, int offset){
		this.funcName = id;
		this.argListValues = argList;
		this.dst = dst;
		this.ownerObj = ownerObj;
		this.vtableAddr = vtableAddr;
		this.offset = offset;
		saveReturnValue = dst!=null;
	}

	//Explicit method call discard return
	public IRcommand_FunctionCall(String id, TEMP_LIST argList,
								  TEMP ownerObj, TEMP vtableAddr, int offset){
		this(id, argList, null, ownerObj, vtableAddr, offset);
	}
	//Implicit method call, save return value
	public IRcommand_FunctionCall(String id, TEMP_LIST argList,
								  TEMP vtableAddr, int offset, TEMP dst){
		this.saveReturnValue = dst!=null;
		this.funcName = id;
		this.argListValues = argList;
		this.vtableAddr = vtableAddr;
		this.offset = offset;
		this.dst = dst;
	}
	//Implicit method call, discard return
	public IRcommand_FunctionCall(String id, TEMP_LIST argList,
								  TEMP vtableAddr, int offset){
		this(id, argList, vtableAddr, offset, null);
	}

	public void printMe(){
		super.printMe();
		if(saveReturnValue)
			System.out.printf("%s = ", dst);
		if(isGlobal)
			System.out.printf("Call to global %s args: ", funcName);
		else
			System.out.printf("Virtual call to %s, Implicit: %b args: ", funcName, ownerObj==null);

		TEMP curr;
		TEMP_LIST currTail = argListValues;
		while (currTail != null){
			curr = currTail.head;
			if(curr!=null) System.out.printf("%s ", curr);
			currTail = currTail.tail;
		}
		System.out.println();
	}

	@Override
	public void mipsMe() {
		TEMP_FACTORY fact = TEMP_FACTORY.getInstance();
		MIPSGenerator gen = MIPSGenerator.getInstance();
		gen.backupTemps();
		if(isGlobal){
			gen.jal(funcName);
		}
		else{
			String vtableReg = fact.tempToRegister(vtableAddr.getSerialNumber());
			String addr = String.format("%d(%s)", offset, vtableReg);
			gen.loadAddress("$s0", addr);
			gen.jalr("$s0");
		}
		gen.restoreTemps();
		gen.addToStack(argListValues.size*4);

		if(saveReturnValue){
			String dstReg = fact.tempToRegister(dst.getSerialNumber());
			//Restore register backup here?
			gen.saveReturn(dstReg);
		}


		super.mipsMe();
	}
}
