package urn6623139;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to set the attributes and methods of a segment
 */
public class Segment {

	/**
	 * Private attributes
	 */
	private int base;
	private int limit;
	private int readOnly; 
	private Process process;
    private List<Process> sharedWith;
	
	/*
	public Segment(int base, int limit, int readOnly) {
		this.base = base;
		this.limit = limit;
		this.readOnly = readOnly;
	}
	*/
	
    
    public Segment(Process proc,  int limit) {
    	this.process = proc;
    	this.limit = limit;
    	this.sharedWith = new ArrayList<Process>();
    }

    
    /**
     * Method to set the base of a segment
     * 
     * @param base
     * 			The base to be set
     */
    public void setBase(int base) {
    	this.base = base;
    }
	
    
    /**
     * Method to set the limit of a segment
     * 
     * @param limit
     * 			The limit to be set
     */
    public void setLimit(int limit) {
    	this.limit	= limit;
    }
   
    
    /**
     * Method to get the limit of a segment
     * 
     * @return limit
     */
    public int getLimit() {
    	return this.limit;
    }
    
    
    /**
     * Method to get the base of a segment
     * 
     * @return base
     */
    public int getBase() {
    	return this.base;
    }
    
    
    /**
     * Method to get the process that the segment belongs to
     * 
     * @return process
     */
    public Process getProcess() {
    	return this.process;
    }
    
    
    /*
    public int isReadOnly() {
    	return this.readOnly;
    	
    }
    
    public void setReadOnly(int permission) {
    	this.readOnly = permission;
    }
    */
}


