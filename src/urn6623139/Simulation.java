package urn6623139;

import java.util.Arrays;

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
		
		String example1 = "1, 100, 200, 3";
		memManager.parseExample(example1);
		
		//memManager.printMemory();
		
		
		//System.out.println("\n");
		
		//String example2  = "2, 100, 200, 3, 300";
		
		String example2 = "1, -25, -50, -1";
		memManager.parseExample(example2);
		//memManager.printMemory();
		
		//System.out.println("\n");
		
		
		/*
		String example3 = "3, 500, 200, 3";
		memManager.parseExample(example3);
		memManager.printMemory();
		
		
		System.out.println("\n");

		
		
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
		System.out.println("");
		
		/*
		String example2 = "1, [100; 0], [200; 0; 2; 3; 4], 10";
		memManager.parseExample(example2);
		
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
		 * Example A.2.3
		 */
		
		
		/*
		 * Example A.3.1
		 */
		
		/*
		 * Example A.3.2
		 */
		
		
		/*
		 * 
		 */
		
		
		
	}
	
	
}
