
package IR;
import MIPS.MIPSGenerator;
import TYPES.TYPE_CLASS;

import java.util.*;

public class IR
{
	private int currentlyActive = 0;
	private IRcommandList dataSectionCommands;
	private IRcommandListList funcSection;

	public TYPE_CLASS activeClass;



	private int cmdIndex = 1;

	private Map<Integer, IRcommand> cmdMap = new HashMap<>();



	public void declareNewFunc(){
		funcSection.AddCommandList(new IRcommandList());
	}


	public void Add_IRcommand(IRcommand cmd){
		switch (currentlyActive){
			case 0:
				dataSectionCommands.addCommand(cmd);
				break;
			case 1:
				funcSection.getLastList().addCommand(cmd);
				break;
		}

	}

	public void activateDataSection(){
		currentlyActive = 0;
	}
	public void activateFunctionSection(){
		currentlyActive = 1;
	}



	public void mipsMe(){
		MIPSGenerator gen = MIPSGenerator.getInstance();
		dataSectionCommands.mipsMe();
		gen.startTextSection();
		funcSection.mipsMe();
		gen.finalizeFile();
	}


	private static IR instance = null;

	protected IR() {
		dataSectionCommands = new IRcommandList();
		funcSection = new IRcommandListList(null, null);

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
		System.out.println("--------Data Section--------");
		dataSectionCommands.printMe();

		System.out.println("--------Functions--------");
		funcSection.printMe();


	}



	public void dataFlowAnalysis(IRcommandList funcCommands){
		TreeSet<Integer> workSet = new TreeSet<>();
		workSet.add(funcCommands.tail.index);
		Map<Integer, InOutSets> setsMap = new HashMap<>();
		IRcommand workingCmd;
		int workingIndex;
		int nextCmd1Index;
		int nextCmd2Index;
		InOutSets workingSets;
		IRcommand nextCmd1;
		IRcommand nextCmd2;

		for (int i = 1; i <= funcCommands.size; i++) {
			setsMap.put(i, new InOutSets());
		} //This doesn't map properly if indexes do not start from 1? possibly.

		while(!workSet.isEmpty()){

			workingIndex = workSet.last();
//			System.out.printf("Now working on %d\n", workingIndex);
			workSet.remove(workingIndex);
			workingCmd = cmdMap.get(workingIndex);
			workingSets = setsMap.get(workingIndex);
			nextCmd1 = workingCmd.prev;
			nextCmd2 = workingCmd.jumpPrev;


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




	}
}
