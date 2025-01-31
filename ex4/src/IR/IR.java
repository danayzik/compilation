
package IR;
import java.util.*;

public class IR
{
	private IRcommand head=null;
	private IRcommandList tail=null;

	private int cmdIndex = 1;

	private Map<Integer, IRcommand> cmdMap = new HashMap<>();

	public void Add_IRcommand(IRcommand cmd)
	{
		cmdMap.put(cmdIndex, cmd);
		cmd.index = cmdIndex++;
		if ((head == null) && (tail == null))
		{
			this.head = cmd;

		}
		else if ((head != null) && (tail == null))
		{
			this.tail = new IRcommandList(cmd,null);

		}
		else
		{
			IRcommandList it = tail;
			while ((it != null) && (it.tail != null))
			{
				it = it.tail;
			}
			it.tail = new IRcommandList(cmd,null);
		}
	}

	public void setupCFG(){
		IRcommand curr = head;
		IRcommandList currTail = tail;
		while (currTail != null){
			if(!curr.isJump())curr.nextCmdInLine = currTail.head;
			curr = currTail.head;
			currTail = currTail.tail;

		}

	}
	private static IR instance = null;

	protected IR() {}

	public static IR getInstance()
	{
		if (instance == null)
		{
			instance = new IR();
		}
		return instance;
	}
	public void printMe(){

		if (head!=null)head.printMe();
		if(tail!=null)tail.printMe();
	}



	public Set<String> dataFlowAnalysis(){
		TreeSet<Integer> workSet = new TreeSet<>();
		workSet.add(1);
		Map<Integer, unInitSets> setsMap = new HashMap<>();
		IRcommand workingCmd;
		int workingIndex;
		int nextCmd1Index;
		int nextCmd2Index;
		unInitSets workingSets;
		IRcommand nextCmd1;
		IRcommand nextCmd2;

		for (int i = 1; i < cmdIndex; i++) {
			setsMap.put(i, new unInitSets());
		}


		while(!workSet.isEmpty()){

			workingIndex = workSet.first();

			workSet.remove(workingIndex);
			workingCmd = cmdMap.get(workingIndex);
			workingSets = setsMap.get(workingIndex);
			nextCmd1 = workingCmd.nextCmdInLine;
			nextCmd2 = workingCmd.jumpToCmd;


			workingSets.calculateOut(workingCmd);

			if (nextCmd1 != null){
				nextCmd1Index = nextCmd1.index;

				if(setsMap.get(nextCmd1Index).calculateIn(workingSets) || !nextCmd1.workedOn){
					workSet.add(nextCmd1Index);
				}
			}
			if (nextCmd2 != null){
				nextCmd2Index = nextCmd2.index;
				if(setsMap.get(nextCmd2Index).calculateIn(workingSets) || !nextCmd2.workedOn){
					workSet.add(nextCmd2Index);
				}
			}
		}

		Set<String> usedUninitVariables = new HashSet<>();
		for (int i = 1; i < cmdIndex; i++) {
			workingCmd = cmdMap.get(i);
			workingSets = setsMap.get(i);
			workingCmd.addUninitVariableUse(usedUninitVariables, workingSets);
		}
		return usedUninitVariables;



	}
}
