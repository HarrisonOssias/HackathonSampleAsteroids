package HackAsteroids;

import java.awt.Polygon;
import java.util.ArrayList;


public class TriangleBoss extends TriangleEnemy {

	public TriangleBoss() {
		super(25, 2, 10);
		maxHp = 15;
		hp = maxHp;
	}

	@Override
	public void fire(ArrayList<Bullet> bulletList, boolean tripleShot) {
		bulletList.add(new Bullet(drawShape.xpoints[0], drawShape.ypoints[0], angle, 0, 7));
		if (tripleShot) {
			bulletList.add(new Bullet(drawShape.xpoints[0], drawShape.ypoints[0], angle + 0.1, 0, 7));
			bulletList.add(new Bullet(drawShape.xpoints[0], drawShape.ypoints[0], angle - 0.1, 0, 7));
		}
		counter = 0;
	}
}
