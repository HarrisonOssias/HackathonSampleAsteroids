package HackAsteroids;

public class HomingBullet extends Bullet {

	double desiredAngle;

	public HomingBullet(int x, int y, double a) {
		super(x, y, a, -25, 50, 7, 0.05);
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
