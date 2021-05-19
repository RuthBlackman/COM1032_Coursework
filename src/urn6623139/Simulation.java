package urn6623139;

public class Simulation {

	
	
	public static void main(String[] args) {
		/*
		 * Initialise MemoryManagement object
		 */
		MemoryManagement memManager = new MemoryManagement(1024, 124); // 1024 bytes is the total memory, 124 is the OS space 
		
		System.out.println("---------------------------------------------------------------------------------------------------");
		System.out.println("	Memory Manager \n");
		System.out.println("	Total size: 1024");
		System.out.println("	OS size: 124");
		System.out.println("        Usable size: 900");
		System.out.println("--------------------------------------------------------------------------------------------------\n");
		
		
		
		/*
		 * Example A.1.2
		 */
		
		System.out.println("\nStart Example A.1.2\n");
		
		String example1 = "1, 100, 100, 10";
		memManager.parseExample(example1);
		System.out.println("After step 1");
		memManager.printMemory();
		
		String example2 = "2, 200";
		memManager.parseExample(example2);
		System.out.println("After step 2");
		memManager.printMemory();
		
		String example3 = "1, -40, 0,0";
		memManager.parseExample(example3);
		System.out.println("After step 3");
		memManager.printMemory();
		
		String example4 = "2, -10, 300";
		memManager.parseExample(example4);
		System.out.println("After step 4");
		memManager.printMemory();
		
		String example5 = "3, 30";
		memManager.parseExample(example5);
		System.out.println("After step 5");
		memManager.printMemory();
		
		String example6 = "4, 100";
		memManager.parseExample(example6);
		System.out.println("After step 6");
		memManager.printMemory();
		
		String example7 = "5, 20";
		memManager.parseExample(example7);
		System.out.println("After step 7");
		memManager.printMemory();
		
		String example8 = "5, -15";
		memManager.parseExample(example8);
		System.out.println("After step 8");
		memManager.printMemory();

		System.out.println("End Example A.1.2");
		System.out.println("\n\n---------------------------------------------------------------------------------------------------\n\n");
		
		
		
		/*
		 * Example A.2.1
		 */

		System.out.println("Start Code Location A.2.1 read-write protection");
		System.out.println("Filename: MemoryManagement.java");
		System.out.println("Line number: 205");
		System.out.println("Line number: 678");
		System.out.println("End Code Location A.2.1 read-write protection\n");	
		
		System.out.println("Start Example A.2.1 read-write protection\n");
		MemoryManagement memManager2 = new MemoryManagement(1024, 124);
		
		String example9 = "1, [100; 0], [200; 1], 10"; //allocate memory for 3 segments in process 1
		memManager2.parseExample(example9);
		memManager2.printReadWriteStatus();
		String example21 = "1, [100;1], 0, 0";
		memManager2.parseExample(example21);
		memManager2.printReadWriteStatus();		
		System.out.println("\nEnd Example A.2.1 read-write protection");	
		System.out.println("\n\n---------------------------------------------------------------------------------------------------");
		
		

				
		/*
		 * Example A.2.2
		 */
		
		/*
		System.out.println("\n\nStart Code Location A.2.2 sharing of segments");
		System.out.println("...filename and line number...");
		System.out.println("End Code Location A.2.2 sharing of segments");
		
		System.out.println("Start Example A.2.2 sharing of segments");
		System.out.println("...the example...");
		
		

		
		String example3 = "1, [10; 1], [-200; 0; 2; 5], 10";
		memManager.parseExample(example3);
		
		String example4 = "3, [100; 0], [200; 0; 2; 3]";
		memManager.parseExample(example4);
		//memManager.printMemory();
		
		System.out.println("End Example A.2.2 sharing of segments");
		*/
		
		/*
		String example9 = "1, [100; 0; 2]";
		memManager.parseExample(example9);
		String example10 = "2, [100; 0; 1]";
		memManager.parseExample(example10);
		System.out.println("\n");
		String example11 = "1, [100;1]";
		memManager.parseExample(example11);
		*/
		
		/*
		String example9 = "1, [100;0], [200;0;2;3;4], 10";
		//String example9 = "1, 100, 200";
		memManager.parseExample(example9);
		//memManager.printReadWriteStatus();
		System.out.println("\n");
		//String example10 = "1, [-10;1], [100;0]";
		String example10 = "1, 10, [100;0;2;5], 10";
		//String example10 = "1, [-10;1], [100;0;2;5]";
		memManager.parseExample(example10);
		
		//String example11 = "2, 10";
		//memManager.parseExample(example11);
		
		memManager.printReadWriteStatus();
		
		memManager.printSharedSegments();
		*/
		
		/*
		 * Example A.2.3
		 */
		
		/*
		System.out.println("Start Example A.2.3");
		System.out.println("End Example A.2.3");
		*/
		
		/*
		 * Example A.3.1
		 */
		
		System.out.println("Start Code Location A.3.1 TLB");
		System.out.println("Filename: MemoryManagement.java");
		System.out.println("Line number: 722 ");
		System.out.println("Line number: 740 ");
		System.out.println("End Code Location A.3.1 TLB\n");
		
		System.out.println("Start Example A.3.1 TLB working");
		MemoryManagement memManager3 = new MemoryManagement(1024, 124);
		String example10 = "1, [10; 1], [200; 0; 2; 5], 10";
		memManager3.parseExample(example10);
		memManager3.printMemory();
		memManager3.printTLBWorking();
		System.out.println("End Example A.3.1 TLB working\n");
		
		System.out.println("Start Example A.3.1 TLB miss");
		memManager3.printTLBMiss();
		System.out.println("End Example A.3.1 TLB miss");
		
		System.out.println("\n\n---------------------------------------------------------------------------------------------------\n\n");

		
		/*
		 * Example A.3.2
		 */
		
		System.out.println("Start Code Location A.3.2 Compaction");
		System.out.println("Filename: MemoryManagement.java");
		System.out.println("Line number: 573");
		System.out.println("End Code Location A.3.2 Compaction\n");
		
		System.out.println("Start Example A.3.2 Compaction");
		MemoryManagement memManager4 = new MemoryManagement(1024, 124);
		String example11  = "1, 100, 200, 3";
		memManager4.parseExample(example11);
		String example12 = "1, -25, -50, -1";
		memManager4.parseExample(example12);
		System.out.println("\nBefore compaction");
		memManager4.printMemory();
		memManager4.compaction();
		System.out.println("After compaction");
		memManager4.printMemory();
		System.out.println("End Example A.3.2 Compaction");
		
		System.out.println("\n\n---------------------------------------------------------------------------------------------------");
	}
	
	
}
