package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

import entity.Entity;
import monster.MON_GreenSlime;
import object.OBJ_GoldCoin;
import object.OBJ_Heart;

public class UI {
	GamePanel gp;
	
	// FONT
	public Font maruMonica;
	
	// HEART IMAGES
	BufferedImage heartFull, heartHalf, heartEmpty, coin, slime;
	
	// DEBUG
	
	// SCROLLING MESSAGE
	ArrayList<String> message = new ArrayList<>();
	ArrayList<Integer> messageCounter = new ArrayList<>();
	
	// DIALOGUE
	public String currentDialogue = "";
	public int charIndex;
	String combinedText = "";
	
	// STATUS
	public int playerSlotCol = 0;
	public int playerSlotRow = 0;
	public int npcSlotCol = 0;
	public int npcSlotRow = 0;
	int subState = 0;
	int moveCounter = 0;
	boolean transitioning = false;
	
	// TITLE SCREEN
	public int commandNum = 0;
	
	// TRANSITION
	public int transitionCounter = 0;
	
	// MONEY
	public int moneyCounter = 0;
	public boolean showMoney = false;
	
	// TRADE
	public Entity npc;
	
	DecimalFormat dFormat = new DecimalFormat("#0.00");
	
	// SLEEP
	int counter = 0;
	
	
    public UI (GamePanel gp) {
		this.gp = gp;
		try {
			InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
			maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
			
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Entity heart = new OBJ_Heart(gp);
		Entity slimeGuy = new MON_GreenSlime(gp);
		heartFull = heart.image;
		heartHalf = heart.image2;
		heartEmpty = heart.image3;
		slime = slimeGuy.down1;
		Entity coinThing = new OBJ_GoldCoin(gp);
		coin = coinThing.down1;
	}

    public void transitionState (Graphics2D g2) {
    	transitionCounter++;
    	g2.setColor(new Color(0,0,0,transitionCounter*5));
    	g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
    	
    	if (transitionCounter == 50) {
    		transitionCounter = 0;
    		gp.gameState = gp.playState;
    		gp.currentMap = gp.eHandler.tempMap;
    		gp.player.worldX = gp.tileSize * gp.eHandler.tempCol;
    		gp.player.worldY = gp.tileSize * gp.eHandler.tempRow;
    		gp.player.direction = gp.eHandler.tempDir;
    		gp.eHandler.previousEventX = gp.player.worldX;
    		gp.eHandler.previousEventY = gp.player.worldY;
    		gp.changeArea();
    	}
    }
	public void drawMonsterLife(Graphics2D g2) {
		
		for (int i = 0; i < gp.monster[1].length; i++) {
			
			Entity monster = gp.monster[gp.currentMap][i];
			
			if (monster != null && monster.inCamera() == true) {
				
				if (monster.boss == false && monster.hpBarOn == true) {
					double oneScale = (double)gp.tileSize/monster.maxLife;
					double hpBarValue = oneScale*monster.life;
					double hpBarMaxValue = oneScale*monster.maxLife;
					
					g2.setColor(new Color(255, 255, 255));
					g2.fillRect(monster.getScreenX() - 3, monster.getScreenY() - 18, gp.tileSize+6, 11);
					g2.setColor(new Color(165, 165, 165));
					g2.fillRect(monster.getScreenX(), monster.getScreenY() - 15, (int)hpBarMaxValue, 5);
					g2.setColor(new Color(255, 0, 30));
					g2.fillRect(monster.getScreenX(), monster.getScreenY() - 15, (int)hpBarValue, 5);
					
					monster.hpBarCounter++;
					
					if (monster.hpBarCounter > 600) {
						monster.hpBarCounter = 0;
						monster.hpBarOn = false;
					}
				} else if (monster.boss == true) {
					double oneScale = (double)gp.tileSize*8/monster.maxLife;
					double hpBarValue = oneScale*monster.life;
					
					int x = gp.screenWidth/2 - gp.tileSize * 4;
					int y = gp.tileSize * 10;
					
					g2.setColor(new Color(35, 35, 35));
					g2.fillRect(x - 1, y - 1, gp.tileSize*8 + 2, 22);
					g2.setColor(new Color(255, 0, 30));
					g2.fillRect(x, y, (int)hpBarValue, 20);
					
					g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24f));
					g2.setColor(Color.white);
					g2.drawString(monster.name, x + 4, y - 10);
				}
			}
		}
	}
	public void drawPlayerMoney(Graphics2D g2) {
		if (showMoney == true) {
			int x = gp.screenWidth - (gp.tileSize * 4);
			int y = gp.screenHeight - (gp.tileSize * 2);
			
			g2.drawImage(coin, x, y, gp.tileSize * 2, gp.tileSize * 2, null);
			
			g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 42f));
			g2.setColor(new Color(255, 255, 255));
			g2.drawString(Integer.toString(gp.player.money), x + gp.tileSize + 10, y + gp.tileSize + 10);
			moneyCounter++;
			
			if (moneyCounter == 120) {
				showMoney = false;
				moneyCounter = 0;
			}
		}
	}
	public void addMessage(String text) {
		message.add(text);
		messageCounter.add(0);
	}
	public void drawDialogueScreen(Graphics2D g2) {
		
		// WINDOW
		int x = gp.tileSize*3;
		int y = gp.tileSize/2;
		int width = gp.screenWidth - (gp.tileSize*6);
		int height = gp.tileSize*4;
		drawSubWindow(x, y, width, height, g2);
		
		g2.setFont(maruMonica);
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30F));
		x += gp.tileSize;
		y += gp.tileSize;
		
		if (npc.dialogues[npc.dialogueSet][npc.dialogueIndex] != null) {
//			currentDialogue = npc.dialogues[npc.dialogueSet][npc.dialogueIndex];
			
			char characters[] = npc.dialogues[npc.dialogueSet][npc.dialogueIndex].toCharArray();
			
			if (charIndex < characters.length) {
				String s = String.valueOf(characters[charIndex]);
				combinedText = combinedText + s;
				currentDialogue = combinedText;
			//	if (s != " ") {
			//		gp.playSE(5);
			//	}
				charIndex++;
			}
			
			else if(gp.keyH.enterPressed == true) {
				charIndex = 0;
				combinedText = "";
				
				if (gp.gameState == gp.dialogueState || gp.gameState == gp.cutsceneState) {
					npc.dialogueIndex++;
					gp.keyH.enterPressed = false;
				}
			}
		} else {
			npc.dialogueIndex = 0;
			
			if(gp.gameState == gp.dialogueState) {
				gp.gameState = gp.playState;
			}
			if (gp.gameState == gp.cutsceneState) {
				gp.csManager.scenePhase++;
			}
		}
		
		for (String line : currentDialogue.split("\n")) {
			g2.drawString(line, x, y);
			y += 40;
		}
		if (gp.gameState != gp.tradeState) {
			y += gp.tileSize;
			x = width - gp.tileSize;
			g2.drawString("Next - ENTER", x, y);
		}
	}
	public void drawSubWindow(int x, int y, int width, int height, Graphics2D g2) {
		Color c = new Color(0, 0, 0, 220);
		g2.setColor(c);
		g2.fillRect(x, y, width, height); // , 25, 25
		
		c = new Color(255, 255, 255);
		g2.setStroke(new BasicStroke(5));
		g2.setColor(c);
		g2.drawRect(x+5, y+5, width-10, height-10); // , 25, 25
	}
	public void drawSleepScreen(Graphics2D g2) {
		counter++;
		
		if (counter < 120) {
			gp.eManager.lighting.filterAlpha += 0.01f;
			
			if (gp.eManager.lighting.filterAlpha > 1f) {
				gp.eManager.lighting.filterAlpha = 1f;
			}
		}
		if (counter >= 120) {
			gp.eManager.lighting.filterAlpha -= 0.01f;
			if (gp.eManager.lighting.filterAlpha <= 0) {
				gp.eManager.lighting.filterAlpha = 0f;
				counter = 0;
				gp.eManager.lighting.dayState = gp.eManager.lighting.day;
				gp.player.getPlayerImage();
				gp.eManager.lighting.dayCounter = 0;
				gp.gameState = gp.playState;
			}
		}
	}
	public void drawPlayerLife(Graphics2D g2) {
		int x = gp.tileSize * 3 - gp.tileSize/2 + 10;
		int y = gp.tileSize - gp.tileSize/2 + 5;
		
		double oneHpScale = (double)gp.tileSize/gp.player.maxLife;
		double hpBarValue = oneHpScale*gp.player.life;
		
		double oneExpScale = (double)gp.tileSize/gp.player.nextLevelExp;
		double expBarValue = oneExpScale*gp.player.exp;
		
		// WHITE BORDER
		g2.setColor(new Color(255, 255, 255));
		g2.setStroke(new BasicStroke(7));
		g2.drawRoundRect(x, y, (gp.tileSize+1) * 4, 34, 10, 10);
		
		// BLACK BORDER
		g2.setStroke(new BasicStroke(2));
		g2.setColor(new Color(35, 35, 35));
		g2.drawRoundRect(x, y, (gp.tileSize+1) * 4, 34, 10, 10);
		
		// BLACK INSIDE
		g2.fillRoundRect(x, y, (gp.tileSize+1) * 4, 34, 10, 10);
		
		// RED HEALTH
		g2.setColor(new Color(255, 0, 30));
		g2.fillRoundRect(x+2, y+2, (int)hpBarValue * 4, 30, 10, 10);
		
		// HEALTH TEXT
		g2.setColor(new Color(255, 255, 255));
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32f));
		g2.drawString("HP: " + Integer.toString(gp.player.life) + "/" + gp.player.maxLife, x + gp.tileSize, y + 28);
				
		// BLACK BORDER
		g2.setStroke(new BasicStroke(2));
		g2.setColor(new Color(35, 35, 35));
		g2.drawRoundRect(x, y + gp.tileSize, (gp.tileSize+1) * 4, 17, 5, 10);
				
		// GRAY INSIDE
		g2.setColor(Color.gray);
		g2.fillRoundRect(x, y + gp.tileSize, (gp.tileSize+1) * 4, 17, 5, 10);
				
		// PURPLE EXP
		g2.setColor(new Color(133, 94, 182));
		g2.fillRoundRect(x+2, y+2 + gp.tileSize, (int)expBarValue * 4, 15, 10, 10);
				
		// EXP TEXT
		g2.setColor(new Color(255, 255, 255));
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 16f));
	    g2.drawString("Exp: " + Integer.toString(gp.player.exp) + "/" + gp.player.nextLevelExp, x + gp.tileSize + (gp.tileSize/2), y + 14 + gp.tileSize);
	}
	public void drawPlayerCurrentWeapon(Graphics2D g2) {
		
		// WEAPON
		int frameX = gp.tileSize;
		int frameY = gp.tileSize - gp.tileSize/2;
		int size = gp.tileSize + 20;
		drawSubWindow(frameX, frameY, size, size, g2);
		
		for (int i = 0; i < gp.player.inventory.size(); i++) {
			if (gp.player.inventory.get(i) == gp.player.hand) {
				if (gp.player.inventory.get(i).type == gp.player.inventory.get(i).projectile_type) {
					g2.drawImage(gp.player.inventory.get(i).image, frameX + 9, frameY + 8, null);
				} else {
					g2.drawImage(gp.player.inventory.get(i).down1, frameX + 9, frameY + 8, null);
				}
			}
		}
		
	}
	public void drawStatusScreen(Graphics2D g2, int x) {
		// FRAME
		final int frameX = gp.tileSize + x;
		final int frameY = gp.tileSize;
		final int frameWidth = gp.tileSize*5;
		final int frameHeight = gp.tileSize * 10;
		
		drawSubWindow(frameX, frameY, frameWidth, frameHeight, g2);
		
		// TEXT
		g2.setColor(Color.white);
		g2.setFont(maruMonica);
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));
		int textX = frameX + gp.tileSize;
		int textY = frameY + gp.tileSize;
		final int lineHeight = 35;
		
		g2.drawString("Level: " + Integer.toString(gp.player.level), textX, textY);
		textY += lineHeight;
		g2.drawString("Life: " + Integer.toString(gp.player.life) + "/" + Integer.toString(gp.player.maxLife), textX, textY);
		textY += lineHeight;
		g2.drawString("Strength: " + Integer.toString(gp.player.strength), textX, textY);
		textY += lineHeight;
		g2.drawString("Dexterity: " + Integer.toString(gp.player.dexterity), textX, textY);
		textY += lineHeight;
		g2.drawString("EXP: " + Integer.toString(gp.player.exp), textX, textY);
		textY += lineHeight;
		g2.drawString("Next Level: " + Integer.toString(gp.player.nextLevelExp), textX, textY);
		textY += lineHeight;
		g2.drawString("Money: " + Integer.toString(gp.player.money), textX, textY);
		textY += lineHeight;
		g2.drawString("Weapon: ", textX, textY);
		textY += gp.tileSize;
		g2.drawImage(gp.player.hand.down1, textX, textY - gp.tileSize, null);
		textY += lineHeight;
	}
	public void drawTitleScreen(Graphics2D g2) {
		// BG
		g2.setColor(Color.black);
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		// TITLE
		g2.setColor(Color.white);
		g2.setFont(maruMonica);
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 100F));
		String text = "Slimes";
		int x = gp.tileSize * 6 + (gp.tileSize/2);
		int y = gp.tileSize*3;
		
		
		// SHADOW
		g2.setColor(Color.gray);
		g2.drawString(text, x+5, y+5);
		
		// MAIN COLOR
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		
		// VERSION
		text = "Early Acess 1.0.1";
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 20F));
		g2.drawString(text, x, y + 30);
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 100F));
		
		// IMAGE
		x = gp.screenWidth/2 - (gp.tileSize*2)/2;
		y += gp.tileSize*2;
		g2.drawImage(slime, x, y, gp.tileSize*2, gp.tileSize*2, null);
		
		// MENU
		g2.setFont(maruMonica);
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
		
		text = "NEW GAME";
		x = gp.screenWidth/2 - gp.tileSize;
		y += gp.tileSize*4;
		g2.drawString(text, x, y);
		if (commandNum == 0) {
			g2.drawString(">", x-gp.tileSize, y);
		}

		text = "LOAD GAME";
		x = gp.screenWidth/2 - gp.tileSize;
		y += gp.tileSize;
		g2.drawString(text, x, y);
		if (commandNum == 1) {
			g2.drawString(">", x-gp.tileSize, y);
		}
	}
	public void drawMessage(Graphics2D g2) {
		
		int messageX = gp.screenWidth/2 - (gp.tileSize * 2);
		int messageY = gp.tileSize;
		
		g2.setFont(maruMonica);
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));
		
		for (int i = 0; i < message.size(); i++) {
			
			if (message.get(i) != null) {
				
				g2.setColor(Color.black);
				g2.drawString(message.get(i), messageX + 2, messageY + 2);
				g2.setColor(Color.white);
				g2.drawString(message.get(i), messageX, messageY);
				
				int counter = messageCounter.get(i) + 1;
				messageCounter.set(i, counter);
				
				messageY += gp.tileSize;
				
				if (messageCounter.get(i) > 180) {
					message.remove(i);
					messageCounter.remove(i);
				}
			}
		}
	}
	public void drawInventory(Graphics2D g2, Entity entity, boolean cursor, int x) {
		// FRAME
		int frameX;
		int frameY;
		int frameWidth;
		int frameHeight;
		int slotCol;
		int slotRow;
		if (entity == gp.player) {
			frameX = (gp.tileSize * 13) + x;
			frameY = gp.tileSize;
			frameWidth = gp.tileSize * 6;
			frameHeight = gp.tileSize * 5;
			slotCol = playerSlotCol;
			slotRow = playerSlotRow;
		} else {
			frameX = gp.tileSize * 1;
			frameY = gp.tileSize;
			frameWidth = gp.tileSize * 6;
			frameHeight = gp.tileSize * 5;
			slotCol = npcSlotCol;
			slotRow = npcSlotRow;
		}
		drawSubWindow(frameX, frameY, frameWidth, frameHeight, g2);
		
		// SLOT
		final int slotXstart = frameX + 20;
		final int slotYstart = frameY + 20;
		int slotX = slotXstart;
		int slotY = slotYstart;
		
		int slotSize = gp.tileSize + 3;
		
		// ITEMS
		for (int i = 0; i < entity.inventory.size(); i++) {
			
			// EQUIPT CURSOR
			if (entity.inventory.get(i) == entity.currentLight || entity.inventory.get(i) == entity.hand) {
				g2.setColor(new Color(240, 190, 90));
				g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
			}
			if (entity.inventory.get(i).type == entity.inventory.get(i).projectile_type) {
				g2.drawImage(entity.inventory.get(i).image, slotX, slotY, null);
			} else {
				g2.drawImage(entity.inventory.get(i).down1, slotX, slotY, null);
			}
			if (entity.inventory.get(i).amount > 1) {
				g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));
				int amountX;
				int amountY;
				
				String s = "" + entity.inventory.get(i).amount;
				
				amountX = (slotX + gp.tileSize) - 10;
				amountY = (slotY + gp.tileSize);
				g2.setColor(new Color(60, 60, 60));
				
				g2.drawString(s, amountX, amountY);
				
				g2.setColor(Color.white);
				g2.drawString(s, amountX-3, amountY-3);
			}
			
			slotX += slotSize;
			
			if (i == 4 || i == 9 || i == 14) {
				slotX = slotXstart;
				
				slotY += slotSize;
			}
		}
		
		// CURSOR
		if (cursor == true) {
			int cursorX = slotXstart + (slotSize * slotCol);
			int cursorY = slotYstart + (slotSize * slotRow);
			int cursorWidth = gp.tileSize;
			int cursorHeight = gp.tileSize;
			
			g2.setColor(Color.white);
			g2.setStroke(new BasicStroke(3));
			g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
			
			// DESCRIPTION
			int dFrameX = frameX;
			int dFrameY = frameY + frameHeight;
			int dFrameWidth = frameWidth;
			int dFrameHeight = gp.tileSize * 4;
			
			int textX = dFrameX + 20;
			int textY = dFrameY + gp.tileSize;
			g2.setFont(maruMonica);
			g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));
			
			int itemIndex = getItemIndexOnSlot(slotCol, slotRow);
			
			if (itemIndex < entity.inventory.size()) {
				
				drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight, g2);
				
				for (String line: entity.inventory.get(itemIndex).description.split("\n")) {
					g2.drawString(line, textX, textY);
					textY += 32;
				}
			}
		}
	}
	public void drawOptionsScreen(Graphics2D g2) {
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));
		
		int frameX = (gp.tileSize*6);
		int frameY = gp.tileSize;
		int frameWidth = gp.tileSize*8;
		int frameHeight = gp.tileSize*10;
		
		drawSubWindow(frameX, frameY, frameWidth, frameHeight, g2);
		
		switch(subState) {
		case 0: options_top(frameX, frameY, g2); break;
		case 1: options_borderlessNotification(frameX, frameY, g2);break;
		case 2: options_control(frameX, frameY, g2); break;
		}
		gp.keyH.enterPressed = false;
	}
	public void options_borderlessNotification(int frameX, int frameY, Graphics2D g2) {
		int textX = frameX + gp.tileSize;
		int textY = frameY + gp.tileSize*3;
		
		currentDialogue = "Changes will take\neffect after restarting\nthe game.";
		
		for (String line: currentDialogue.split("\n")) {
			g2.drawString(line, textX, textY);
			textY += gp.tileSize;
		}
		// BACK
		textY = frameX + gp.tileSize*4;
		g2.drawString("Back", textX, textY);
		if (commandNum == 0) {
			g2.drawString(">", textX-25, textY);
			if (gp.keyH.enterPressed == true) {
				commandNum = 0;
				subState = 0;
			}
		}
	}
	public void options_top (int frameX, int frameY, Graphics2D g2) {
		int textX;
		int textY;
		
		// TITLE
		String text = "Options";
		textX = gp.tileSize * 9;
		textY = frameY + gp.tileSize;
		g2.drawString(text, textX, textY);
		
		// BORDERLESS WINDOW
		text = "Fullscreen";
		textX = frameX + gp.tileSize;
		textY += gp.tileSize * 2;
		g2.drawString(text, textX, textY);
		if (commandNum == 0) {
			g2.drawString(">", textX - 25, textY);
			if (gp.keyH.enterPressed == true) {
				if (gp.fullscreen == false) {
					gp.fullscreen = true;
				}
				else if (gp.fullscreen == true) {
					gp.fullscreen = false;
				}
				subState = 1;
			}
		}
		
		// SOUND
		text = "Sound";
		textY += gp.tileSize;
		g2.drawString(text, textX, textY);
		if (commandNum == 1) {
			g2.drawString(">", textX - 25, textY);
		}
		
		// CONTROLS
		text = "Controls";
		textY += gp.tileSize;
		g2.drawString(text, textX, textY);
		if (commandNum == 2) {
			g2.drawString(">", textX - 25, textY);
			if (gp.keyH.enterPressed == true) {
				subState = 2;
				commandNum = 0;
			}
		}
		
		// MOVEMENT TYPE
		text = "🔄 Movement: " + gp.movement;
		textY += gp.tileSize;
		g2.drawString(text, textX, textY);
		if (commandNum == 3) {
			g2.drawString(">", textX - 25, textY);
			if (gp.keyH.enterPressed == true) {
				switch (gp.movement) {
				case "keys":
					gp.movement = "mouse";
					break;
				case "mouse":
					gp.movement = "keys";
					break;
				}
			}
		}
		
		// EXIT GAME
		text = "Quit";
		textY += gp.tileSize;
		g2.drawString(text, textX, textY);
		if (commandNum == 4) {
			g2.drawString(">", textX - 25, textY);
			if (gp.keyH.enterPressed == true) {
				System.exit(0);
			}
		}
		
		// BACK
		text = "Back";
		textY += gp.tileSize * 2;
		g2.drawString(text, textX, textY);
		if (commandNum == 5) {
			g2.drawString(">", textX - 25, textY);
			if (gp.keyH.enterPressed == true) {
				gp.gameState = gp.playState;
				commandNum = 0;
				subState = 0;
			}
		}
		
		// BORDERLESS WINDOW
		textX = frameX + gp.tileSize*7;
		textY = frameY + gp.tileSize*2 + 24;
		g2.setStroke(new BasicStroke(3));
		g2.drawRect(textX, textY, 24, 24);
		if (gp.fullscreen == true) {
			g2.fillRect(textX, textY, 24, 24);
		}
		
		// SOUND
		textY += gp.tileSize;
		g2.drawRect(textX - (gp.tileSize*2), textY, 120, 24);
		int volumeWidth = 24 * gp.sound.volumeScale;
		g2.fillRect(textX - (gp.tileSize*2), textY, volumeWidth, 24);
		
		gp.config.saveConfig();
	}
	public int getItemIndexOnSlot(int slotCol, int slotRow) {
		int itemIndex = slotCol + (slotRow*5);
		return itemIndex;
	}
	public void options_control(int frameX, int frameY, Graphics2D g2) {
		int textX;
		int textY;
		
		String text = "Controls";
		
		textX = frameX + gp.tileSize;
		textY = frameY + gp.tileSize;
		g2.drawString(text, textX, textY);
		
		textY += gp.tileSize*2;
		text = "Move - W/A/S/D";
		g2.drawString(text, textX, textY);
		textY += gp.tileSize;
		text = "Attack - M1";
		g2.drawString(text, textX, textY);
		textY += gp.tileSize;
		text = "Confirm/Interact - Enter";
		g2.drawString(text, textX, textY);
		textY += gp.tileSize;
		text = "Inventory - E";
		g2.drawString(text, textX, textY);
		textY += gp.tileSize;
		
		// BACK
		textY = gp.tileSize*9;
		text = "Back";
		g2.drawString(text, textX, textY);
		if (commandNum == 0) {
			g2.drawString(">", textX-25, textY);
			if (gp.keyH.enterPressed == true) {
				subState = 0;
			}
		}
	}
	public void drawGameOverScreen(Graphics2D g2) {
		g2.setColor(new Color(0,0,0,150));
		
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		g2.setStroke(new BasicStroke(gp.tileSize/2));
		g2.setColor(new Color(128,25,25));
		g2.drawRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		int x;
		int y;
		String text;
		x = gp.tileSize*6;
		y = gp.tileSize*4;
		text = "Game Over";
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110F));
		g2.setColor(Color.black);
		g2.drawString(text, x, y);
		
		text = "Game Over";
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110F));
		g2.setColor(Color.white);
		g2.drawString(text, x-5, y-5);
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50F));
		text = "Retry";
		x = gp.tileSize*9;
		y += gp.tileSize*4;
		g2.drawString(text, x, y);
		if (commandNum == 0) {
			g2.drawString(">", x-25, y);
		}
		
		y += gp.tileSize;
		
		text = "Quit";
		g2.drawString(text, x, y);
		if (commandNum == 1) {
			g2.drawString(">", x-25, y);
		}
	}
	public void drawTradeScreen(Graphics2D g2) {
		switch (subState) {
		case 0: tradeSelect(g2); break;
		case 1: tradeBuy(g2); break;
		case 2: tradeSell(g2); break;
		}
		
		gp.keyH.enterPressed = false;
	}
	public void tradeSelect(Graphics2D g2) {
		drawDialogueScreen(g2);
		
		// WINDOW
		int x = gp.tileSize * 15;
		int y = gp.tileSize * 4;
		int width = gp.tileSize * 3;
		int height = gp.tileSize * 4;
		
		drawSubWindow(x, y, width, height, g2);
		
		// TEXT
		x += gp.tileSize;
		y += gp.tileSize;
		g2.drawString("Buy", x, y);
		if (commandNum == 0) {
			g2.drawString(">", x-24, y);
			if (gp.keyH.enterPressed == true) {
				subState = 1;
			}
		}
		y += gp.tileSize;
		g2.drawString("Sell", x, y);
		if (commandNum == 1) {
			g2.drawString(">", x-24, y);
			if (gp.keyH.enterPressed == true) {
				subState = 2;
			}
		}
		y += gp.tileSize;
		g2.drawString("Leave", x, y);
		if (commandNum == 2) {
			g2.drawString(">", x-24, y);
			if (gp.keyH.enterPressed == true) {
				commandNum = 0;
				npc.startDialogue(npc, 1);
			}
		}
	}
    public void tradeBuy(Graphics2D g2) {
    	// PLAYER INVENTORY
		drawInventory(g2, gp.player, false, 0);
		
		// NPC INVENTORY
		drawInventory(g2, npc, true, 0);
		
		// HINT WINDOW
		int x = gp.tileSize * 1;
		int y = gp.tileSize * 10;
		int width = gp.tileSize * 6;
		int height = gp.tileSize * 2;
		
		drawSubWindow(x, y, width, height, g2);
		g2.drawString("[ESC] - Back", x+25, y+60);
		
		// PLAYER BALANCE
		x = gp.tileSize * 13;
		y = gp.tileSize * 10;
		width = gp.tileSize * 6;
		height = gp.tileSize * 2;
		
		drawSubWindow(x, y, width, height, g2);
		g2.drawString("Balance: " + gp.player.money, x+25, y+60);
		
		// PRICE
		int itemIndex = getItemIndexOnSlot(npcSlotCol,npcSlotRow);
		if (itemIndex < npc.inventory.size()) {
			x = (int)(gp.tileSize * 5.5);
			y = (int)(gp.tileSize * 5.5);
			width = (int)(gp.tileSize * 2.5);
			height = gp.tileSize;
			
			drawSubWindow(x, y, width, height, g2);
			g2.drawImage(coin, x+10, y+4, 42, 42, null);
			
			int price = npc.inventory.get(itemIndex).price;
			String text = ""+price;
			x += gp.tileSize;
			y += 35;
			g2.drawString(text, x, y);
			if (gp.keyH.enterPressed == true) {
				if (npc.inventory.get(itemIndex).price > gp.player.money) {
					subState = 0;
					npc.startDialogue(npc, 2);
				}
				else {
					if (gp.player.canObtainItem(npc.inventory.get(itemIndex)) == true) {
						gp.player.money -= price;
					} else {
						subState = 0;
						npc.startDialogue(npc, 3);
					}
				}
			}
		}
	}
    public void tradeSell(Graphics2D g2) {
    	
    	// PLAYER INVENTORY
    	drawInventory(g2, gp.player, true, 0);
    	
    	// NPC INVENTORY
    	drawInventory(g2, npc, false, 0);
    	
		// HINT WINDOW
		int x = gp.tileSize * 1;
		int y = gp.tileSize * 10;
		int width = gp.tileSize * 6;
		int height = gp.tileSize * 2;
		
		drawSubWindow(x, y, width, height, g2);
		g2.drawString("[ESC] - Back", x+25, y+60);
		
		// PLAYER BALANCE
		x = gp.tileSize * 13;
		y = gp.tileSize * 10;
		width = gp.tileSize * 6;
		height = gp.tileSize * 2;
		
		drawSubWindow(x, y, width, height, g2);
		g2.drawString("Balance: " + gp.player.money, x+25, y+60);
		
		// PRICE
		int itemIndex = getItemIndexOnSlot(playerSlotCol,playerSlotRow);
		if (itemIndex < gp.player.inventory.size()) {
			x = (int)(gp.tileSize * 15.5);
			y = (int)(gp.tileSize * 5.5);
			width = (int)(gp.tileSize * 2.5);
			height = gp.tileSize;
			
			drawSubWindow(x, y, width, height, g2);
			g2.drawImage(coin, x+10, y+4, 42, 42, null);
			
			int price = gp.player.inventory.get(itemIndex).price;
			String text = ""+price;
			x += gp.tileSize;
			y += 35;
			g2.drawString(text, x, y);
			if (gp.keyH.enterPressed == true) {
				if (gp.player.inventory.get(itemIndex) == gp.player.hand || gp.player.inventory.get(itemIndex) == gp.player.currentLight) {
					subState = 0;
					npc.startDialogue(npc, 4);
				} else {
					if (gp.player.inventory.get(itemIndex).amount > 1) {
						gp.player.inventory.get(itemIndex).amount--;
					} else {
						gp.player.inventory.remove(itemIndex);
					}
					gp.player.money += price;
				}
			}
		}
    }
    public void drawWorkbenchScreen(Graphics2D g2) {
    	int x;
		int y;
		int width;
		int height;
		String text;
		
		
		g2.setFont(maruMonica);
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));
		g2.setColor(Color.white);
		
		x = gp.screenWidth/2 - gp.tileSize;
		y = gp.tileSize;
		width = gp.tileSize * 4;
		height = gp.tileSize * 6;
		text = "ESC - Exit";		
		
		g2.drawString(text, x, y);
		
		x -= gp.tileSize;
		y += gp.tileSize * 2;
		text = "Crafting";
		
		drawSubWindow(x, y, width, height, g2);
		
		g2.drawString(text, x + 42, y + 40);
		
		drawInventory(g2, gp.player, false, 0);
		
		final int slotXstart = x - 10;
		final int slotYstart = y + 50;
		int slotX = slotXstart;
		int slotY = slotYstart;
		int slotCol = playerSlotCol;
		int slotRow = playerSlotRow;
		int slotSize = gp.tileSize + 3;
		int ii = 0;
		
		for (int i = 0; i < gp.recepies.length; i++) {
            if (gp.recepies[i] != null) {
    			g2.drawImage(gp.recepies[i].down1, slotX + gp.tileSize, slotY, null);
    			
    			ii++;
    			slotX += slotSize;
    			
    			if (ii == 2 || ii == 4 || ii == 6) {
    				slotX = slotXstart;
    				
    				slotY += slotSize;
    			}
            }
		}
		
		int cursorX = slotXstart + (slotSize * slotCol);
		int cursorY = slotYstart + (slotSize * slotRow);
		int cursorWidth = gp.tileSize;
		int cursorHeight = gp.tileSize;
		
		g2.setColor(Color.white);
		g2.setStroke(new BasicStroke(3));
		g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
    }
    public void draw (Graphics2D g2) {
		//TITLE STATE
		if (gp.gameState == gp.titleState) {
			drawTitleScreen(g2);
		}
		//TRANSITION STATE
		if (gp.gameState == gp.transitionState) {
			transitionState(g2);
		}
		// PLAY STATE
		if (gp.gameState == gp.playState) {
			drawPlayerCurrentWeapon(g2);
			drawPlayerLife(g2);
			drawMonsterLife(g2);
			drawPlayerMoney(g2);
		    drawMessage(g2);
    	}
		
		// GAME OVER STATE
		if (gp.gameState == gp.gameOverState) {
			drawGameOverScreen(g2);
		}
		
		// TRADE STATE
		if (gp.gameState == gp.tradeState) {
			drawTradeScreen(g2);
		}
		// SLEEP STATE
		if (gp.gameState == gp.sleepState) {
			drawSleepScreen(g2);
		}
    	// PAUSE STATE
    	if (gp.gameState == gp.pauseState) {
    		String text = "GAMEPLAY PAUSED";
    		int x;
    		
    		x = gp.screenWidth/2 - 200;
    		

    		int y = gp.screenHeight/2;
    		
    		g2.setFont(maruMonica);
    		g2.setColor(Color.white);
    		g2.drawString(text, x, y);
    	}
    	// DIALOGUE STATE
    	if (gp.gameState == gp.dialogueState) {
    		drawDialogueScreen(g2);
    		drawPlayerMoney(g2);
    	}
    	// STATUS STATE
    	if (gp.gameState == gp.statusState) {
    		drawStatusScreen(g2, 0);
    		drawInventory(g2, gp.player, true, 0);
    	}
    	if (gp.gameState == gp.mapState) {
    		gp.map.drawFullMapScreen(g2);
    	}
    	if (gp.gameState == gp.optionsState) {
    		drawOptionsScreen(g2);
    	}
    	if (gp.gameState == gp.workbenchState) {
    		drawWorkbenchScreen(g2);
    	}
	}
}
