package IR;

public class IRcommandListList {

    public IRcommandList head;
    public IRcommandListList tail;

    public IRcommandListList(IRcommandList head, IRcommandListList tail)
    {
        this.head = head;
        this.tail = tail;
    }

    public void AddCommandList(IRcommandList cmdList)
    {
        if ((head == null) && (tail == null))
        {
            this.head = cmdList;
        }
        else if ((head != null) && (tail == null))
        {
            this.tail = new IRcommandListList(cmdList, null);
        }
        else
        {
            IRcommandListList it = tail;
            while ((it != null) && (it.tail != null))
            {
                it = it.tail;
            }
            it.tail = new IRcommandListList(cmdList,null);
        }
    }
    public IRcommandList getLastList(){
        IRcommandList curr = head;
        IRcommandListList currTail = tail;
        while(currTail != null){
            curr = currTail.head;
            currTail = currTail.tail;

        }
        return curr;
    }

    public void printMe(){
        if(head!= null)head.printMe();
        if(tail!=null)tail.printMe();
    }
}
