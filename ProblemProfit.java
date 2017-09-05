/*
 * ProblemProfit.java
 * 
 * Dr Mark C. Sinclair, August 2017
 * 
 * Assess ServiceArea profit for the base station problem (live programming)
 */

package ec.app.basestation;

import java.io.*;
import java.util.*;

public class ProblemProfit {
	public static void main(String[] argv) {
    if (argv.length != 2) {
      System.err.println("usage: java ProblemProfit prbFilename genotype");
      System.exit(1);
    }

    // prbProp
    if (!argv[0].endsWith(".prb"))
      throw new IllegalArgumentException("prbFilename not .prb file");
    FileInputStream prbStream = null;
    Properties      prbProp   = null;
    try {
      prbStream = new FileInputStream(argv[0]);
      prbProp   = new Properties();
      prbProp.load(prbStream);
    } catch(Exception e) {
      throw new RuntimeException("cannot load properties from " +
          argv[0] + " (" + e.getMessage() + ")");
    }

		// create ServiceArea
		ServiceArea sa  = new ServiceArea(prbProp);

		// check genotype length
		String genotype = argv[1];
		if (genotype.length() != sa.getNumber()) {
			System.err.println("genotype length does not match number in .prb file");
      System.exit(1);
		}

		// assess it
		sa.setGenotype(genotype);
		System.out.format("income:  %.0f\n", sa.getIncome());
		System.out.format("penalty: %.0f\n", sa.getPenalty());
		System.out.format("cost:    %.0f\n", sa.getCost());
		System.out.format("profit:  %.0f\n", sa.getProfit());
	}
}
