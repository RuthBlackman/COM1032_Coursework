package urn6623139;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that defines that attributes and methods of a process
 */
public class Process {

	/**
	 * Private attributes
	 */
    private int reference_number;
    private List<Segment> segmentTable;
   

    /*
     * Constructor for Process
     * @param reference_number
     * 				 A reference number (a unique identifier for that operation)
     * 
     */
    public Process(int reference_number) {
    	this.reference_number = reference_number;
    	this.segmentTable = new ArrayList<Segment>();
    }
    
    /**
     * Method to add a segment to the process's ArrayList of segments
     * 
     * @param segment 
     * 				Segment to add to the ArrayList
     */
    public void addSegmentToSegmentTable(Segment segment) { //need to add validation
    	this.segmentTable.add(segment);
    }
    
    
    /**
     * Method to get the process's reference number
     * 
     * @return reference number
     */
    public int getReference_number(){
        return this.reference_number;
    }
    
    
    /**
     * Method to get the list of the process's segments
     * 
     * @return list of segments
     */
    public List<Segment> getListSegments(){
    	return this.segmentTable;
    }
    
    
    /**
     * Method to print the base and limit for each segment in the ArrayList of segments
     */
    public void printSegments() {
    	for(Segment s : this.segmentTable) {
    		System.out.println("Segment " + s.getSegmentID()+ " base: " + s.getBase() + " limit: "+ s.getLimit());
    	}
    }
    
    /**
     * Method to remove a segment from the process's ArrayList of segments
     * 
     * @param segment
     * 			Segment to remove
     * 
     * @return boolean of whether it has been removed
     */
    public boolean removeSegment(Segment segment) {
    	boolean removed = this.segmentTable.remove(segment);
    	return removed;
    }
}