
package TEMP;


import java.util.HashMap;
import java.util.Map;

public class TEMP_FACTORY
{
	private int counter=0;


	public Map<Integer, Integer> tempToReg;
	public TEMP getFreshTEMP()
	{
		return new TEMP(counter++);
	}

	public String tempToRegister(int serial){
		return String.format("$t%d", tempToReg.get(serial));
	}


	private static TEMP_FACTORY instance = null;


	protected TEMP_FACTORY() {}

	public static TEMP_FACTORY getInstance()
	{
		if (instance == null)
		{
			instance = new TEMP_FACTORY();
			instance.tempToReg = new HashMap<>();
		}
		return instance;
	}

}
