package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.OBJ_GoldCoin;
import object.OBJ_SilverCoin;

public class BlueSlime extends Entity {
	
	public BlueSlime(GamePanel gp) {
		super(gp);
		type = monster_type;
		name = "Blue Slime";
		defaultSpeed = 3;
		speed = defaultSpeed;
		maxLife = 15;
		life = maxLife;
		damage = 0;
		exp = 4;
		solidArea.x = 3;
		solidArea.y = 18;
		solidArea.width = 42;
		solidArea.height = 30;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		knockBackPower = 5;
		
		getImage();
	}
	public void getImage() {
		up1 = setup("/monster/blueSlime/blueSlime_1", gp.tileSize, gp.tileSize);
		up2 = setup("/monster/blueSlime/blueSlime_2", gp.tileSize, gp.tileSize);
		down1 = setup("/monster/blueSlime/blueSlime_1", gp.tileSize, gp.tileSize);
		down2 = setup("/monster/blueSlime/blueSlime_2", gp.tileSize, gp.tileSize);
		left1 = setup("/monster/blueSlime/blueSlime_1", gp.tileSize, gp.tileSize);
		left2 = setup("/monster/blueSlime/blueSlime_2", gp.tileSize, gp.tileSize);
		right1 = setup("/monster/blueSlime/blueSlime_1", gp.tileSize, gp.tileSize);
		right2 = setup("/monster/blueSlime/blueSlime_2", gp.tileSize, gp.tileSize);
	}
	public void setAction() {
		
		if (onPath == true) {
			damage = 2;
			checkStopChasing(gp.player, 15, 100);
			
			searchPath(getGoalCol(gp.player), getGoalRow(gp.player));
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