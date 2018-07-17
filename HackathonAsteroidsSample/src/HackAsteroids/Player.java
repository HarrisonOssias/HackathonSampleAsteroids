package HackAsteroids;

import java.awt.Polygon;
import java.util.ArrayList;


public class Player extends Spacecraft {

	int xp;
	int level;
	public int bulletTime;
	boolean homing;
	static final double NORMAL_SPEED = 25;
	static final double BOOST_SPEED = NORMAL_SPEED * 2;

	public Player() {
		fireTime = 5;
		maxHp = 4;
		hp = maxHp;
		xp = 0;
		level = 1;
		homing = false;
		bulletTime = 50;
		shape = new Polygon();
		shape.addPoint(20, 0);
		shape.addPoint(-10, 15);
		shape.addPoint(-10, -15);
		speedLimit = NORMAL_SPEED;

		drawShape = new Polygon();
		drawShape.addPoint(20, 0);
		drawShape.addPoint(-10, 15);
		drawShape.addPoint(-10, -15);
	}

	public void addXp(int xp) {
		this.xp += xp;
		if (this.xp >= level * 100) {
			this.xp -= level * 100;
			levelUp();
			addXp(0);
		}
	}

	public void levelUp() {
		level++;
		switch (level) {
			case 2:
				fireTime /= 2;
				break;
			case 3:
				maxHp++;
				hp = maxHp;
				break;
			case 4:
				bulletTime *= 2;
				break;
			case 7:
				speedLimit = BOOST_SPEED;
				break;
			case 8:
				homing = true;
				break;
			default:
				maxHp++;
				hp = maxHp;
				break;

		}
	}

	@Override
	public void reset() {
		super.reset();
		hp = maxHp;
	}

	@Override
	public void fire(ArrayList<Bullet> bulletList, boolean tripleShot) {
		if (homing) {

			bulletList.add(new PlayerHomingBullet(drawShape.xpoints[0], drawShape.ypoints[0], angle));
			if (tripleShot) {
				bulletList.add(new PlayerHomingBullet(drawShape.xpoints[0], drawShape.ypoints[0], angle + 0.1));
				bulletList.add(new PlayerHomingBullet(drawShape.xpoints[0], drawShape.ypoints[0], angle - 0.1));
			}
			counter = 0;
		} else {
			super.fire(bulletList, tripleShot);
		}
	}

}