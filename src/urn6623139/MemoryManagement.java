package urn6623139;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


/*
 * 
 */
public class MemoryManagement {

	/*
	 * private attributes
	 */
    public static int TOTAL_BYTES; // this is the total space in the main memory
	private final int os_size; // this is the space 
	public static int user_space; // this is the space that can be used for the segments
	
	private SegmentTable segmentTable;
	private Map<Process, List<Segment> > processesInMemory;
	
	private Node start;
	private Node end; 
	private Node previous;

	/*
	 * Constructor for MemoryManagement
	 * 
	 * 
	 */
	public MemoryManagement(int total_bytes, int os_size) {
		MemoryManagement.TOTAL_BYTES = total_bytes;
		this.os_size = os_size;  
		MemoryManagement.user_space = total_bytes - os_size;
		
		segmentTable = new SegmentTable();

		this.processesInMemory = new HashMap<Process, List<Segment> >();

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
						//System.out.println("a: "+ (this.getIndexOfSegment(a)+1) + " base: " + a.getBase() + " limit: " + a.getLimit());
					}
				}
				//if((this.getIndexOfSegment(entry.getValue()) + 1) == segment_number) {
				//	System.out.println("line 161");
				//	segmentForProcessAlreadyInMemory = true;
				//	a = entry.getValue();
				//}
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
				segment = a;
				segment.setReadWriteFlag(readWritePermissions);
				System.out.println("segment r/w: " + a.getReadWriteFlag());
				if(this.checkReadWrite(segment) == false) { return; }
				
				//System.out.println("segment: base: " + segment.getBase() + " limit: " + segment.getLimit());
			}
		}
		/*
		System.out.println("Segment before going into deallocateMemory() or allocateMemory()");
		System.out.println("Segment " + (this.getIndexOfSegment(segment) +1) + " base: " + segment.getBase() + " limit: " + segment.getLimit() + " size to change: " + numBytes);
*/
		
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
	
	
	public void initaliseLinkedList() {
    	Node startNode = new Node(null, null, false);
    	
    	this.start = startNode;
    	this.previous = null;
    	startNode.setBase(0);
    	startNode.setLimit(this.getUser_space());
        this.end = null;
        this.start.setAllocation(false);
        
	}
	

	public void allocateMemory(Segment segment, int numBytes) {
		if(this.segmentAlreadyUsed(segment)) {
			//System.out.println("Segment in linked list");
			if(!this.firstFitInsert(segment, numBytes)) {
				System.out.println("Failure: memory not allocated!");
			}else {
				System.out.println("Memory successfully allocated!");
			}
		}else {
			//System.out.println("Segment not already in linked list");
			if(!this.firstFitInsert(segment, numBytes)) {
				System.out.println("Failure: memory not allocated!");
			}else {
				System.out.println("Memory successfully allocated!");
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
    			if((this.getIndexOfSegment(temp.getSegment())+1) == (this.getIndexOfSegment(segment)+1) && temp.getSegment().getProcess() == segment.getProcess()) {
    			//if(temp.getSegment().getSegmentNumber() == segment.getSegmentNumber()) {
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
        
        //System.out.println("num seg nodes: " + this.numSegmentNodes());
     
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
            
            /*
            System.out.println("Memory successfully allocated to segment " + (this.getIndexOfSegment(segment) + 1) + " for process " + segment.getProcess().getReference_number());
            newNode.getSegment().setBase(0);
            System.out.println("Base: " + 0 + ", Limit: " + newNode.getSegment().getLimit());
            System.out.println("Next node: base " + start.getNext().getBase() + " limit " + start.getNext().getLimit()  );
            System.out.println("Next node allocated? " + start.getNext().isAllocated());
            */
            //this.printMemory();
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
        					//newNode.setPrevious(curr.getPrevious());
        					//newNode.setNext(curr.getNext());
        					
        					
        					
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
        					
        					
        					
        					
        					//System.out.println("New hole base: " + newNode.getBase() + ", limit: " + newNode.getLimit());
        					
        					if(curr.getPrevious() != null) {
        					System.out.println("curr previous  base: " + curr.getPrevious().getBase() + ", limit: " + curr.getPrevious().getLimit());
        					}
        					
        					//this.testBaseLimit();

        					curr.setLimit(curr.getLimit() + numBytes);
        					
  
        					//System.out.println("Temp base: " + curr.getBase() + ", limit: " + curr.getLimit());
   
        					//this.printMemory();
        					//this.testBaseLimit();
        					       				
        					System.out.println("Hole: base: " + newNode.getBase() + " limit: " +  newNode.getLimit());
        					//System.out.println("Hole previous: base: " + newNode.getPrevious().getBase() + " limit: " +  newNode.getPrevious().getLimit());
        					
        					Node tempHole = start;
        					System.out.println("tempHole base " + tempHole.getBase() +  " limit "+  tempHole.getLimit() );
        					while(tempHole != null) {
        						if(tempHole.isAllocated() == false && tempHole.getLimit() >= curr.getLimit()) {
                    				/*	
        							System.out.println("hole >= amount of memory to allocate");
                    					System.out.println("hole base: " + tempHole.getBase() + ", limit: "  + tempHole.getLimit());
                    					
                    					
                    					if(tempHole.getPrevious() == null) {
                    						start = curr;
                    						curr.setPrevious(null);
                    					}else {
                    						curr.setPrevious(tempHole.getPrevious());
                    						tempHole.getPrevious().setNext(curr);
                    					}
                    					
                    					if(tempHole.getNext() == null) {
                    						end = curr;
                    						curr.setNext(null);
                    					}else {
                    						curr.setNext(tempHole);
                    						tempHole.getNext().setPrevious(curr);
                    					}
     
                    					
                    					tempHole.setLimit(tempHole.getLimit() - numBytes);
                    					return true;
                    					*/
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
            		//System.out.println("curr allocated? " + curr.isAllocated());
            		if(curr.isAllocated() == false) { // if the currents node is a hole
            			//System.out.println("curr limit: " + curr.getLimit());
            			//System.out.println("segment limit: " + segment.getLimit());
            			if(curr.getLimit() > segment.getLimit()) { // if the size of the hole is greater than the size of the segment
            				//System.out.println("yeet");
            				curr.getPrevious().setNext(newNode);
            				newNode.setNext(curr);
            				newNode.setPrevious(curr.getPrevious());
            				curr.setPrevious(newNode);
            				Node nodePrevious = newNode.getPrevious();
            				newNode.setSegment(segment);

            				/*
            				int newBaseLimit = nodePrevious.getLimit();
            				int newBaseBase = nodePrevious.getBase(); 
            				int newBase =  newBaseLimit + newBaseBase;
            				
            				newNode.setBase(newBase);
            				*/
            				//newNode.setBase(this.calculateNewBase(newNode, nodePrevious));
            				newNode = this.calculateNewBase(newNode, nodePrevious);
            				
            				//System.out.println("New node base: " + newNode.getBase());
            				newNode.setLimit(segment.getLimit());
            				
            				/*
            				newBaseLimit = newNode.getLimit();
            				newBaseBase = newNode.getBase();
            				newBase =  newBaseLimit + newBaseBase; 
            				//curr.setBase(newNode.getSegment().getLimit()+ newNode.getSegment().getBase() );
            				*/
            				
            				
            				//curr.setBase(newBase);
            				
            				//curr.setBase(this.calculateNewBase(curr, newNode));
            				
            				curr = this.calculateNewBase(curr, newNode);
            				
            				//System.out.println("Hole base: " + curr.getBase());
            				curr.setLimit(curr.getLimit() - newNode.getLimit());
            				return true;
            			}
            		}
            		//System.out.println("not pog");
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
			System.out.println("Segment not in main memory");
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
							System.out.println("Amount to deallocate > amount in segment!");
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
    	Node curr = this.start;
    	
  
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
    	System.out.println("last node: " + curr.isAllocated() + " base: "+  curr.getBase() + " limit: " + curr.getLimit());
    	curr.setLimit(curr.getLimit() + totalHoleSize);
    	System.out.println("NEW last node: " + curr.isAllocated() + " base: "+  curr.getBase() + " limit: " + curr.getLimit());
    	System.out.println("Total hole space to reallocate: " + totalHoleSize);
    }
    
    public Node calculateNewBase(Node nodeToChange, Node previousNode) {
    	int newBaseLimit = previousNode.getLimit();
    	int newBaseBase = previousNode.getBase();
    	int newBase = newBaseLimit + newBaseBase;
    	
    	nodeToChange.setBase(newBase);
    	return nodeToChange;
    }
    
    public void mergeHoles() {
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
    
    
    public boolean checkReadWrite(Segment segment) {
    	if(segment.getReadWriteFlag() == false) {
        	System.out.println("Cannot write to this segment!");
        	System.out.println("Read/write flag is false!");
        	return false;
    	}
    	return true;

    }
}
