package HackAsteroids;


import java.awt.Polygon;

public class Debris extends VectorSprite {

	public Debris(double x, double y) {
		super(1, 0);
		shape = new Polygon();
		shape.addPoint(5, 4);
		shape.addPoint(-4, -5);
		shape.addPoint(-1, 3);
		shape.addPoint(3, 1);
		shape.addPoint(2, 0);

		drawShape = new Polygon();
		drawShape.addPoint(5, 4);
		drawShape.addPoint(-4, -5);
		drawShape.addPoint(-1, 3);
		drawShape.addPoint(3, 1);
		drawShape.addPoint(2, 0);
		xPosition = x;
		yPosition = y;
		angle = Math.random() * 2 * Math.PI;
		xSpeed = angle * Math.cos(angle);
		ySpeed = angle * Math.sin(angle);

	}

}
