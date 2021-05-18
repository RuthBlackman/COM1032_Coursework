package urn6623139;

import java.util.ArrayList;
import java.util.Collections;
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
	private boolean readWriteFlag; 
	private Process process;
    private List<Integer> sharedWithProcessIDs;
	
    private int segmentID;
    
    /**
     * Constructor for when only the limit of the segment is specified
     * 
     * In this case, it has taken as default that the read-write flag is true
     */
    public Segment(Process proc,  int limit) {
    	this.process = proc;
    	this.limit = limit;
    	this.readWriteFlag = true;
    	this.sharedWithProcessIDs = new ArrayList<Integer>();
    }
    
    
    /**
     * Constructor for when the read-write flag is specified, along with the limit of the segment
     */
    public Segment(Process proc, int limit, int permission) {
    	this.process = proc;
    	this.limit = limit;
    	
    	this.setReadWriteFlag(permission);
    	
    	this.sharedWithProcessIDs = new ArrayList<Integer>();
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
    
    
    /**
     * Method to get the read-write flag for the segment
     * 
     * @return boolean 
     * 			true if the segment has read-write permissions
     * 			false if the segment is read-only
     */
    
    public boolean getReadWriteFlag() {
    	return this.readWriteFlag;
    	
    }
    
    
    /**
     * Method to set the read-write flag for a segment
     * 
     * @param permission
     * 			0 means the read-write flag is set to false
     * 			1 means the read-write flag is set to true
     */
    public void setReadWriteFlag(int permission) {
    	if(permission ==0) {
    		this.readWriteFlag = false;
    	}else if (permission == 1) {
    		this.readWriteFlag = true;
    	}else {
    		throw new IllegalArgumentException("Read-write permission must be either 0 or 1");
    	}
    }
 
    
    public void addProcessToSharedList(List<Integer> segmentSharedWith) {
    	for(int id : segmentSharedWith) {
    		if(!(this.sharedWithProcessIDs.contains(id))) {
    			this.sharedWithProcessIDs.add(id);
    		}
    	}
    	
    	System.out.println("Segment shared with process IDs: " + this.sharedWithProcessIDs);
    	
    }
   
    
    public List<Integer> getSharedProcesses(){
    	return this.sharedWithProcessIDs;
    }
    
    public List<Integer> getAllSharedProcesses(){
    	this.sharedWithProcessIDs.add(this.getSegmentID());
    	Collections.sort(sharedWithProcessIDs);
    	return sharedWithProcessIDs;
    	
    }
    
    public boolean hasSharedList() {
    	if(this.getSharedProcesses().size() >0) {
    		return true;
    	}
    	return false;
    }
        
    public void emptySharedList() {
    	this.sharedWithProcessIDs.clear();
    }
    
    public boolean checkIfProcessInSharedList(Process proc) {
    	if(this.getSharedProcesses().contains(proc.getReference_number())) {
    		return true;
    	}
    	
    	return false;
    }
    
    public int getSegmentID() {
    	return this.segmentID;
    }
    
    public void setSegmentID(int id) {
    	this.segmentID = id;
    }
    
}


