/*************************************************************************
 * Name:
 * Email:
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new BySlope();       // YOUR DEFINITION HERE

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        /* YOUR CODE HERE */
    	if (this.x == that.x && this.y == that.y) {
    		return Double.NEGATIVE_INFINITY;
    	} else {
    		if (this.y == that.y) {
    			return +0.0;
    		}
    		if (this.x == that.x) {
    			return Double.POSITIVE_INFINITY;
    		}
    	}
    	
    	return Double.valueOf(that.y - this.y) / Double.valueOf(that.x - this.x); 
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        /* YOUR CODE HERE */
    	if (this.y == that.y) {
    		return Integer.valueOf(this.x).compareTo(Integer.valueOf(that.x));
    	} else {
    		if (this.y > that.y) 
    			return 1;
    		else
    			return -1;
    	}
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    
    private class BySlope implements Comparator<Point> {

		@Override
		public int compare(Point u, Point v) {
			return Double.valueOf(slopeTo(u)).compareTo(Double.valueOf(slopeTo(v)));
		}
    	
    }
}
