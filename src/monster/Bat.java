package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.OBJ_GoldCoin;
import object.OBJ_Heart;
import object.OBJ_Rock;
import object.OBJ_SilverCoin;

public class Bat extends Entity {

	public Bat(GamePanel gp) {
		super(gp);
		type = monster_type;
		name = "Bat";
		defaultSpeed = 2;
		speed = defaultSpeed;
		maxLife = 5;
		life = maxLife;
		damage = 5;
		exp = 7;
		solidArea.x = 3;
		solidArea.y = 16;
		solidArea.width = 42;
		solidArea.height = 21;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		getImage();
	}
	public void getImage() {
		up1 = setup("/monster/bat/bat_1", gp.tileSize, gp.tileSize);
		up2 = setup("/monster/bat/bat_2", gp.tileSize, gp.tileSize);
		down1 = setup("/monster/bat/bat_1", gp.tileSize, gp.tileSize);
		down2 = setup("/monster/bat/bat_2", gp.tileSize, gp.tileSize);
		left1 = setup("/monster/bat/bat_1", gp.tileSize, gp.tileSize);
		left2 = setup("/monster/bat/bat_2", gp.tileSize, gp.tileSize);
		right1 = setup("/monster/bat/bat_1", gp.tileSize, gp.tileSize);
		right2 = setup("/monster/bat/bat_2", gp.tileSize, gp.tileSize);
	}
	public void setAction() {
		
		if (onPath == true) {
			goToPlayer();
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
			dropItem(new OBJ_Heart(gp), 0);
		}
	}

}
