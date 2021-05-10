package urn6623139;
import java.util.ArrayList;
import java.util.Arrays;

public class Test {


	public static void parseExample (String example){
		
		int size = example.split(",").length;
		
		//process id, followed by segments
		ArrayList<Integer>[] comp = new ArrayList[size];
		
		//init all
		for (int i =0; i<size; i++){
			comp[i] = new ArrayList<Integer>();
		}
		
		//TODO: add exceptions as you see fit
		
	
		int index = 0;
		
		//split into pieces 
		for (String part:example.split(",")) {
			//remove space
			part = part.replace(" ", "");	
			
			
			//see if options are declared by looking at [ and ]
			if (part.contains("[") && part.contains("]")){
				part = part.replace(" ", "").replace("[", "").replace("]", "");
				
				//split into options: size, read-write bit, shared list
				for (String split:part.split(";")) {
					comp[index].add(Integer.parseInt(split));
				}
			}else {
				//no options presented
				comp[index].add(Integer.parseInt(part));
			}
			index++;
		}
		
		//TODO: remove the print
		for (int i =0; i<size; i++){
			System.out.println(comp[i].toString());
		}
		
		//TODO: update return of function as you see fit
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int [] example1 = {1, 100, 200, 3};
		//FIXME: be careful with using this version
		System.out.println("Parse Example 1");
		//System.out.println(Arrays.toString(example1).replace("[", "").replace("]", ""));
		parseExample(Arrays.toString(example1).replace("[", "").replace("]", ""));
		
		//make sure there is no ; at the end
		//DO NOTICE the use of , and ; 
		//the character ; is used inside of a segment
		//the character , is used to separate segments
		String example2 = "1, [100; 0], [200; 0; 2; 3]";
		System.out.println("Parse Example 1");
		parseExample(example2);

	}

}
