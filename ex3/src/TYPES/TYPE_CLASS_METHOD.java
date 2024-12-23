package TYPES;

public class TYPE_CLASS_METHOD extends TYPE_CLASS_MEMBER
{

	public TYPE_LIST args;

	public TYPE_CLASS_METHOD(TYPE t, String name)
	{
		this.t = t;
		this.name = name;
		this.args = null;
	}
	public void setArgs(TYPE_LIST args){
		this.args=args;
	}
	public boolean isMethod(){return true;}
}
