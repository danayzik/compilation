
package IR;



public class IRcommandList
{
	public IRcommand head;
	public IRcommandList tail;

	IRcommandList(IRcommand head, IRcommandList tail)
	{
		this.head = head;
		this.tail = tail;
	}
	public void printMe(){

		if (head!=null)head.printMe();
		if(tail!=null)tail.printMe();
	}
}
