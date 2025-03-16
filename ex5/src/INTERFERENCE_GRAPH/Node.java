package INTERFERENCE_GRAPH;

import java.util.*;


public class Node {
    public Set<Node> neighboursSet;
    public int tempSerialNum;
    public int degree;
    public int assignedReg = -1;

    public Node(int serialNum){
        this.tempSerialNum = serialNum;
        this.degree = 0;
        this.neighboursSet = new HashSet<>();
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Node other = (Node) obj;
        return tempSerialNum == other.tempSerialNum;
    }
    @Override
    public int hashCode() {
        return Objects.hash(tempSerialNum);
    }

    public void addNeighbour(Node neighbour){
        if(!neighboursSet.contains(neighbour))
            degree++;
        neighboursSet.add(neighbour);
    }
    public void removeNeighbour(Node neighbour){
        if(!neighboursSet.contains(neighbour))return;
        degree--;
        neighboursSet.remove(neighbour);
    }
    public boolean assignReg(){
        TreeSet<Integer> possibleRegs = new TreeSet<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
        for (Node neighbour : neighboursSet){
            possibleRegs.remove(neighbour.assignedReg);
        }
        if(possibleRegs.isEmpty()){
            return false;
        }
        assignedReg = possibleRegs.first();
        return true;
    }
    public void addSelfToNeighbours(){
        for (Node neighbour : neighboursSet){
            neighbour.addNeighbour(this);
        }
    }


}
