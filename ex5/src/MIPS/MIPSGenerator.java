
package MIPS;


import java.io.PrintWriter;

/*******************/
/* PROJECT IMPORTS */
/*******************/

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
		fileWriter.close();
	}

	public void printInt()
	{
		fileWriter.format("\tjal PrintInt\n");
	}
	public void printIntFunc(){
		fileWriter.format("PrintInt:\n");
		fileWriter.format("\tlw $a0, 0($sp)\n");
		fileWriter.format("\tli $v0, 1\n");
		syscall();
		fileWriter.format("\tli $a0, 32\n");
		fileWriter.format("\tli $v0, 11\n");
		syscall();
		fileWriter.format("\tjr $ra\n");
		addToStack(4);
	}
	public void syscall(){
		fileWriter.format("\taddi $sp, $sp, -4\n");
		fileWriter.format("\tsw $a3, 0($sp)\n");
		fileWriter.format("\tsyscall\n");
		fileWriter.format("\tlw $a3, 0($sp)\n");
		fileWriter.format("\taddi $sp, $sp, 4\n");

	}
	public void printString(){
		fileWriter.format("\tjal PrintString\n");
	}
	public void printStringFunc(){
		fileWriter.format("PrintString:\n");
		fileWriter.format("\tlw $a0, 0($sp)\n");
		fileWriter.format("\tli $v0, 4\n");
		syscall();
		addToStack(4);
	}

	public void addConstStringToData(String label, String data){
		fileWriter.format("\t%s: .asciiz \"%s\"\n", label, data);
	}

	public void checkForNullDeref(String objAddrReg){
		fileWriter.format("\tmove $a0, %s\n", objAddrReg);
		fileWriter.format("\tjal null_deref_check_func\n");
	}

	public void checkForNullDerefFunc(){
		fileWriter.format("null_deref_check_func:\n");
		fileWriter.format("\tbne $a0, $zero, legal_ref\n");
		fileWriter.format("\tla $a0, string_invalid_ptr_dref\n");
		fileWriter.format("\tli $v0, 4\n");
		fileWriter.format("\tsyscall\n");
		fileWriter.format("\tli $v0, 10\n");
		fileWriter.format("\tsyscall\n");
		fileWriter.format("\tlegal_ref:\n");
		fileWriter.format("\tjr $ra\n");
	}

	public void outOfBoundsCheck(String arrayAddr, String indexToAccess){
		fileWriter.format("\tmove $a0, %s\n", arrayAddr);
		fileWriter.format("\tmove $a1, %s\n", indexToAccess);
		fileWriter.format("\tjal array_access_check_func\n");


	}
	public void outOfBoundsCheckFunc(){
		fileWriter.format("array_access_check_func:\n");
		fileWriter.format("\tblt $a0, $zero, illegal_access_to_arr\n");
		fileWriter.format("\tlw $s0, 0($a0)\n");
		fileWriter.format("\tblt $a1, $s0, legal_access_to_arr\n");
		fileWriter.format("\tillegal_access_to_arr:\n");
		fileWriter.format("\tla $a0, string_access_violation\n");
		fileWriter.format("\tli $v0, 4\n");
		fileWriter.format("\tsyscall\n");
		fileWriter.format("\tli $v0, 10\n");
		fileWriter.format("\tsyscall\n");
		fileWriter.format("\tlegal_access_to_arr:\n");
		fileWriter.format("\tjr $ra\n");

	}

	public void addGlobalVar(String label, String data){
		fileWriter.format("\t%s: .word %s\n", label, data);
	}
	public void loadAddress(String reg, String label){
		fileWriter.format("\tla %s, %s\n", reg, label);
	}
	public void writeLabel(String label){
		fileWriter.format("%s:\n", label);
	}
	public void addvtableEntry(String funcName){
		fileWriter.format("\t.word %s\n", funcName);
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
	public void add(String dst, String reg1, String reg2){
		fileWriter.format("\tadd %s, %s, %s\n", dst, reg1, reg2);
		overflowCheck(dst);
	}
	public void addAddresses(String dst, String reg1, String reg2){
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
	public void addImmediate(String dst, String reg1, int immediate){
		fileWriter.format("\taddi %s, %s, %d\n", dst, reg1, immediate);
	}
	public void shiftLeft(String dst, String reg, int amount){
		fileWriter.format("\tsll %s, %s, %d\n", dst, reg, amount);
	}




	public void mul(String dst,String oprnd1,String oprnd2)
	{
		fileWriter.format("\tmul %s, %s, %s\n",dst, oprnd1, oprnd2);
		overflowCheck(dst);
	}
	public void slt(String dst,String oprnd1,String oprnd2)
	{
		fileWriter.format("\tslt %s, %s, %s\n",dst, oprnd1, oprnd2);
	}
	public void div(String dst, String oprnd1, String oprnd2)
	{
		fileWriter.format("\tmove $a0, %s\n", oprnd2);
		fileWriter.format("\tjal divide_by_zero_check_func\n", oprnd2);
		fileWriter.format("\tdiv %s, %s\n", oprnd1, oprnd2);
		fileWriter.format("\tmflo %s\n", dst);
		overflowCheck(dst);
	}
	public void divideByZeroCheckFunc(){
		fileWriter.format("divide_by_zero_check_func:\n");
		fileWriter.format("\tbne $a0, $zero, legal_div\n");
		fileWriter.format("\tla $a0, string_illegal_div_by_0\n");
		fileWriter.format("\tli $v0, 4\n");
		fileWriter.format("\tsyscall\n");
		fileWriter.format("\tli $v0, 10\n");
		fileWriter.format("\tsyscall\n");
		fileWriter.format("\tlegal_div:\n");
		fileWriter.format("\tjr $ra\n");
	}
	public void overflowCheckFunc(){
		//s0 = max
		//s1 = min
		fileWriter.format("overflow_check_func:\n");
		fileWriter.format("\tmove $v0, $a0\n");
		fileWriter.format("\tli $s0, 32767\n");
		fileWriter.format("\tli $s1, -32768\n");
		fileWriter.format("\tbge $a0, $s0, return_max_value\n");
		fileWriter.format("\tblt $a0, $s1, return_min_value\n");
		fileWriter.format("\tjr $ra\n");
		fileWriter.format("\treturn_max_value:\n");
		fileWriter.format("\tli $v0, 32767\n");
		fileWriter.format("\tjr $ra\n");
		fileWriter.format("\treturn_min_value:\n");
		fileWriter.format("\tli $s1, -32768\n");
		fileWriter.format("\tjr $ra\n");

	}
	public void overflowCheck(String resultReg){
		fileWriter.format("\tmove $a0, %s\n", resultReg);
		fileWriter.format("\tjal overflow_check_func\n");
		fileWriter.format("\tmove %s, $v0\n", resultReg);
	}

	public void sub(String dst,String oprnd1,String oprnd2)
	{
		fileWriter.format("\tsub %s, %s, %s\n",dst, oprnd1, oprnd2);
		overflowCheck(dst);
	}
	public void seq(String dst,String oprnd1,String oprnd2)
	{
		fileWriter.format("\txor %s, %s, %s\n",dst, oprnd1, oprnd2);
		fileWriter.format("\tsltiu %s, %s, 1\n",dst, dst);
	}
	public void backupRegisters(){
		fileWriter.format("\taddi $sp, $sp, -4\n");
		fileWriter.format("\tsw $a3, 0($sp)\n");
		for (int i = 0; i < 10; i++) {
			fileWriter.format("\taddi $sp, $sp, -4\n");
			fileWriter.format("\tsw $t%d, 0($sp)\n", i);
		}
	}
	public void restoreRegisters(){
		for (int i = 9; i >= 0; i--) {
			fileWriter.format("\tlw $t%d, 0($sp)\n", i);
			fileWriter.format("\taddi $sp, $sp, 4\n");
		}
		fileWriter.format("\tlw $a3, 0($sp)\n");
		fileWriter.format("\taddi $sp, $sp, 4\n");

	}

	public void jump(String inlabel)
	{
		fileWriter.format("\tj %s\n",inlabel);
	}	


	public void stringEqualityCheckFunc(){
		fileWriter.format("str_cmp_func:\n");
		fileWriter.format("\tli $v0, 1\n");
		fileWriter.format("\tstr_eq_loop:\n");
		fileWriter.format("\tlb $s2, 0($a1)\n");
		fileWriter.format("\tlb $s3, 0($a2)\n");
		fileWriter.format("\tbne $s2, $s3, neq_label\n");
		fileWriter.format("\tbeq $s2, $zero, str_eq_end\n");
		fileWriter.format("\taddi $a1, $a1 1\n");
		fileWriter.format("\taddi $a2, $a2 1\n");
		fileWriter.format("\tj str_eq_loop\n");
		fileWriter.format("\tneq_label:\n");
		fileWriter.format("\tli $v0, 0\n");
		fileWriter.format("\tstr_eq_end:\n");
		fileWriter.format("\tjr $ra\n");
	}
	public void stringEqualityCheck(String dst, String reg1, String reg2){
		fileWriter.format("\tmove $a1 %s\n", reg1);
		fileWriter.format("\tmove $a2 %s\n", reg2);
		fileWriter.format("\tjal str_cmp_func\n");
		fileWriter.format("\tmove %s $v0\n", dst);
	}
	public void stringConcat(String dst, String reg1, String reg2){
		fileWriter.format("\tmove $a1 %s\n", reg1);
		fileWriter.format("\tmove $a2 %s\n", reg2);
		fileWriter.format("\tjal concat_strings\n");
		fileWriter.format("\tmove %s $v0\n", dst);
	}
	public void stringConcatFunc(){
		//size = s1
		//ptr1 = s0
		//currChar = s2
		fileWriter.format("concat_strings:\n");
		fileWriter.format("\tmove $s0, $a1\n");
		fileWriter.format("\tli $s1, 0\n");
		fileWriter.format("\tcount_loop1:\n");
		fileWriter.format("\tlb $s2, 0($s0)\n");
		fileWriter.format("\tbeq $s2, $zero, done_count1\n");
		fileWriter.format("\taddi $s1, $s1, 1\n");
		fileWriter.format("\taddi $s0, $s0, 1\n");
		fileWriter.format("\tj count_loop1\n");
		fileWriter.format("\tdone_count1:\n");

		// Count length of second string
		fileWriter.format("\tmove $s0, $a2\n");
		fileWriter.format("\tcount_loop2:\n");
		fileWriter.format("\tlb $s2, 0($s0)\n");
		fileWriter.format("\tbeq $s2, $zero, done_count2\n");
		fileWriter.format("\taddi $s1, $s1, 1\n");
		fileWriter.format("\taddi $s0, $s0, 1\n");
		fileWriter.format("\tj count_loop2\n");
		fileWriter.format("\tdone_count2:\n");

		// Allocate memory
		fileWriter.format("\taddi $a0, $s1, 1\n");
		fileWriter.format("\tli $v0, 9\n");
		syscall();
		fileWriter.format("\tmove $s2, $v0\n");
		// newPtr = s2
		// Copy first string

		fileWriter.format("\tcopy_loop1:\n");
		fileWriter.format("\tlb $s0, 0($a1)\n");
		fileWriter.format("\tbeq $s0, $zero, done_copy1\n");
		fileWriter.format("\tsb $s0, 0($s2)\n");
		fileWriter.format("\taddi $a1, $a1, 1\n");
		fileWriter.format("\taddi $s2, $s2, 1\n");
		fileWriter.format("\tj copy_loop1\n");
		fileWriter.format("\tdone_copy1:\n");

		// Copy second string

		fileWriter.format("\tcopy_loop2:\n");
		fileWriter.format("\tlb $s0, 0($a2)\n");
		fileWriter.format("\tsb $s0, 0($s2)\n");
		fileWriter.format("\tbeq $s0, $zero, done_copy2\n");
		fileWriter.format("\taddi $a2, $a2, 1\n");
		fileWriter.format("\taddi $s2, $s2, 1\n");
		fileWriter.format("\tj copy_loop2\n");
		fileWriter.format("\tdone_copy2:\n");
		fileWriter.format("\tjr $ra\n");
	}
	public void functionPrologue(){
		fileWriter.format("\taddi $sp, $sp, -4\n");
		fileWriter.format("\tsw $ra, 0($sp)\n");
		fileWriter.format("\taddi $sp, $sp, -4\n");
		fileWriter.format("\tsw $fp, 0($sp)\n");
		fileWriter.format("\tmove $fp, $sp\n");
	}
	public void functionEpilogue(){
		fileWriter.format("\tmove $sp, $fp\n");
		fileWriter.format("\tlw $fp, 0($sp)\n");
		fileWriter.format("\tlw $ra, 4($sp)\n");
		fileWriter.format("\taddi $sp, $sp, 8\n");
		fileWriter.format("\tjr $ra\n");
	}
	public void returnValue(String returnReg){
		fileWriter.format("\tmove $v0, %s\n", returnReg);
	}


	public void beq(String reg1, String reg2,String label)
	{

		fileWriter.format("\tbeq %s, %s, %s\n", reg1, reg2,label);
	}

	public void newClassObject(String dstReg, int sizeToAlloc, boolean hasMethod, String vtableLabel){
		fileWriter.format("\tli $a0, %d\n", sizeToAlloc);
		fileWriter.format("\tli $v0,9\n");
		syscall();
		fileWriter.format("\tmove %s, $v0\n", dstReg);
		if(hasMethod){
			fileWriter.format("\tla $s0, %s\n", vtableLabel);
			fileWriter.format("\tsw $s0, 0(%s)\n", dstReg);
		}
	}
	public void newArray(String dstReg, String sizeReg){
		fileWriter.format("\tmove $s0, %s\n", sizeReg);
		shiftLeft(sizeReg, sizeReg, 2);
		addImmediate(sizeReg, sizeReg, 4);
		fileWriter.format("\tmove $a0, %s\n", sizeReg);
		fileWriter.format("\tli $v0, 9\n");
		syscall();
		fileWriter.format("\tmove %s, $v0\n", dstReg);
		fileWriter.format("\tsw $s0, 0(%s)\n", dstReg);
	}
	public void startTextSection(){
		fileWriter.format(".text\n");
		stringEqualityCheckFunc();
		stringConcatFunc();
		divideByZeroCheckFunc();
		checkForNullDerefFunc();
		outOfBoundsCheckFunc();
		overflowCheckFunc();
		printStringFunc();
		printIntFunc();
	}

	public void loadImmediate(String dstReg, String immediate){
		fileWriter.format("\tli %s, %s\n", dstReg, immediate);
	}

	

	private static MIPSGenerator instance = null;


	protected MIPSGenerator() {}

	public void setOutPutFileName(String filePath){
		try
		{

			instance.fileWriter = new PrintWriter(filePath);
			instance.fileWriter.print(".data\n");
			instance.fileWriter.print("\tstring_access_violation: .asciiz \"Access Violation\"\n");
			instance.fileWriter.print("\tstring_illegal_div_by_0: .asciiz \"Illegal Division By Zero\"\n");
			instance.fileWriter.print("\tstring_invalid_ptr_dref: .asciiz \"Invalid Pointer Dereference\"\n");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}
	public static MIPSGenerator getInstance()
	{
		if (instance == null)
		{

			instance = new MIPSGenerator();



		}
		return instance;
	}
}
