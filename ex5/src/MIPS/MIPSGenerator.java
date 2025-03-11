
package MIPS;


import java.io.PrintWriter;

/*******************/
/* PROJECT IMPORTS */
/*******************/
import TEMP.*;

public class MIPSGenerator
{
	private int WORD_SIZE=4;
	/***********************/
	/* The file writer ... */
	/***********************/
	private PrintWriter fileWriter;

	/***********************/
	/* The file writer ... */
	/***********************/
	public void finalizeFile()
	{
		fileWriter.print("\tli $v0,10\n");
		fileWriter.print("\tsyscall\n");
		fileWriter.close();
	}
	public void print_int(String argReg)
	{
		fileWriter.format("\tmove $a0, %s\n",argReg);
		fileWriter.format("\tli $v0,1\n");
		fileWriter.format("\tsyscall\n");
		fileWriter.format("\tli $a0,32\n");
		fileWriter.format("\tli $v0,11\n");
		fileWriter.format("\tsyscall\n");
	}
	//public TEMP addressLocalVar(int serialLocalVarNum)
	//{
	//	TEMP t  = TEMP_FACTORY.getInstance().getFreshTEMP();
	//	int idx = t.getSerialNumber();
	//
	//	fileWriter.format("\taddi Temp_%d,$fp,%d\n",idx,-serialLocalVarNum*WORD_SIZE);
	//	
	//	return t;
	//}
	public void addConstStringToData(String label, String data){
		fileWriter.format("\t%s: .asciiz \"%s\"\n", label, data);
	}

	public void addGlobalVar(String label, String data){
		fileWriter.format("\t%s: .word \"%s\"\n", label, data);
	}
	public void loadAddress(String reg, String label){
		fileWriter.format("\tla: %s, %s\n", reg, label);
	}
	public void writeLabel(String label){
		fileWriter.format("%s:\n", label);
	}
	public void addvtableEntry(String funcName){
		fileWriter.format(".word %s\n", funcName);
	}
	public void storeToAddress(String srcReg, String address){
		fileWriter.format("\tsw %s, %s\n", srcReg, address);
	}
	public void loadFromAddress(String dstReg, String address){
		fileWriter.format("\tlw %s, %s\n", dstReg, address);
	}
	public void addToStack(int offset){
		fileWriter.format("\taddi $sp, $sp, %d\n", offset);
	}
	public void add(String dst, String reg1, String reg2){//Add overflow check
		fileWriter.format("\tadd %s, %s, %s\n", dst, reg1, reg2);
	}
	public void addImmediate(String dst, String reg1, String immediate){//Add overflow check
		fileWriter.format("\taddi %s, %s, %s\n", dst, reg1, immediate);
	}
	public void allocate(String var_name)
	{
		fileWriter.format(".data\n");
		fileWriter.format("\tglobal_%s: .word 721\n",var_name);
	}
	public void load(TEMP dst,String var_name)
	{
		int idxdst=dst.getSerialNumber();
		fileWriter.format("\tlw Temp_%d,global_%s\n",idxdst,var_name);
	}
	public void store(String var_name,TEMP src)
	{
		int idxsrc=src.getSerialNumber();
		fileWriter.format("\tsw Temp_%d,global_%s\n",idxsrc,var_name);		
	}
	public void li(TEMP t,int value)
	{
		int idx=t.getSerialNumber();
		fileWriter.format("\tli Temp_%d,%d\n",idx,value);
	}
	public void add(TEMP dst,TEMP oprnd1,TEMP oprnd2)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		int dstidx=dst.getSerialNumber();

		fileWriter.format("\tadd Temp_%d,Temp_%d,Temp_%d\n",dstidx,i1,i2);
	}
	public void mul(TEMP dst,TEMP oprnd1,TEMP oprnd2)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		int dstidx=dst.getSerialNumber();

		fileWriter.format("\tmul Temp_%d,Temp_%d,Temp_%d\n",dstidx,i1,i2);
	}

	public void jump(String inlabel)
	{
		fileWriter.format("\tj %s\n",inlabel);
	}	
	public void blt(TEMP oprnd1,TEMP oprnd2,String label)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		
		fileWriter.format("\tblt Temp_%d,Temp_%d,%s\n",i1,i2,label);				
	}
	public void bge(TEMP oprnd1,TEMP oprnd2,String label)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		
		fileWriter.format("\tbge Temp_%d,Temp_%d,%s\n",i1,i2,label);				
	}
	public void bne(TEMP oprnd1,TEMP oprnd2,String label)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		
		fileWriter.format("\tbne Temp_%d,Temp_%d,%s\n",i1,i2,label);				
	}
	public void beq(TEMP oprnd1,TEMP oprnd2,String label)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		
		fileWriter.format("\tbeq Temp_%d,Temp_%d,%s\n",i1,i2,label);				
	}
	public void beqz(TEMP oprnd1,String label)
	{
		int i1 =oprnd1.getSerialNumber();
				
		fileWriter.format("\tbeq Temp_%d,$zero,%s\n",i1,label);				
	}
	

	private static MIPSGenerator instance = null;


	protected MIPSGenerator() {}


	public static MIPSGenerator getInstance()
	{
		if (instance == null)
		{

			instance = new MIPSGenerator();

			try
			{

				String dirname="./output/";
				String filename=String.format("MIPS.txt");


				instance.fileWriter = new PrintWriter(dirname+filename);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}


			instance.fileWriter.print(".data\n");
			instance.fileWriter.print("string_access_violation: .asciiz \"Access Violation\"\n");
			instance.fileWriter.print("string_illegal_div_by_0: .asciiz \"Illegal Division By Zero\"\n");
			instance.fileWriter.print("string_invalid_ptr_dref: .asciiz \"Invalid Pointer Dereference\"\n");
		}
		return instance;
	}
}
