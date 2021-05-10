package urn6623139;

/**
 * This class serves the purpose of storing the size of the block in memory.
 */
class Hole {

	// base and limit values
    private int base;
    private int end;
    

    /**
     * Default constructor.
     * Sets the first hole the size of the maximum space, and is free.
     */
    Hole(){
        base = 0;
        end = MemoryManagement.user_space;
    }

    /**
     * Constructor of hole.
     * @param start start byte of the hole.
     * @param end end byte of the hole.
     */
    Hole(int base, int end){
        this.base = base;
        this.end   = end;
    }

    
    public int getBase(){
        return this.base;
    }
    
    public int getEnd(){
        return this.end;
    }
    /**
     * This method returns the size of the hole.
     * @return hole size
     */
    int getSize(){
        return (end - base) + 1;
    }

    /**
     * This method sets the range of the hole for the block
     * @param start start byte of the hole
     * @param end end byte of the hole
     */
    public void setRange(int base, int end){
        this.base = base
        		;
        this.end   = end;
    }
    
    
}