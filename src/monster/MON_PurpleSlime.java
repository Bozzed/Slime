package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.OBJ_GoldCoin;
import object.OBJ_Heart;
import object.OBJ_Rock;
import object.OBJ_SilverCoin;

public class MON_PurpleSlime extends Entity {

	public MON_PurpleSlime(GamePanel gp) {
		super(gp);
		type = monster_type;
		name = "Purple Slime";
		defaultSpeed = 3;
		speed = defaultSpeed;
		maxLife = 20;
		life = maxLife;
		damage = 5;
		exp = 5;
		solidArea.x = 3;
		solidArea.y = 8;
		solidArea.width = 27;
		solidArea.height = 24;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		getImage();
	}
	public void getImage() {
		up1 = setup("/monster/slime/slime_purple_up_1", gp.tileSize, gp.tileSize);
		up2 = setup("/monster/slime/slime_purple_up_2", gp.tileSize, gp.tileSize);
		down1 = setup("/monster/slime/slime_purple_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/monster/slime/slime_purple_down_2", gp.tileSize, gp.tileSize);
		left1 = setup("/monster/slime/slime_purple_left_1", gp.tileSize, gp.tileSize);
		left2 = setup("/monster/slime/slime_purple_left_2", gp.tileSize, gp.tileSize);
		right1 = setup("/monster/slime/slime_purple_right_1", gp.tileSize, gp.tileSize);
		right2 = setup("/monster/slime/slime_purple_right_2", gp.tileSize, gp.tileSize);
	}
	public void setAction() {
		
		if (onPath == true) {
			checkStopChasing(gp.player, 20, 100);
			
			searchPath(getGoalCol(gp.player), getGoalRow(gp.player));
		} else {
			checkStartChasing(gp.player, 3, 100);
			
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