package monster;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import ai.SimplePathFinder;
import entity.Entity;
import main.GamePanel;
import object.OBJ_GoldCoin;
import object.OBJ_Heart;
import object.OBJ_Rock;
import object.OBJ_SilverCoin;

public class MON_GreenSlime extends Entity {
	
	SimplePathFinder spf;
	
	public MON_GreenSlime(GamePanel gp) {
		super(gp);
		type = monster_type;
		name = "Green Slime";
		defaultSpeed = 3;
		speed = defaultSpeed;
		maxLife = 10;
		life = maxLife;
		damage = 5;
		exp = 2;
		solidArea.x = 3;
		solidArea.y = 18;
		solidArea.width = 42;
		solidArea.height = 30;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		projectile = new OBJ_Rock(gp);
		
		getImage();
		
		spf = new SimplePathFinder(this, gp.player);
	}
	public void getImage() {
		up1 = setup("/monster/slime/slime_green_down_1", gp.tileSize, gp.tileSize);
		up2 = setup("/monster/slime/slime_green_down_2", gp.tileSize, gp.tileSize);
		down1 = setup("/monster/slime/slime_green_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/monster/slime/slime_green_down_2", gp.tileSize, gp.tileSize);
		left1 = setup("/monster/slime/slime_green_down_1", gp.tileSize, gp.tileSize);
		left2 = setup("/monster/slime/slime_green_down_2", gp.tileSize, gp.tileSize);
		right1 = setup("/monster/slime/slime_green_down_1", gp.tileSize, gp.tileSize);
		right2 = setup("/monster/slime/slime_green_down_2", gp.tileSize, gp.tileSize);
	}
	public void setAction() {
		
		if (onPath == true) {
			checkStopChasing(gp.player, 20, 100);
			
			searchPath(getGoalCol(gp.player), getGoalRow(gp.player));
			
		//	spf.goToTarget(speed);
			
			checkThrow(200, 0);
		} else {
			
			getRandomDirection(120);
		}
	}
	public void damageReaction() {
		actionLockCounter = 0;
		onPath = true;
	}
	public void checkDrop() {
		int i = new Random().nextInt(100) + 1;
		if (i > 50) {
			dropItem(new OBJ_SilverCoin(gp), 0);
		}
		if (i < 50) {
			dropItem(new OBJ_GoldCoin(gp), 0);
		}
	}
}
