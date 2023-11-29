package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.OBJ_GoldCoin;
import object.OBJ_SilverCoin;

public class GreenSlime extends Entity {
	
	public GreenSlime(GamePanel gp) {
		super(gp);
		type = monster_type;
		name = "Green Slime";
		defaultSpeed = 3;
		speed = defaultSpeed;
		maxLife = 10;
		life = maxLife;
		damage = 0;
		exp = 2;
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
		up1 = setup("/monster/greenSlime/greenSlime_1", gp.tileSize, gp.tileSize);
		up2 = setup("/monster/greenSlime/greenSlime_2", gp.tileSize, gp.tileSize);
		down1 = setup("/monster/greenSlime/greenSlime_1", gp.tileSize, gp.tileSize);
		down2 = setup("/monster/greenSlime/greenSlime_2", gp.tileSize, gp.tileSize);
		left1 = setup("/monster/greenSlime/greenSlime_1", gp.tileSize, gp.tileSize);
		left2 = setup("/monster/greenSlime/greenSlime_2", gp.tileSize, gp.tileSize);
		right1 = setup("/monster/greenSlime/greenSlime_1", gp.tileSize, gp.tileSize);
		right2 = setup("/monster/greenSlime/greenSlime_2", gp.tileSize, gp.tileSize);
	}
	public void setAction() {
		
		if (onPath == true) {
			damage = 1;
			checkStopChasing(gp.player, 10, 100);
			
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
		if (i > 70) {
			dropItem(new OBJ_SilverCoin(gp), 0);
		}
		if (i < 30) {
			dropItem(new OBJ_GoldCoin(gp), 0);
		}
	}
}
