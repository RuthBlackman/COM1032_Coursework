package urn6623139;

import java.util.ArrayList;
import java.util.List;

public class Process {

    private int reference_number;
    private List<Segment> segments;

    
    public Process(int reference_number) {
    	this.reference_number = reference_number;
    	this.segments = new ArrayList<Segment>();
    }
    
    public void addProcessToSegment(Segment segment) { //need to add validation
    	this.segments.add(segment);
    }
    
    
    public int getReference_number(){
        return this.reference_number;
    }
    
    
    public List<Segment> getListSegments(){
    	return this.segments;
    }
    
    public void printSegments() {
    	for(Segment s : this.segments) {
    		System.out.println("base: " + s.getBase() + " limit: "+ s.getLimit());
    	}
    }
    
    public boolean removeSegment(Segment segment) {
    	boolean removed = this.segments.remove(segment);
    	return removed;
    }
}