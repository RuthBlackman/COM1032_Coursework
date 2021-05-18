package urn6623139;

import java.util.ArrayList;
import java.util.List;

public class TLB {

	private List<Segment> TLB;
	private MemoryManagement physicalMemory;
	private int cacheSize;
	
	public TLB(int size, MemoryManagement memory) {
		this.TLB = new ArrayList<Segment>();
		this.cacheSize = size;
		this.physicalMemory = memory;
	}
		
	
    /*
    *
    */
   public void searchTLB(Segment segment) {
	   System.out.println("Searching for S" + segment.getSegmentID() + " P" + segment.getProcess().getReference_number());
	   boolean found = false;
	   for(Segment s : this.TLB) {
		   if(s.equals(segment)) {
			   found = true;
   			}
   		}
	   if(found) {
		   System.out.println("TLB hit! Found S" + segment.getSegmentID() + " P" + segment.getProcess().getReference_number() +"\n");
	   }else {
		   System.out.println("TLB miss! S" + segment.getSegmentID() + " P" + segment.getProcess().getReference_number() + " not in TLB\n");
		   this.searchPhysicalMemory(segment);
	   }
   }
   
   public void searchPhysicalMemory(Segment segment) {
	   Node temp = this.physicalMemory.getStart();
	   while(temp != null) {
		   if(temp.getSegment().getProcess().getReference_number() == segment.getProcess().getReference_number() && temp.getSegment().getSegmentID() == segment.getSegmentID()) {
			   this.addSegment(segment);
			   return;
		   }
		   temp = temp.getNext();
	   }
	   
	   throw new IllegalArgumentException("Segment not in main memory!");
   }
   
   public void addSegment(Segment segment) {
	   int newCacheSize = this.currentCacheSize() + segment.getLimit();
	   if(newCacheSize >= this.cacheSize) {
		   this.TLB.remove(0);
		   this.addSegment(segment);
	   }
	   
	   this.TLB.add(segment);
   }
   
   
   public int currentCacheSize() {
	   int currentSize = 0;
	   for(Segment s : this.TLB) {
		   currentSize += s.getLimit();
	   }
	   return currentSize;
   }
   
   public void deleteSegment(Segment segment) {
	   this.TLB.remove(segment);
   }
}
