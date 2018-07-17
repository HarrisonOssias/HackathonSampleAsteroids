package HackAsteroids;


import java.awt.Polygon;
import java.util.ArrayList;

public class CircleBoss extends Enemy {

	public CircleBoss() {
		super(100, 30, 5);
		maxHp = 25;
		hp = maxHp;
		shape = new Polygon();
		drawShape = new Polygon();
		for (double d = 0; d < 2 * Math.PI; d += 0.1) {
			shape.addPoint((int) Math.round(size * Math.cos(d)), (int) Math.round(size * Math.sin(d)));
			drawShape.addPoint((int) Math.round(size * Math.cos(d)), (int) Math.round(size * Math.sin(d)));

		}
	}

	@Override
	public void fire(ArrayList<Bullet> bulletList, boolean tripleShot) {
		for (double d = 0; d < 2 * Math.PI; d += 0.1) {

			bulletList.add(new Bullet(xPosition, yPosition, d, 60, 5));
			if (tripleShot) {
				bulletList.add(new Bullet(xPosition, yPosition, d + 0.05, 60, 5));
				bulletList.add(new Bullet(xPosition, yPosition, d - 0.05, 60, 5));
			}
		}
		counter = 0;
	}
}
