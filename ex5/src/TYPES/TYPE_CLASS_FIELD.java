package TYPES;

public class TYPE_CLASS_FIELD extends TYPE_CLASS_MEMBER
{

	public boolean hasInitialValue = false;
	public boolean isInt = false;
	public boolean isString = false;
	public int initialIntValue;
	public String initialStringValue = null;
	public TYPE_CLASS_FIELD(TYPE t,String name)
	{
		this.t = t;
		this.name = name;
	}
	public void setInitialInt(int val){
		this.hasInitialValue = true;
		this.isInt = true;
		this.initialIntValue = val;
	}
	public void setInitialStringValue(String val){
		this.hasInitialValue = true;
		this.isString = true;
		this.initialStringValue = val;
	}
	public String getInitialValue(){
		if(isString)return initialStringValue;
		if(isInt)return String.valueOf(initialIntValue);
		return "0";
	}
	public boolean isField(){return true;}
}
