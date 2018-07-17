package HackAsteroids;


import java.awt.*;


public class VectorSprite {

	double xPosition, yPosition;
	double xSpeed, ySpeed;
	double angle;
	Polygon shape, drawShape;
	final double ROTATION;
	final double THRUST;
	boolean active;
	int counter;

	public VectorSprite(double thrust, double rotation) {
		THRUST = thrust;
		ROTATION = rotation;

	}

	public void paint(Graphics g) {
		g.drawPolygon(drawShape);
	}

	public void updatePosition() {
		counter++;
		xPosition += xSpeed;
		yPosition += ySpeed;
		wrapAround();
		int x, y;
		for (int i = 0; i < shape.npoints; i++) {
			//shape.xpoints[i] += xSpeed;
			//shape.ypoints[i] += ySpeed;
			x = (int) Math.round(shape.xpoints[i] * Math.cos(angle) - shape.ypoints[i] * Math.sin(angle));
			y = (int) Math.round(shape.xpoints[i] * Math.sin(angle) + shape.ypoints[i] * Math.cos(angle));
			drawShape.xpoints[i] = x;
			drawShape.ypoints[i] = y;

		}
		drawShape.invalidate();
		drawShape.translate((int) Math.round(xPosition), (int) Math.round(yPosition));

	}

	private void wrapAround() {
		if (xPosition > Asteroids.SCREEN_WIDTH) {
			xPosition = 0;
		}
		if (yPosition > Asteroids.SCREEN_HEIGHT) {
			yPosition = 0;
		}
		if (xPosition < 0) {
			xPosition = Asteroids.SCREEN_WIDTH;
		}

		if (yPosition < 0) {
			yPosition = Asteroids.SCREEN_HEIGHT;
		}

	}

	public void updatePosition(boolean slowDown) {
		counter++;
		if (slowDown) {
			xPosition += xSpeed / 10;
			yPosition += ySpeed / 10;
		} else {
			xPosition += xSpeed;
			yPosition += ySpeed;
		}

		wrapAround();
		int x, y;
		for (int i = 0; i < shape.npoints; i++) {
			x = (int) Math.round(shape.xpoints[i] * Math.cos(angle) - shape.ypoints[i] * Math.sin(angle));
			y = (int) Math.round(shape.xpoints[i] * Math.sin(angle) + shape.ypoints[i] * Math.cos(angle));
			drawShape.xpoints[i] = x;
			drawShape.ypoints[i] = y;

		}
		drawShape.invalidate();
		drawShape.translate((int) Math.round(xPosition), (int) Math.round(yPosition));

	}
}
