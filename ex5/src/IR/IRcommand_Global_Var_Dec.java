
package IR;



public class IRcommand_Global_Var_Dec extends IRcommand //Add nil?
{
	String var_name;

	boolean hasInitialValue = false;
	String strInitValLabel;
	int intInitVal = 0;



	public IRcommand_Global_Var_Dec(String var_name)
	{
		this.var_name = var_name;
	}
	public void setStrInitVal(String label){
		strInitValLabel = label;
		hasInitialValue = true;
	}
	public void setIntInitVal(int val){
		intInitVal = val;
		hasInitialValue = true;
	}

	public void printMe(){
		super.printMe();
		System.out.printf("Global var %s value not printed\n", var_name);
	}


}
