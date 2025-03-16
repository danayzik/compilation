
package IR;
import INTERFERENCE_GRAPH.*;
import MIPS.MIPSGenerator;
import TYPES.TYPE_CLASS;

import java.util.*;

public class IR
{
	private int currentlyActive = 0;
	private IRcommandList dataSectionCommands;
	private IRcommandListList funcSection;

	public TYPE_CLASS activeClass;




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

	public void allocateRegister(){
		IRcommandListList currList = funcSection;
		IRcommandList currFunc = funcSection.head;
		while(currFunc!=null){
//			System.out.printf("Now handling %s\n", currFunc.head.toString());
			InterferenceGraph graph = new InterferenceGraph(currFunc);
			graph.dataFlowAnalysis();
			graph.buildInterferenceGraph();
			graph.colorGraph();
			if(currList.tail==null)
				break;
			currList = currList.tail;
			currFunc = currList.head;
		}
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




}
