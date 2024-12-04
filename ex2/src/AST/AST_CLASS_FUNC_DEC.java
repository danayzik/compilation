package AST;
import java.util.List;
import java.util.ArrayList;
// not finished need to print argList
public class AST_CLASS_FUNC_DEC extends AST_CFIELD
{
    public AST_TYPE type;
    public String ID;
    public AST_STMT_LIST body;
    public List<FUNC_ARG> argList;

    public AST_CLASS_FUNC_DEC(AST_TYPE type,String id,AST_STMT_LIST stLst, List<FUNC_ARG> argList)
    {
        SerialNumber = AST_Node_Serial_Number.getFresh();
        this.type = type;
        this.ID = id;
        this.body = stLst;
        this.argList = argList;
    }
    public void PrintMe()
    {
        System.out.print("AST NODE CLASS METHOD DEC\n");
        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                String.format("METHOD\nDEC\nID: %s", ID));
        AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,type.SerialNumber);
        type.PrintMe();
        if (body != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,body.SerialNumber);
    }






}