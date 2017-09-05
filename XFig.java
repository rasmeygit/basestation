/*
 * XFig.java
 * 
 * Dr Mark C. Sinclair, August 2017
 * 
 *  XFig 3.2 drawing class methods, initially for BaseStation problem
 */

package ec.app.basestation;

import java.io.*;

public class XFig {
	public static final int PICTWIDTH     = 13800;
	public static final int PICTHEIGHT    = 9600;
	public static final int PICTMARGIN    = 600;
	public static final int NOTFILLED     = -1;
	public static final int AREAFILLBLACK = 20;
	public static final int AREAFILLGREY1 =  2;
	public static final int AREAFILLGREY2 =  4;
	public static final int AREAFILLGREY3 =  6;
	public static final int AREAFILLWHITE =  0;
	public static final int PALATINOROMAN = 28;
	
	public static void init(PrintStream out) {
		assert(out != null);
		out.format("#FIG 3.2\nLandscape\nCenter\nMetric\nA4\n100.00\nSingle\n-2\n1200 2\n");
	}

	public static void init() {
		// default to System.out
		init(System.out);
	}

	public static void drawCircle(int thickness, double x, double y, double radius,
			XFigScale xfs, PrintStream out) {
		assert(thickness >= 1);
		drawFilledCircle(thickness, NOTFILLED, x, y, radius, xfs, out);
	}	

	public static void drawCircle(int thickness, double x, double y, double radius, XFigScale xfs) {
		// default to System.out
		drawCircle(thickness, x, y, radius, xfs, System.out);
	}

	public static void drawFilledCircle(int thickness, int areaFill, double x, double y, double radius,
			XFigScale xfs, PrintStream out) {
		assert(thickness >= 0); // allow for no edge
		assert((areaFill >= -1) && (areaFill <= 20));
		assert(x >= 0);
		assert(y >= 0);
		assert(radius >= 1);
		assert(xfs != null);
		assert(out != null);

		// scale to XFig output pixels	
		x       = xfs.xMap(x);
		y       = xfs.yMap(y);
		radius *= xfs.getXScale(); // scale with x axis

		// draw filled circle
		out.format("1 3 0 %d 0 0 0 0 %d 0.000 1 0.0000 %.0f %.0f %.0f %.0f %.0f %.0f %.0f %.0f\n",
				thickness, areaFill, x, y, radius, radius, x, y, x, y+radius);
	}
	
	public static void drawFilledCircle(int thickness, int areaFill, double x, double y, double radius,
			XFigScale xfs) {
		// default to System.out
		drawFilledCircle(thickness, areaFill, x, y, radius, xfs, System.out);
	}

	public static void drawFilledCircle(double x, double y, double radius, XFigScale xfs) {
		// default to thickness of 1, AREAFILLBLACK and System.out
			drawFilledCircle(1, AREAFILLBLACK, x, y, radius, xfs, System.out);
	}

	public static void drawFilledRectangle(int thickness, int areaFill, double x1, double y1, double x2, double y2,
			 XFigScale xfs, PrintStream out) {
		assert(thickness >= 0); // allow for no edge
		assert((areaFill >= -1) && (areaFill <= 20));
		assert(x1 >= 0);
		assert(y1 >= 0);
		assert(x2 >= 0);
		assert(y2 >= 0);
		assert(xfs != null);
		assert(out != null);

		// scale to XFig output pixels
		x1 = xfs.xMap(x1);
		y1 = xfs.yMap(y1);
		x2 = xfs.xMap(x2);
		y2 = xfs.yMap(y2);

		// draw rectangle
		out.format("2 1 0 %d 0 0 0 0 %d 0.000 0 0 -1 0 0 5\n%.0f %.0f\n", thickness, areaFill, x1, y1);
		out.format("%.0f %.0f\n", x2, y1);
		out.format("%.0f %.0f\n", x2, y2);
		out.format("%.0f %.0f\n", x1, y2);
		out.format("%.0f %.0f\n", x1, y1);
	}
	
	public static void drawFilledRectangle(int thickness, int areaFill, double x1, double y1, double x2, double y2,
			 XFigScale xfs) {
		// default to System.out
		drawFilledRectangle(thickness, areaFill, x1, y1, x2, y2, xfs, System.out);
	}

	public static void drawFilledRectangle(double x1, double y1, double x2, double y2, XFigScale xfs) {
		// default to thickness of 1, AREAFILLBLACK and System.out
		drawFilledRectangle(1, AREAFILLBLACK, x1, y1, x2, y2, xfs, System.out);
	}

	public static void drawRectangle(int thickness, double x1, double y1, double x2, double y2,
			XFigScale xfs, PrintStream out) {
		assert(thickness >= 1);
		drawFilledRectangle(thickness, NOTFILLED, x1, y1, x2, y2, xfs, out);
	}

	public static void drawRectangle(int thickness, double x1, double y1, double x2, double y2,
			XFigScale xfs) {
		// default to System.out
		drawRectangle(thickness, x1, y1, x2, y2, xfs, System.out);
	}
	
	public static void drawText(int font, int fontSize, double x, double y, String text,
			XFigScale xfs, PrintStream out) {
		assert((font >= 0) && (font <= 34));
		assert(fontSize >= 1);
		assert(x >= 0);
		assert(y >= 0);
		assert(text != null);
		assert(xfs != null);
		assert(out != null);

		// scale to XFig output pixels
		x = xfs.xMap(x);
		y = xfs.yMap(y);

		out.format("4 0 0 0 0 %d %d 0.0000 4 0 0 %.0f %.0f %s\\001\n", font, fontSize, x, y, text);
	}
	
	public static void drawText(int font, int fontSize, double x, double y, String text, XFigScale xfs) {
		// default to System.out
		drawText(font, fontSize, x, y, text, xfs, System.out);
	}
}
