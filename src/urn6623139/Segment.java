package urn6623139;

import java.util.ArrayList;
import java.util.List;

public class Segment {


	private int base;
	private int limit;
	private int readOnly; 
	
	private int segment_number;
	
	private int process_number;
    private Hole hole;

    private List<Process> sharedWith;
	
	/*
	public Segment(int base, int limit, int readOnly) {
		this.base = base;
		this.limit = limit;
		this.readOnly = readOnly;
	}
	*/
	
    
    
    
    public Segment(int process_number, int segment_number, int limit) {
    	this.process_number = process_number;
    	this.segment_number = segment_number;
    	this.limit = limit;
    	this.sharedWith = new ArrayList<Process>();
    }

    
    public void setBase(int base) {
    	this.base = base;
    }
	
    
    public void setLimit(int limit) {
    	this.limit	= limit;
    }
    
    public int getSegmentNumber() {
    	return this.segment_number;
    }
    
    public int getLimit() {
    	return this.limit;
    }
    
    public int getBase() {
    	return this.base;
    }
    
    public int getProcessNumber() {
    	return this.process_number;
    }
}


