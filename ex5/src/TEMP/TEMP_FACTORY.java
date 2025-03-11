
package TEMP;


public class TEMP_FACTORY
{
	private int counter=0;


	
	public TEMP getFreshTEMP()
	{
		return new TEMP(counter++);
	}

	public String tempToRegister(int serial){
		return String.format("$t%d", serial);
	}

	private static TEMP_FACTORY instance = null;


	protected TEMP_FACTORY() {}

	public static TEMP_FACTORY getInstance()
	{
		if (instance == null)
		{
			instance = new TEMP_FACTORY();
		}
		return instance;
	}

}
