import java.util.ArrayList;

public class PointSET {

	private SET<Point2D> set;

	public PointSET() {
		set = new SET<Point2D>();
	}

	public boolean isEmpty() {
		return set.isEmpty();
	}

	public int size() {
		return set.size();
	}

	public void insert(Point2D p) {
		if (p == null) 
			throw new NullPointerException();
		set.add(p);
	}

	public boolean contains(Point2D p) {
		if (p == null)
			throw new NullPointerException();
		return set.contains(p);
	}

	public void draw() {
		StdDraw.setPenRadius(.01);
		for (Point2D p : set) {
			p.draw();
		}
		StdDraw.setPenRadius();
	}

	public Iterable<Point2D> range(RectHV rect) {
		if (rect == null) 
			throw new NullPointerException();
		ArrayList<Point2D> res = new ArrayList<Point2D>();
		for (Point2D p : set)
			if (rect.contains(p))
				res.add(p);

		return res;
	}

	public Point2D nearest(Point2D p) {
		if (p == null) {
			throw new NullPointerException();
		}
		double minDistance = Double.MAX_VALUE;
		Point2D min = null;
		for (Point2D p1 : set) {
			if (p1.distanceSquaredTo(p) < minDistance) {
				minDistance = p1.distanceSquaredTo(p);
				min = p1;
			}
		}

		return min;
	}

	public static void main(String[] args) {
	}
}
