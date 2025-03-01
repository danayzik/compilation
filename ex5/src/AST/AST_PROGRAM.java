package AST;
import IR.IR;
import TEMP.*;
import TYPES.TYPE;
import IR.*;
public class AST_PROGRAM extends AST_Node
{
    public AST_DEC_LIST decList;

    public AST_PROGRAM(AST_DEC_LIST decList){
        this.decList = decList;
    }
    public TYPE semantMe(){
        return decList.semantMe();

    }

    @Override
    public void PrintMe() {
        decList.PrintMe();
    }

    public TEMP IRme(){
        TEMP t = decList.IRme();
        return t;
    }
}