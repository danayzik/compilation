
package IR;

import MIPS.MIPSGenerator;
import TEMP.*;
import AST.Address;

public class IRcommand_Store extends IRcommand
{
	Address addr;
	TEMP src;
	
	public IRcommand_Store(Address addr, TEMP src)
	{
		this.src      = src;
		this.addr = addr;
	}

	public void printMe(){
		super.printMe();

		System.out.printf("SW from %s to %s\n", addr, src);
	}

	@Override
	public void mipsMe() {
		String srcReg = TEMP_FACTORY.getInstance().tempToRegister(src.getSerialNumber());
		String address = addr.toString();
		MIPSGenerator gen = MIPSGenerator.getInstance();
		gen.storeToAddress(srcReg, address);
		super.mipsMe();

	}
}
