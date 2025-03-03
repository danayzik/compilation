package TYPES;

public class TYPE_CLASS_FIELD extends TYPE_CLASS_MEMBER
{

	public boolean hasInitialValue = false;
	public int initialIntValue;
	public String initialStringValue;
	public TYPE_CLASS_FIELD(TYPE t,String name)
	{
		this.t = t;
		this.name = name;
	}
	public void setInitialInt(int val){
		this.hasInitialValue = true;
		this.initialIntValue = val;
	}
	public void setInitialStringValue(String val){
		this.hasInitialValue = true;
		this.initialStringValue = val;
	}
	public boolean isField(){return true;}
}
