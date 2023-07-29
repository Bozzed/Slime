package object;

import java.awt.Color;

import entity.Projectile;
import main.GamePanel;

public class OBJ_Rock extends Projectile {
	
	public static final String objName = "Rock";
	GamePanel gp;
	public OBJ_Rock(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		name = objName;
		speed = 5;
		maxLife = 40;
		life = maxLife;
		attack = 5;
		bulletCost = 0;
		alive = false;
		type = projectile_type;
		knockBackPower = 2;
		
		getImage();
		
        description = "[" + name + "]\nYou look at it, and the\nRock looks back at you...";
		
		price = 1;
	}
	public void getImage() {
		image = setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
		up1 = setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
		up2 = setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
		down1 = setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
		left1 = setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
		left2 = setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
		right1 = setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
		right2 = setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
	}
	public Color getParticleColor() {
		Color color = new Color(129, 129, 129);
		return color;
	}
	public int getParticleSize() {
		int size = 10;
		return size;
	}
	public int getParticleSpeed() {
		int speed = 1;
		return speed;
	}
	public int getParticleMaxLife() {
		int maxLife = 20;
		return maxLife;
	}
}
