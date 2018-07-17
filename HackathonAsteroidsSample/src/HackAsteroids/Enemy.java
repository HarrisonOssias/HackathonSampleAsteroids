package HackAsteroids;

import java.awt.Polygon;
import java.util.ArrayList;


public class Enemy extends Spacecraft {

	double desiredAngle;
	boolean respawn;
	int size;

	public Enemy() {
		this(40, 1, 6);

	}

	public Enemy(int fireTime, int size, double maxSpeed) {
		super(maxSpeed);
		this.fireTime = fireTime;
		this.size = size;
		resetPos();
	}

	public void resetPos() {
		angle = 0;
		respawn = false;
		desiredAngle = angle;

		double h, a;
		a = Math.random() * 2 * Math.PI;
		h = Math.random() * 200 + 300;
		xPosition = h * Math.cos(a) + Asteroids.SCREEN_WIDTH / 2;
		yPosition = h * Math.sin(a) + Asteroids.SCREEN_HEIGHT / 2;
		active = true;
	}

	public void aim() {
		Player target = Asteroids.ship[closestPlayer()];
		double xDistance = target.xPosition - xPosition;
		double yDistance = target.yPosition - yPosition;
		Vector2D enemyToPlayer = new Vector2D(xDistance, yDistance);
		Vector2D xAxis = new Vector2D(1, 0);
		if (!target.active) {
			enemyToPlayer.scalarProduct(-1);
		}
		desiredAngle = Vector2D.angle(enemyToPlayer, xAxis);
		if (yDistance < 0) {
			desiredAngle = Math.PI * 2 - desiredAngle;
		}
	}

	public int closestPlayer() {
		int closestPlayer = 0;
		double shortestDistance = Double.MAX_VALUE;
		for (int i = 0; i < Asteroids.ship.length; i++) {
			double distance = MathHelp.distance(this, Asteroids.ship[i]);
			if (distance < shortestDistance) {
				shortestDistance = distance;
				closestPlayer = i;
			}

		}
		return closestPlayer;
	}

	public void updatePosition() {
		super.updatePosition();

	}

	public void move() {
		final double MIN_DIST = 100.0;
		Vector2D direction = new Vector2D(0, 0);
		for (Rock r : Asteroids.rockList) {
			Vector2D rockToEnemy = new Vector2D(xPosition - r.xPosition, yPosition - r.yPosition);
			double magnitude = rockToEnemy.magnitude();
			if (magnitude < MIN_DIST) {
				double desiredMagnitude = MIN_DIST - magnitude;
				Vector2D adjust = rockToEnemy.unit().scalarProduct(desiredMagnitude);
				direction.add(adjust);
			}

		}
		if (direction.magnitude() > 0) {
			Vector2D xAxis = new Vector2D(1, 0);

			desiredAngle = Vector2D.angle(direction, xAxis);
			if (direction.y < 0) {
				desiredAngle = Math.PI * 2 - desiredAngle;
			}

			rotateToPosition();
			if (Math.abs(-desiredAngle) < ROTATION * 5) {
				accelerate();
			}
		} else {
			aim();
			rotateToPosition();
			accelerate();
		}
	}

	public void updatePosition(boolean slowDown) {
		//if (slowDown) {
		//angle -= ROTATION / 10;
		//} else {
		//angle -= ROTATION;
		//}
		move();
		super.updatePosition(slowDown);

	}

	private void rotateToPosition() {

		angle = MathHelp.mod(angle, Math.PI * 2);
		desiredAngle = MathHelp.mod(desiredAngle, Math.PI * 2);
		if (Math.abs(angle - desiredAngle) <= ROTATION) {
			angle = desiredAngle;
		} else {

			if (angle < desiredAngle) {
				if (desiredAngle - angle > Math.PI) {
					rotateLeft();
				} else {
					rotateRight();
				}
			} else {
				if (angle - desiredAngle > Math.PI) {
					rotateRight();
				} else {
					rotateLeft();
				}
			}
		}
	}

}
