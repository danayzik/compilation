package TYPES;

public class TYPE_FUNCTION extends TYPE
{

	public TYPE returnType;

	public TYPE_LIST params;

	public TYPE_FUNCTION(TYPE returnType,String name)
	{
		this.name = name;
		this.returnType = returnType;
		this.params = null;
	}


	public void setParams(TYPE_LIST params){
		this.params = params;
	}

	public boolean canAssignToArgs(TYPE_LIST args){
		boolean onlyOneIsNull = (args == null) != (params == null);
		if (args == null && params == null)
			return true;
		if (onlyOneIsNull)
			return false;
		return this.params.canAssignList(args);
	}
}
