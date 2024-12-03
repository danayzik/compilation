package AST;
import java.util.List;
import java.util.ArrayList;
//not finished
public class AST_GLOBAL_FUNC_DEC extends AST_DEC
{
    public AST_TYPE type;
    public String ID;
    public AST_STMT_LIST body;
    public List<FUNC_ARG> argList;

    public AST_GLOBAL_FUNC_DEC(AST_TYPE type,String id,AST_STMT_LIST stLst, List<FUNC_ARG> argList)
    {
        this.type = type;
        this.ID = id;
        this.body = stLst;
        this.argList = argList;
    }






}