package urn6623139;

import java.util.ArrayList;
import java.util.List;

public class TLB {

	private List<Segment> TLB;
	
	public TLB() {
		this.TLB = new ArrayList<Segment>(10);
	}
	
    /*
    *
    */
   public void searchTLB(Segment segment) {
   	for(Segment s : this.TLB) {
   		if(s.equals(segment)) {
   			
   		}
   	}
   }
}
