package urn6623139;

public class MemLL {

    private Node start;
    private Node end;
    private int size;

    
    /**
     * Constructor, initialize linked list
     */
    public MemLL(){
        //this.hole = new Hole();
        //this.startHole = new Node(hole, null);
        
    	Node startNode = new Node(null, false);
    	
    	this.start = startNode;
    	
    	startNode.setBase(0);
    	//startNode.setLimit();
    	
        this.end   = null;
        
        
      //  this.size = ;
        
        
    }

    /**
     * Checks if linked list is empty
     * @return True if empty,  false if not
     */
    public Boolean isEmpty(){
        if(start.isAllocated()) {
        	return false;
        }
        return true;
    }

    /**
     * Gets the size of linked list
     * @return size of linked list
     */
    public int getSize(){
        return size;
    }

    /**
     * Inserts Block at start of linked list, best to be used to initialize first node.
     * @param block Block of memory to insert.
     
    public void insertAtStart(Segment segment){
        Node nptr = new Node(segment,null);
        size++;
        if(start == null){
            start = nptr;
            end = start;
        }
        else
        {
            nptr.setNext(start);
            start = nptr;
        }
    }
    */
    
    public boolean segmentAlreadyUsed(Segment segment) {
    	
    	int index = 0;
    	Node temp = start;
    	while(temp != null) {
    		if(temp.isAllocated()) {
    			if(temp.getSegment().getSegmentNumber() == segment.getSegmentNumber()) {
        			return true;
        		}
    		}
    		
    		index++;
    		temp = temp.getNext();
    	}
    	
    	return false;
    }
    
    /*
    public boolean firstFitInsert(Segment segment) {
        Node nptr = new Node(segment,null);

        if(start == null){
            start = nptr;
            end = null;
            System.out.println("Memory successfully allocated to segment "+ segment.getSegmentNumber());
            nptr.getSegment().setBase(0);
            System.out.println("Base: " + 0 + ", Limit: " + nptr.getSegment().getLimit());
            return true;
        }else{
        	Node curr = start;
        	while(curr != null) {
        		
        	}
        	
        	return false;
        }
    }
    */
  

}
