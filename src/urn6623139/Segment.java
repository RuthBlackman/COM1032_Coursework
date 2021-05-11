package urn6623139;

import java.util.ArrayList;
import java.util.List;

public class Segment {


	private int base;
	private int limit;
	private int readOnly; 
	
	//private int segment_number;
	
	//private int process_number;
	
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
    	//this.process_number = process_number;
    	//this.segment_number = segment_number;
    	this.process = proc;
    	this.limit = limit;
    	this.sharedWith = new ArrayList<Process>();
    }

    
    public void setBase(int base) {
    	this.base = base;
    }
	
    
    public void setLimit(int limit) {
    	this.limit	= limit;
    }
   
    /*
    public int getSegmentNumber() {
    	return this.segment_number;
    }
    */
    
    public int getLimit() {
    	return this.limit;
    }
    
    public int getBase() {
    	return this.base;
    }
    
    /*
    public int getProcessNumber() {
    	return this.process_number;
    }
    */
    
    public Process getProcess() {
    	return this.process;
    }
}


