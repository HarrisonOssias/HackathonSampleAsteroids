package HackAsteroids;

public class Vector2D {

	double x, y;

	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;

	}

	public Vector2D scalarProduct(double s) {
		x *= s;
		y *= s;
		return this;
	}

	public Vector2D scalarQuotient(double s) {
		if (s != 0) {
			x /= s;
			y /= s;
		}
		return this;
	}

	public Vector2D unit() {
		return scalarQuotient(magnitude());
	}

	public Vector2D add(Vector2D a) {
		x += a.x;
		y += a.y;
		return this;
	}

	public static double dotProduct(Vector2D a, Vector2D b) {
		return a.x * b.x + a.y * b.y;
	}

	public double magnitude() {
		return Math.sqrt(x * x + y * y);
	}

	public static double angle(Vector2D a, Vector2D b) {
		double dot = dotProduct(a, b);
		double magnitudes = a.magnitude() * b.magnitude();
		return Math.acos(dot / magnitudes);
	}
}