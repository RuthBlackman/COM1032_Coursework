package urn6623139;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemoryManagement {

    //size of memory
    public static int TOTAL_BYTES; // this is the total space in the main memory
	private final int os_size; // this is the space 
	public static int user_space; // this is the space that can be used for the segments
	
	private SegmentTable segmentTable;
	private Map<Process, Segment> processesInMemory;
	
	
	private Node start;
	private Node end; 
	private Node previous;

	
	public MemoryManagement(int total_bytes, int os_size) {
		MemoryManagement.TOTAL_BYTES = total_bytes;
		this.os_size = os_size;  
		MemoryManagement.user_space = total_bytes - os_size;
		
		segmentTable = new SegmentTable();

		this.processesInMemory = new HashMap<Process, Segment>();

		this.initaliseLinkedList();
		
	}

	public int getTOTAL_BYTES() {
		return TOTAL_BYTES;
	}

	public int getOs_size() {
		return os_size;
	}

	public int getUser_space() {
		return user_space;
	}
	
	
	
	
	
	public void parseExample (String example){

		int segment_number = 0;
		Process proc;
		int numBytes = 0;
		
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
		
		//create a new process using the first value in comp, and add to mem_processes	
		proc = new Process(Integer.parseInt(comp[0].toString().replace("[" ,"").replace("]", "").replace(",", ";")));

		System.out.println("Process: " + proc.getReference_number());
		

		
		//TODO: remove the print
		for (int i =1; i<size; i++){
			String string = comp[i].toString().replace("[" ,"").replace("]", "").replace(",", ";");
			int numSemiColons = string.split("\\;", -1).length-1;			
		    StringBuffer buff = new StringBuffer("");
			
			
			if(numSemiColons > 0) { //if the input is like: String example2 = "1, [100; 0], [200; 0; 2; 3]";
				buff.append("Segment: " + i + ", Size: " + string.substring(0, string.indexOf(";")) + ", R/W Permissions: ");
				numBytes = Integer.parseInt(string.substring(0, string.indexOf(";")));
				
				int numFound = 0;
	
				if(numSemiColons == 1) {
					buff.append(string.charAt(string.length()-1));
				}
				else {
					for(int k = 0; k < string.length(); k++) {
						if(string.charAt(k) == ';'){
							numFound++;
							if(numFound == 2) {
								
								buff.append(string.charAt(k-1));
								buff.append(", Shared with process IDs: ");
							}
							if(numFound > 2) {
								buff.append(string.charAt(k-1)+ ", ");
							}
						}
						
					}
					buff.append(string.charAt(string.length()-1));
				}
			}else { //if the input is like: int [] example1 = {1, 100, 200, 3};
				buff.append("Segment: "+ i + ", Size: " + comp[i].toString().replace("[", "").replace("]", ""));
				numBytes = Integer.parseInt(comp[i].toString().replace("[", "").replace("]", ""));
			}
			
			System.out.println(buff.toString() + "");
			
			segment_number = i;


		//System.out.println("seg num: " + segment_number);
		//System.out.println("Limit: " + numBytes );
		//System.out.println("Process: " + proc.getReference_number());
		
		//int process_number = proc.getReference_number();

		Segment segment = new Segment(proc, numBytes);
		proc.addProcessToSegment(segment);
		
		if(!processesInMemory.containsKey(proc)) {
			processesInMemory.put(proc, segment);
		}
		
		
		this.allocateMemory(segment);
		System.out.println("\n");
		
		//this.deallocateMemory(segment, 20);
		this.printMemory();
		}
	}
	
	
	public void initaliseLinkedList() {
    	Node startNode = new Node(null, null, false);
    	
    	this.start = startNode;
    	this.previous = null;
    	startNode.setBase(0);
    	startNode.setLimit(this.getUser_space());
        this.end = null;
        
	}
	

	public void allocateMemory(Segment segment) {
		if(this.segmentAlreadyUsed(segment)) {
			System.out.println("Segment in linked list");
		}else {
			System.out.println("Segment not already in linked list");
			boolean placed = this.firstFitInsert(segment);
			if(!placed) {
				System.out.println("fail");
			}else {
				System.out.println("Success");
			}
		}
	}
	
    public int getIndexOfSegment(Segment segment) {
    	int index = 0;
    	for(Segment s : segment.getProcess().getListSegments()) {
    		if(s == segment) {
    			return index;
    		}
    		index++;
    	}
    	return -1;
    }
	
	
    public boolean segmentAlreadyUsed(Segment segment) {
    	Node temp = start;
    	while(temp != null) {
    		if(temp.isAllocated()) {
    			if(this.getIndexOfSegment(temp.getSegment()) == this.getIndexOfSegment(segment)) {
    			//if(temp.getSegment().getSegmentNumber() == segment.getSegmentNumber()) {
        			return true;
        		}
    		}
    		temp = temp.getNext();
    	}
    	
    	return false;
    }
    
    
    public boolean firstFitInsert(Segment segment) {
        Node newNode = new Node(null, null, true);
        newNode.setSegment(segment);
        Node temp = start;

        if(!start.isAllocated()){ // if the memory only contains one hole - no segments yet
            start = newNode;
            end = temp;
            previous = null;
            
            start.setNext(end);
            start.setBase(0);
            start.setLimit(newNode.getSegment().getLimit());
            
            end.setBase(start.getBase());
            end.setLimit(end.getLimit() - start.getLimit());
            //System.out.println("Memory successfully allocated to segment "+ segment.getSegmentNumber() + " for process " + segment.getProcessNumber());
            System.out.println("Memory successfully allocated to segment " + (this.getIndexOfSegment(segment) + 1) + " for process " + segment.getProcess().getReference_number());
            newNode.getSegment().setBase(0);
            System.out.println("Base: " + 0 + ", Limit: " + newNode.getSegment().getLimit());
            //this.printMemory();
            return true;
        }else{ // if the memory contains holes and segments
        	Node curr = start;
        	while(curr != null) {
        		if(!curr.isAllocated()) { // if the currents node is a hole
        			if(curr.getLimit() < segment.getLimit() ) { // if the size of the hole is less than the size of the segment
        				curr.getPrevious().setNext(newNode);
        				newNode.setNext(curr);
        				newNode.setPrevious(curr.getPrevious());
        				curr.setPrevious(newNode);
        				
        				newNode.setBase(newNode.getPrevious().getLimit());
        				newNode.setLimit(segment.getLimit());
        				curr.setBase(newNode.getLimit());
        				curr.setLimit(curr.getLimit() - newNode.getLimit());
        				return true;
        			}
        		}
        	
        		curr = curr.getNext();
        	}
        	
        	return false;
        }
        
        
        
    }
    
    public void deallocateMemory(Segment segment, int size) {
		if(!this.segmentAlreadyUsed(segment)) {
			//throw new IllegalArgumentException("Segment not in main memory");
			System.out.println("Segment not in main memory");
		}else {
			Node temp = start;
			while(temp != null) {
				if(temp.isAllocated()) {
					//if(temp.getSegment().getSegmentNumber() == segment.getSegmentNumber()) {
					if(this.getIndexOfSegment(temp.getSegment()) == this.getIndexOfSegment(segment)) {
						segment.setLimit(segment.getLimit()-size);
						temp.setLimit(segment.getLimit());
						if(segment.getLimit() == 0) { // if the size of the segment is now 0, remove it from the LL
							System.out.println("Segment has a size of 0");
						}else { // else, create a hole with the size of memory removed, and set that to be the next node
							Node newNext = new Node(temp.getNext(), temp, false);
							temp.setNext(newNext);
							newNext.setLimit(size);
							newNext.setBase(segment.getLimit());
							System.out.println(size + " bytes successfully deallocated from memory");
							//System.out.println("Segment " + segment.getSegmentNumber() + " Base: " + segment.getBase() + " Limit: " + segment.getLimit());
							System.out.println("Segment " + this.getIndexOfSegment(segment) + " Base: " + segment.getBase() + " Limit: " + segment.getLimit());
							System.out.println("Next node (hole): Base:" + newNext.getBase() + " Limit: " + newNext.getLimit() );
						}
					}
				}
				temp = temp.getNext();
			}
		}
    }
    
    public void printMemory() {
    	Node temp = this.start;
    	StringBuffer memory = new StringBuffer("\n---------------------------------------------------------------------------------------------------\n");
    	memory.append("\tMain Memory\n");
    	memory.append("---------------------------------------------------------------------------------------------------\n");
    	String A = "";
    	while(temp != null) {
    		if(temp.isAllocated()) {
    			A = "A";
    		}else {
    			A = "H";
    		}
    		memory.append("[" + A + temp.getLimit() +"] ");
    		temp = temp.getNext();
    	}
    	memory.append("\n");
    	System.out.println(memory.toString());
    }
	
    
	
}
