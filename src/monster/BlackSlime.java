package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.OBJ_GoldCoin;
import object.OBJ_Rock;
import object.OBJ_SilverCoin;

public class BlackSlime extends Entity {

	public BlackSlime(GamePanel gp) {
		super(gp);
		type = monster_type;
		name = "Black Slime";
		defaultSpeed = 3;
		speed = defaultSpeed;
		maxLife = 20;
		life = maxLife;
		damage = 6;
		exp = 10;
		solidArea.x = 3;
		solidArea.y = 18;
		solidArea.width = 42;
		solidArea.height = 30;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		projectile = new OBJ_Rock(gp);
		
		getImage();
	}
	public void getImage() {
		up1 = setup("/monster/blackSlime/blackSlime_1", gp.tileSize, gp.tileSize);
		up2 = setup("/monster/blackSlime/blackSlime_2", gp.tileSize, gp.tileSize);
		down1 = setup("/monster/blackSlime/blackSlime_1", gp.tileSize, gp.tileSize);
		down2 = setup("/monster/blackSlime/blackSlime_2", gp.tileSize, gp.tileSize);
		left1 = setup("/monster/blackSlime/blackSlime_1", gp.tileSize, gp.tileSize);
		left2 = setup("/monster/blackSlime/blackSlime_2", gp.tileSize, gp.tileSize);
		right1 = setup("/monster/blackSlime/blackSlime_1", gp.tileSize, gp.tileSize);
		right2 = setup("/monster/blackSlime/blackSlime_2", gp.tileSize, gp.tileSize);
	}
	public void setAction() {
		
		if (onPath == true) {
			
			searchPath(getGoalCol(gp.player), getGoalRow(gp.player));
			
			checkThrow(200, 0);
		} else {
			
			getRandomDirection(120);
			
			checkStartChasing(gp.player, 10, 100);
		}
	}
	public void damageReaction() {
		actionLockCounter = 0;
		onPath = true;
	}
	public void checkDrop() {
		int i = new Random().nextInt(100) + 1;
		if (i > 20) {
			dropItem(new OBJ_SilverCoin(gp), 0);
		}
		if (i < 80) {
			dropItem(new OBJ_GoldCoin(gp), 0);
		}
	}
}