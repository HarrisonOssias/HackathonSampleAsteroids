package HackAsteroids;

public class MathHelp {

	public static double distance(VectorSprite v1, VectorSprite v2) {
		double a, b;

		a = v1.xPosition - v2.xPosition;
		b = v1.yPosition - v2.yPosition;
		return Math.sqrt(a * a + b * b);
	}

	public static double mod(double a, double b) {
		a %= b;
		if (a < 0) {
			a += b;
		}
		return a;
	}
}
