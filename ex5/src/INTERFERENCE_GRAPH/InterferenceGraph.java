package INTERFERENCE_GRAPH;

import IR.IRcommand;
import IR.IRcommandList;
import IR.InOutSets;
import TEMP.TEMP_FACTORY;
import java.util.*;

public class InterferenceGraph {
    public Set<Node> graph;

    public Set<Integer> uniqueTemps;
    public Map<Integer, Node> serialToNode;

    public Map<Integer, Integer> tempToIndex;
    public Map<Integer, InOutSets> setsMap;
    public IRcommandList funcCommands;
    Stack<Node> nodeStack;

    public InterferenceGraph(IRcommandList funcCommands){
        this.funcCommands = funcCommands;
        uniqueTemps = new HashSet<>();
        setsMap = new HashMap<>();
        tempToIndex = new HashMap<>();
        nodeStack = new Stack<>();
        graph = new HashSet<>();
        serialToNode = new HashMap<>();
    }


    public Node getMinDegreeNode(){
        int minDegree = Integer.MAX_VALUE;
        Node minDegNode = null;
        for(Node curr : graph){
            int currDeg = curr.degree;
            if(currDeg < minDegree){
                minDegree = currDeg;
                minDegNode = curr;
            }
        }
        return minDegNode;
    }

    public void colorGraph(){
        while(!graph.isEmpty()){
            Node minDegNode = getMinDegreeNode();
            graph.remove(minDegNode);
            nodeStack.push(minDegNode);
            for (Node node : graph){
                node.removeNeighbour(minDegNode);
            }
        }
        while(!nodeStack.isEmpty()){ // Fix error throw
            Node curr = nodeStack.pop();
            graph.add(curr);
            if(!curr.assignReg())
                throw new RuntimeException("Can't color graph");
            curr.addSelfToNeighbours();
            TEMP_FACTORY.getInstance().tempToReg.put(curr.tempSerialNum, curr.assignedReg);
        }
    }


    public void buildInterferenceGraph(){
        for (int i = 0; i < funcCommands.size; i++) {
            Set<Integer> tempsSet = setsMap.get(i).tempsOut;
            uniqueTemps.addAll(tempsSet);
        }

        for(int temp : uniqueTemps){
            Node tempNode = new Node(temp);
            graph.add(tempNode);
            serialToNode.put(temp, tempNode);
        }

        for (int i = 0; i < funcCommands.size; i++) {
            Set<Integer> tempsSet = setsMap.get(i).tempsOut;
            for(int temp1: tempsSet){
                for(int temp2: tempsSet){
                    if(temp1!=temp2){
                        Node temp1Node = serialToNode.get(temp1);
                        Node temp2Node = serialToNode.get(temp2);
                        temp1Node.addNeighbour(temp2Node);
                        temp2Node.addNeighbour(temp1Node);
                    }
                }
            }
        }

    }

    public void dataFlowAnalysis(){
        TreeSet<Integer> workSet = new TreeSet<>();
//        workSet.add(funcCommands.tail.index);
        IRcommand workingCmd;
        int workingIndex;
        int nextCmd1Index;
        int nextCmd2Index;
        InOutSets workingSets;
        IRcommand nextCmd1;
        IRcommand nextCmd2;
        for (int i = 0; i < funcCommands.size; i++) {
            setsMap.put(i, new InOutSets());
            if(funcCommands.cmdMap.get(i).isReturn())
                workSet.add(i);

        }

        while(!workSet.isEmpty()){

            workingIndex = workSet.last();
//			System.out.printf("Now working on %d\n", workingIndex);
            workSet.remove(workingIndex);
            workingCmd = funcCommands.cmdMap.get(workingIndex);
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
