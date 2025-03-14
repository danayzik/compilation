
package IR;

import TEMP.TEMP;
import TEMP.TEMP_FACTORY;
import MIPS.MIPSGenerator;


public class IRcommand_Null_Obj_Check extends IRcommand
{

	TEMP objAddr;


	public IRcommand_Null_Obj_Check(TEMP objAddr)
	{
		this.objAddr = objAddr;
	}

	public void printMe(){
		super.printMe();
		System.out.printf("if %s == 0 abort\n", objAddr);
	}

	@Override
	public void mipsMe() {
		MIPSGenerator gen = MIPSGenerator.getInstance();
		TEMP_FACTORY fact = TEMP_FACTORY.getInstance();
		String reg = fact.tempToRegister(objAddr.getSerialNumber());
		gen.checkForNullDeref(reg);
		super.mipsMe();
	}
	public void inToOut(InOutSets setsObj){
		super.inToOut(setsObj);

		setsObj.tempsOut.add(objAddr.getSerialNumber());

	}
}
