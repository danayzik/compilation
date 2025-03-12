
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
	public void printString(String argReg){
		fileWriter.format("\tmove $a0, %s\n",argReg);
		fileWriter.format("\tli $v0,4\n");
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
	public void saveReturn(String dstReg){
		fileWriter.format("\tmove %s, $v0\n", dstReg);
	}
	public void jalr(String reg){
		fileWriter.format("\tjalr %s\n", reg);
	}
	public void jal(String label){
		fileWriter.format("\tjal %s\n", label);
	}
	public void addImmediate(String dst, String reg1, int immediate){//Add overflow check
		fileWriter.format("\taddi %s, %s, %d\n", dst, reg1, immediate);
	}
	public void shiftLeft(String dst, String reg, int amount){
		fileWriter.format("\tsll %s, %s, %d\n", dst, reg, amount);
	}
	public void allocate(String var_name)
	{
		fileWriter.format(".data\n");
		fileWriter.format("\tglobal_%s: .word 721\n",var_name);
	}



	public void mul(String dst,String oprnd1,String oprnd2)
	{
		fileWriter.format("\tmul %s, %s, %s\n",dst, oprnd1, oprnd2);
	}
	public void sub(String dst,String oprnd1,String oprnd2)//Add overflow checks
	{
		fileWriter.format("\tsub %s, %s, %s\n",dst, oprnd1, oprnd2);
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
	public void beq(String reg1, String reg2,String label)
	{

		fileWriter.format("\tbeq %s, %s, %s\n", reg1, reg2,label);
	}
	public void beqz(TEMP oprnd1,String label)
	{
		int i1 =oprnd1.getSerialNumber();
				
		fileWriter.format("\tbeq Temp_%d,$zero,%s\n",i1,label);				
	}
	public void newClassObject(String dstReg, int sizeToAlloc, boolean hasMethod, String vtableLabel){
		fileWriter.format("\tli $a0, %d\n", sizeToAlloc);
		fileWriter.format("\tli $v0,9\n");
		fileWriter.format("\tsyscall\n");
		fileWriter.format("\tmove %s, $v0\n", dstReg);
		if(hasMethod){
			fileWriter.format("\tla $s0, %s\n", vtableLabel);
			fileWriter.format("\tsw $s0, 0(%s)\n", dstReg);
		}
	}
	public void newArray(String dstReg, String bytesReg){//Add expression is 0 check? if so then check if it's 4
		fileWriter.format("\tmove $a0, %s\n", bytesReg);
		fileWriter.format("\tli $v0, 9\n");
		fileWriter.format("\tsyscall\n");
		fileWriter.format("\tmove %s, $v0\n", dstReg);
	}

	public void loadImmediate(String dstReg, String immediate){
		fileWriter.format("\tli %s, %s\n", dstReg, immediate);
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
