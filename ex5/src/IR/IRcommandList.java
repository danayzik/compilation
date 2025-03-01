
package IR;



public class IRcommandList
{
	public IRcommand head;
	public IRcommand tail;

	public IRcommandList()
	{
		this.head = null;
		this.tail = null;
	}

	public void addCommand(IRcommand cmd){
		if (head == null)
		{
			this.head = cmd;
		}
		else
		{
			this.tail.next = cmd;
		}
		this.tail = cmd;
	}

	public void printMe(){
		IRcommand curr = head;
		while(curr!=null){
			curr.printMe();
			curr = curr.next;
		}
	}
}
