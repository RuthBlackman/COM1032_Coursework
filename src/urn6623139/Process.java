package urn6623139;

import java.util.ArrayList;
import java.util.List;

//import java.util.Arrays;

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
    
    /*
    public int getIndexOfSegment(Segment segment) {
    	int index = 0;
    	for(Segment s : this.segments) {
    		if(s == segment) {
    			return index;
    		}
    		index++;
    	}
    	return -1;
    }
    */
    
    public List<Segment> getListSegments(){
    	return this.segments;
    }
}