/*
 * ServiceElement.java
 * 
 * Dr Mark C. Sinclair, August 2017
 * 
 * A ServiceElement is a small area within the ServiceArea which is the 'quantum' 
 * of the ServiceArea; it is used for both visualisation and cost assessment
 * (live programming)
 */

package ec.app.basestation;

import java.util.*;

public class ServiceElement {
	public ServiceElement(ServiceArea sa, Properties p, int id) {
		assert(sa != null);
		this.sa      = sa;
		assert(p  != null);
		assert((id >= 0) && (id < sa.getNose()));
		this.id      = id;
		int height   = sa.getHeight();
		int elemSize = sa.getElemSize();
		int nih      = height/elemSize;   // number of service elements in height of service area
		this.x       = (id/nih)*elemSize; // x varies less with id
		this.y       = (id%nih)*elemSize; // y varies more with id
		this.density = PropertiesHelper.getDoubleProp(p, "se"+id+".density", "density");
		// collect potential BaseStations that serve this element
		bs = new Vector<BaseStation>();
		for (int bsid=0; bsid<sa.getNumber(); bsid++) {
			BaseStation b = sa.getBaseStation(bsid);
			// is centre of ServiceElement in range of each BaseStation?
			if (b.isInRange(x+elemSize/2, y+elemSize/2))
				bs.add(b);
		}
	}

	public ServiceArea getSA() {
		return sa;
	}

	public int getId() {
		assert(sa != null);
		assert((id >= 0) && (id < sa.getNose()));
		return id;
	}

	public int getX() {
		assert(sa != null);
		assert((x >= 0) && (x <= sa.getWidth()));
		return x;
	}

	public int getY() {
		assert(sa != null);
		assert((y >= 0) && (y <= sa.getHeight()));
		return y;
	}

	public double getDensity() {
		assert(density > 0.0);
    return density;
	}	
	
	public int getCount() {
		// count of number of potential BaseStations that serve this element
		return bs.size();
	}

	public int getActiveCount() {
		// count of number of active BaseStations that serve this element
		int ac = 0;
		for (BaseStation b : bs)
			if (b.isActive())
				ac++;
		return ac;
	}

	public double getIncome() {
		// income from this service element (zero if no active base station)
		assert(sa != null);
		int elemSize   = sa.getElemSize();
		double income  = 0.0;
		if (getActiveCount() > 0)
			income = (elemSize * elemSize) * density;
		return income;
	}

	public double getPenalty() {
		// penalty from this service element for lack of service
		assert(sa != null);
		int elemSize   = sa.getElemSize();
		double penalty = 0.0;
		if (getActiveCount() == 0)
			penalty = (elemSize * elemSize) * density * sa.getPenaltyRatio();
		return penalty;
	}

	private ServiceArea         sa      = null; // service area
	private int                 id      = -1;   // id within service area
	private int                 x       = 0;    // x coordinate within service area
	private int                 y       = 0;    // y coordinate within service area
	private double              density = 0.0;  // income density of this service element
	private Vector<BaseStation> bs      = null; // the base stations providing service to this element
}
