package HackAsteroids;

import java.awt.Polygon;
import java.util.ArrayList;


public class PentaBoss extends Enemy {

	public PentaBoss() {
		super(50, 30, 3);
		maxHp = 30;
		hp = maxHp;
		shape = new Polygon();
		drawShape = new Polygon();
		for (double d = 0; d < 2 * Math.PI; d += (2 * Math.PI) / 5.0) {
			shape.addPoint((int) Math.round(size * Math.cos(d)), (int) Math.round(size * Math.sin(d)));
			drawShape.addPoint((int) Math.round(size * Math.cos(d)), (int) Math.round(size * Math.sin(d)));

		}

	}

	@Override
	public void fire(ArrayList<Bullet> bulletList, boolean tripleShot) {
		bulletList.add(new HomingBullet(drawShape.xpoints[0], drawShape.ypoints[0], angle));
		if (tripleShot) {
			bulletList.add(new HomingBullet(drawShape.xpoints[0], drawShape.ypoints[0], angle + 0.1));
			bulletList.add(new HomingBullet(drawShape.xpoints[0], drawShape.ypoints[0], angle - 0.1));
		}
		counter = 0;
	}
}

