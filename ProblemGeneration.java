/*
 * ProblemGeneration.java
 * 
 * Dr Mark C. Sinclair, August 2017
 * 
 * Problem generation for the base station problem (live programming)
 */

package ec.app.basestation;

import java.io.*;
import java.util.*;

public class ProblemGeneration {
	public static void main(String argv[]) throws IOException {
		if (argv.length != 3) {
			System.err.println("usage: java ProblemGeneration genFilename prbFilename seed");
			System.exit(1);
		}

		// genProp
		if (!argv[0].endsWith(".gen"))
			throw new IllegalArgumentException("genFilename not .gen file");
		FileInputStream genStream = null;
		Properties genProp = null;
		try {
			genStream = new FileInputStream(argv[0]);
			genProp = new Properties();
			genProp.load(genStream);
		} catch (Exception e) {
			throw new RuntimeException("cannot load properties from " + argv[0] + " (" + e.getMessage() + ")");
		}

		// prbProp
		if (!argv[1].endsWith(".prb"))
			throw new IllegalArgumentException("prbFilename not .prb file");
		FileOutputStream prbStream = null;
		Properties prbProp = null;
		try {
			prbStream = new FileOutputStream(argv[1]);
			prbProp = new Properties();
		} catch (Exception e) {
			prbStream.close();
			throw new RuntimeException("cannot open " + argv[1] + " (" + e.getMessage() + ")");
		}

		// seed
		long seed = 0L;
		try {
			seed = Long.parseLong(argv[2]);
		} catch (NumberFormatException nfe) {
			throw new RuntimeException("seed not a long");
		}

		System.out.println("genFilename:  " + argv[0]);
		System.out.println("prbFilename:  " + argv[1]);
		System.out.println("seed:         " + seed);

		prbProp.setProperty("genFilename", argv[0]);
		prbProp.setProperty("prbFilename", argv[1]);
		prbProp.setProperty("seed", "" + seed);

		Random rnd = new Random(seed);
		ProblemGeneration pg = new ProblemGeneration(genProp, prbProp, rnd);

		try {
			prbProp.store(prbStream, "ProblemGeneration by Dr Mark C. Sinclair");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ProblemGeneration(Properties genProp, Properties prbProp, Random rnd) {
		assert(genProp != null);
		assert(prbProp != null);
		assert(rnd != null);

		// get problem generation properties
		width        = PropertiesHelper.getIntProp(genProp, "width");
		height       = PropertiesHelper.getIntProp(genProp, "height");
		number       = PropertiesHelper.getIntProp(genProp, "number");
    radius       = PropertiesHelper.getIntProp(genProp, "radius");
    radiusSigma  = PropertiesHelper.getIntProp(genProp, "radiusSigma");		
    rental       = PropertiesHelper.getDoubleProp(genProp, "rental");
    rentalSigma  = PropertiesHelper.getDoubleProp(genProp, "rentalSigma");
		elemSize     = PropertiesHelper.getIntProp(genProp, "elemSize");
    density      = PropertiesHelper.getDoubleProp(genProp, "density");
    densitySigma = PropertiesHelper.getDoubleProp(genProp, "densitySigma");

		// copy through problem generation properties
		prbProp.putAll(genProp);

    // generate base station locations, radii & rentals
		assert(radius > 1);
		assert(radiusSigma >= 0);
		assert(rental > 0.0);
		assert(rentalSigma >= 0.0);
		
		for (int bsid = 0; bsid < number; bsid++) {
			//locations
			int x = rnd.nextInt(width + 1);
			int y = rnd.nextInt(height + 1);
			assert ((x >= 0) && (x <= width));
			assert ((y >= 0) && (y <= height));
			prbProp.setProperty("bs"+bsid+".x", Integer.toString(x));
			prbProp.setProperty("bs"+bsid+".y", Integer.toString(y));

			// radii
			double rad = radius + rnd.nextGaussian() * radiusSigma;
			if (rad <= 1.0) rad = 1.0; // disallow negative or zero radius
			prbProp.setProperty("bs"+bsid+".radius", Long.toString(Math.round(rad)));

			// rentals
			double rent = rental + rnd.nextGaussian() * rentalSigma;
			if (rent <= 0.0) rent = 0.01 * rentalSigma; // disallow negative or zero rent
			prbProp.setProperty("bs"+bsid+".rental", Double.toString(rent));
		}
		
		// generate service element densities
		assert(elemSize >= 1);
		assert(width%elemSize  == 0);
		assert(height%elemSize == 0);
		nose = (width/elemSize)*(height/elemSize);
		assert(density > 0.0);
		assert(densitySigma >= 0.0);
		
		for (int seid=0; seid<nose; seid++) {
			double den = density + rnd.nextGaussian() * densitySigma;
			if (den <= 0.0) den = 0.01 * densitySigma; // disallow negative or zero density
			prbProp.setProperty("se"+seid+".density", Double.toString(den));
		}
	}

	private int    width        = 0;   // width of service area
	private int    height       = 0;   // height of service area
	private int    number       = 0;   // number of potential base station locations
	private int    radius       = 0;   // radius of coverage area of base station
	private int    radiusSigma  = 0;   // standard deviation of radius of coverage of base stations
	private double rental       = 0.0; // default rental cost of a base station
	private double rentalSigma  = 0.0; // standard deviation of rental cost of base stations
	private int    elemSize     = 0;   // size of a service element
	private int    nose         = 0;   // number of service elements
	private double density      = 0.0; // default income density of a service element
	private double densitySigma = 0.0; // standard deviation of income density of service elements
}
