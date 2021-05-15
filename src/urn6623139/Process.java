package urn6623139;

import java.util.ArrayList;
import java.util.List;

/*
 * Class that defines that attributes and methods of a process
 */
public class Process {

	/*
	 * Private attributes
	 */
    private int reference_number;
    private List<Segment> segments;
   

    /*
     * Constructor for Process
     * @param reference_number
     * 				 A reference number (a unique identifier for that operation)
     * 
     */
    public Process(int reference_number) {
    	this.reference_number = reference_number;
    	this.segments = new ArrayList<Segment>();
    }
    
    /*
     * Method to add a segment to the process's ArrayList of segments
     * 
     * @param segment 
     * 				Segment to add to the ArrayList
     */
    public void addProcessToSegment(Segment segment) { //need to add validation
    	this.segments.add(segment);
    }
    
    
    /*
     * Method to get the process's reference number
     * 
     * @return reference number
     */
    public int getReference_number(){
        return this.reference_number;
    }
    
    
    /*
     * Method to get the list of the process's segments
     * 
     * @return list of segments
     */
    public List<Segment> getListSegments(){
    	return this.segments;
    }
    
    
    /*
     * Method to print the base and limit for each segment in the ArrayList of segments
     */
    public void printSegments() {
    	for(Segment s : this.segments) {
    		System.out.println("base: " + s.getBase() + " limit: "+ s.getLimit());
    	}
    }
    
    /*
     * Method to remove a segment from the process's ArrayList of segments
     * 
     * @param segment
     * 			Segment to remove
     * 
     * @return boolean of whether it has been removed
     */
    public boolean removeSegment(Segment segment) {
    	boolean removed = this.segments.remove(segment);
    	return removed;
    }
}