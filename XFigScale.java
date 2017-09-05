/*
  XFigScale.java

  Dr Mark C. Sinclair, July 2017

  A helper class for XFig that allows for scaling of output figs
*/

package ec.app.basestation;

public class XFigScale {
	public XFigScale(double minX, double minY, double maxX, double maxY, boolean fixedAspect) {
		assert(minX >= 0);
		assert(minY >= 0);
		assert(maxX > minX);
		assert(maxY > minY);
		this.minX        = minX;
		this.minY        = minY;
		this.maxX        = maxX;
		this.maxY        = maxY;
		this.fixedAspect = fixedAspect;
		calc();
	}

	public XFigScale(double minX, double minY, double maxX, double maxY) {
		// default to non fixed aspect
		this(minX, minY, maxX, maxY, false);
	}

	public void setWidth(int w) {
		assert(w > 0);
		width = w;
		calc();
	}

	public void setHeight(int h) {
		assert(h > 0);
		height = h;
		calc();
	}

	public void setMargin(int m) {
		assert(m > 0);
		margin = m;
		calc();
	}

	public double xMap(double x) {
		// scale and offset an x coordinate
		x += xOffset;
		x *= xScale;
		return x;
	}

	public double yMap(double y) {
		// scale and offset a y coordinate
		y += yOffset;
		y *= yScale;
		return y;
	}	

	public double getXScale() {
		assert(xScale > 0.0);
		return xScale;
	}

	public double getYScale() {
		assert(yScale > 0.0);
		return yScale;
	}

	public double getXOffset() {
		return xOffset;
	}

	public double getYOffset() {
		return yOffset;
	}

	public boolean isFixedAspect() {
		return fixedAspect;
	}
	
	private void calc() {
		xScale  = (width-(2*margin))/(maxX-minX);
		if (fixedAspect)
			yScale = xScale;
		else
			yScale = (height-(2*margin))/(maxY-minY);
		xOffset = -minX+(margin/xScale);
		yOffset = -minY+(margin/yScale);
		//System.out.format("xfs = (%f, %f, %f, %f)\n",
		//									xOffset, yOffset, xScale, yScale);
	}

	private int     width   = XFig.PICTWIDTH;
	private int     height  = XFig.PICTHEIGHT;
	private int     margin  = XFig.PICTMARGIN;
	private double  xScale  = 1.0;
	private double  yScale  = 1.0;
	private double  xOffset = 0.0;
	private double  yOffset = 0.0;
	private double  minX;
	private double  minY;
	private double  maxX;
	private double  maxY;
	private boolean fixedAspect = false;
}
