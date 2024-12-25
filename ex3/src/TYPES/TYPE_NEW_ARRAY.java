package TYPES;

public class TYPE_NEW_ARRAY extends TYPE
{

	public TYPE arrayType;

	public TYPE_NEW_ARRAY(TYPE type)
	{

		arrayType = type;

	}
	public boolean isArray(){return true;}
	public boolean isNewArray(){return true;}


}
