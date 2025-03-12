
package IR;

import TEMP.TEMP;

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


}
