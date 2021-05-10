package urn6623139;

class Node{
	private Segment segment;
	private Node next;
	private Boolean allocated;
	
	private int base;
	private int limit;
	
	public Node() {
		next = null;
		segment = null;
	}
	
	public Node(Node n, Boolean allocated) {
		this.next = n;
		this.allocated = allocated;
	}
	
	
	public int getBase() {
		return this.base;
	}
	
	public void setBase(int base) {
		this.base = base;
	}
	
	
	
	public int getLimit() {
		return this.limit;
	}
	
	public void setLimit(int limit) {
		this.limit = limit;
	}
	
	
	
	public Boolean isAllocated() {
		return this.allocated;
	}
	
	public void setAllocation(Boolean allocated) {
		this.allocated = allocated;
	}
	
	
	

    /**
     * Gets the next BlockNode that's linked.
     * @return next BlockNode linked to current.
     */
    public Node getNext(){
        return next;
    }

    /**
     * Setter for the next BlockNode link
     * @param n next BlockNode to be linked to
     */
    public void setNext(Node n){
        next = n;
    }

    
    
    
    /**
     * Getter for the block stored at this BlockNode if one.
     * @return block to get
     */
    public Segment getSegment(){
        return segment;
    }
    
    /**
     * Sets the block of memory for current node.
     * @param d Block to be set.
     */
    public void setSegment(Segment d){
        segment = d;
    }

    
    
}