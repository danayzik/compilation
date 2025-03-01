package TEMP;

import IR.IRcommandListList;

public class TEMP_LIST {
    public TEMP head;
    public TEMP_LIST tail;

    public TEMP_LIST(TEMP head, TEMP_LIST tail){
        this.head = head;
        this.tail = tail;
    }

    public void addTemp(TEMP t){
        if ((head == null) && (tail == null))
        {
            this.head = t;
        }
        else if ((head != null) && (tail == null))
        {
            this.tail = new TEMP_LIST(t, null);
        }
        else
        {
            TEMP_LIST it = tail;
            while ((it != null) && (it.tail != null))
            {
                it = it.tail;
            }
            it.tail = new TEMP_LIST(t,null);
        }
    }
}
