
package IR;

import MIPS.MIPSGenerator;
import TEMP.TEMP;
import TEMP.TEMP_FACTORY;

public class IRcommand_Add_Addresses extends IRcommand
{
	public TEMP t1;
	public TEMP t2;
	public TEMP dst;


	public IRcommand_Add_Addresses(TEMP dst, TEMP t1, TEMP t2)
	{
		this.dst = dst;
		this.t1 = t1;
		this.t2 = t2;

	}



	public void printMe(){
		super.printMe();
		System.out.printf("%s = %s + %s\n", dst, t1,  t2);
	}
	public void inToOut(InOutSets setsObj){
		super.inToOut(setsObj);
		setsObj.tempsOut.remove(dst.getSerialNumber());
		setsObj.tempsOut.add(t1.getSerialNumber());
		setsObj.tempsOut.add(t2.getSerialNumber());
	}
	public void mipsMe(){
		TEMP_FACTORY fact = TEMP_FACTORY.getInstance();
		MIPSGenerator gen = MIPSGenerator.getInstance();
		String dstReg = fact.tempToRegister(dst.getSerialNumber());
		String reg1 = fact.tempToRegister(t1.getSerialNumber());
		String reg2 = fact.tempToRegister(t2.getSerialNumber());
		gen.addAddresses(dstReg, reg1, reg2);

		super.mipsMe();
	}

}
