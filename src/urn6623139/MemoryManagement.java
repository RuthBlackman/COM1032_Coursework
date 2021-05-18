package urn6623139;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;




/*
 * Class the manages the allocation/deallocation of segments to the physical memory
 */
public class MemoryManagement {

	/*
	 * private attributes
	 */
    public static int TOTAL_BYTES; // this is the total space in the main memory
	private final int os_size; // this is the space 
	public static int user_space; // this is the space that can be used for the segments
	
	private Map<Process, List<Segment> > processesInMemory;
	

	
	private Node start;
	private Node end; 
	private Node previous;

	
	private TLB tlb;
	
	/*
	 * Constructor for MemoryManagement
	 * @param total_bytes
	 * 			An integer representing the total amount of space in the physical memory
	 * @param os_size
	 * 			An integer representing the amount of space the OS takes up
	 * 
	 */
	public MemoryManagement(int total_bytes, int os_size) {
		MemoryManagement.TOTAL_BYTES = total_bytes;
		this.os_size = os_size;  
		MemoryManagement.user_space = total_bytes - os_size;

		this.processesInMemory = new HashMap<Process, List<Segment> >();

		this.tlb = new TLB(user_space, this);
		
		this.initaliseLinkedList();
		
	}

	public Node getStart() {
		return this.start;
	}
	
	
	/*
	 * Method to get the total size of the physical memory
	 * 
	 * @return total_bytes
	 */
	public int getTOTAL_BYTES() {
		return TOTAL_BYTES;
	}

	
	/*
	 * Method to get the size of the OS
	 * 
	 *  @return os_size
	 */
	public int getOs_size() {
		return os_size;
	}

	
	/*
	 * Method to get the total usable space - used for allocating segments
	 * 
	 * @return user_space
	 */
	public int getUser_space() {
		return user_space;
	}
	
	
	
	
	
	public void parseExample (String example){

		int segment_number = 0;
		Process proc;
		int numBytes = 0;
		int readWritePermissions = 1; // assume it is 1 unless the segment is shared or it specifies 0
		
		
		
		
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
			List<Integer> segmentSharedWith =new ArrayList<Integer>();
			
			String string = comp[i].toString().replace("[" ,"").replace("]", "").replace(",", ";");
			int numSemiColons = string.split("\\;", -1).length-1;			
		    StringBuffer buff = new StringBuffer("");
			
			
			if(numSemiColons > 0) { //if the input is like: String example2 = "1, [100; 0], [200; 0; 2; 3]";
				buff.append("Segment: " + i + ", Size: " + string.substring(0, string.indexOf(";")) + ", R/W Permissions: ");
				numBytes = Integer.parseInt(string.substring(0, string.indexOf(";")));
				
				int numFound = 0;
	
				if(numSemiColons == 1) {
					buff.append(string.charAt(string.length()-1));
					
					readWritePermissions  = Integer.parseInt(String.valueOf(string.charAt(string.length()-1)));
					System.out.println("read write permissions: " + readWritePermissions);
					
				}
				else {
					for(int k = 0; k < string.length(); k++) {
						if(string.charAt(k) == ';'){
							numFound++;
							if(numFound == 2) {
								
								buff.append(string.charAt(k-1));
								readWritePermissions  = Integer.parseInt(String.valueOf(string.charAt(k-1)));
								buff.append(", Shared with process IDs: ");
							}
							if(numFound > 2) {
								buff.append(string.charAt(k-1)+ ", ");
								segmentSharedWith.add(Integer.parseInt(String.valueOf(string.charAt(k-1))));
							}
							
						}
						
					}
					buff.append(string.charAt(string.length()-1));
					
					//if there are at least 2 semi colons, then the last digit in the input is a shared process
					//therefore, add the last character in the input to the segmentSharedWith ArrayList
					//without this if statement, the last process in the input would not be added to the ArrayList
					if(numSemiColons >= 2) { 
						segmentSharedWith.add(Integer.parseInt(String.valueOf(string.charAt(string.length()-1))));
					}
				}
			}else { //if the input is like: int [] example1 = {1, 100, 200, 3};
				buff.append("Segment: "+ i + ", Size: " + comp[i].toString().replace("[", "").replace("]", ""));
				numBytes = Integer.parseInt(comp[i].toString().replace("[", "").replace("]", ""));
				readWritePermissions = 1;
				System.out.println("read write permissions: " + readWritePermissions);
			}
			
			System.out.println(buff.toString() + "");
			
			
			segment_number = i;
			
			


		//System.out.println("seg num: " + segment_number);
		//System.out.println("Limit: " + numBytes );
		//System.out.println("Process: " + proc.getReference_number());
		
		//int process_number = proc.getReference_number();

			/*
			System.out.println("\nPROCESSES AND SEGMENTS IN MEMORY");
			for(Map.Entry<Process,List<Segment> > entry : this.processesInMemory.entrySet()) {
				for(Segment s : entry.getValue()) {
					System.out.println("Process " + entry.getKey().getReference_number() + " Segment " + (this.getIndexOfSegment(s)+1));
				}
				//System.out.println("Process: " + entry.getKey().getReference_number() + " Segment" + (this.getIndexOfSegment(entry.getValue())+1));
			}
			System.out.println("Size: " + processesInMemory.size() +"\n");
			*/
			
		Segment segment = new Segment(proc, numBytes);
		segment.setReadWriteFlag(readWritePermissions);
		Segment a = null;
		System.out.println(segment.getReadWriteFlag());
		
		Boolean processAlreadyInMemory = false;
		Boolean segmentForProcessAlreadyInMemory = false;
		for(Map.Entry<Process,List<Segment> > entry : this.processesInMemory.entrySet()) {
			if(entry.getKey().getReference_number() == proc.getReference_number()) {
				processAlreadyInMemory = true;
				for(Segment s : entry.getValue()) {
					if((this.getIndexOfSegment(s)) == (segment_number-1)) { //segment 1 is stored at index 0 
						//System.out.println("segment already in memory");
						segmentForProcessAlreadyInMemory = true;
						System.out.println(s.getReadWriteFlag());
						a = s;
						a.setReadWriteFlag(readWritePermissions);
						System.out.println(a.getReadWriteFlag());
						//System.out.println("a: "+ (this.getIndexOfSegment(a)+1) + " base: " + a.getBase() + " limit: " + a.getLimit());
					}
				}
			}
		}
		
		
		if(processAlreadyInMemory == false) {
			//System.out.println("Process not already in memory");
			//System.out.println("Putting process into hash map");
			proc.addProcessToSegment(segment);
			processesInMemory.put(proc, proc.getListSegments());
			
		}else { // process is already in memory, so the segment may be too
			//System.out.println("Process in memory");
			if(segmentForProcessAlreadyInMemory == false) { // segment is not in memory for that process
				//System.out.println("Segment not in memory for that process");
				proc.addProcessToSegment(segment);
				processesInMemory.put(proc, proc.getListSegments());
				
			}else { // segment is in memory for that process
				System.out.println("Segment already in memory for that process");
				//segment.setReadWriteFlag(a.getReadWriteFlag());
				System.out.println("OLD: " + a.getSharedProcesses());
				segment = a;
				segment.setReadWriteFlag(readWritePermissions);
				segment.emptySharedList();
				segment.addProcessToSharedList(segmentSharedWith);
				System.out.println("NEW: "+ segment.getSharedProcesses());
				
		//		System.out.println("segment r/w: " + a.getReadWriteFlag());
		//		if(this.checkReadWrite(segment) == false) { return; }
				
				//System.out.println("segment: base: " + segment.getBase() + " limit: " + segment.getLimit());
			}
		}
		
		segment.setSegmentID(this.getSegmentID(segment));
		segment.addProcessToSharedList(segmentSharedWith);
		
		if(segment.hasSharedList()) {
			this.sharedSegmentReadOnly(segment);
			//if(segment.getReadWriteFlag()) {
			//	System.out.println(segment.getReadWriteFlag());
			//	throw new IllegalArgumentException("Shared segment must be read-only!");
			//}
		}
		
		if(this.checkIfSharedSegmentInMemory(segment)) {
			System.out.println("shared segment exists");
			break;
		}
		
		
		if(numBytes < 0) {
			numBytes *= -1;
			System.out.println("Deallocating: " + numBytes);
			this.deallocateMemory(segment, numBytes);
		}else if (numBytes > 0) {
			System.out.println("Allocating: " + numBytes);
			this.allocateMemory(segment, numBytes);
		}
		
		System.out.println("\n");
		this.printMemory();
		}
	}
	
	
	/*
	 * Method used to initalise the liked list (physical memory)
	 * It adds a node (hole) to the memory, sets the start and nodes to be the hole
	 */
	public void initaliseLinkedList() {
    	Node startNode = new Node(null, null, false);
    	
    	this.start = startNode;
    	this.previous = null;
    	startNode.setBase(0);
    	startNode.setLimit(this.getUser_space());
        this.end = startNode;
        this.start.setAllocation(false);
        
	}
	

	public void allocateMemory(Segment segment, int numBytes) {
		if(this.segmentAlreadyUsed(segment)) {
			//System.out.println("Segment in linked list");
			if(!this.firstFitInsert(segment, numBytes)) {
				System.out.println("Failure: memory not allocated!");
				throw new RuntimeException("Error: not enough space!");
				
			}else {
				System.out.println("Memory successfully allocated!");
			}
		}else {
			//System.out.println("Segment not already in linked list");
			if(!this.firstFitInsert(segment, numBytes)) {
				System.out.println("Failure: memory not allocated!");
				throw new RuntimeException("Error: not enough space!");
			}else {
				System.out.println("Memory successfully allocated!");
				this.removeSizeZeroSegments();
				
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
	
    public int getSegmentID(Segment segment) {
    	int index = 0;
    	for(Segment s : segment.getProcess().getListSegments()) {
    		if(s == segment) {
    			return index +1;
    		}
    		index++;
    	}
    	return -1;
    }
	
    public boolean segmentAlreadyUsed(Segment segment) {
    	Node temp = start;
    	while(temp != null) {
    		if(temp.isAllocated()) {
    			if((this.getIndexOfSegment(temp.getSegment())+1) == (this.getIndexOfSegment(segment)+1) && temp.getSegment().getProcess() == segment.getProcess()) {
        			return true;
        		}
    		}
    		temp = temp.getNext();
    	}
    	
    	return false;
    }
    
    
    public boolean firstFitInsert(Segment segment, int numBytes) {
        Node newNode = new Node(null, null, true);
        newNode.setSegment(segment);
        Node temp = start;
        
        if(!start.isAllocated() && (this.numSegmentNodes() ==0) ){ // if the memory only contains one hole - no segments yet
            System.out.println("No segments in memory already - adding segment to the start");
        	start = newNode;
            end = temp;
            previous = null;
            
            start.setNext(end);
            start.setBase(0);
            start.setLimit(newNode.getSegment().getLimit());
            
            end.setBase(start.getLimit() +start.getBase());
            end.setLimit(end.getLimit() - start.getLimit());
           
            end.setPrevious(start);

            return true;
        }else{ // if the memory contains holes and segments
        	//System.out.println("Other segment/s already in memory");
        	if(this.segmentAlreadyUsed(segment)) { // if segment is already in main memory
            	//System.out.println("Segment already in memory");
        		Node curr = start;
            	while(curr != null) {
            		//if(curr.isAllocated() == true && curr.getSegment() == segment) {
            		if((curr.isAllocated() == true) && (this.getIndexOfSegment(curr.getSegment())== this.getIndexOfSegment(segment))) {
            			//System.out.println("found segment in memory");
            			if(curr.getNext().isAllocated() == false) { // if the node after the current node (where the segment is) is a hole
            				System.out.println("node after segment is a hole");
            				
            				if(curr.getNext().getLimit() >= numBytes) { // if hole >= amount of memory to allocate
            					System.out.println("hole >= amount of memory to allocate");
            					curr.setLimit(curr.getLimit()+numBytes);
            					curr.getNext().setBase(curr.getSegment().getBase() + curr.getSegment().getLimit());
            					curr.getNext().setLimit(curr.getNext().getLimit() -  numBytes);
            					curr.setSegment(segment);
            					return true;
            				}else { // if hole < segment, then remove segment and allocate it in another location		//need to adapt for when the segment can't be reallocated because of space issues
            					System.out.println("hole < amount of memory to allocate");
            					temp.setBase(curr.getSegment().getBase() + curr.getSegment().getLimit());
            					temp.setLimit(curr.getSegment().getLimit());
            					
            					curr.getNext().setPrevious(curr.getPrevious());
            					curr.getNext().setBase(curr.getSegment().getBase() + curr.getSegment().getLimit());
            					curr.getNext().setLimit(curr.getNext().getLimit() + curr.getSegment().getLimit());
            					
            					this.firstFitInsert(segment, numBytes); 
            				}
						}else { //if the node after the current node (the segment) is already allocated 
            				System.out.println("Temp base: " + temp.getBase() + " limit: " + temp.getLimit()); // temp = start node
            				System.out.println("Node after segment is already allocated");
        					System.out.println("curr base: " + curr.getBase() + ", limit: " + curr.getLimit() + ", previous: " + curr.getPrevious());
        					
        					int holeLimit = curr.getLimit();
        					System.out.println(holeLimit);
        					
        					newNode.setAllocation(false); //replace the segment with a hole, with the same limit, base, previous node, and next node as the the segment in the memory
        					newNode.setLimit(holeLimit);
        					newNode.setBase(curr.getBase());
        					
        					
        					if(curr.getPrevious() == null) {
        						start = newNode;
        					}else {
        						curr.getPrevious().setNext(newNode);
        						newNode.setPrevious(curr.getPrevious());
        					}
        					
        					if(curr.getNext() == null) {
        						end = newNode;
        					}else {
        						curr.getNext().setPrevious(newNode);
        						newNode.setNext(curr.getNext());
        					}
        					curr.getNext().setPrevious(newNode);
        					
        				
        					if(curr.getPrevious() != null) {
        					System.out.println("curr previous  base: " + curr.getPrevious().getBase() + ", limit: " + curr.getPrevious().getLimit());
        					}
        					
        					curr.setLimit(curr.getLimit() + numBytes);
        					

        					System.out.println("Hole: base: " + newNode.getBase() + " limit: " +  newNode.getLimit());
        					
        					Node tempHole = start;
        					System.out.println("tempHole base " + tempHole.getBase() +  " limit "+  tempHole.getLimit() );
        					while(tempHole != null) {
        						if(tempHole.isAllocated() == false && tempHole.getLimit() >= curr.getLimit()) {
 
        							int holeSize = tempHole.getLimit() - curr.getLimit();
        							tempHole.setAllocation(true);
        							tempHole.setSegment(curr.getSegment());
        							tempHole.setLimit(curr.getLimit());
        							Node newHole = new Node();
        							newHole.setAllocation(false);
        							newHole.setLimit(holeSize);
        							newHole.setNext(tempHole.getNext());
        							tempHole.setNext(newHole);
        							return true;
        						}
        						tempHole = tempHole.getNext();
        					}
        					return false;
        					
            			}
            		}
            		curr = curr.getNext();
            	}
            		
            	
        	}else { // if segment is not already in memory
            	System.out.println("Segment not aleady in memory");
        		Node curr = start;
            	while(curr != null) {
            		if(curr.isAllocated() == false) { // if the currents node is a hole
            			if(curr.getLimit() >= segment.getLimit()) { // if the size of the hole is greater than the size of the segment

            				curr.getPrevious().setNext(newNode);
            				newNode.setNext(curr);
            				newNode.setPrevious(curr.getPrevious());
            				curr.setPrevious(newNode);
            				Node nodePrevious = newNode.getPrevious();
            				newNode.setSegment(segment);

            				newNode = this.calculateNewBase(newNode, nodePrevious);

            				newNode.setLimit(segment.getLimit());
            				
            				curr = this.calculateNewBase(curr, newNode);
            				
            				curr.setLimit(curr.getLimit() - newNode.getLimit());
            				return true;
            			}
            		}
            		curr = curr.getNext();
            	}
        	}
        	
        	System.out.println("Not enough space to allocate this memory");
        	return false;
        }
    }
    
    public void testBaseLimit() {
    	Node temp = this.start;
    	StringBuffer memory = new StringBuffer("\n---------------------------------------------------------------------------------------------------\n");
    	memory.append("\nFOR TESTING PURPOSES: Main Memory with base and limit\n");
    	memory.append("---------------------------------------------------------------------------------------------------\n");
    	String A = "";
    	while(temp != null) {
    		if(temp.isAllocated()) {
    			A = "A";
    		}else {
    			A = "H";
    		}
    		memory.append("[" + A + temp.getLimit() +"] Base = " + temp.getBase() + " limit = " + temp.getLimit());
    		temp = temp.getNext();
    	}
    	memory.append("\n");
    	System.out.println(memory.toString());
    }
    
    public void deallocateMemory(Segment segment, int size) {
		if(this.segmentAlreadyUsed(segment) == false) {
			throw new RuntimeException("Failure: segment not in main memory!");
		}else {
			Node temp = start;
			while(temp != null) {
				if(temp.isAllocated()) {
					//if(temp.getSegment().getSegmentNumber() == segment.getSegmentNumber()) {
					if((this.getIndexOfSegment(temp.getSegment())+1) == (this.getIndexOfSegment(segment)+1) && temp.getSegment().getProcess().getReference_number() == segment.getProcess().getReference_number()) {
						if(temp.getLimit() >= size) {
							segment.setLimit(segment.getLimit()-size);
							temp.setLimit(segment.getLimit());
							if(segment.getLimit() == 0) { // if the size of the segment is now 0, remove it from the LL
								System.out.println("Segment has a size of 0");
							}else { // else, create a hole with the size of memory removed, and set that to be the next node
								Node newNext = new Node(temp.getNext(), temp, false);
								temp.setNext(newNext);
								newNext.setLimit(size);

								newNext = this.calculateNewBase(newNext, temp);
								
								System.out.println(size + " bytes successfully deallocated from memory");
								//System.out.println("Segment " + segment.getSegmentNumber() + " Base: " + segment.getBase() + " Limit: " + segment.getLimit());
								System.out.println("Segment " + (this.getIndexOfSegment(temp.getSegment())+1) + " Base: " + temp.getSegment().getBase() + " Limit: " + temp.getSegment().getLimit());
								System.out.println("Next node (hole): Base:" + newNext.getBase() + " Limit: " + newNext.getLimit() );
								
								this.testBaseLimit();
								this.mergeHoles();
								
							}
						}else {
							throw new RuntimeException("Amount to deallocate > amount in segment!");
							
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
	
    
    public void compaction() {
    	System.out.println("Compaction:");
    	Node curr = this.start;
    	
    	if(curr.getBase() == 0 && curr.getLimit() == this.getUser_space())
    	{
    		throw new RuntimeException("Error: cannot run compaction as the memory only has one hole");
    	}
    	
    	int totalHoleSize = 0;
    	System.out.println("before while loop");
    	while(curr.getNext() != null) {
    		System.out.println("inside while loop");
    		if(curr.isAllocated() == false) { // if current node is a hole
    			System.out.println("curr base: " + curr.getBase() + " limit: " + curr.getLimit() );
    			curr.getPrevious().setNext(curr.getNext());
        		curr.getNext().setPrevious(curr.getPrevious());
        		
        		Node previous = curr.getPrevious();
        		Node next = curr.getNext();
        		
        		next = this.calculateNewBase(next, previous);
        		
        		totalHoleSize += curr.getLimit();
    		}
        	curr = curr.getNext();
    	}
    	/*
    	System.out.println("last node: " + curr.isAllocated() + " base: "+  curr.getBase() + " limit: " + curr.getLimit());
    	curr.setLimit(curr.getLimit() + totalHoleSize);
    	System.out.println("NEW last node: " + curr.isAllocated() + " base: "+  curr.getBase() + " limit: " + curr.getLimit());
    	System.out.println("Total hole space to reallocate: " + totalHoleSize);
    	*/
    }
    
    public Node calculateNewBase(Node nodeToChange, Node previousNode) {
    	int newBaseLimit = previousNode.getLimit();
    	int newBaseBase = previousNode.getBase();
    	int newBase = newBaseLimit + newBaseBase;
    	
    	nodeToChange.setBase(newBase);
    	return nodeToChange;
    }
    
    public void mergeHoles() {
    	System.out.println("Merging holes");
    	Node search = start;
    	while(true) {
    		if(search.isAllocated() == false) {
    			if(search.getNext() == null) {
    				break;
    			}
    			while(search.getNext().isAllocated() == false) {
    				System.out.println("Search, base: " + search.getBase() + " limit " + search.getLimit());
    				Node searchNext = search.getNext();
    				search.setLimit(search.getLimit() + searchNext.getLimit());
    				System.out.println("Search, base: " +search.getBase() + ", NEW LIMIT: " + search.getLimit());
    				
    				Node searchNextNext = searchNext.getNext();
    				search.setNext(searchNextNext);

    				break;
    			}
    		}
        	if(search.getNext() == null) {
        		break;
        	}else {
        		search = search.getNext();
        	}
    	}
    }
	

    
    public void printActiveProcess() {
    	for(Entry<Process, List<Segment>> entry : this.processesInMemory.entrySet()) {
    		System.out.println("Process: " + entry.getKey() + ", list of segment: "+ entry.getValue());
    	}
    }
    
    
    public int numSegmentNodes() {
    	Node curr = start;
    	int numSegNodes = 0;
    	while(curr != null) {
    		if(curr.isAllocated()) {
    			numSegNodes++;
    		}
    		curr = curr.getNext();
    	}
    	return numSegNodes;
    }
    
    /*
    public boolean checkReadWrite(Segment segment) {
    	if(segment.getReadWriteFlag() == false) {
        	System.out.println("Cannot write to this segment!");
        	System.out.println("Read/write flag is false!");
        	return false;
    	}
    	return true;
    }
    */
    
    public void removeSizeZeroSegments() {
    	Node temp = start;
    	while(temp != null) {
    		if(temp.isAllocated() && temp.getLimit() == 0) {
    			System.out.println("ZERO");
    		}
    		temp = temp.getNext();
    	}
    }
    
    public void printReadWriteStatus() {
    	System.out.println("\n----------------------------------\nRead-Write status for each segment\n----------------------------------\n");
    	Node temp = start;
    	while(temp != null) {
    		if(temp.isAllocated()) {
    			if(temp.getSegment().getReadWriteFlag() == true) {
    				System.out.println("Segment " + this.getSegmentID(temp.getSegment()) + " for process " + temp.getSegment().getProcess().getReference_number() + " is read-write.");
    				
    			}else {
    				System.out.println("Segment " + this.getSegmentID(temp.getSegment()) + " for process " + temp.getSegment().getProcess().getReference_number() + " is read-only.");
    			}
    		}
    		temp = temp.getNext();
    	}
    }
    
    public void sharedSegmentReadOnly(Segment segment) {
    	if(segment.hasSharedList() && segment.getReadWriteFlag() == true) {
    		throw new RuntimeException("Shared segment must be read only!");
    	}
    }
    
    public boolean checkIfSharedSegmentInMemory(Segment segment){
    	
    	Node temp = start;
    	while(temp != null) {
    		if(temp.isAllocated() == true && (this.getIndexOfSegment(temp.getSegment()) == this.getIndexOfSegment(segment))) {
    			if(temp.getSegment().getLimit() == segment.getLimit()) { // check whether the size of the segment in memory is equal to the size of the other segment
            		if(temp.getSegment().checkIfProcessInSharedList(segment.getProcess())){
            			System.out.println(temp.getSegment().getAllSharedProcesses());

            			System.out.println(segment.getAllSharedProcesses());
            			
            			return true;
            		}
    			}else {
    				throw new RuntimeException("Segment needs to be the same size as the shared segment");
    			}

    		}
    		temp = temp.getNext();
    	}
    	return false;
    }
 
    
    public void printSharedSegments() {
    	System.out.println("\n----------------------------------\nSegments that are shared\n----------------------------------\n");
    	Node temp = start;
    	while(temp != null) {
    		if(temp.isAllocated() == true && temp.getSegment().hasSharedList()) {
    			System.out.println("Segment " + temp.getSegment().getSegmentID() + " for process " + temp.getSegment().getProcess().getReference_number() + " is shared with " + temp.getSegment().getSharedProcesses());
    		}
    		temp = temp.getNext();
    	}
    }
    

    public List<Segment> getListSegmentsInMemory(){
    	List<Segment> segmentsInMemory = new ArrayList<Segment>();
    	Node temp = start;
    	while(temp != null) {
    		if(temp.isAllocated() == true) {
    			segmentsInMemory.add(temp.getSegment());
    		}
    		temp = temp.getNext();
    	}
    	return segmentsInMemory;
    }
    
    public void printTLBWorking() {
    	List<Segment> segmentsInMemory = this.getListSegmentsInMemory();
    	Segment segmentToCheck = null;
    	Random rand = new Random();
    	
    	int testNumber = 5; //number of times to run the ting
    	
    	for(int i = 0; i <= testNumber; i++ ) {
    		
    		int randomIndex = rand.nextInt(segmentsInMemory.size());
    		segmentToCheck = segmentsInMemory.get(randomIndex);
    		this.tlb.searchTLB(segmentToCheck);
    	}
    }
    
    public void printTLBMiss() {
    	List<Segment> segmentsInMemory = this.getListSegmentsInMemory();
    	Segment segment = null;

    	segment = segmentsInMemory.get(0);
    	
    	this.tlb.deleteSegment(segment);
    	this.tlb.searchTLB(segment);
    	this.tlb.searchTLB(segment);

    }
    
    
    
}
