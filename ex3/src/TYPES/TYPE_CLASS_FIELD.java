package TYPES;

public class TYPE_CLASS_FIELD extends TYPE_CLASS_MEMBER
{
	public TYPE t;
	public String name;

	public TYPE_CLASS_FIELD(TYPE t,String name)
	{
		this.t = t;
		this.name = name;
	}
	public boolean isField(){return true;}
}
