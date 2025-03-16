
package IR;
import TEMP.TEMP_FACTORY;
import MIPS.MIPSGenerator;
import TEMP.TEMP;

public class IRcommand_Binop extends IRcommand
{
	public TEMP t1;
	public TEMP t2;
	public TEMP dst;
	public int op;

	public IRcommand_Binop(TEMP dst, TEMP t1, TEMP t2, int op)
	{
		this.dst = dst;
		this.t1 = t1;
		this.t2 = t2;
		this.op = op;
	}

	public static String mapOperator(int operator) {
		switch (operator) {
			case 0:
				return "+";
			case 1:
				return "-";
			case 2:
				return "*";
			case 3:
				return "/";
			case 4:
				return "<";
			case 5:
				return ">";
			case 6:
				return "==";
			default:
				throw new IllegalArgumentException("Invalid operator: " + operator);
		}
	}

	public void printMe(){
		super.printMe();
		System.out.printf("%s = %s %s %s\n", dst, t1, mapOperator(op), t2);
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

		switch (op){
			case 0:
				gen.add(dstReg, reg1, reg2);
				break;
			case 1:
				gen.sub(dstReg, reg1, reg2);
				break;
			case 2:
				gen.mul(dstReg, reg1, reg2);
				break;
			case 3:
				gen.div(dstReg, reg1, reg2);
				break;
			case 4:
				gen.slt(dstReg, reg1, reg2);
				break;
			case 5:
				gen.slt(dstReg, reg2, reg1);
				break;
			case 6:
				gen.seq(dstReg, reg2, reg1);

		}
		super.mipsMe();
	}

}
