package object;

import entity.Projectile;
import main.GamePanel;

public class OBJ_Glock_17 extends Projectile{
	GamePanel gp;
	
	public static final String objName = "Glock 17";
	public OBJ_Glock_17(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		name = objName;
		
		description = "[" + name + "]\nFires bullets.";
		speed = 20;
		maxLife = 80;
		life = maxLife;
		attack = 10;
		bulletCost = 1;
		alive = false;
		type = projectile_type;
		price = 150;
		knockBackPower = 5;
		
		getImage();
		
		getImage();
	}
	public void getImage() {
		image = setup("/objects/glock17", gp.tileSize, gp.tileSize);
		up1 = setup("/projectile/bullet_vertical", gp.tileSize, gp.tileSize);
		up2 = setup("/projectile/bullet_vertical", gp.tileSize, gp.tileSize);
		down1 = setup("/projectile/bullet_vertical", gp.tileSize, gp.tileSize);
		down2 = setup("/projectile/bullet_vertical", gp.tileSize, gp.tileSize);
		left1 = setup("/projectile/bullet_horizonal", gp.tileSize, gp.tileSize);
		left2 = setup("/projectile/bullet_horizonal", gp.tileSize, gp.tileSize);
		right1 = setup("/projectile/bullet_horizonal", gp.tileSize, gp.tileSize);
		right2 = setup("/projectile/bullet_horizonal", gp.tileSize, gp.tileSize);
	}
}
