package HackAsteroids;

import java.awt.Polygon;


public class TriangleEnemy extends Enemy {

	public TriangleEnemy() {
		this(40, 1, 6);

	}

	public TriangleEnemy(int fireTime, int size, double maxSpeed) {
		super(fireTime, size, maxSpeed);
		shape = new Polygon();
		shape.addPoint(20 * size, 0 * size);
		shape.addPoint(-10 * size, 10 * size);
		shape.addPoint(-10 * size, -10 * size);

		drawShape = new Polygon();
		drawShape.addPoint(20 * size, 0 * size);
		drawShape.addPoint(-10 * size, 10 * size);
		drawShape.addPoint(-10 * size, -10 * size);
	}
}
