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
	public boolean matchingTypes(TYPE t){
		return t == returnType;
	}
	public boolean matchingParams(TYPE_LIST pList){
		return params.matchingList(pList);
	}

	public void setParams(TYPE_LIST params){
		this.params = params;
	}
}
