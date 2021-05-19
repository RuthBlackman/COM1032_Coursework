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
	
	
	/**
	 * Method to get the start node
	 * 
	 * @return start
	 */
	public Node getStart() {
		return this.start;
	}
	
	
	/**
	 * Method to get the total size of the physical memory
	 * 
	 * @return total_bytes
	 */
	public int getTOTAL_BYTES() {
		return TOTAL_BYTES;
	}

	
	/**
	 * Method to get the size of the OS
	 * 
	 *  @return os_size
	 */
	public int getOs_size() {
		return os_size;
	}

	
	/**
	 * Method to get the total usable space - used for allocating segments
	 * 
	 * @return user_space
	 */
	public int getUser_space() {
		return user_space;
	}
	
	
	
	/**
	 * Method to process the user input
	 * 
	 * @param example
	 * 			This is the input formatted as a string
	 */
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
			}			
			
			segment_number = i;
			
		//System.out.println(segmentSharedWith);
		Segment segment = new Segment(proc, numBytes); //create new segment with details from input
		segment.setReadWriteFlag(readWritePermissions);
		Segment a = null;
		
		
		
	/*
		if(this.needToAllocateSharedSegment(segmentSharedWith, segment)) {
			System.out.println("want to allocate");
			segment.addProcessToSharedList(segmentSharedWith);
		}else {
			System.out.println("dont want to allocate");
			break;
		}
		*/
		
		
		//check whether process already has segments in memory
		Boolean processAlreadyInMemory = false;
		Boolean segmentForProcessAlreadyInMemory = false;
		for(Map.Entry<Process,List<Segment> > entry : this.processesInMemory.entrySet()) {
			if(entry.getKey().getReference_number() == proc.getReference_number()) {
				processAlreadyInMemory = true;
				for(Segment s : entry.getValue()) {
					if((this.getIndexOfSegment(s)) == (segment_number-1)) { //segment 1 is stored at index 0 
						segmentForProcessAlreadyInMemory = true;
						a = s;
						a.setReadWriteFlag(readWritePermissions);
					}
				}
			}
		}
		
		// check to see whether the process already has segments in the memory
		if(processAlreadyInMemory == false) { //if not, then add the segment to the process's segment table and add the process to the list of processes in memory
			proc.addSegmentToSegmentTable(segment);
			processesInMemory.put(proc, proc.getListSegments());
			
		}else { // process is already in memory, so the segment may be too
			if(segmentForProcessAlreadyInMemory == false) { // segment is not in memory for that process, so add to the segment table 
				proc.addSegmentToSegmentTable(segment);
				processesInMemory.put(proc, proc.getListSegments());
				
			}else { // segment is in memory for that process
				segment = a;
				segment.setReadWriteFlag(readWritePermissions); // override the read-write permissions
				//segment.emptySharedList();
				//segment.addProcessToSharedList(segmentSharedWith); 
			}
		}
		
		segment.setSegmentID(this.getSegmentID(segment));
		//System.out.println(segment.getSegmentID());
	
		
		//if the size of the segment is less than 0, then we are deallocating memory
		if(numBytes < 0) {
			numBytes *= -1;
			//System.out.println("Deallocating: " + numBytes);
			this.deallocateMemory(segment, numBytes);
		}else if (numBytes > 0) { // if it's greater than 0, then we are allocating memory
			//System.out.println("Allocating: " + numBytes);
			if(this.allocateMemory(segment, numBytes)) {
				//System.out.println("Memory successfully allocated!");
			}else {
				throw new RuntimeException("Error: not enough space!");
			}
		}
		}
	}
		
	
	
	/**
	 * Method used to initalise the linked list (physical memory)
	 * It adds a node (hole) to the memory, sets the start and nodes to be the hole
	 */
	public void initaliseLinkedList() {
    	Node startNode = new Node(null, null, false); // create a new node and set the allocation to false
    	
    	this.start = startNode; //shows it's the first node 
    	this.previous = null; //as it's the first node, there's no previous segment
    	startNode.setBase(0); //base is 0
    	startNode.setLimit(this.getUser_space()); //in this case, the limit is 900
        this.end = startNode; //it's the only node, so it's the end node too
        this.start.setAllocation(false); //make sure it's definitely a hole
        
	}
	
	
	/**
	 * Method used to get the index of the segment in the segment table
	 * 
	 * @param segment
	 * @return index
	 */
    public int getIndexOfSegment(Segment segment) {
    	int index = 0;
    	for(Segment s : segment.getProcess().getListSegments()) { // for each segment in the segment table, if it's the one we're looking for, then return the index
    		if(s == segment) {
    			return index;
    		}
    		index++;
    	}
    	return -1;
    }
	
    
    /**
     * Method to get the segment ID 
     * 
     * @param segment
     * @return index + 1
     */
    public int getSegmentID(Segment segment) {
    	int index = 0;
    	for(Segment s : segment.getProcess().getListSegments()) { // for each segment in the segment table, if it's the one we're looking for, then return the index + 1
    		if(s == segment) {
    			return index +1;
    		}
    		index++;
    	}
    	return -1;
    }
	
    
    /**
     * Method to check whether a segment has already been used
     * 
     * @param segment
     * @return boolean
     */
    public boolean segmentAlreadyUsed(Segment segment) {
    	Node temp = start;
    	while(temp != null) { // for each segment in the memory, if it's the segment we're looking for, then return true
    		if(temp.isAllocated()) {
    			if((this.getIndexOfSegment(temp.getSegment())+1) == (this.getIndexOfSegment(segment)+1) && temp.getSegment().getProcess() == segment.getProcess()) { //check if the index and process match
        			return true;
        		}
    		}
    		temp = temp.getNext();
    	}
    	
    	return false;
    }
    
    
    /**
     * Method to allocate memory to both new and existing segments
     * 
     * @param segment
     * 			the segment to allocate memory to
     * @param numBytes
     * 			the limit of the segment
     * @return boolean
     * 			true if the memory was allocated successfully
     * 			false if the memory was not allocated successfully 
     */
    public boolean allocateMemory(Segment segment, int numBytes) {
        Node newNode = new Node(null, null, true);
        newNode.setSegment(segment);
        Node temp = start;
        
        if(!start.isAllocated() && (this.numSegmentNodes() ==0) ){ // if the memory only contains one hole - no segments yet
        	start = newNode; //we want to make the new segment the first node in the memory which will then point to the hole
            end = temp;
            previous = null;
            
            start.setNext(end);
            start.setBase(0);
            start.setLimit(newNode.getSegment().getLimit());
            
            end.setBase(start.getLimit() +start.getBase()); //calculate the new base of the hole
            end.setLimit(end.getLimit() - start.getLimit()); //calculate the new limit of the hole
           
            end.setPrevious(start);

            return true;
        }else{ // if the memory contains holes and segments
        	if(this.segmentAlreadyUsed(segment)) { // if segment is already in main memory
        		Node curr = start;
            	while(curr != null) {
            		if((curr.isAllocated() == true) && (this.getIndexOfSegment(curr.getSegment())== this.getIndexOfSegment(segment))) { //check to see if current segment matches the one we are wanting to allocate more memory for
            			if(curr.getNext().isAllocated() == false) { // if the node after the current node (where the segment is) is a hole
            				if(curr.getNext().getLimit() >= numBytes) { // if hole >= amount of memory to allocate
            					curr.setLimit(curr.getLimit()+numBytes); //increase the limit of the segment
            					curr.getNext().setBase(curr.getSegment().getBase() + curr.getSegment().getLimit()); //calculate a new base for the hole that's next to the segment
            					curr.getNext().setLimit(curr.getNext().getLimit() -  numBytes); //calculate a new limit for the hole
            					curr.setSegment(segment); //set the segment of the node to be of the current segment
            					return true;
            				}else { // if hole < segment, then remove segment and allocate it in another location		
            					temp.setBase(curr.getSegment().getBase() + curr.getSegment().getLimit());
            					temp.setLimit(curr.getSegment().getLimit());
            					
            					curr.getNext().setPrevious(curr.getPrevious());
            					curr.getNext().setBase(curr.getSegment().getBase() + curr.getSegment().getLimit());
            					curr.getNext().setLimit(curr.getNext().getLimit() + curr.getSegment().getLimit());
            					
            					this.allocateMemory(segment, numBytes); 
            				}
						}else { //if the node after the current node (the segment) is already allocated 
        					int holeLimit = curr.getLimit();
        					newNode.setAllocation(false); //replace the segment with a hole, with the same limit, base, previous node, and next node as the the segment in the memory
        					newNode.setLimit(holeLimit);
        					newNode.setBase(curr.getBase());
        					
        					
        					if(curr.getPrevious() == null) { //set the next node of the current node's previous node to be the new hole, if the current node's previous node isn't null
        						start = newNode;
        					}else {
        						curr.getPrevious().setNext(newNode);
        						newNode.setPrevious(curr.getPrevious());
        					}
        					
        					if(curr.getNext() == null) { //set the previous node of the current node's next node to be the new hole, if the current node's next null isn't null
        						end = newNode;
        					}else {
        						curr.getNext().setPrevious(newNode);
        						newNode.setNext(curr.getNext());
        					}
        					curr.getNext().setPrevious(newNode);
        					
        					curr.setLimit(curr.getLimit() + numBytes); //set the new limit of the current node to be the limit + the size to allocate
        					
        					Node tempHole = start; 
        					while(tempHole != null) {
        						if(tempHole.isAllocated() == false && tempHole.getLimit() >= curr.getLimit()) { //loop through all the holes and check whether the limit of the hole > new limit of the segment 
 
        							int holeSize = tempHole.getLimit() - curr.getLimit(); //initialise the limit of the hole to come after the segment
        							tempHole.setAllocation(true); //set the current node to be allocated
        							tempHole.setSegment(curr.getSegment()); //set the current node's segment to be the segment we are allocating
        							tempHole.setLimit(curr.getLimit()); //set the limit of the segment
        							Node newHole = new Node(); //make a new node that will be a hole to come after the segment
        							newHole.setAllocation(false);
        							newHole.setLimit(holeSize); 
        							newHole.setNext(tempHole.getNext()); //set the next node of the new hole to be the next node of the current node's (hole) next node
        							tempHole.setNext(newHole); //set the next node of the segment to be the new hole
        							return true;
        						}
        						tempHole = tempHole.getNext();
        					}
        					return false; //if the code gets to this point, then the memory wasnt allocated successfully
        					
            			}
            		}
            		curr = curr.getNext();
            	}
            		
            	
        	}else { // if segment is not already in memory
        		Node curr = start;
            	while(curr != null) { //loop through all the nodes
            		if(curr.isAllocated() == false) { // if the current node is a hole
            			if(curr.getLimit() >= segment.getLimit()) { // if the size of the hole is greater than the size of the segment

            				curr.getPrevious().setNext(newNode); //insert segment before node
            				newNode.setNext(curr); //set the next node of the segment to be the hole
            				newNode.setPrevious(curr.getPrevious()); //set the previous node of the segment to be the hole's previous node
            				curr.setPrevious(newNode); //set the previous node of the hole to be the segment
            				
            				Node nodePrevious = newNode.getPrevious(); //set the segment's previous node to be a node object
            				
            				newNode.setSegment(segment); //set the segment of the node 
            				newNode = this.calculateNewBase(newNode, nodePrevious); //calculate a new base for the segment
            				newNode.setLimit(segment.getLimit()); //set the limit of the segment
            				
            				curr = this.calculateNewBase(curr, newNode); //calculate a new base of the hole
            				curr.setLimit(curr.getLimit() - newNode.getLimit()); //set limit of hole
            				return true;
            			}
            		}
            		curr = curr.getNext();
            	}
        	}
        	return false; //if the code gets to this point, then the memory wasn't successfully allocated
        }
    }
    
    /*
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
    */
    
    
    /**
     * Method to deallocate memory from a segment
     * 
     * @param segment
     * 			segment to deallocate memory from
     * @param size
     * 			the amount of memory to deallocate
     */
    public void deallocateMemory(Segment segment, int size) {
		if(this.segmentAlreadyUsed(segment) == false) {
			throw new RuntimeException("Failure: segment not in main memory!");
		}else {
			Node temp = start;
			while(temp != null) {
				if(temp.isAllocated()) {
					if((this.getIndexOfSegment(temp.getSegment())+1) == (this.getIndexOfSegment(segment)+1) && temp.getSegment().getProcess().getReference_number() == segment.getProcess().getReference_number()) {
						if(temp.getLimit() >= size) {
							segment.setLimit(segment.getLimit()-size);
							temp.setLimit(segment.getLimit());
							if(segment.getLimit() == 0) { // if the size of the segment is now 0, remove it from the LL
							}else { // else, create a hole with the size of memory removed, and set that to be the next node
								Node newNext = new Node(temp.getNext(), temp, false);
								temp.setNext(newNext);
								newNext.setLimit(size);

								newNext = this.calculateNewBase(newNext, temp);
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
    
    
    /**
     * Method to print out the current state of the physical memory
     */
    public void printMemory() {
    	Node temp = this.start;
    	StringBuffer memory = new StringBuffer();
    	//StringBuffer memory = new StringBuffer("\n---------------------------------------------------------------------------------------------------\n");
    	//memory.append("\tMain Memory\n");
    	//memory.append("---------------------------------------------------------------------------------------------------\n");
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
	
    
    /**
     * Method that applies compaction
     */
    public void compaction() {
    	Node curr = this.start;
    	
    	if(curr.getBase() == 0 && curr.getLimit() == this.getUser_space())
    	{
    		throw new RuntimeException("Error: cannot run compaction as the memory only has one hole");
    	}
    	
    	int totalHoleSize = 0;
    	while(curr.getNext() != null) {
    		if(curr.isAllocated() == false) { // if current node is a hole
    			curr.getPrevious().setNext(curr.getNext()); //remove current node
        		curr.getNext().setPrevious(curr.getPrevious());
        		
        		Node previous = curr.getPrevious();
        		Node next = curr.getNext();
        		
        		next = this.calculateNewBase(next, previous); 
        		
        		totalHoleSize += curr.getLimit(); //add to total hole size
    		}
        	curr = curr.getNext();
    	}
    	
    	if(!curr.isAllocated()) { //if last node in memory is not allocated (so it's a hole)
    		curr.setLimit(curr.getLimit() + totalHoleSize); //add total hole size to limit
    	}
    	else {
    		Node n = new Node(); //make a new hole with the total hole size as its limit
    		n.setLimit(totalHoleSize);
    		curr.setNext(n);
    	}
    }
    
    
    /**
     * Method to calculate the new base of a segment
     * 
     * @param nodeToChange
     * @param previousNode
     * @return node
     * 			this is the node with the new base
     */
    public Node calculateNewBase(Node nodeToChange, Node previousNode) {
    	int newBaseLimit = previousNode.getLimit();
    	int newBaseBase = previousNode.getBase();
    	int newBase = newBaseLimit + newBaseBase;
    	
    	nodeToChange.setBase(newBase);
    	return nodeToChange;
    }
    
    
    /**
     * Method to merge adjacent holes in memory
     */
    public void mergeHoles() {
    	Node search = start;
    	while(true) {
    		if(search.isAllocated() == false) { //if search is a hole
    			if(search.getNext() == null) { 
    				break;
    			}
    			while(search.getNext().isAllocated() == false) { //while next node is a hole, merge holes
    				Node searchNext = search.getNext();
    				search.setLimit(search.getLimit() + searchNext.getLimit());
    				
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
    
    
    /**
     * Method to calculate number of segments currently in memory
     * 
     * @return int
     * 			number of segments
     */
    public int numSegmentNodes() {
    	Node curr = start;
    	int numSegNodes = 0;
    	while(curr != null) {
    		if(curr.isAllocated()) { //if node is allocated, increase counter
    			numSegNodes++;
    		}
    		curr = curr.getNext();
    	}
    	return numSegNodes;
    }

        
    /*
     * Method to print out the read-write status for each segment in the physical memory
     * 
     */
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
    
    
    /**
     * Code for A.3.1:  TLB
     * 
     */
    
    
    /**
     * Method to create a list of all the segments in the memory
     * 
     * @return segmentsInMemory
     */
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
    
    /**
     * Method to demonstrate the functionality of the TLB
     * Random numbers are generated, which represent the index of the segments the physical memory
     */
    public void printTLBWorking() {
    	List<Segment> segmentsInMemory = this.getListSegmentsInMemory();
    	Segment segmentToCheck = null;
    	Random rand = new Random();
    	
    	int testNumber = 5; //number of times to run
    	
    	for(int i = 0; i <= testNumber; i++ ) {
    		
    		int randomIndex = rand.nextInt(segmentsInMemory.size());
    		segmentToCheck = segmentsInMemory.get(randomIndex);
    		this.tlb.searchTLB(segmentToCheck);
    	}
    }
    
    /**
     * Method to show an example of a TLB miss
     */
    public void printTLBMiss() {
    	List<Segment> segmentsInMemory = this.getListSegmentsInMemory();
    	Segment segment = null;

    	segment = segmentsInMemory.get(0);
    	
    	this.tlb.deleteSegment(segment);
    	this.tlb.searchTLB(segment);
    	this.tlb.searchTLB(segment);

    } 
    
    
    
    
    /**
     * @TODO
     * Code for A.2.2: Shared segments
     * 
     */
    
	/*
	public boolean needToAllocateSharedSegment(List<Integer> segmentSharedWith, Segment segment) {
		//if the segment is shared, then it must be read-only
		if(segmentSharedWith.size() > 0) {
			if(segment.getReadWriteFlag() == true) {
				throw new RuntimeException("Shared segment must be read-only!");
			}
			System.out.println(segment.getLimit());
			
			for(Integer id: segmentSharedWith) {
				System.out.println("h");
				System.out.println(processesInMemory.size());
				for(Entry<Process, List<Segment>> entry: processesInMemory.entrySet()) {
					System.out.println("k");
					if(entry.getKey().getReference_number() == id) {
						System.out.println("Process exists");
						for(Segment memorySegment: entry.getKey().getListSegments()) {
							System.out.println("inner for loop");
							System.out.println(memorySegment.getSharedProcesses());
							//if(memorySegment.getSharedProcesses().contains(segment.getProcess().getReference_number())) {
							if(memorySegment.getAllSharedProcesses().equals(segment.getAllSharedProcesses())){
								if(memorySegment.getLimit() == segment.getLimit()) {
									System.out.println("Shared segment aleady exists");
									segment.addProcessToSharedList(segmentSharedWith);
									return false;
								}else {
									System.out.println("Size different - adding segment");
									return true;
								}
							}else {
								segment.addProcessToSharedList(segmentSharedWith);
								return true;
							}
							
							

						}
					}
				}
			}
			
		}
		
		return true;
	}
	*/
    
    
    
    /*
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
 */
    
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
    


    
     
}
