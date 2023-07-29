package monster;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import entity.Entity;
import entity.NPC_Gordon;
import main.GamePanel;
import main.UtilityTool;
import object.OBJ_GoldCoin;
import object.OBJ_Heart;

public class MON_SpiderLord extends Entity {
	
	public static final String monName = "Spider Lord";
	public MON_SpiderLord(GamePanel gp) {
		super(gp);
		type = monster_type;
		name = monName;
		defaultSpeed = 1;
		speed = defaultSpeed;
		maxLife = 50;
		life = maxLife;
		damage = 5;
		defense = 0;
		exp = 50;
		knockBackPower = 10;
		boss = true;
		sleep = true;
		
		int size = gp.tileSize * 5;
		solidArea.x = 48;
		solidArea.y = 48;
		solidArea.width = size - 48*2;
		solidArea.height = size - 48;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		attackArea.width = 170;
		attackArea.height = 170;
		
		getImage();
		setDialogue();
	}
	public void getImage() {
		int i = 2;
		up1 = setup("/monster/spider/spider_down_1", gp.tileSize*i, gp.tileSize*i);
		up2 = setup("/monster/spider/spider_down_2", gp.tileSize*i, gp.tileSize*i);
		down1 = setup("/monster/spider/spider_down_1", gp.tileSize*i, gp.tileSize*i);
		down2 = setup("/monster/spider/spider_down_2", gp.tileSize*i, gp.tileSize*i);
		left1 = setup("/monster/spider/spider_down_1", gp.tileSize*i, gp.tileSize*i);
		left2 = setup("/monster/spider/spider_down_2", gp.tileSize*i, gp.tileSize*i);
		right1 = setup("/monster/spider/spider_down_1", gp.tileSize*i, gp.tileSize*i);
		right2 = setup("/monster/spider/spider_down_2", gp.tileSize*i, gp.tileSize*i);
	}
	public void setDialogue() {
		dialogues[0][0] = "HISS!";
	}
	public void updte() {
		
	}
	public void setAction() {
		
		if (getTileDistance(gp.player) < 10) {
			moveTowardPlayer(60);
		} else {
			
			getRandomDirection(120);
		}
	}
	public void damageReaction() {
		actionLockCounter = 0;
	}
	public void checkDrop() {
		
		gp.bossBattleOn = false;
		gp.removeTempObjects();
		for (int i = 0; i < gp.npc[1].length; i++) {
			if (gp.npc[gp.currentMap][i] == null) {
			//	gp.npc[gp.currentMap][i] = new NPC_Gordon(gp);
				gp.npc[gp.currentMap][i].worldX = worldX + gp.tileSize;
				gp.npc[gp.currentMap][i].worldY = worldY + gp.tileSize;
				i++;
			}
		}
		
		int i = new Random().nextInt(100) + 1;
		if (i > 50) {
			dropItem(new OBJ_GoldCoin(gp), 0);
		}
		if (i < 50) {
			dropItem(new OBJ_Heart(gp), 0);
		}
	}
}
