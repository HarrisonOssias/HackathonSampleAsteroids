package HackAsteroids;


import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;

/**
 *
 * @author Computer 10
 */
public class Asteroids extends Applet implements KeyListener, ActionListener {

	public static Player[] ship;
	public static int playerCount;
	boolean beatLevel;
	int nextGame = 1;
	Timer timer;
	Image offScreen;
	Graphics offG;
	boolean[] upKey, leftKey, rightKey, fireKey;
	public static final int SCREEN_WIDTH = 1300;
	public static final int SCREEN_HEIGHT = 600;
	public static ArrayList<Rock> rockList;
	ArrayList<Bullet> playerBulletList;
	boolean dead;
	ArrayList<Bullet> enemyBulletList;
	ArrayList<Fire> fireList;
	int score;
	public static ArrayList<Enemy> enemyList;
	ArrayList<Debris> debrisList;
	ArrayList<MysteryBox> mysteryList;
	AudioClip laser, shipHit, rockHit;
	AudioClip[] thruster;
	int level = 1;
	LinkedList<Long> framelist;

	@Override
	public void init() {
		playerCount = nextGame;
		framelist = new LinkedList<>();
		leftKey = new boolean[playerCount];
		rightKey = new boolean[playerCount];
		upKey = new boolean[playerCount];
		fireKey = new boolean[playerCount];
		ship = new Player[playerCount];
		thruster = new AudioClip[playerCount];
		score = 0;

		for (int i = 0; i < playerCount; i++) {
			ship[i] = new Player();
		}

		this.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		this.addKeyListener(this);
		playerBulletList = new ArrayList();
		enemyBulletList = new ArrayList();
		beatLevel = false;
		debrisList = new ArrayList();
		enemyList = new ArrayList();
		fireList = new ArrayList();
		rockList = new ArrayList();
		mysteryList = new ArrayList();
		load();
		resetLevel();

		timer = new Timer(20, this);
		offScreen = createImage(this.getWidth(), this.getHeight());
		offG = offScreen.getGraphics();
		laser = getAudioClip(getCodeBase(), "laser80.wav");
		for (int i = 0; i < playerCount; i++) {
			thruster[i] = getAudioClip(getCodeBase(), "thruster.wav");
		}
		shipHit = getAudioClip(getCodeBase(), "explode1.wav");
		rockHit = getAudioClip(getCodeBase(), "explode0.wav");

	}

	public void load() {
		try {
			BufferedReader read = new BufferedReader(new FileReader("save.txt"));
			level = Integer.parseInt(read.readLine());
			if (playerCount == 2) {

				int xp = Integer.parseInt(read.readLine());
				ship[0].xp = xp;
				ship[1].xp = xp;

				int level = Integer.parseInt(read.readLine());
				for (int i = 1; i < level; i++) {
					ship[0].levelUp();
					ship[1].levelUp();
				}

				ship[0].lives = Integer.parseInt(read.readLine());
				ship[1].lives = Integer.parseInt(read.readLine());

			} else {
				ship[0].xp = Integer.parseInt(read.readLine());
				int level = Integer.parseInt(read.readLine());
				for (int i = 1; i < level; i++) {
					ship[0].levelUp();
				}
				ship[0].lives = Integer.parseInt(read.readLine());

			}
			read.close();
		} catch (Exception ex) {
			level = 1;
			if (playerCount == 2) {

				int xp = 0;
				ship[0].xp = xp;
				ship[1].xp = xp;
				ship[0].level = 1;
				ship[1].level = 1;
				ship[0].lives = 3;
				ship[1].lives = 3;

			} else {
				ship[0].maxHp = 4;
				ship[0].xp = 0;
				ship[0].level = 1;
				ship[0].lives = 3;
			}
		}

	}

	public void save() {
		try {
			PrintWriter write = new PrintWriter(new FileWriter("save.txt"));
			write.println(level);
			write.println(ship[0].xp);
			write.println(ship[0].level);
			write.println(ship[0].lives);

			if (playerCount == 2) {
				write.println(ship[1].lives);

			} else {
				write.println(3);
			}
			write.close();
		} catch (IOException ex) {
			Logger.getLogger(Asteroids.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void resetLevel() {
		for (int i = 0; i < playerCount; i++) {
			ship[i].reset();
			ship[i].addBuff(Buff.BuffType.START_UP);
		}
		playerBulletList.clear();
		enemyBulletList.clear();
		beatLevel = false;
		debrisList.clear();
		enemyList.clear();
		fireList.clear();
		rockList.clear();
		mysteryList.clear();
		if (level % 2 != 0) {
			for (int i = 0; i < 1 + level / 2; i++) {
				rockList.add(new Rock());
			}
		}
		for (int i = 0; i < 2 + level / 2; i++) {
			enemyList.add(new TriangleEnemy());
		}
		if (level % 2 == 0) {
			if (level >= 6) {
				enemyList.add(new FatTriangle());
			}
			if (level >= 4) {
				enemyList.add(new CircleBoss());

			}
			if (level >= 8) {
				enemyList.add(new PentaBoss());
			}

			if (level >= 2) {
				enemyList.add(new TriangleBoss());
			}
		}
		mysteryList.add(new MysteryBox(0, 0));
		dead = false;
	}

	@Override
	public void paint(Graphics g) {

		offG.setColor(Color.BLACK);
		offG.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

		for (int i = 0; i < playerCount; i++) {
			offG.setColor(Color.RED);
			if (ship[i].hasBuff(Buff.BuffType.SLOW_DOWN) && ship[i].hasBuff(Buff.BuffType.INVINCIBLE)) {
				offG.setColor(Color.CYAN);
			} else if (ship[i].hasBuff(Buff.BuffType.INVINCIBLE)) {
				Buff b = ship[i].getBuff(Buff.BuffType.INVINCIBLE);
				if (b.counter < 100 && b.counter % 2 == 0) {
					offG.setColor(Color.RED);
				} else {

					offG.setColor(Color.MAGENTA);
				}

			} else if (ship[i].hasBuff(Buff.BuffType.SLOW_DOWN)) {
				Buff b = ship[i].getBuff(Buff.BuffType.SLOW_DOWN);
				if (b.counter < 100 && b.counter % 2 == 0) {
					offG.setColor(Color.RED);
				} else {

					offG.setColor(Color.BLUE);
				}
			} else {
				offG.setColor(Color.RED);
			}

			if (ship[i].active) {
				ship[i].paint(offG);
			}
			int drawBuffPos;
			if (playerCount > 1) {
				drawBuffPos = SCREEN_WIDTH / 3 + (i * (SCREEN_WIDTH / 3));
			} else {
				drawBuffPos = SCREEN_WIDTH / 2;
			}

			String buffText = getBuffText(ship[i]);
			offG.drawString(buffText, drawBuffPos, 20);

		}

		if (dead) {
			offG.drawString("You Lose!", SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2);
		}

		offG.setColor(Color.WHITE);
		for (int i = 0; i < rockList.size(); i++) {
			rockList.get(i).paint(offG);
		}
		offG.setColor(Color.PINK);
		for (int i = 0; i < enemyList.size(); i++) {
			enemyList.get(i).paint(offG);
		}

		offG.setColor(Color.YELLOW);
		for (int i = 0; i < playerBulletList.size(); i++) {
			playerBulletList.get(i).paint(offG);
		}
		offG.setColor(Color.CYAN);
		for (int i = 0; i < enemyBulletList.size(); i++) {
			enemyBulletList.get(i).paint(offG);
		}
		offG.setColor(Color.YELLOW);
		for (int i = 0; i < mysteryList.size(); i++) {
			mysteryList.get(i).paint(offG);
		}
		offG.setColor(Color.WHITE);
		for (int i = 0; i < debrisList.size(); i++) {;
			debrisList.get(i).paint(offG);
		}
		offG.setColor(Color.ORANGE);
		for (int i = 0; i < fireList.size(); i++) {;
			fireList.get(i).paint(offG);
		}
		offG.setColor(Color.WHITE);
		offG.drawString("Level: " + level, 25, 25);
		if (beatLevel) {
			offG.drawString("You Win!", SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2);

		}
		if (playerCount > 1) {
			offG.drawString("Player 1 lives: " + ship[0].lives, 10, SCREEN_HEIGHT - 20);
			offG.drawString("Player 2 lives: " + ship[1].lives, SCREEN_WIDTH - 100, SCREEN_HEIGHT - 20);
			offG.drawString("Player 1 HP: " + ship[0].hp + "/" + ship[0].maxHp, 10, SCREEN_HEIGHT - 40);
			offG.drawString("Player 2 HP: " + ship[0].hp + "/" + ship[0].maxHp, SCREEN_WIDTH - 100, SCREEN_HEIGHT - 40);
		} else {
			offG.drawString("Lives: " + ship[0].lives, 10, SCREEN_HEIGHT - 20);
			offG.drawString("HP        : " + ship[0].hp + "/" + ship[0].maxHp, 10, SCREEN_HEIGHT - 40);

		}

		offG.drawString("Score: " + score, 10, SCREEN_HEIGHT - 30);
		offG.drawString("FPS: " + getFPS(), 25, 50);
		offG.drawString("XP: " + ship[0].xp + "/" + ship[0].level * 100, 25, 75);
		offG.drawString("XP level: " + ship[0].level, 25, 100);

		g.drawImage(offScreen, 0, 0, this);

		repaint();

	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			rightKey[0] = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			leftKey[0] = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			upKey[0] = true;
			thruster[0].loop();
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			fireKey[0] = true;
		}
		if (playerCount > 1) {
			if (e.getKeyCode() == KeyEvent.VK_D) {
				rightKey[1] = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_A) {
				leftKey[1] = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_W) {
				upKey[1] = true;
				thruster[1].loop();
			}
			if (e.getKeyCode() == KeyEvent.VK_S) {
				fireKey[1] = true;
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_1) {
			nextGame = 1;
		}
		if (e.getKeyCode() == KeyEvent.VK_2) {
			nextGame = 2;
		}
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if (beatLevel) {

				resetLevel();
				save();
			} else if (dead) {
				System.exit(0);
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_9) {
			for (Player p : ship) {
				p.addBuff(Buff.BuffType.INVINCIBLE, 1000000);
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			rightKey[0] = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			leftKey[0] = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			upKey[0] = false;
			thruster[0].stop();
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			fireKey[0] = false;
		}
		if (playerCount > 1) {
			if (e.getKeyCode() == KeyEvent.VK_D) {
				rightKey[1] = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_A) {
				leftKey[1] = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_W) {
				upKey[1] = false;
				thruster[1].loop();
			}
			if (e.getKeyCode() == KeyEvent.VK_S) {
				fireKey[1] = false;
			}
		}
	}

	public void update(Graphics g) {
		paint(g);
	}

	public int getFPS() {
		long time = System.nanoTime();
		framelist.add(time);
		while (true) {
			if (time - framelist.getFirst() > 1000000000L) {
				framelist.remove();
			} else {
				break;
			}
		}
		return framelist.size();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		keyCheck();
		respawnShip();
		boolean slowDown = false;
		boolean speedUp = false;

		for (Player s : ship) {
			slowDown = s.hasBuff(Buff.BuffType.SLOW_DOWN) || slowDown;
			speedUp = s.hasBuff(Buff.BuffType.FASTER_ROCK) || speedUp;

			s.updateBuffs();

		}

		for (Player s : ship) {
			s.updatePosition(slowDown);
		}
		for (int i = 0; i < rockList.size(); i++) {
			rockList.get(i).updatePosition(slowDown);
		}
		for (int i = 0; i < enemyList.size(); i++) {
			enemyList.get(i).updatePosition(slowDown);
			enemyList.get(i).updateBuffs();
			fireBullet(enemyList.get(i), enemyBulletList);
		}

		if (speedUp) {
			for (int i = 0; i < rockList.size(); i++) {
				rockList.get(i).updatePosition(slowDown);
			}

		}
		for (int i = 0; i < playerBulletList.size(); i++) {
			playerBulletList.get(i).updatePosition();
			if (playerBulletList.get(i).counter > ship[0].bulletTime || playerBulletList.get(i).active == false) {

				playerBulletList.remove(i);
			}
		}
		for (int i = 0; i < enemyBulletList.size(); i++) {
			enemyBulletList.get(i).updatePosition();
			if (enemyBulletList.get(i).counter > 80 || enemyBulletList.get(i).active == false) {

				enemyBulletList.remove(i);
			}
		}
		for (int i = 0; i < debrisList.size(); i++) {
			debrisList.get(i).updatePosition();
			if (debrisList.get(i).counter > 100) {
				debrisList.remove(i);
			}
		}
		for (int i = 0; i < fireList.size(); i++) {
			fireList.get(i).updatePosition();
			if (fireList.get(i).counter > 25) {
				fireList.remove(i);
			}
		}
		for (int i = 0; i < mysteryList.size(); i++) {
			mysteryList.get(i).updatePosition(slowDown);
		}

		checkCollisions();
		checkRockDestruction();
		checkEnemyDestruction();

		dead = true;
		for (Player s : ship) {
			dead = s.lives < 0 && dead;
		}

		if (rockList.isEmpty() && enemyList.isEmpty() && !beatLevel) {
			level++;
			beatLevel = true;
			for (Player s : ship) {
				s.lives++;
			}
		}
	}

	public void start() {
		timer.start();
	}

	@Override
	public void stop() {
		timer.stop();
	}

	public void keyCheck() {
		for (int i = 0; i < playerCount; i++) {
			if (rightKey[i]) {
				ship[i].rotateRight();
			}
			if (leftKey[i]) {
				ship[i].rotateLeft();
			}
			if (upKey[i] && ship[i].active) {
				ship[i].accelerate();
				fireEffect(ship[i]);
			}
			if (fireKey[i]) {
				fireBullet(ship[i], playerBulletList);

			}
		}
	}

	public void fireEffect(Player s) {
		int x = (s.drawShape.xpoints[2] + s.drawShape.xpoints[1]) / 2;
		int y = (s.drawShape.ypoints[2] + s.drawShape.ypoints[1]) / 2;
		fireList.add(new Fire(x, y, s.angle));
	}

	public boolean collision(VectorSprite object1, VectorSprite object2) {
		int x, y;

		for (int i = 0; i < object1.drawShape.npoints; i++) {
			x = object1.drawShape.xpoints[i];
			y = object1.drawShape.ypoints[i];
			if (object2.drawShape.contains(x, y)) {
				return true;
			}
		}
		for (int i = 0; i < object2.drawShape.npoints; i++) {
			x = object2.drawShape.xpoints[i];
			y = object2.drawShape.ypoints[i];
			if (object1.drawShape.contains(x, y)) {
				return true;
			}
		}
		return false;
	}

	public void checkPlayerCollisions() {
		for (Bullet b : enemyBulletList) {
			for (Player p : ship) {
				if (collision(p, b) && p.active && !p.hasBuff(Buff.BuffType.INVINCIBLE) && b.active) {
					p.hp -= level;
					p.hp -= b.damage;
					b.active = false;
					if (p.hp <= 0) {
						killPlayer(p);
					}

				}
			}
		}

	}

	public void killPlayer(Player p) {
		p.hit();
		shipHit.play();
		score -= 100;
		double debrisAmount = 5 + Math.random() * 5;
		for (int k = 0; k < debrisAmount; k++) {
			debrisList.add(new Debris(p.xPosition, p.yPosition));
		}
	}

	public void checkRockCollisions() {
		for (int i = 0; i < rockList.size(); i++) {
			double debrisAmount;
			for (int j = 0; j < playerBulletList.size(); j++) {
				if (collision(playerBulletList.get(j), rockList.get(i))) {
					playerBulletList.get(j).active = false;
					rockList.get(i).active = false;
					rockHit.play();
					score += 50;
					debrisAmount = 5 + Math.random() * 5;
					for (int k = 0; k < debrisAmount; k++) {

						debrisList.add(new Debris(rockList.get(i).xPosition, rockList.get(i).yPosition));
					}
				}

			}
			for (int j = 0; j < enemyBulletList.size(); j++) {
				if (collision(enemyBulletList.get(j), rockList.get(i))) {
					enemyBulletList.get(j).active = false;
					rockList.get(i).active = false;
					rockHit.play();
					debrisAmount = 5 + Math.random() * 5;
					for (int k = 0; k < debrisAmount; k++) {

						debrisList.add(new Debris(rockList.get(i).xPosition, rockList.get(i).yPosition));
					}
				}

			}
			for (Player s : ship) {
				if (collision(s, rockList.get(i)) && s.active && !s.hasBuff(Buff.BuffType.INVINCIBLE)) {
					killPlayer(s);
				}
			}
			for (Enemy e : enemyList) {
				if (collision(e, rockList.get(i)) && e.active && !e.hasBuff(Buff.BuffType.INVINCIBLE)) {
					e.respawn = true;
					killEnemy(e);
				}
			}
		}
	}

	public void checkCollisions() {
		checkRockCollisions();
		checkBoxCollisions();
		checkPlayerCollisions();
		checkEnemyCollisions();
		checkPlayerEnemyCollisions();
	}

	public void checkPlayerEnemyCollisions() {
		for (Enemy e : enemyList) {
			for (Player s : ship) {
				if (collision(e, s) && s.active && !s.hasBuff(Buff.BuffType.INVINCIBLE) && e.active) {
					killPlayer(s);
				}
			}

		}
	}

	public void checkEnemyCollisions() {
		for (Bullet b : playerBulletList) {
			for (Enemy e : enemyList) {
				if (collision(e, b) && !e.hasBuff(Buff.BuffType.INVINCIBLE) && b.active) {
					b.active = false;
					e.hp -= b.damage + 1;
					if (e.hp <= 0) {
						killEnemy(e);
						addXp(50 * e.size);

					}

				}
			}
		}
	}

	public void killEnemy(Enemy e) {
		e.hit();
		shipHit.play();
		score += 100;
		double debrisAmount = 5 + Math.random() * 5;
		for (int k = 0; k < debrisAmount; k++) {
			debrisList.add(new Debris(e.xPosition, e.yPosition));
		}
	}

	public void checkBoxCollisions() {
		for (int i = 0; i < mysteryList.size(); i++) {
			for (Player s : ship) {
				if (s.active && collision(s, mysteryList.get(i))) {
					s.addBuff(mysteryList.get(i).buff);
					mysteryList.remove(i);
					mysteryList.add(new MysteryBox(0, 0));
				}
			}
			for (Enemy e : enemyList) {
				if (e.active && collision(e, mysteryList.get(i))) {
					e.addBuff(mysteryList.get(i).buff);
					mysteryList.remove(i);
					mysteryList.add(new MysteryBox(0, 0));
				}
			}
		}
	}

	private void addXp(int xp) {
		for (Player s : ship) {
			s.addXp(xp);
		}

	}

	public void respawnShip() {
		for (Player s : ship) {
			if (!s.active && s.counter > 50 && isRespawnSafe() && !dead) {
				s.reset();
				s.hp = s.maxHp;
			}
		}

	}

	public boolean isRespawnSafe() {
		double x, y, h;
		for (int i = 0; i < rockList.size(); i++) {
			x = rockList.get(i).xPosition - SCREEN_WIDTH / 2;
			y = rockList.get(i).yPosition - SCREEN_HEIGHT / 2;
			h = Math.sqrt(x * x + y * y);
			if (h < 100) {
				return false;
			}
		}
		return true;
	}

	public void fireBullet(Spacecraft s, ArrayList<Bullet> bulletList) {
		if (s.counter > s.fireTime && s.active && !s.hasBuff(Buff.BuffType.NO_BULLET)) {
			s.fire(bulletList, s.hasBuff(Buff.BuffType.TRIPLE_SHOT));
			laser.play();
		}

	}

	public void checkRockDestruction() {
		for (int i = 0; i < rockList.size(); i++) {
			if (rockList.get(i).active == false) {
				if (rockList.get(i).size > 1) {
					rockList.add(new Rock(rockList.get(i).xPosition - 50, rockList.get(i).yPosition - 50, rockList.get(i).size - 1));
					rockList.add(new Rock(rockList.get(i).xPosition + 50, rockList.get(i).yPosition + 50, rockList.get(i).size - 1));
				}

				rockList.remove(i);

			}
		}

	}

	private String getBuffText(Player s) {
		String text = "";
		if (s.buffList.size() > 0) {
			if (s.buffList.get(0).type == Buff.BuffType.TRIPLE_SHOT) {
				text += "Triple shot";
			} else if (s.buffList.get(0).type == Buff.BuffType.INVINCIBLE) {
				text += "Invincibilty";
			} else if (s.buffList.get(0).type == Buff.BuffType.SLOW_DOWN) {
				text += "Slow down";
			} else if (s.buffList.get(0).type == Buff.BuffType.NO_BULLET) {
				text += "No bullets";
			} else if (s.buffList.get(0).type == Buff.BuffType.LOSE_LIFE) {
				text += "lose life";
			} else if (s.buffList.get(0).type == Buff.BuffType.FASTER_ROCK) {
				text += "Faster rocks";
			}
			for (int i = 1; i < s.buffList.size(); i++) {
				if (s.buffList.get(i).type == Buff.BuffType.TRIPLE_SHOT) {
					text += " and triple shot";
				} else if (s.buffList.get(i).type == Buff.BuffType.INVINCIBLE) {
					text += " and invincibilty";
				} else if (s.buffList.get(i).type == Buff.BuffType.SLOW_DOWN) {
					text += " and slow down";
				} else if (s.buffList.get(i).type == Buff.BuffType.NO_BULLET) {
					text += " and no bullets";
				} else if (s.buffList.get(i).type == Buff.BuffType.LOSE_LIFE) {
					text += " and lose life";
				} else if (s.buffList.get(i).type == Buff.BuffType.FASTER_ROCK) {
					text += " and faster rocks";
				}
			}
			text += "!";
		}
		return text;
	}

	private void checkEnemyDestruction() {
		for (int i = 0; i < enemyList.size(); i++) {
			if (enemyList.get(i).active == false) {
				if (enemyList.get(i).respawn == true) {
					enemyList.get(i).resetPos();
				} else {
					enemyList.remove(i);
				}

			}
		}
	}
}
