package urn6623139;

/**
 * Class that defines that attributes and methods of a node
 */

class Node{
	/**
	 * private attributes
	 */
	private Segment segment;
	private Node next;
	private Node previous;
	private Boolean allocated;
	private int base;
	private int limit;
	
	
	/**
	 * Default constructor
	 */
	public Node() {
		next = null;
		segment = null;
	}
	
	
	/**
	 * Parameterised constructor
	 * 
	 * @param next
	 * 			The node that comes after this node
	 * @param previous
	 * 			The node that comes before this node
	 * @param allocated
	 * 			Boolean variable to represent whether the node has been allocated or not. If false, then the node is a hole
	 */
	public Node(Node next, Node previous, Boolean allocated) {
		this.next = next;
		this.previous = previous;
		this.allocated = allocated;
	}
	
	
	/**
	 * Method to get the base of a node
	 * 
	 * @return base
	 */
	public int getBase() {
		return this.base;
	}
	
	
	/**
	 * Method to set the base of a node
	 * 
	 * @param base
	 * 			Integer representing the base of a node
	 */
	public void setBase(int base) {
		this.base = base;
	}
	
	
	/**
	 * Method to get the limit of a node
	 * 
	 * @return limit
	 */
	public int getLimit() {
		return this.limit;
	}
	
	
	/**
	 * Method to set the limit of a node
	 * 
	 * @param limit
	 * 			Integer representing the limit of a node
	 */
	public void setLimit(int limit) {
		this.limit = limit;
	}
	
	
	/**
	 * Method to check whether a node has been allocated or not.
	 * If it returns true, then the node is a segment
	 * If it returns false, then the node is a hole
	 * 
	 * @return allocated
	 */
	public Boolean isAllocated() {
		return this.allocated;
	}
	
	
	/**
	 * Method to set whether a node has been allocated or not
	 * 
	 * @param allocated
	 * 				Represents whether a node has been allocated
	 */
	public void setAllocation(Boolean allocated) {
		this.allocated = allocated;
	}
	
	
	/**
	 * Method to get the next node
	 * 
	 * @return next
	 */
    public Node getNext(){
        return next;
    }

    
    /**
     * Method to set the next node
     * @param n
     * 			 next node to be linked to
     */
    public void setNext(Node n){
        next = n;
    }

    
    /**
     * Method to get the previous node
     * 
     * @return previous
     */
    public Node getPrevious() {
    	return previous;
    }
    
    
    /**
     * Method to set the previous node
     * 
     * @param n
     * 			node to be set as the previous node
     */
    public void setPrevious(Node n) {
    	this.previous = n;
    }
    
    
    /**
     * Method to get the Segment
     * 
     * @return segment
     */
    public Segment getSegment(){
        return segment;
    }
    
    
    /**
     * Method to set the segment for the node
     * @param d 
     * 			Segment to be set
     */
    public void setSegment(Segment d){
        segment = d;
    }

    
    
}