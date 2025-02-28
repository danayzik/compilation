package IR;

import java.util.HashSet;
import java.util.Set;

public class unInitSets {

    public Set<String> uninitTempsIn;
    public Set<String> uninitVariablesIn;
    public Set<String> uninitTempsOut;
    public Set<String> uninitVariablesOut;

    public unInitSets(){
        this.uninitTempsIn = new HashSet<>();
        this.uninitVariablesIn = new HashSet<>();
        this.uninitTempsOut = new HashSet<>();
        this.uninitVariablesOut = new HashSet<>();
    }

    public void calculateOut(IRcommand cmd){
        cmd.inToOut(this);
    }

    public boolean calculateIn(unInitSets incomingSets){
        boolean changedTemps = this.uninitTempsIn.addAll(incomingSets.uninitTempsOut);
        boolean changedVariables = this.uninitVariablesIn.addAll(incomingSets.uninitVariablesOut);
        return changedTemps | changedVariables;
    }

}
