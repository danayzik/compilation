package TYPES;

public class TYPE_ARRAY extends TYPE
{

	public TYPE arrayType;

	public TYPE_ARRAY(TYPE type, String name)
	{
		this.name = name;
		arrayType = type;

	}
	public boolean isArray(){return true;}


}
