import java.awt.Color;

public class SeamCarver {

	private int[][] red, green, blue;
	private int[] dx = { -1, 0, 1 };
	private int[] dy = { -1, 0, 1 };
	private int width, height;
	private int END;

	// create a seam carver object based on the given picture
	public SeamCarver(Picture picture) {
		if (picture == null)
			throw new NullPointerException();
		
		red = new int[picture.width()][picture.height()];
		green = new int[picture.width()][picture.height()];
		blue = new int[picture.width()][picture.height()];
		
		width = picture.width();
		height = picture.height();
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				red[i][j] = picture.get(i, j).getRed();
				green[i][j] = picture.get(i, j).getGreen();
				blue[i][j] = picture.get(i, j).getBlue();
			}
		}
	}

	// current picture
	public Picture picture() {
		Picture picture = new Picture(width, height);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				picture.set(i, j, new Color(red[i][j], green[i][j], blue[i][j]));
			}
		}

		return picture;
	}

	// width of current picture
	public int width() {
		return width;
	}

	// height of current picture
	public int height() {
		return height;
	}

	// energy of pixel at column x and row y
	public double energy(int x, int y) {
		if (x < 0 || x > width() - 1 || y < 0 || y > height() - 1)
			throw new IndexOutOfBoundsException();

		if (x == 0 || x == width() - 1 || y == 0 || y == height() - 1)
			return 195075; // 255^2 + 255^2 + 255^2


		double rx = square(red[x - 1][y] - red[x + 1][y]);
		double gx = square(green[x - 1][y] - green[x + 1][y]);
		double bx = square(blue[x - 1][y] - blue[x + 1][y]);

		double ry = square(red[x][y - 1] - red[x][y + 1]);
		double gy = square(green[x][y - 1] - green[x][y + 1]);
		double by = square(blue[x][y - 1] - blue[x][y + 1]);

		return rx + gx + bx + ry + gy + by;
	}

	private int square(int x) {
		return x * x;
	}

	// sequence of indices for horizontal seam
	public int[] findHorizontalSeam() {

		int[] horiSeam = bfs(false);

		return horiSeam;
	}

	// sequence of indices for vertical seam
	public int[] findVerticalSeam() {

		int[] verSeam = bfs(true);

		return verSeam;
	}

	private int[] bfs(boolean isVertical) {
		END = width() * height();
		if (isVertical) {
			int[] res = new int[height()];
			int[] parent = new int[width() * height() + 1];
			double[] distance = new double[width() * height() + 1];
			boolean[] onQueue = new boolean[width() * height() + 1];

			for (int i = 0; i < width() * height() + 1; i++) {
				distance[i] = Double.POSITIVE_INFINITY;
				onQueue[i] = false;
			}

			Queue<Integer> queue = new Queue<Integer>();
			for (int i = 0; i < width(); i++) {
				distance[i] = energy(x(i), y(i));
				onQueue[i] = true;
				queue.enqueue(i);
				parent[i] = -1;
			}

			while (!queue.isEmpty()) {
				int p = queue.dequeue();
				int x = x(p), y = y(p);

				if (y < height() - 1) {
					for (int i = 0; i < 3; i++) {
						int newX = x + dx[i];
						int newY = y + 1;
						if (newX < 0 || newX > width() - 1)
							continue;
						int newP = newY * width() + newX;
						if (distance[newP] > distance[p] + energy(newX, newY)) {
							distance[newP] = distance[p] + energy(newX, newY);
							parent[newP] = p;
							if (!onQueue[newP])
								queue.enqueue(newP);
							onQueue[newP] = true;
						}
					}
				} else {
					if (distance[END] > distance[p]) {
						distance[END] = distance[p];
						parent[END] = p;
					}
				}
			}

			int i = END;
			while (parent[i] != -1) {
				i = parent[i];
				res[y(i)] = x(i);
			}

			return res;
		} else {
			int[] res = new int[width()];
			int[] parent = new int[width() * height() + 1];
			double[] distance = new double[width() * height() + 1];
			boolean[] onQueue = new boolean[width() * height() + 1];

			for (int i = 0; i < width() * height() + 1; i++) {
				distance[i] = Double.POSITIVE_INFINITY;
				onQueue[i] = false;
			}

			Queue<Integer> queue = new Queue<Integer>();
			for (int i = 0; i < height(); i++) {
				distance[width() * i] = energy(0, 0);
				parent[width() * i] = -1;
				queue.enqueue(width() * i);
				onQueue[i * width()] = true;
			}

			while (!queue.isEmpty()) {
				int p = queue.dequeue();
				int x = x(p), y = y(p);

				if (x < width() - 1) {
					for (int i = 0; i < 3; i++) {
						int newX = x + 1;
						int newY = y + dy[i];

						if (newY < 0 || newY > height() - 1)
							continue;
						int newP = newY * width() + newX;

						if (distance[newP] > distance[p] + energy(newX, newY)) {
							distance[newP] = distance[p] + energy(newX, newY);
							parent[newP] = p;
							if (!onQueue[newP])
								queue.enqueue(newP);
							onQueue[newP] = true;
						}
					}
				} else {
					if (distance[END] > distance[p]) {
						distance[END] = distance[p];
						parent[END] = p;
					}
				}
			}

			int i = END;
			while (parent[i] != -1) {
				i = parent[i];
				res[x(i)] = y(i);
			}

			return res;
		}
	}

	private int x(int id) { // column
		return id % width();
	}

	private int y(int id) { // row
		return id / width();
	}

	private void validate(int[] seam, boolean isVertical) {
		int bound;
		if (isVertical)
			bound = height();
		else
			bound = width();
		if (seam == null)
			throw new NullPointerException();
		if (seam.length != bound)
			throw new IllegalArgumentException();
		for (int i = 0; i < seam.length - 1; i++) {
			if (Math.abs(seam[i] - seam[i + 1]) > 1)
				throw new IllegalArgumentException();
		}
		if (width() <= 1 || height() <= 1)
			throw new IllegalArgumentException();

	}

	// remove horizontal seam from current picture
	public void removeHorizontalSeam(int[] seam) {
		validate(seam, false);
		int[][] r = new int[width][height - 1];
		int[][] g = new int[width][height - 1];
		int[][] b = new int[width][height - 1];
		for (int i = 0; i < seam.length; i++) {
			for (int j = 0; j < seam[i]; j++) {
				r[i][j] = red[i][j];
				g[i][j] = green[i][j];
				b[i][j] = blue[i][j];
			}

			for (int j = seam[i]; j < height - 1; j++) {
				r[i][j] = red[i][j + 1];
				g[i][j] = green[i][j + 1];
				b[i][j] = blue[i][j + 1];
			}
		}
		red = r; 
		green = g; 
		blue = b;
		height--;
	}

	// remove vertical seam from current picture
	public void removeVerticalSeam(int[] seam) {
		validate(seam, true);
		int[][] r = new int[width - 1][height];
		int[][] g = new int[width - 1][height];
		int[][] b = new int[width - 1][height];
		for (int j = 0; j < seam.length; j++) {
			for (int i = 0; i < seam[j]; i++) {
				r[i][j] = red[i][j];
				g[i][j] = green[i][j];
				b[i][j] = blue[i][j];

			}
			for (int i = seam[j]; i < width - 1; i++) {
				r[i][j] = red[i + 1][j];
				g[i][j] = green[i + 1][j];
				b[i][j] = blue[i + 1][j];

			}
		}
		red = r;
		green = g;
		blue = b;
		width--;
	}
}