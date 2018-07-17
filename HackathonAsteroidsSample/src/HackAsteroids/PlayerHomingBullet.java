package HackAsteroids;

public class PlayerHomingBullet extends Bullet {

	double desiredAngle;

	public PlayerHomingBullet(int x, int y, double a) {
		super(x, y, a, 7, 1, 7, 0.05);
		desiredAngle = a;
	}

	public void aim() {
		VectorSprite target = closestEnemy();
		if (target != null) {

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
	}

	public VectorSprite closestEnemy() {
		VectorSprite closestEnemy = null;
		double shortestDistance = Double.MAX_VALUE;
		for (Enemy e : Asteroids.enemyList) {
			double distance = MathHelp.distance(this, e);
			if (distance < shortestDistance) {
				shortestDistance = distance;
				closestEnemy = e;
			}

		}
		if (closestEnemy == null) {
			for (Rock r : Asteroids.rockList) {
				double distance = MathHelp.distance(this, r);
				if (distance < shortestDistance) {
					shortestDistance = distance;
					closestEnemy = r;
				}

			}
		}
		return closestEnemy;
	}

	private void rotateToPosition() {

		angle = MathHelp.mod(angle, Math.PI * 2);
		desiredAngle = MathHelp.mod(desiredAngle, Math.PI * 2);
		if (Math.abs(angle - desiredAngle) <= ROTATION) {
			angle = desiredAngle;
		} else {

			if (angle < desiredAngle) {
				if (desiredAngle - angle > Math.PI) {
					angle -= ROTATION;
				} else {
					angle += ROTATION;
				}
			} else {
				if (angle - desiredAngle > Math.PI) {
					angle += ROTATION;
				} else {
					angle -= ROTATION;
				}
			}
		}
	}

	@Override
	public void updatePosition() {
		aim();
		rotateToPosition();
		xSpeed = THRUST * Math.cos(angle);
		ySpeed = THRUST * Math.sin(angle);
		super.updatePosition();

	}
}

