package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;
import main.KeyHandler;
import material.MAT_IronNugget;
import material.MAT_Stick;
import object.OBJ_HealthSyringe;
import object.OBJ_Knife;
import object.OBJ_Sleeping_Bag;
import object.OBJ_SpeedSyringe;

public class Player extends Entity {
	GamePanel gp;
	KeyHandler keyH;
	
	public int screenX;
	public int screenY;
	int deadPersonCounter = 0;
	public boolean outside;
	public boolean lightUpdated = false;
	
	public int amountQuestComplete = 0;
	
	public Player(GamePanel gp, KeyHandler keyH) {
		super(gp);
		
		this.gp = gp;
		this.keyH = keyH;
		this.name = "player";
		
		screenX = gp.screenWidth/2 - (gp.tileSize/2);
		screenY = gp.screenHeight/2 - (gp.tileSize/2);
		
		solidArea = new Rectangle(8, 16, 32, 32);
		
		setDefaultValues();
		getPlayerImage();
		getPlayerAttackImage();
		setItems();
		setDialogue();
	}
	public void setDialogue() {
		dialogues[0][0] = "You take a drink of the water.\nYour life has been restored.\n(Progress saved)\nThat's some good water!";
	}
	public void setDefaultPositions() {
		worldX = gp.tileSize * 23;
		worldY = gp.tileSize * 21;
		direction = "down";
	}
	public void restoreAfterDeath() {
		life = maxLife;
		invincible = false;
		knockBack = false;
		speed = defaultSpeed;
	}
	public void setDefaultValues() {
		worldX = gp.tileSize * 23;
		worldY = gp.tileSize * 21;
		defaultSpeed = 4;
		speed = defaultSpeed;
		direction = "down";
		type = 0;
		// PLAYER STATUS
		if (gp.currentMap == 0) {
			outside = true;
		}
		if (gp.currentMap == 1) {
			outside = false;
		}
		maxLife = 6;
		life = maxLife;
		level = 1;
		strength = 1;
		exp = 0;
		nextLevelExp = 4;
		money = 100;
		hand = new OBJ_Knife(gp);
		attack = getAttack();
	}
	public void setItems () {
		inventory.clear();
		inventory.add(hand);
		inventory.add(new OBJ_Sleeping_Bag(gp));
		inventory.add(new OBJ_HealthSyringe(gp));
		inventory.add(new OBJ_SpeedSyringe(gp));
		inventory.add(new MAT_Stick(gp));
		inventory.add(new MAT_IronNugget(gp));
	}
	public int getAttack() {
		attackArea = hand.attackArea;
		return attack = strength*hand.attackValue;
	}
	public void getPlayerImage() {
		up1 = setup("/player/up_1", gp.tileSize, gp.tileSize);
		up2 = setup("/player/up_2", gp.tileSize, gp.tileSize);
		down1 = setup("/player/down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/player/down_2", gp.tileSize, gp.tileSize);
		left1 = setup("/player/left_1", gp.tileSize, gp.tileSize);
		left2 = setup("/player/left_2", gp.tileSize, gp.tileSize);
		right1 = setup("/player/right_1", gp.tileSize, gp.tileSize);
		right2 = setup("/player/right_2", gp.tileSize, gp.tileSize);
	}
	public void getPlayerAttackImage() {
		if (hand.type == melee_type) {
			attackUp1 = setup("/player/attack/attack_up_1", gp.tileSize, gp.tileSize*2);
			attackUp2 = setup("/player/attack/attack_up_2", gp.tileSize, gp.tileSize*2);
			attackDown1 = setup("/player/attack/attack_down_1", gp.tileSize, gp.tileSize*2);
			attackDown2 = setup("/player/attack/attack_down_2", gp.tileSize, gp.tileSize*2);
			attackLeft1 = setup("/player/attack/attack_left_1", gp.tileSize*2, gp.tileSize);
			attackLeft2 = setup("/player/attack/attack_left_2", gp.tileSize*2, gp.tileSize);
			attackRight1 = setup("/player/attack/attack_right_1", gp.tileSize*2, gp.tileSize);
			attackRight2 = setup("/player/attack/attack_right_2", gp.tileSize*2, gp.tileSize);
		} else if (hand.type == axe_type) {
			attackUp1 = setup("/player/attack/axe_up_1", gp.tileSize, gp.tileSize*2);
			attackUp2 = setup("/player/attack/axe_up_2", gp.tileSize, gp.tileSize*2);
			attackDown1 = setup("/player/attack/axe_down_1", gp.tileSize, gp.tileSize*2);
			attackDown2 = setup("/player/attack/axe_down_2", gp.tileSize, gp.tileSize*2);
			attackLeft1 = setup("/player/attack/axe_left_1", gp.tileSize*2, gp.tileSize);
			attackLeft2 = setup("/player/attack/axe_left_2", gp.tileSize*2, gp.tileSize);
			attackRight1 = setup("/player/attack/axe_right_1", gp.tileSize*2, gp.tileSize);
			attackRight2 = setup("/player/attack/axe_right_2", gp.tileSize*2, gp.tileSize);
		} else if (hand.type == pickaxe_type) {
			attackUp1 = setup("/player/attack/pickaxe_up_1", gp.tileSize, gp.tileSize*2);
			attackUp2 = setup("/player/attack/pickaxe_up_2", gp.tileSize, gp.tileSize*2);
			attackDown1 = setup("/player/attack/pickaxe_down_1", gp.tileSize, gp.tileSize*2);
			attackDown2 = setup("/player/attack/pickaxe_down_2", gp.tileSize, gp.tileSize*2);
			attackLeft1 = setup("/player/attack/pickaxe_left_1", gp.tileSize*2, gp.tileSize);
			attackLeft2 = setup("/player/attack/pickaxe_left_2", gp.tileSize*2, gp.tileSize);
			attackRight1 = setup("/player/attack/pickaxe_right_1", gp.tileSize*2, gp.tileSize);
			attackRight2 = setup("/player/attack/pickaxe_right_2", gp.tileSize*2, gp.tileSize);
		}  else if (hand.type == broadsword_type) {
			attackUp1 = setup("/player/attack/broadsword_up_1", gp.tileSize, gp.tileSize*2);
			attackUp2 = setup("/player/attack/broadsword_up_2", gp.tileSize, gp.tileSize*2);
			attackDown1 = setup("/player/attack/broadsword_down_1", gp.tileSize, gp.tileSize*2);
			attackDown2 = setup("/player/attack/broadsword_down_2", gp.tileSize, gp.tileSize*2);
			attackLeft1 = setup("/player/attack/broadsword_left_1", gp.tileSize*2, gp.tileSize);
			attackLeft2 = setup("/player/attack/broadsword_left_2", gp.tileSize*2, gp.tileSize);
			attackRight1 = setup("/player/attack/broadsword_right_1", gp.tileSize*2, gp.tileSize);
			attackRight2 = setup("/player/attack/broadsword_right_2", gp.tileSize*2, gp.tileSize);
		} else {
			attackUp1 = setup("/player/up_blank", gp.tileSize, gp.tileSize*2);
			attackUp2 = setup("/player/up_blank", gp.tileSize, gp.tileSize*2);
			attackDown1 = setup("/player/down_1", gp.tileSize, gp.tileSize);
			attackDown2 = setup("/player/down_2", gp.tileSize, gp.tileSize);
			attackLeft1 = setup("/player/left_blank", gp.tileSize*2, gp.tileSize);
			attackLeft2 = setup("/player/left_blank", gp.tileSize*2, gp.tileSize);
			attackRight1 = setup("/player/right_1", gp.tileSize, gp.tileSize);
			attackRight2 = setup("/player/right_2", gp.tileSize, gp.tileSize);
		}
	}
	public void getPlayerSleepImage(BufferedImage image) {
		up1 = image;
		up2 = image;
		down1 = image;
		down2 = image;
		left1 = image;
		left2 = image;
		right1 = image;
		right2 = image;
	}
	public void update() {
		
		if (attacking == true && attackCancelled == false) {
			attacking();
		} else if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true || keyH.enterPressed == true) {
	    	if (keyH.upPressed == true) {
	    		direction = "up";
	    	}
	    	if (keyH.downPressed == true) {
	    		direction = "down";
	    	}
	    	if (keyH.leftPressed == true) {
	    		direction = "left";
	    	}
	    	if (keyH.rightPressed == true) {
	    		direction = "right";
	    	}
	    	
	    	collisionOn = false;
	    	if (worldX > 0 && worldX < gp.worldWidth && worldY > 0 && worldY < gp.worldHeight) {
	    		gp.cChecker.checkTile(this);
	    	}
	    	
	    	// OBJECT COLLISION
	    	int objIndex = gp.cChecker.checkObject(this, true);
	    	pickUpObject(objIndex);
	    	
	    	// NPC COLLISION
	    	int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
	    	interactNPC(npcIndex);
	    	
	    	// ALIEN COLLISION
	    	int monIndex = gp.cChecker.checkEntity(this, gp.monster);
	    	contactMonster(monIndex);
	    	
	    	// EVENT TRIGGER
	    	gp.eHandler.checkEvent();
	    	
	    	gp.cChecker.checkEntity(this, gp.iTile);
	    	
	    	
	    	if (collisionOn == false) {
	    		switch (direction) {
	    		case "up": worldY -= speed; break;
	    		case "down": worldY += speed; break;
	    		case "left": worldX -= speed; break;
	    		case "right": worldX += speed; break;
	    		}
	    	}
	    	
	    	gp.keyH.enterPressed = false;
	    	attackCancelled = false;
	    	
	    	spriteCounter++;
	    	if (spriteCounter > 6) {
	    		if (spriteNum == 1) {
	    			spriteNum = 2;
	    		} else if (spriteNum == 2) {
	    			spriteNum = 1;
	    		}
	    		spriteCounter = 0;
	    	}
		}
		else {
			gp.keyH.enterPressed = false;
	    	attackCancelled = false;
		}
    	
    	if (invincible == true) {
    		invincibleCounter++;
    		if (invincibleCounter > 20) {
    			invincible = false;
    			invincibleCounter = 0;
    		}
    	}
    	if (life <= 0) {
    		gp.gameState = gp.gameOverState;
    	}
    	checkLevelUp();
	}
	public int getCurrentWeaponSlot() {
		int slot = 0;
		for (int i = 0; i < inventory.size(); i++) {
			if (inventory.get(i) == hand) {
				slot = i;
			}
		}
		return slot;
	}


	public void pickUpObject(int i) {
		
		if (i != 999) {
			if (gp.obj[gp.currentMap][i].pickupable == true) {
				// PICKUP ONLY TYPE
				if (gp.obj[gp.currentMap][i].type == pickupOnly_type) {
					gp.obj[gp.currentMap][i].use(this);
					gp.obj[gp.currentMap][i] = null;
				}
				
				// OBSTACLE TYPE
				else if (gp.obj[gp.currentMap][i].type == obstacle_type) {
					if (gp.keyH.enterPressed == true) {
						gp.obj[gp.currentMap][i].interact(this);
					}
			    } 
				
				// INVENTORY ITEMS
				else {
					String text;
					
					if (canObtainItem(gp.obj[gp.currentMap][i]) == true) {
						text = "+" + gp.obj[gp.currentMap][i].name;
					} else {
						text = "Inventory is full";
					}
					gp.ui.addMessage(text);
					gp.obj[gp.currentMap][i] = null;
				}
			}
		}
	}
	public int getCurrentWeaponOnSlot() {
		int slot = 0;
		for (int i = 0; i < gp.player.inventory.size(); i++) {
			if (inventory.get(i) == hand) {
				slot = i;
			}
		}
		return slot;
	}


	public void damageProjectile(int i) {
		if (i != 999) {
			Entity projectile = gp.projectile[gp.currentMap][i];
			projectile.generateParticle(projectile, this);
			projectile.alive = false;
		}
	}
	public void interactNPC(int i) {
		if (i != 999) {
			if (gp.keyH.enterPressed == true) {
				attackCancelled = true;
				gp.npc[gp.currentMap][i].speak();
			}
			gp.npc[gp.currentMap][i].move(direction);
		} else {
			
		}
	}

	public void damageMonster(int i, Entity attacker, int attack, int knockBackPower) {
		if (i != 999) {
			if (gp.monster[gp.currentMap][i].invincible == false) {
				
				if (knockBackPower > 0) {
					setKnockBack(gp.monster[gp.currentMap][i], attacker, knockBackPower);
				}
				
				int damage = attack - gp.monster[gp.currentMap][i].defense;
				if (damage < 0) {
					damage = 0;
				}
				gp.monster[gp.currentMap][i].life -= damage;
				gp.ui.addMessage("Dealt " + damage + " damage!");
				gp.monster[gp.currentMap][i].invincible = true;
				gp.monster[gp.currentMap][i].damageReaction();
				
				gp.monster[gp.currentMap][i].hpBarOn = true;
				if (gp.monster[gp.currentMap][i].life <= 0) {
					
					gp.monster[gp.currentMap][i].dying = true;
					
					gp.ui.addMessage("Killed " + gp.monster[gp.currentMap][i].name + "!");
					exp += gp.monster[gp.currentMap][i].exp;
					gp.ui.addMessage("+" + gp.monster[gp.currentMap][i].exp + " exp");
					
					gp.monster[gp.currentMap][i].checkDrop();
				}
			}
		}
	}
	public void contactMonster(int i) {
		if (i != 999) {
			if (invincible == false && gp.monster[gp.currentMap][i].alive == true) {
				int damage = gp.monster[gp.currentMap][i].damage - defense;
				if (damage < 0) {
					damage = 0;
				}
				life -= damage;
				invincible = true;
			}
		}
	}
	public void checkLevelUp() {
		if (exp >= nextLevelExp) {
			exp = exp - nextLevelExp;
			level++;
			maxLife += 2;
			nextLevelExp *= 3;
			life = maxLife;
			gp.ui.addMessage("Level up!");
		}
	}
	public void selectWorkbenchItem() {
		System.out.println("Something!");
	}
	public void selectItem() {
		int itemIndex = gp.ui.getItemIndexOnSlot(gp.ui.playerSlotCol, gp.ui.playerSlotRow);
		
		if (itemIndex < inventory.size()) {
			Entity selectedItem = inventory.get(itemIndex);
			
			if (inventory.get(itemIndex) != null) {
				if (selectedItem.type == melee_type || selectedItem.type == axe_type || selectedItem.type == pickaxe_type || selectedItem.type == projectile_type) {
					
					hand = selectedItem;
					attack = getAttack();
					getPlayerAttackImage();
				}
				if (selectedItem.type == light_type) {
					if (currentLight == selectedItem) {
						currentLight = null;
					} else {
						currentLight = selectedItem;
					}
					lightUpdated = true;
				}
				if (selectedItem.type == consumable_type) {
					if (selectedItem.amount > 1) {
						selectedItem.use(this);
						selectedItem.amount--;
					} else {
						selectedItem.use(this);
						inventory.remove(itemIndex);
					}
				}
			}
		}
	}
	public void damageInteractiveTile(int i) {
		if (i != 999 && gp.iTile[gp.currentMap][i].destructable == true && gp.iTile[gp.currentMap][i].isCorrectItem(this) == true && gp.iTile[gp.currentMap][i].invincible == false) {
			gp.iTile[gp.currentMap][i].playSE();
			
			gp.iTile[gp.currentMap][i].life--;
			gp.iTile[gp.currentMap][i].invincible = true;
			
			generateParticle(gp.iTile[gp.currentMap][i],gp.iTile[gp.currentMap][i]);
			
			if (gp.iTile[gp.currentMap][i].life == 0) {
				gp.iTile[gp.currentMap][i] = gp.iTile[gp.currentMap][i].getDestroyedForm();
			}
		}
	}
	public int searchItemInInventory(String itemName) {
		
		int itemIndex = 999;
		
		for (int i = 0; i < inventory.size(); i++) {
			if (inventory.get(i).name.equals(itemName)) {
				itemIndex = i;
				break;
			}
		}
		return itemIndex;
	}
	public boolean canObtainItem(Entity item) {
		
		boolean canObtain = false;
		
		Entity newItem = gp.eGenerator.getObject(item.name);
		
		if (item.stackable == true) {
			int index = searchItemInInventory(newItem.name);
			
			if (index != 999) {
				inventory.get(index).amount++;
				canObtain = true;
			}
			else {
				if (inventory.size() != maxInventorySize) {
					inventory.add(newItem);
					canObtain = true;
				}
			}
		} else {
			if (inventory.size() != maxInventorySize) {
				inventory.add(newItem);
				canObtain = true;
			}
		}
		return canObtain;
	}
	public void draw(Graphics2D g2) {
		
		BufferedImage shadow = setup("/npc/shadow", gp.tileSize, gp.tileSize);
		int tempScreenX = screenX;
		int tempScreenY = screenY;
		
		switch(direction) {
		case "up":
			if (attacking == false) {
				if (spriteNum == 1) {image = up1;}
				if (spriteNum == 2) {image = up2;}
			}
			if (attacking == true) {
				tempScreenY = screenY - gp.tileSize;
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
				tempScreenX = screenX - gp.tileSize;
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
		if (invincible == true) {
			changeAlpha(g2, 0.8f);
		}
		if (drawing == true) {
			if (screenX > worldX) {
				tempScreenX = worldX;
			}
			if (screenY > worldY) {
				tempScreenY = worldY;
			}
			int rightOffset = gp.screenWidth - screenX;
			if (rightOffset > gp.worldWidth - worldX) {
				tempScreenX = gp.screenWidth - (gp.worldWidth - worldX);
			}
			int bottomOffset = gp.screenHeight - screenY;
			if (bottomOffset > gp.worldHeight - worldY) {
				tempScreenY = gp.screenHeight - (gp.worldHeight - worldY);
			}
			if (attacking == true) {
				if (direction == "up" ) {
					changeAlpha(g2, 0.5f);
					g2.drawImage(shadow, tempScreenX, tempScreenY + 5 + gp.tileSize, null);
					changeAlpha(g2, 1f);
				}
				if (direction == "left" ) {
					changeAlpha(g2, 0.5f);
					g2.drawImage(shadow, tempScreenX + gp.tileSize, tempScreenY + 5, null);
					changeAlpha(g2, 1f);
				} else {
					changeAlpha(g2, 0.5f);
					g2.drawImage(shadow, tempScreenX, tempScreenY + 5, null);
					changeAlpha(g2, 1f);
				}
			} else {
				changeAlpha(g2, 0.5f);
				g2.drawImage(shadow, tempScreenX, tempScreenY + 5, null);
				changeAlpha(g2, 1f);
			}
			g2.drawImage(image, tempScreenX, tempScreenY, null);
		}
		changeAlpha(g2, 1f);
	}
}
