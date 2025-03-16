
package IR;


import java.util.HashMap;
import java.util.Map;

public class IRcommandList
{
	public IRcommand head;
	public IRcommand tail;
	public int size = 0;
	public Map<Integer, IRcommand> cmdMap = new HashMap<>();

	public IRcommandList()
	{
		this.head = null;
		this.tail = null;
	}

	public void addCommand(IRcommand cmd){
		cmd.index = size;
		cmdMap.put(size, cmd);
		size++;
		if (head == null)
		{
			this.head = cmd;
		}
		else
		{
			if(!(this.tail.isReturn() || this.tail.isJump()) || cmd.isReturn())
				cmd.prev = this.tail;
			this.tail.next = cmd;
		}
		this.tail = cmd;
	}
	public void mipsMe(){if(head!=null)head.mipsMe();}

	public void printMe(){
		IRcommand curr = head;
		while(curr!=null){
			curr.printMe();
			curr = curr.next;
		}
	}
}
