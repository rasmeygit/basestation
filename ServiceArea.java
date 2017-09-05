/*
 * ServiceArea.java
 * 
 * Dr Mark C. Sinclair, August 2017
 * 
 * Service area for the base station problem (live programming)
 */

package ec.app.basestation;

import java.util.*;

public class ServiceArea {
	public ServiceArea(Properties p) {
		assert(p != null);

		width         = PropertiesHelper.getIntProp(p, "width");
		height        = PropertiesHelper.getIntProp(p, "height");
		number        = PropertiesHelper.getIntProp(p, "number");
		elemSize      = PropertiesHelper.getIntProp(p, "elemSize");
		penaltyRatio  = PropertiesHelper.getDoubleProp(p, "penaltyRatio");
		
		assert(width    >= 1);
		assert(height   >= 1);
		assert(number   >= 1);
		assert(elemSize >= 1);
		assert(penaltyRatio >= 0.0);
	
    // base stations
    bs = new Vector<BaseStation>();
    for (int bsid=0; bsid<number; bsid++) {
			BaseStation b = new BaseStation(this, p, bsid);
			bs.add(b);
    }

		// service elements
		se = new Vector<ServiceElement>();
		assert(width%elemSize  == 0);
		assert(height%elemSize == 0);
		nose = (width/elemSize)*(height/elemSize);
    for (int seid=0; seid<nose; seid++) {
			ServiceElement e = new ServiceElement(this, p, seid);
			se.add(e);
		}
	}

	public void setGenotype(String genotype) {
		assert(genotype.length() == number);
		for (int id=0; id<genotype.length(); id++)
			bs.elementAt(id).setActive(genotype.charAt(id) == '0' ? false : true);
	}

	public String getGenotype() {
		StringBuffer buf = new StringBuffer();
		for (BaseStation b : bs)
			buf.append(b.isActive() ? "1" : "0");
		String genotype = buf.toString();
		assert(genotype.length() == number);
		return genotype;
	}

	public int getWidth() {
		assert(width  >= 1);
 		return width;
	}

	public int getHeight() {
		assert(height >= 1);
		return height;
	}

	public int getNumber() {
		assert(number >= 1);
		return number;
	}
	
  public int getElemSize() {
		assert(elemSize >= 1);
    return elemSize;
  }

	public int getNose() {
		// check on nose omitted for efficiency
		return nose;
	}

  public double getPenaltyRatio() {
		assert(penaltyRatio >= 0.0);
    return penaltyRatio;
  }	

	BaseStation getBaseStation(int id) {
		assert((id >= 0) && (id < number));
		BaseStation b = bs.elementAt(id);
		assert(b.getId() == id);
		return b;
	}

	ServiceElement getServiceElement(int id) {
		assert((id >= 0) && (id < nose));
		ServiceElement e = se.elementAt(id);
		assert(e.getId() == id);
		return e;
	}
	
	public double getIncome() {
		double income = 0.0;
		for (ServiceElement e : se)
			income += e.getIncome();
		return income;
	}

	public double getPenalty() {
		double penalty = 0.0;
		for (ServiceElement e : se)
			penalty += e.getPenalty();
		return penalty;
	}	

	public double getCost() {
		double cost = 0.0;
		for (BaseStation b : bs)
			cost += b.getCost();
		return cost;
	}

	public double getProfit() {
		double profit = getIncome() - getPenalty() - getCost();
		return profit;
	}

	private int    width                = 0;    // width of service area
	private int    height               = 0;    // height of service area
	private int    number               = 0;    // number of potential base stations
	private int    elemSize             = 0;    // size of a service element
	private int    nose                 = 0;    // number of service elements
	private double penaltyRatio         = 0.0;  // penalty for lack of service (ratio wrt income density)
	private Vector<BaseStation>    bs   = null;
	private Vector<ServiceElement> se   = null;
}
