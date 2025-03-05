package TYPES;

public abstract class TYPE
{

	public String name;

	public boolean isClass(){ return false;}


	public boolean isArray(){ return false;}
	public boolean isNil(){ return false;}
	public boolean isNewArray(){return false;}
	public String toString(){return "type";}
}
