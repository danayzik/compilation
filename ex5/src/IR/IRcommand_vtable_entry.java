
package IR;



public class IRcommand_vtable_entry extends IRcommand //Add nil?
{
	String label;






	public IRcommand_vtable_entry(String label)
	{
		this.label = label;
	}


	public void printMe(){
		super.printMe();
		System.out.printf(".word %s\n", label);
	}


}
