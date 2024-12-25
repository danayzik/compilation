package TYPES;

public abstract class TYPE_CLASS_MEMBER extends TYPE
{
	public TYPE t;
	public TYPE_CLASS_MEMBER next;
	public boolean isField(){return false;}
	public boolean isMethod(){return false;}
}
