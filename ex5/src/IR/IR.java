
package IR;
import java.util.*;

public class IR
{
	private int currentlyActive = 0;
	private IRcommandListList globalVarDecs;
	private IRcommandListList globalFuncs;
	private IRcommandListList classDecs;


	private int cmdIndex = 1;

	private Map<Integer, IRcommand> cmdMap = new HashMap<>();



	private IRcommandListList activeIndexToIRLL(){
		return switch (currentlyActive) {
			case 0 -> globalVarDecs;
			case 1 -> classDecs;
			case 2 -> globalFuncs;
			default -> null;
		};
	}

	public void addCommandList(IRcommandList cmdList){
		IRcommandListList active = activeIndexToIRLL();
		active.AddCommandList(cmdList);
	}

	public void Add_IRcommand(IRcommand cmd){
		IRcommandListList active = activeIndexToIRLL();
		active.getLastList().addCommand(cmd);
	}

	public void setGlobalVarsActive(){
		currentlyActive = 0;
	}
	public void setFuncsActive(){
		currentlyActive = 2;
	}
	public void setClassDecActive(){
		currentlyActive = 1;
	}





	private static IR instance = null;

	protected IR() {
		globalVarDecs = new IRcommandListList(null, null);
		globalFuncs = new IRcommandListList(null, null);
		classDecs = new IRcommandListList(null, null);

	}

	public static IR getInstance()
	{
		if (instance == null)
		{
			instance = new IR();
		}
		return instance;
	}
	public void printMe(){
		System.out.println("--------Global variables--------");
		globalVarDecs.printMe();
		System.out.println("--------Classes--------");
		classDecs.printMe();
		System.out.println("--------Global Functions--------");
		globalFuncs.printMe();


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
//			System.out.printf("Now working on %d\n", workingIndex);
			workSet.remove(workingIndex);
			workingCmd = cmdMap.get(workingIndex);
			workingSets = setsMap.get(workingIndex);
			nextCmd1 = workingCmd.next;
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
