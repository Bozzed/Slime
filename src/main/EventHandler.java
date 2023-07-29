package main;

import entity.Entity;
import object.OBJ_Iron_Door;

public class EventHandler {
	
	GamePanel gp;
	EventRect eventRect[][][];
	
	int previousEventX, previousEventY;
	boolean canTouchEvent = true;
	int tempMap, tempCol, tempRow;
	String tempDir;
	int questCooldown = 0;
	
	public EventHandler (GamePanel gp) {
		this.gp = gp;
		
		eventRect = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
		
		int map = 0;
		int col = 0;
		int row = 0;
		while (map < gp.maxMap && col < gp.maxWorldCol && row < gp.maxWorldRow) {
			eventRect[map][col][row] = new EventRect();
			eventRect[map][col][row].x = 23;
			eventRect[map][col][row].y = 23;
			eventRect[map][col][row].width = 5;
			eventRect[map][col][row].height = 5;
			eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
			eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;
			
			col++;
			if (col == gp.maxWorldCol) {
				col = 0;
				row++;
				
				if (row == gp.maxWorldRow) {
					row = 0;
					map++;
				}
			}
		}
	}
	
	public void checkEvent() {
		if (questCooldown != 0) {
			questCooldown--;
		}
		int xDistance = Math.abs(gp.player.worldX - previousEventX);
		int yDistance = Math.abs(gp.player.worldY - previousEventY);
		int distance = Math.max(xDistance, yDistance);
		if (distance > gp.tileSize) {
			canTouchEvent = true;
		}
		if (canTouchEvent == true) {
			if (hit(4, 13, 20, "up") == true) {teleport("/maps/interiorV0.txt", 2, 21, 23, "up", gp.indoor);}
			else if (hit(2, 21, 23, "down") == true) {teleport("/maps/worldV3.txt", 4, 13, 20, "down", gp.outside);}
			else if (hit(1, 21, 20, "left") == true) {teleport("/maps/worldV0.txt", 0, 26, 18, "down", gp.outside);}
			else if (hit(4, 20, 12, "up") == true) {teleport("/maps/worldV0.txt", 0, 23, 8, "down", gp.outside);}
			else if (hit(0, 23, 8, "up") == true) {teleport("/maps/worldV3.txt", 4, 20, 12, "down", gp.outside);}
			else if (hit(1, 11, 13, "down") == true) {spiderLord();}
			else if (hit(4, 17, 19, "up") == true) {healingPool();}
			else if (hit(2, 21, 21, "up") == true) {speak(gp.npc[2][0]);}
			else if (hit(4, 23, 23, "any") == true) {quest1();}
		}
	}
	public void spiderLord() {
		if (gp.bossBattleOn == false) {
			gp.gameState = gp.cutsceneState;
			gp.csManager.sceneNum = gp.csManager.spiderLord;
		}
	}
	public boolean hit(int map, int col, int row, String reqDirection) {
		
		boolean hit = false;
		
		if (map == gp.currentMap) {
			gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
			gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
			eventRect[map][col][row].x = col*gp.tileSize + eventRect[map][col][row].x;
			eventRect[map][col][row].y = row*gp.tileSize + eventRect[map][col][row].y;
			
			if (gp.player.solidArea.intersects(eventRect[map][col][row]) && eventRect[map][col][row].eventDone == false) {
				if (gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
					hit = true;
					
					previousEventX = gp.player.worldX;
					previousEventY = gp.player.worldY;
				}
			}
			
			gp.player.solidArea.x = gp.player.solidAreaDefaultX;
			gp.player.solidArea.y = gp.player.solidAreaDefaultY;
			eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
			eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;
		}
		return hit;
	}
	public void teleport(String path, int map, int col, int row, String direction, int area) {
		
		gp.gameState = gp.transitionState;
		tempMap = map;
		tempCol = col;
		tempRow = row;
		tempDir = direction;
		
		canTouchEvent = false;
		gp.nextArea = area;
		gp.tileM.loadMap(path, map);
	}
	public void speak(Entity entity) {
		if (gp.keyH.enterPressed == true) {
			gp.player.attackCancelled = true;
			gp.gameState = gp.dialogueState;
			entity.speak();
		}
	}
	public void healingPool() {
		if (gp.keyH.enterPressed == true) {
			gp.player.life = gp.player.maxLife;
			gp.aSetter.setMON();
			gp.saveLoad.save();
			gp.player.startDialogue(gp.player, 0);
			
			canTouchEvent = false;
		}
	}
	public void quest1() {
		if (questCooldown == 0) {
			gp.aSetter.setQuest1();
			
			canTouchEvent = false;
			
			questCooldown = gp.FPS * 60;
		}
	}
}
