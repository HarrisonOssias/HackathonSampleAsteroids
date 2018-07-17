package HackAsteroids;

import java.awt.Polygon;
import java.util.ArrayList;


public class Spacecraft extends VectorSprite {

	/**
	 * how long it takes to fire again
	 */
	int fireTime;
	double speedLimit;
	int lives;
	int maxHp;
	int hp;
	ArrayList<Buff> buffList;

	public Spacecraft() {
		this(40);
	}

	public Spacecraft(double speedLimit) {
		super(1, 0.1);
		maxHp = 1;
		hp = maxHp;
		this.speedLimit = speedLimit;
		lives = 1000;
		buffList = new ArrayList();
		reset();

	}

	public void fire(ArrayList<Bullet> bulletList, boolean tripleShot) {
		bulletList.add(new Bullet(drawShape.xpoints[0], drawShape.ypoints[0], angle));
		if (tripleShot) {
			bulletList.add(new Bullet(drawShape.xpoints[0], drawShape.ypoints[0], angle + 0.1));
			bulletList.add(new Bullet(drawShape.xpoints[0], drawShape.ypoints[0], angle - 0.1));
		}
		counter = 0;
	}

	public void accelerate() {
		xSpeed += Math.cos(angle) * THRUST;
		ySpeed += Math.sin(angle) * THRUST;

		double speed = Math.sqrt(xSpeed * xSpeed + ySpeed * ySpeed);
		double mlt = speedLimit * Math.tanh(speed / speedLimit);
		xSpeed = (xSpeed / speed) * mlt;
		ySpeed = (ySpeed / speed) * mlt;
	}

	public void rotateLeft() {
		angle -= ROTATION;

	}

	public void rotateRight() {
		angle += ROTATION;
	}

	public void hit() {
		active = false;
		counter = 0;
		lives--;
	}

	public void reset() {
		xPosition = Asteroids.SCREEN_WIDTH / 2;
		yPosition = Asteroids.SCREEN_HEIGHT / 2;
		xSpeed = 0;
		ySpeed = 0;
		active = true;
		angle = -Math.PI / 2;

	}

	public boolean hasBuff(Buff.BuffType buff) {
		for (Buff b : buffList) {
			if (b.type == buff) {
				return true;
			}
		}
		return false;
	}

	public Buff getBuff(Buff.BuffType buff) {
		for (Buff b : buffList) {
			if (b.type == buff) {
				return b;
			}
		}
		return null;
	}

	public void updateBuffs() {
		for (int i = 0; i < buffList.size(); i++) {
			buffList.get(i).counter--;
			if (buffList.get(i).counter < 0) {
				buffList.remove(i);
			}
		}
	}

	void addBuff(Buff.BuffType buff) {
		for (int i = 0; i < buffList.size(); i++) {
			if (buffList.get(i).type == buff) {
				buffList.remove(i);
			}
		}
		switch (buff) {
			case TRIPLE_SHOT:
				buffList.add(new Buff(buff, 500));
				break;
			case INVINCIBLE:
				buffList.add(new Buff(buff, 250));
				break;
			case SLOW_DOWN:
				buffList.add(new Buff(buff, 500));
				break;
			case START_UP:
				addBuff(Buff.BuffType.INVINCIBLE);
				addBuff(Buff.BuffType.NO_BULLET);
				break;
			case NO_BULLET:
				buffList.add(new Buff(buff, 250));
				break;
			case FASTER_ROCK:
				buffList.add(new Buff(buff, 500));
				break;
			case LOSE_LIFE:
				buffList.add(new Buff(buff, 250));
				lives--;
				break;
		}
	}

	void addBuff(Buff.BuffType buff, int time) {
		for (int i = 0; i < buffList.size(); i++) {
			if (buffList.get(i).type == buff) {
				buffList.remove(i);
			}
		}
		switch (buff) {
			case TRIPLE_SHOT:
				buffList.add(new Buff(buff, time));
				break;
			case INVINCIBLE:
				buffList.add(new Buff(buff, time));
				break;
			case SLOW_DOWN:
				buffList.add(new Buff(buff, time));
				break;
			case START_UP:
				addBuff(Buff.BuffType.INVINCIBLE);
				addBuff(Buff.BuffType.NO_BULLET);
				break;
			case NO_BULLET:
				buffList.add(new Buff(buff, time));
				break;
			case FASTER_ROCK:
				buffList.add(new Buff(buff, time));
				break;
			case LOSE_LIFE:
				buffList.add(new Buff(buff, time));
				lives--;
				break;
		}
	}

}
