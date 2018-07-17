package HackAsteroids;

import java.awt.Polygon;


public class Rock extends VectorSprite {

	int size;

	public Rock() {
		this(3);
	}

	public Rock(double x, double y, int s) {
		this(s);
		xPosition = x;
		yPosition = y;

	}

	public Rock(int s) {
		super(0, (Math.random() * 0.1 + 0.05) * (Math.random() < 0.5 ? 1 : -1) / s);
		size = s;
		setupShape();
		active = true;
		double h, a;
		h = Math.random() + 3;
		a = Math.random() * 2 * Math.PI;
		xSpeed = (h * Math.cos(a)) / size;
		ySpeed = (h * Math.sin(a)) / size;

		a = Math.random() * 2 * Math.PI;
		h = Math.random() * 200 + 80;
		xPosition = h * Math.cos(a) + Asteroids.SCREEN_WIDTH / 2;
		yPosition = h * Math.sin(a) + Asteroids.SCREEN_HEIGHT / 2;
	}

	public void updatePosition() {
		angle -= ROTATION;
		super.updatePosition();

	}

	public void updatePosition(boolean slowDown) {
		if (slowDown) {
			angle -= ROTATION / 10;
		} else {
			angle -= ROTATION;
		}

		super.updatePosition(slowDown);

	}

	private void setupShape() {
		shape = new Polygon();
		shape.addPoint(10 * size, 3 * size);
		shape.addPoint(2 * size, 10 * size);
		shape.addPoint(-15 * size, 10 * size);
		shape.addPoint(-10 * size, -15 * size);
		shape.addPoint(15 * size, -15 * size);

		drawShape = new Polygon();
		drawShape.addPoint(10 * size, 3 * size);
		drawShape.addPoint(2 * size, 10 * size);
		drawShape.addPoint(-15 * size, 10 * size);
		drawShape.addPoint(-10 * size, -15 * size);
		drawShape.addPoint(15 * size, -15 * size);

	}

}
