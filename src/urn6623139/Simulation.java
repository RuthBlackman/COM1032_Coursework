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
		
		/*
		System.out.println("Start Example A.1.2\n");
		
		String example1 = "1, 100, 100, 10";
		memManager.parseExample(example1);
		
		String example2 = "2, 200";
		memManager.parseExample(example2);
		
		String example3 = "1, -40, 0,0";
		memManager.parseExample(example3);
		
		String example4 = "2, -10, 300";
		memManager.parseExample(example4);
		
		String example5 = "3, 180, 60";
		memManager.parseExample(example5);
		
		System.out.println("\nEnd Example A.1.2");
		*/
		
		
		/*
		 * Example A.2.1
		 */

		/*
		System.out.println("Start Code Location A.2.1 read-write protection");
		System.out.println("...filename and line number...");
		System.out.println("Filename: MemoryManagement.java");
		System.out.println("Line number: ");
		System.out.println("End Code Location A.2.1 read-write protection");	
		
		System.out.println("Start Example A.2.1 read-write protection");
		MemoryManagement memManager2 = new MemoryManagement(1024, 124);
		
		String example6 = "1, [100; 0], [200; 1], 10"; //allocate memory for 3 segments in process 1
		memManager2.parseExample(example6);
		System.out.println("\n");
		memManager2.printReadWriteStatus();
		
		System.out.println("End Example A.2.1 read-write protection");	
		*/
		

				
		/*
		 * Example A.2.2
		 */
		
		/*
		System.out.println("Start Code Location A.2.2 sharing of segments");
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
		 * Example A.2.3
		 */
		
		/*
		System.out.println("Start Example A.2.3");
		System.out.println("End Example A.2.3");
		*/
		
		/*
		 * Example A.3.1
		 */
		
		/*
		System.out.println("Start Code Location A.3.1 TLB");
		System.out.println("...filename and line number...");
		System.out.println("Filename: MemoryManagement.java");
		System.out.println("Line number: ");
		System.out.println("End Code Location A.3.1 TLB");
		
		System.out.println("Start Example A.3.1 TLB working");
		System.out.println("...the example...");
		
		memManager.
		System.out.println("End Example A.3.1 TLB");
		
		System.out.println("Start Example A.3.1 TLB miss");
		
		System.out.println("...the example...");
		System.out.println("End Example A.3.1 TLB miss");
		*/
		
		/*
		 * Example A.3.2
		 */
		
		/*
		System.out.println("Start Code Location A.3.2 Compaction");
		//System.out.println("...filename and line number...");
		System.out.println("Filename: MemoryManagement.java");
		System.out.println("Line number: 539");
		System.out.println("End Code Location A.3.2 Compaction\n");
		
		System.out.println("Start Example A.3.2 Compaction");
		//System.out.println("...the example...");
		String example4  = "1, 100, 200, 3";
		memManager.parseExample(example4);
		String example5 = "1, -25, -50, -1";
		memManager.parseExample(example5);
		System.out.println("Before compaction");
		memManager.printMemory();
		memManager.compaction();
		System.out.println("After compaction");
		memManager.printMemory();
		System.out.println("End Example A.3.2 Compaction");
		*/	
	}
	
	
}
