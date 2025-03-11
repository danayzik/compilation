
package IR;


import MIPS.MIPSGenerator;

public class IRcommand_Global_Var_Dec extends IRcommand //Add nil?
{
	String var_name;

	boolean hasInitialValue = false;
	String strInitValLabel = null;
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

	public void mipsMe(){
		String value;
		if(!hasInitialValue){
			value = "0";
		}
		else{
			if(strInitValLabel!= null){
				value = strInitValLabel;
			}
			else{
				value = String.valueOf(intInitVal);
			}
		}
		MIPSGenerator.getInstance().addGlobalVar(var_name, value);
		super.mipsMe();
	}


}
