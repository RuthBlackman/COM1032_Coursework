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
		System.out.println("Start Example A.1.2\n");

		//int [] example1 = {1, 100, 200, 3};
		//memManager.parseExample(Arrays.toString(example1).replace("[", "").replace("]", ""));
		
		
		
		//String example1 = "1, 100, 200, 3";
		//memManager.parseExample(example1);
		
		//memManager.printMemory();
		//memManager.testBaseLimit();
		
		System.out.println("\n");
		
		//String example2  = "1, 100, 200, 3";
		
		//String example3 = "1, -25, -20, -1";
		
		//String example3 = "1, 50, 0";
		//memManager.parseExample(example2);
		//memManager.parseExample(example3);
		//memManager.testBaseLimit();
		//memManager.printMemory();
		
		//System.out.println("\n");
		
		
		
		//String example3 = "1, 0,1, 1";
		
		//String example3 = "1, 0, 50,0";
		//memManager.parseExample(example2);
		//memManager.testBaseLimit();
		
		//String example4 = "1, 0,0,1";
		//memManager.parseExample(example4);
		//memManager.printMemory();
		
		
		
		System.out.println("\n");
		
		//memManager.compaction();
		//memManager.printMemory();
		//memManager.testBaseLimit();
		
		/*

		
		
		String example4  = "2, 100, 200, 3";
		memManager.parseExample(Arrays.toString(example4).replace("[", "").replace("]", ""));
		
		
		System.out.println("\n");
		
		String example5  = "2, 100, 200, 3";
		memManager.parseExample(Arrays.toString(example5).replace("[", "").replace("]", ""));
		
*/
		//manager.printInputs();
		
		System.out.println("\nEnd Example A.1.2");
		
		
		
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
		
		String example2 = "1, [100; 0], [200; 1], 10"; //allocate memory for 3 segments in process 1
		memManager2.parseExample(example2);
		System.out.println("\n");
		
		String example3 = "1, [100; 0]"; //try to allocate another 100 to segment 1, process 1
		memManager2.parseExample(example3); // this example will not work because the segment is read only
		System.out.println("\n");
		
		String example4 = "1, [100; 1]"; // try again to allocate another 100 to segment 1, process 1
		memManager2.parseExample(example4); // this example will work because the read-write flag is now set to true
		System.out.println("\n");
		
		System.out.println("End Example A.2.1 read-write protection");	
		*/
		

		
		
		/*
		String example3 = "1, [10; 1], [-200; 0; 2; 5], 10";
		memManager.parseExample(example3);
		
		String example4 = "3, [100; 0], [200; 0; 2; 3]";
		memManager.parseExample(example4);
		//memManager.printMemory();
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
		
		
		String example2 = "1, [100; 0], [200; 0; 2; 3; 4], 10";
		memManager.parseExample(example2);
		
		
		System.out.println("End Example A.2.2 sharing of segments");
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
