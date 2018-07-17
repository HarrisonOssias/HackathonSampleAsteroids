package HackAsteroids;

public class Buff {

	public enum BuffType {
		SLOW_DOWN, INVINCIBLE, TRIPLE_SHOT, START_UP, LOSE_LIFE, NO_BULLET, FASTER_ROCK
	}
	public static BuffType[] pickBuffs = new BuffType[]{BuffType.SLOW_DOWN, BuffType.INVINCIBLE, BuffType.TRIPLE_SHOT, BuffType.NO_BULLET, BuffType.LOSE_LIFE, BuffType.FASTER_ROCK};
	int counter;
	BuffType type;

	public Buff(BuffType type, int counter) {
		this.type = type;
		this.counter = counter;

	}

}
