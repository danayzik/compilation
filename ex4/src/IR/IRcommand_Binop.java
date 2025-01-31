
package IR;

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
	public void inToOut(unInitSets setsObj){
		super.inToOut(setsObj);
		if(setsObj.uninitTempsIn.contains(t1.toString()) | setsObj.uninitTempsIn.contains(t2.toString())){
			setsObj.uninitTempsOut.add(dst.toString());
		}
		else{
			setsObj.uninitTempsOut.remove(dst.toString());
		}
	}

}
