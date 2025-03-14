
package IR;

import AST.Address;
import MIPS.MIPSGenerator;
import TEMP.*;

public class IRcommand_Load extends IRcommand
{
	Address addr;
	TEMP dst;

	public IRcommand_Load(TEMP dst, Address addr)
	{
		this.dst      = dst;
		this.addr = addr;
	}

	public void printMe(){
		super.printMe();
		System.out.printf("LW %s  %s\n", dst, addr);
	}
	@Override
	public void mipsMe() {
		String dstReg = TEMP_FACTORY.getInstance().tempToRegister(dst.getSerialNumber());
		String address = addr.toString();
		MIPSGenerator gen = MIPSGenerator.getInstance();
		gen.loadFromAddress(dstReg, address);
		super.mipsMe();

	}
	public void inToOut(InOutSets setsObj){
		super.inToOut(setsObj);
		setsObj.tempsOut.remove(dst.getSerialNumber());
		if(addr.isCustomReg)
			setsObj.tempsOut.add(addr.tempRegister.getSerialNumber());

	}


}
