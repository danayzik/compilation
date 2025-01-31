
package IR;



public class IRcommand_Allocate extends IRcommand
{
	String var_name;
	
	public IRcommand_Allocate(String var_name)
	{
		this.var_name = var_name;
	}

	public void printMe(){
		super.printMe();
		System.out.printf("Alloc %s\n", var_name);
	}

	public void inToOut(unInitSets setsObj){
		super.inToOut(setsObj);
		setsObj.uninitVariablesOut.add(var_name);

	}
}
