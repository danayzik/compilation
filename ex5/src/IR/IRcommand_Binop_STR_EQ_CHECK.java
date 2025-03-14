
package IR;

import TEMP.TEMP;
import MIPS.MIPSGenerator;
import TEMP.TEMP_FACTORY;

public class IRcommand_Binop_STR_EQ_CHECK extends IRcommand
{
	public TEMP t1;
	public TEMP t2;
	public TEMP dst;


	public IRcommand_Binop_STR_EQ_CHECK(TEMP dst, TEMP t1, TEMP t2)
	{
		this.dst = dst;
		this.t1 = t1;
		this.t2 = t2;

	}



	public void printMe(){
		super.printMe();
		System.out.printf("Strings EQ check %s = %s == %s\n", dst, t1,  t2);
	}

	@Override
	public void mipsMe() {
		MIPSGenerator gen = MIPSGenerator.getInstance();
		TEMP_FACTORY fact = TEMP_FACTORY.getInstance();
		String dstReg = fact.tempToRegister(dst.getSerialNumber());
		String reg1 = fact.tempToRegister(t1.getSerialNumber());
		String reg2 = fact.tempToRegister(t2.getSerialNumber());
		gen.stringEqualityCheck(dstReg, reg1, reg2);
		super.mipsMe();
	}
	public void inToOut(InOutSets setsObj){
		super.inToOut(setsObj);
		setsObj.tempsOut.remove(dst.getSerialNumber());
		setsObj.tempsOut.add(t1.getSerialNumber());
		setsObj.tempsOut.add(t2.getSerialNumber());
	}
}
