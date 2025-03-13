
package IR;

import TEMP.TEMP;
import TEMP.TEMP_FACTORY;
import MIPS.MIPSGenerator;
public class IRcommand_Binop_STR_CONCAT extends IRcommand
{
	public TEMP t1;
	public TEMP t2;
	public TEMP dst;


	public IRcommand_Binop_STR_CONCAT(TEMP dst, TEMP t1, TEMP t2)
	{
		this.dst = dst;
		this.t1 = t1;
		this.t2 = t2;

	}



	public void printMe(){
		super.printMe();
		System.out.printf("Concat Strings %s = %s + %s\n", dst, t1,  t2);
	}
	@Override
	public void mipsMe() {
		MIPSGenerator gen = MIPSGenerator.getInstance();
		TEMP_FACTORY fact = TEMP_FACTORY.getInstance();
		String dstReg = fact.tempToRegister(dst.getSerialNumber());
		String reg1 = fact.tempToRegister(t1.getSerialNumber());
		String reg2 = fact.tempToRegister(t2.getSerialNumber());
		gen.stringConcat(dstReg, reg1, reg2);
		super.mipsMe();
	}

}
