/*
 * BaseStation.java
 * 
 * Dr Mark C. Sinclair, August 2017
 * 
 * Base station for the base station problem (live programming)
 */

package ec.app.basestation;

import java.util.*;

public class BaseStation {
	public BaseStation(ServiceArea sa, Properties p, int id) {
		assert(sa != null);
		this.sa     = sa;
		assert(p  != null);
		assert((id >= 0) && (id < sa.getNumber()));
		this.x      = PropertiesHelper.getIntProp(p, "bs"+id+".x");
		this.y      = PropertiesHelper.getIntProp(p, "bs"+id+".y");
		assert((x >= 0) && (x <= sa.getWidth()));
		assert((y >= 0) && (y <= sa.getHeight()));
		this.id     = id;
		this.active = true;
		this.radius = PropertiesHelper.getIntProp(p, "bs"+id+".radius", "radius");
		assert(radius   >= 1);		
		this.rental = PropertiesHelper.getDoubleProp(p, "bs"+id+".rental", "rental");
		this.upkeep = PropertiesHelper.getDoubleProp(p, "bs"+id+".upkeep", "upkeep");
		assert(rental > 0.0);
		assert(upkeep > 0.0);
	}

	public ServiceArea getSA() {
		return sa;
	}

	public int getId() {
		assert(sa != null);
		assert((id >= 0) && (id < sa.getNumber()));
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

	void setActive(boolean a) {
		active = a;
	}

	public boolean isActive() {
		return active;
	}

  public int getRadius() {
		assert(radius >= 1);
    return radius;
  }	

  public double getRental() {
		assert(rental > 0.0);
    return rental;
  }	

  public double getUpkeep() {
		assert(upkeep > 0.0);
    return upkeep;
  }
  
	public double getCost() {
		// cost for this base station (zero if not active)
		double cost = 0.0;
		if (active)
			cost = rental + upkeep;
		return cost;
	}
	
	public double dist(double x, double y) {
		// distance to this base station from x,y
		return Math.sqrt((x - this.x)*(x - this.x) + (y - this.y)*(y - this.y));
	}

	public boolean isInRange(double x, double y) {
		// is x,y within the coverage area of this base station?
		return dist(x, y) <= radius;
	}

	private ServiceArea sa     = null; // service area
	private int         id     = -1;   // id within service area
	private int         x      = 0;    // x coordinate within service area
	private int         y      = 0;    // y coordinate within service area
	private boolean     active = true; // is this an active (or potential) base station?
	private int         radius = 0;    // radius of coverage area of base station
	private double      rental = 0.0;  // rental cost
	private double      upkeep = 0.0;  // upkeep cost
}
