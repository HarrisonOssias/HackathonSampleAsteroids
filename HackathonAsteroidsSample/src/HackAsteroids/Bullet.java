package HackAsteroids;


import java.awt.Polygon;


public class Bullet extends VectorSprite {

	int damage;

	public Bullet(double x, double y, double a) {
		this(x, y, a, 0, 0, 10, 0.05);
	}

	public Bullet(double x, double y, double a, int startLife, int damage, int maxSpeed, double rotation) {
		super(maxSpeed, rotation);
		this.damage = damage;
		shape = new Polygon();
		shape.addPoint(0, 0);
		shape.addPoint(0, 0);
		shape.addPoint(0, 0);
		drawShape = new Polygon();
		drawShape.addPoint(0, 0);
		drawShape.addPoint(0, 0);
		drawShape.addPoint(0, 0);
		xPosition = x;
		yPosition = y;
		angle = a;
		xSpeed = THRUST * Math.cos(angle);
		ySpeed = THRUST * Math.sin(angle);
		active = true;
		counter = startLife;
	}

	public Bullet(double x, double y, double a, int startLife) {
		this(x, y, a, startLife, 0, 10, 0.05);

	}

	public Bullet(double x, double y, double a, int startLife, int damage) {
		this(x, y, a, startLife, damage, 10, 0.05);

	}

}
