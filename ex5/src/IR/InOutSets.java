package IR;

import java.util.HashSet;
import java.util.Set;

public class InOutSets {

    public Set<Integer> tempsIn;
    public Set<Integer> tempsOut;


    public InOutSets(){
        this.tempsIn = new HashSet<>();
        this.tempsOut = new HashSet<>();
    }

    public void calculateOut(IRcommand cmd){
        cmd.inToOut(this);
    }

    public boolean calculateIn(InOutSets incomingSets){
        boolean changedTemps = this.tempsIn.addAll(incomingSets.tempsOut);
        return changedTemps;
    }

}
