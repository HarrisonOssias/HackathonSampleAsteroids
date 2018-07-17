package HackAsteroids;

import java.awt.Polygon;


public class MysteryBox extends VectorSprite {

	public final Buff.BuffType buff;

	public MysteryBox(double x, double y) {
		super(1 + Math.random() * 1.8, Math.random() * 0.2);
		shape = new Polygon();
		shape.addPoint(-10, -10);
		shape.addPoint(10, -10);
		shape.addPoint(10, 10);
		shape.addPoint(-10, 10);
		drawShape = new Polygon();
		drawShape.addPoint(-10, -10);
		drawShape.addPoint(10, -10);
		drawShape.addPoint(10, 10);
		drawShape.addPoint(-10, 10);
		xPosition = x;
		yPosition = y;
		angle = Math.random() * 2 * Math.PI;
		xSpeed = THRUST * angle * Math.cos(angle);
		ySpeed = THRUST * angle * Math.sin(angle);
		buff = Buff.pickBuffs[(int) (Math.random() * Buff.pickBuffs.length)];
		//buff = Buff.BuffType.TRIPLE_SHOT;

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

}
