package urn6623139;

import java.util.ArrayList;
import java.util.List;


/*
class Entry{
	
	private int base;
	private int limit;
	
	public Entry(int base, int limit) {
		this.base = base;
		this.limit = limit;
	}
	
}
*/




public class SegmentTable {

	private List<Segment> SegmentTable;
	
	public SegmentTable() {
		this.SegmentTable = new ArrayList<>();
	}
	
	
	public boolean addSegment(Segment s) {
		this.SegmentTable.add(s);
		if(!this.SegmentTable.contains(s)) {
			return false;
		}
		
		return true;
	}
	
	
	public List<Segment> getSegmentTable(){
		return this.SegmentTable;
	}
	
	
	
	/*
	public void addEntry(int base, int limit) {
		Entry e = new Entry(base, limit);
		SegmentTable.add(e);
		
	}
	
	public void removeEntry(){
	
	}
	
	*/
}
