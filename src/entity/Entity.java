package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class Entity {
	
	public GamePanel gp;
	public int worldX, worldY;
	public int defaultSpeed = 1;
	public int speed = defaultSpeed;
	public int damage;
	public ArrayList<Entity> inventory = new ArrayList<>();
	public int maxInventorySize = 20;
	public Entity linkedEntity;
	Entity attacker;
	
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
	public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2,
	gaurdDown, gaurdUp, gaurdLeft, gaurdRight;
	public String direction = "down";
	public boolean gaurd = false;

	public int solidAreaX = 8;
	public int solidAreaY = 16;
	
	public int solidAreaDefaultX = 8;
	public int solidAreaDefaultY = 16;
	public int shotAvailableCounter = 0;
	
	public int spriteNum = 1;
	public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
	public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
	public boolean collisionOn = false;
	public boolean invincible = false;
	public int invincibleCounter = 0;
	public boolean attacking = false;
	public String name;
	public BufferedImage image, image2, image3;
	public boolean collision = false;
	public String dialogues[][] = new String[20][20];
	public int dialogueIndex = 0;
	public boolean knockBack = false;
	public String knockBackDirection;
	public boolean boss;
	public boolean hpBarOn;
	public boolean temp;
	public boolean sleep = false;
	public boolean dropped = false;
	public boolean flying = false;
	
	// COUNTER
	public int actionLockCounter = 0;
	public int spriteCounter = 0;
	int dyingCounter = 0;
	public int knockBackCounter = 0;
	public int hpBarCounter = 0;
	public int bobCounter = 0;
	
	// STATUS
	public int dialogueSet = 0;
	public boolean stackable = false;
	public int amount = 1;
	Entity loot;
	public boolean onPath = false;
	public boolean alive = true;
	public boolean dying = false;
	public int maxLife;
	public int life;
	public int maxMana;
	public int mana;
	public int level;
	public int strength;
	public int dexterity;
	public int attack;
	public int defense;
	public int exp;
	public int nextLevelExp;
	public int money;
	public Entity hand;
	public Projectile projectile;
	public Entity currentLight;
	public boolean drawing = true;
	int xd;

	public boolean attackCancelled = false;
	public boolean pickupable = true;
	
	// VALUES
	public int value;
	public int attackValue;
	public int defenseValue;
	public String description = "";
	public int bulletCost;
	public int price;
	public int lightRadius;
	protected int knockBackPower = 0;
	public Entity loot1 = null;
	public Entity loot2;
	public boolean open;
	
	// TYPE
	public int type; // 0 = player, 1 = npc, 2 = alien, 3 = weapon, 4 = shield
	public final int player_type = 0;
	public final int npc_type = 1;
	public final int monster_type = 2;
	public final int melee_type = 3;
	public final int shield_type = 4;
	public final int axe_type = 5;
	public final int consumable_type = 6;
	public final int decor_type = 6;
	public final int pickupOnly_type = 7;
	public final int projectile_type = 8;
	public final int obstacle_type = 9;
	public final int light_type = 10;
	public final int pickaxe_type = 11;
	public final int broadsword_type = 12;
	public final int material_type = 13;
	
	public Entity craftMat1, craftMat2;
	
	public Entity(GamePanel gp) {
		this.gp = gp;
	}
	public void checkDrop() {}
	public void dropItem(Entity droppedItem, int xdir) {
		for (int i = 0; i < gp.obj[1].length; i++) {
			if (gp.obj[gp.currentMap][i] == null) {
				gp.obj[gp.currentMap][i] = droppedItem;
				gp.obj[gp.currentMap][i].xd = xdir;
				gp.obj[gp.currentMap][i].worldX = worldX;
				gp.obj[gp.currentMap][i].worldY = worldY;
				gp.obj[gp.currentMap][i].dropped = true;
				break;
			}
		}
	}
	public void checkStopChasing(Entity target, int distance, int rate) {
		if (getTileDistance(target) > distance) {
			int i = new Random().nextInt(rate);
			if (i == 0) {
				onPath = false;
			}
		}
	}
	public void checkStartChasing(Entity target, int distance, int rate) {
		if (getTileDistance(target) < distance) {
			int i = new Random().nextInt(rate);
			if (i == 0) {
				onPath = true;
			}
		}
	}
	public void attacking() {
		if (attackCancelled == false) {
			if (hand.type == projectile_type) {
				((Projectile) hand).set(worldX, worldY, direction, true, this);
	    		attacking = false;
	    		
	    		for (int i = 0; i < gp.projectile[1].length; i++) {
	    			if (gp.projectile[gp.currentMap][i] == null) {
	    				gp.projectile[gp.currentMap][i] = ((Projectile) hand);
	    				break;
	    			}
	    		}
			} else {
				spriteCounter++;
				if (spriteCounter <= 5) {
					spriteNum = 1;
				}
				if (spriteCounter > 5 && spriteCounter <= 25) {
					spriteNum = 2;
					int currentWorldX = worldX;
					int currentWorldY = worldY;
					int solidAreaWidth = solidArea.width;
					int solidAreaHeight = solidArea.height;
					
					switch(direction) {
					case "up": worldY -= attackArea.height; break;
					case "down": worldY += attackArea.height; break;
					case "left": worldX -= attackArea.width; break;
					case "right": worldX += attackArea.width; break;
					}
					
					solidArea.width = attackArea.width;
					solidArea.height = attackArea.height;
					
					if (type == monster_type) {
						attack = damage - gp.player.defense;
						if (gp.cChecker.checkPlayer(this) == true) {
							damagePlayer(attack);
							setKnockBack(gp.player, this, knockBackPower);
						}
					} else {
						int alienIndex = gp.cChecker.checkEntity(this, gp.monster);
						gp.player.damageMonster(alienIndex, this, attack, hand.knockBackPower);
						
						int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
						gp.player.damageInteractiveTile(iTileIndex);
						
						int projectileIndex = gp.cChecker.checkEntity(this, gp.projectile);
						gp.player.damageProjectile(projectileIndex);
					}
					
					worldX = currentWorldX;
					worldY = currentWorldY;
					solidArea.width = solidAreaWidth;
					solidArea.height = solidAreaHeight;
				}
				if (spriteCounter > 25) {
					spriteNum = 1;
					spriteCounter = 0;
					attacking = false;
				}
			}
		}
	}
	public void checkAttack(int rate, int straight, int horizontal) {
		boolean targetInRange = false;
		int xDis = getXDistance(gp.player);
		int yDis = getYDistance(gp.player);
		
		switch(direction) {
		case "up":
			if (gp.player.worldY < worldY && yDis < straight && xDis < horizontal) {
				targetInRange = true;
			}
			break;
		case "down":
			if (gp.player.worldY > worldY && yDis < straight && xDis < horizontal) {
				targetInRange = true;
			}
			break;
		case "left":
			if (gp.player.worldY < worldY && xDis < straight && yDis < horizontal) {
				targetInRange = true;
			}
			break;
		case "right":
			if (gp.player.worldY > worldY && xDis < straight && yDis < horizontal) {
				targetInRange = true;
			}
			break;
		}
		if (targetInRange == true) {
			int i = new Random().nextInt(rate);
			if (i == 0) {
				attacking = true;
				spriteNum = 1;
				spriteCounter = 0;
				shotAvailableCounter = 0;
			}
		}
	}
	public void getRandomDirection(int interval) {
		actionLockCounter++;
		if (actionLockCounter > interval) {
			Random random = new Random();
			int i = random.nextInt(100)+1;
			
			if (i <= 25) {
				direction = "up";
			}
			if (i > 25 && i <= 50) {
				direction = "down";
			}
			if (i > 50 && i <= 75) {
				direction = "left";
			}
			if (i > 75 && i <= 100) {
				direction = "right";
			}
			actionLockCounter = 0;
		}
	}
	public void checkThrow(int rate, int shotInterval) {

		int i = new Random().nextInt(rate);
		if (i == 0 && projectile.alive == false && shotAvailableCounter == shotInterval) {
			
			projectile.set(worldX, worldY, direction, true, this);
			
			for (int ii = 0; ii < gp.projectile[1].length; ii++) {
    			if (gp.projectile[gp.currentMap][ii] == null) {
    				gp.projectile[gp.currentMap][ii] = projectile;
    				break;
    			}
    		}
			
			shotAvailableCounter = 0;
		}
	}
	public int getXDistance(Entity target) {
		int xDistance = Math.abs(getCenterX() - target.getCenterX());
		return xDistance;
	}
	public int getYDistance(Entity target) {
		int yDistance = Math.abs(getCenterY() - target.getCenterY());
		return yDistance;
	}
	public int getCenterX() {
		int centerX = worldX + left1.getWidth()/2;
		return centerX;
	}
	public int getCenterY() {
		int centerY = worldY + up1.getHeight()/2;
		return centerY;
	}
	public int getTileDistance(Entity target) {
		int tileDistance = (getXDistance(target) + getYDistance(target))/gp.tileSize;
		return tileDistance;
	}
	public int getGoalCol(Entity target) {
		int goalCol = (target.worldX + target.solidArea.x)/gp.tileSize;
		return goalCol;
	}
	public int getGoalRow(Entity target) {
		int goalRow = (target.worldY + target.solidArea.y)/gp.tileSize;
		return goalRow;
	}
	public void setAction() {}
	public void damageReaction() {}
	public void use(Entity entity) {}
	public void speak() {
		
	}
	public void facePlayer() {
		switch(gp.player.direction) {
		case "up":direction = "down";break;
		case "down":direction = "up";break;
		case "left":direction = "right";break;
		case "right":direction = "left";break;
		}
	}
	public void startDialogue(Entity entity, int setNum) {
		gp.gameState = gp.dialogueState;
		gp.ui.npc = entity;
		dialogueSet = setNum;
	}
	public void setDialogue() {}
	public void damagePlayer(int attack) {
		if (gp.player.invincible == false) {
			int damage = attack - gp.player.defense;
			if (damage < 0) {
				damage = 0;
			}
			gp.player.life -= damage;
			
			gp.ui.addMessage("Took " + damage + " damage!");
		}
	}
	public void update() {
		if (sleep == false) {
			if (knockBack == true) {
				checkCollision();
				
				if (collisionOn == true) {
					knockBackCounter = 0;
					knockBack = false;
					speed = defaultSpeed;
				} else if (collisionOn == false) {
					switch (knockBackDirection) {
		    		case "up": worldY -= speed; break;
		    		case "down": worldY += speed; break;
		    		case "left": worldX -= speed; break;
		    		case "right": worldX += speed; break;
		    		}
				}
				knockBackCounter++;
				if (knockBackCounter == 10) {
					knockBackCounter = 0;
					knockBack = false;
					speed = defaultSpeed;
				}
			} else if (attacking == true) {
				checkCollision();
				if (hand != null) {
					attacking();
				}
			} else {
			
				setAction();
				checkCollision();
				
				if (collisionOn == false) {
		    		switch (direction) {
		    		case "up": worldY -= speed; break;
		    		case "down": worldY += speed; break;
		    		case "left": worldX -= speed; break;
		    		case "right": worldX += speed; break;
		    		}
		    	}
				if (dropped == true) {
					jump();
				}
				spriteCounter++;
		    	if (spriteCounter > 10) {
		    		if (spriteNum == 1) {
		    			spriteNum = 2;
		    		} else if (spriteNum == 2) {
		    			spriteNum = 1;
		    		}
		    		spriteCounter = 0;
		    	}
			}
	    	if (invincible == true && dying == false) {
	    		invincibleCounter++;
	    		if (invincibleCounter > 20) {
	    			invincible = false;
	    			invincibleCounter = 0;
	    		}
	    	}
		}
	}
	public void jump() {
		bobCounter++;
		worldX += xd;
		if (bobCounter < 10) {
			worldY -= 2;
		}
		if (bobCounter > 10) {
			worldY += 2;
		}
		if (bobCounter == 20) {
			dropped = false;
		}
	}
	public void goToPlayer() {
		if (gp.player.worldX > worldX) {
			worldX += 16;
		}
		if (gp.player.worldX < worldX) {
			worldX -= 16;
		}
		if (gp.player.worldY > worldY) {
			worldY += 16;
		}
		if (gp.player.worldY < worldY) {
			worldY -= 16;
		}
		if (worldX == gp.player.worldX && worldY == gp.player.worldY) {
			use(gp.player);
		}
	}
	public void setKnockBack(Entity target, Entity attacker, int knockBackPower) {
		this.attacker = attacker;
		target.knockBackDirection = attacker.direction;
		target.knockBack = true;
		target.speed += knockBackPower;
	}
	public int getDetected(Entity user, Entity target[][], String targetName) {
		int index = 999;
		
		int nextWorldX = user.getLeftX();
		int nextWorldY = user.getTopY();
		
		switch (user.direction) {
		case "up": nextWorldY = user.getTopY()-1; break;
		case "down": nextWorldY = user.getBottomY()+1;break;
		case "left": nextWorldX = user.getLeftX()-1; break;
		case "right": nextWorldX = user.getRightX()+1;break;
		}
		int col = nextWorldX/gp.tileSize;
		int row = nextWorldY/gp.tileSize;
		
		for (int i = 0; i < target[1].length; i++) {
			if (target[gp.currentMap][i] != null) {
				if (target[gp.currentMap][i].getCol() == col 
						&& target[gp.currentMap][i].getRow() == row 
						&& target[gp.currentMap][i].name.equals(targetName)) {
					index = i;
					break;
				}
			}
		}
		return index;
	}
	public int getLeftX() {
		return worldX + solidArea.x;
	}
	public int getRightX() {
		return worldX * solidArea.x + solidArea.width;
	}
	public int getTopY() {
		return worldY * solidArea.y;
	}
	public int getBottomY() {
		return worldY * solidArea.y + solidArea.height;
	}
	public int getCol() {
		return (worldX + solidArea.x)/gp.tileSize;
	}
	public int getRow() {
		return (worldY + solidArea.y)/gp.tileSize;
	}
	public void draw(Graphics2D g2) {
		BufferedImage image = null;
		BufferedImage shadow = setup("/npc/shadow", gp.tileSize, gp.tileSize);
		
		if (inCamera() == true) {
			int tempScreenX = getScreenX();
			int tempScreenY = getScreenY();
			
			switch(direction) {
			case "up":
				if (attacking == false) {
					if (spriteNum == 1) {image = up1;}
					if (spriteNum == 2) {image = up2;}
				}
				if (attacking == true) {
					tempScreenY = getScreenY() - up1.getHeight();
					if (spriteNum == 1) {image = attackUp1;}
					if (spriteNum == 2) {image = attackUp2;}
				}
				break;
			case "down":
				if (attacking == false) {
					if (spriteNum == 1) {image = down1;}
					if (spriteNum == 2) {image = down2;}
				}
				if (attacking == true) {
					if (spriteNum == 1) {image = attackDown1;}
					if (spriteNum == 2) {image = attackDown2;}
				}
				break;
			case "left":
				if (attacking == false) {
					if (spriteNum == 1) {image = left1;}
					if (spriteNum == 2) {image = left2;}
				}
				if (attacking == true) {
					tempScreenX = getScreenX() - left1.getWidth();
					if (spriteNum == 1) {image = attackLeft1;}
					if (spriteNum == 2) {image = attackLeft2;}
				}
				break;
			case "right":
				if (attacking == false) {
					if (spriteNum == 1) {image = right1;}
					if (spriteNum == 2) {image = right2;}
				}
				if (attacking == true) {
					if (spriteNum == 1) {image = attackRight1;}
					if (spriteNum == 2) {image = attackRight2;}
				}
				break;
			}
			if (gp.player.screenX > gp.player.worldX) {
				tempScreenX = worldX;
			}
			if (gp.player.screenY > gp.player.worldY) {
				tempScreenY = worldY;
			}
			int rightOffset = gp.screenWidth - gp.player.screenX;
			if (rightOffset > gp.worldWidth - gp.player.worldX) {
				tempScreenX = gp.screenWidth - (gp.worldWidth - worldX);
			}
			
			int bottomOffset = gp.screenHeight - gp.player.screenY;
			if (bottomOffset > gp.worldHeight - gp.player.worldY) {
				tempScreenY = gp.screenHeight - (gp.worldHeight - worldY);
			}
			
			if (dying == true) {
				dyingAnimation(g2);
			}
			else if (invincible == true) {
				changeAlpha(g2, 0.6f);
			}
			if (flying == true) {
				g2.drawImage(image, tempScreenX, tempScreenY - (gp.tileSize/2), null);
				changeAlpha(g2, 0.5f);
				g2.drawImage(shadow, tempScreenX, tempScreenY, null);
				changeAlpha(g2, 1f);
			} else {
				g2.drawImage(image, tempScreenX, tempScreenY, null);
			}
			changeAlpha(g2, 1f);
		}
	}
	public int getScreenX() {
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		return screenX;
	}
	public int getScreenY() {
		int screenY = worldY - gp.player.worldY + gp.player.screenY;
		return screenY;
	}
	public boolean inCamera() {
		boolean inCamera = false;
		if (worldX + gp.tileSize*5 > gp.player.worldX - gp.player.screenX && 
				worldX - gp.tileSize < gp.player.worldX + gp.player.screenX && 
				worldY + gp.tileSize*5 > gp.player.worldY - gp.player.screenY && 
				worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
			inCamera = true;
		}
		return inCamera;
	}
	public void moveTowardPlayer(int interval) {
		actionLockCounter++;
		if (actionLockCounter > interval) {
			if (getXDistance(gp.player) > getYDistance(gp.player)) {
				if (gp.player.getCenterX() < getCenterX()) {
					direction = "left";
				} else {
					direction = "right";
				}
			} else if (getXDistance(gp.player) < getYDistance(gp.player)) {
				if (gp.player.getCenterY() < getCenterY()) {
					direction = "up";
				} else {
					direction = "down";
				}
			}
			actionLockCounter = 0;
		}
	}
	public Color getParticleColor() {
		Color color = null;
		return color;
	}
	public int getParticleSize() {
		int size = 0;
		return size;
	}
	public int getParticleSpeed() {
		int speed = 0;
		return speed;
	}
	public int getParticleMaxLife() {
		int maxLife = 0;
		return maxLife;
	}
	public void generateParticle(Entity generator, Entity target) {
		if (generator != null) {
			Color color = generator.getParticleColor();
			int size = generator.getParticleSize();
			int speed = generator.getParticleSpeed();
			int maxLife = generator.getParticleMaxLife();
			
			Particle p1 = new Particle(gp, generator, color, size, speed, maxLife, -2, -1);
			gp.particleList.add(p1);
			Particle p2 = new Particle(gp, generator, color, size, speed, maxLife, 2, -1);
			gp.particleList.add(p2);
			Particle p3 = new Particle(gp, generator, color, size, speed, maxLife, -2, 1);
			gp.particleList.add(p3);
			Particle p4 = new Particle(gp, generator, color, size, speed, maxLife, 2, 1);
			gp.particleList.add(p4);
		}
	}
	public void dyingAnimation(Graphics2D g2) {
		dyingCounter++;
		
		if (dyingCounter <= 5) {changeAlpha(g2,0f);}
		if (dyingCounter > 5 && dyingCounter <= 10) {changeAlpha(g2,1f);}
		if (dyingCounter > 10 && dyingCounter <= 15) {changeAlpha(g2,0f);}
		if (dyingCounter > 15 && dyingCounter <= 20) {changeAlpha(g2,1f);}
		if (dyingCounter > 20 && dyingCounter <= 25) {changeAlpha(g2,0f);}
		if (dyingCounter > 25 && dyingCounter <= 30) {changeAlpha(g2,1f);}
		if (dyingCounter > 30 ) {
			dying = false;
			alive = false;
		}
		
	}
	public void changeAlpha(Graphics2D g2, float alphaValue) {
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
	}
	public BufferedImage setup (String imagePath, int width, int height) {
		UtilityTool uTool = new UtilityTool();
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".gif"));
			image = uTool.scaleImage(image, width, height);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
	public void checkCollision() {
		collisionOn = false;
		gp.cChecker.checkTile(this);
		gp.cChecker.checkObject(this, false);
		boolean contactPlayer = gp.cChecker.checkPlayer(this);
		gp.cChecker.checkEntity(this, gp.npc);
		gp.cChecker.checkEntity(this, gp.monster);
		
		if (this.type == monster_type && contactPlayer == true) {
			attack = damage - gp.player.defense;
			damagePlayer(attack);
			gp.player.invincible = true;
		}
	}
	public void interact(Entity entity) {}
	public void searchPath(int goalCol, int goalRow) {
		int startCol = (worldX + solidArea.x)/gp.tileSize;
		int startRow = (worldY + solidArea.y)/gp.tileSize;
		
		gp.pFinder.setNodes(startCol, startRow, goalCol, goalRow);
		
		if (gp.pFinder.search() == true) {
			// NEXT worldX AND worldY
			int nextX = gp.pFinder.pathList.get(0).col * gp.tileSize;
			int nextY = gp.pFinder.pathList.get(0).row * gp.tileSize;
			
			// ENTITY'S SOLID AREA POSITION
			int enLeftX = worldX + solidArea.x;
			int enRightX = worldX + solidArea.x + solidArea.width;
			int enTopY = worldY + solidArea.y;
			int enBottomY = worldY + solidArea.y + solidArea.height;
			
			if (enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
				direction = "up";
			}
			else if (enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
				direction = "down";
			}
			else if (enTopY >= nextY && enBottomY < nextY + gp.tileSize) {
				if (enLeftX > nextX) {
					direction = "left";
				}
				if (enLeftX < nextX) {
					direction = "right";
				}
			}
			else if (enTopY > nextY && enLeftX > nextX) {
				direction = "up";
				checkCollision();
				if (collisionOn == true) {
					direction = "left";
				}
			}
			else if (enTopY > nextY && enLeftX < nextX) {
				direction = "up";
				checkCollision();
				if (collisionOn == true) {
					direction = "right";
				}
			}
			else if (enTopY < nextY && enLeftX > nextX) {
				direction = "down";
				checkCollision();
				if (collisionOn == true) {
					direction = "left";
				}
			}
			else if (enTopY < nextY && enLeftX < nextX) {
				direction = "down";
				checkCollision();
				if (collisionOn == true) {
					direction = "right";
				}
			}
//			int nextCol = gp.pFinder.pathList.get(0).col;
//			int nextRow = gp.pFinder.pathList.get(0).row;
//			if (nextCol == goalCol && nextRow == goalRow) {
//				onPath = false;
//			}
			
		}
	}
	public void move(String direction) {}
	public void checkAttackingOrNot(int rate, int straight, int horizontal) {
		
		boolean targetInRange = false;
		int xDis = getXDistance(gp.player);
		int yDis = getYDistance(gp.player);
		
		switch(direction) {
		case"up":
			if (gp.player.getCenterY() < getCenterY() && yDis < straight && xDis < horizontal) {
				targetInRange = true;
			}
			break;
		case"down":
			if (gp.player.getCenterY() > getCenterY() && yDis < straight && xDis < horizontal) {
				targetInRange = true;
			}
			break;
		case"left":
			if (gp.player.getCenterX() < getCenterX() && xDis < straight && yDis < horizontal) {
				targetInRange = true;
			}
			break;
		case"right":
			if (gp.player.getCenterX() > getCenterX() && xDis < straight && yDis < horizontal) {
				targetInRange = true;
			}
			break;
		}
		if (targetInRange == true) {
			int i = new Random().nextInt(rate);
			if (i == 0) {
				attacking = true;
				spriteNum = 1;
				spriteCounter = 0;
				shotAvailableCounter = 0;
			}
		}
	}
	public void ability1() {
		
	}
	public void ability2() {
		
	}
	public void ability3() {
		
	}
}
