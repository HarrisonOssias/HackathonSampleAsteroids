package HackAsteroids;


import java.awt.Polygon;

public class Fire extends VectorSprite {

	public Fire(double x, double y, double a) {
		super(0, 0);
		shape = new Polygon();
		shape.addPoint(-1, 0);
		shape.addPoint(1, 0);

		drawShape = new Polygon();
		drawShape.addPoint(-1, 0);
		drawShape.addPoint(1, 0);

		xPosition = x;
		yPosition = y;
		angle = a;
		xSpeed = 0;
		ySpeed = 0;

	}

}

