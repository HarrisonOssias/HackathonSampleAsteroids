package HackAsteroids;


import java.awt.Polygon;
import java.util.ArrayList;


public class FatTriangle extends Enemy {

	static final double NORMAL_SPEED = 10;
	static final double BOOST_SPEED = NORMAL_SPEED * 4;

	public FatTriangle() {
		super(0, 15, NORMAL_SPEED);
		maxHp = 17;
		hp = maxHp;
		shape = new Polygon();
		shape.addPoint(1 * size, 0 * size);
		shape.addPoint(-1 * size, -6 * size);
		shape.addPoint(-1 * size, 6 * size);

		drawShape = new Polygon();
		drawShape.addPoint(1 * size, 0 * size);
		drawShape.addPoint(-1 * size, -6 * size);
		drawShape.addPoint(-1 * size, 6 * size);
	}

	@Override
	public void move() {
		super.move();
		if (MathHelp.distance(this, Asteroids.ship[closestPlayer()]) < 200) {
			speedLimit = BOOST_SPEED;
		} else {
			speedLimit = NORMAL_SPEED;
		}
	}

	@Override
	public void fire(ArrayList<Bullet> bulletList, boolean tripleShot) {

	}
}
