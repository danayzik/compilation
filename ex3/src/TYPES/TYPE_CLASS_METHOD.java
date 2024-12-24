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
	public void setParams(TYPE_LIST args){
		this.args=args;
	}
	public boolean isMethod(){return true;}

	public boolean canAssignToArgs(TYPE_LIST params){
		boolean onlyOneIsNull = (args == null) != (params == null);
		if (args == null && params == null)
			return true;
		if (onlyOneIsNull)
			return false;
		return this.args.canAssignList(params);
	}
}
