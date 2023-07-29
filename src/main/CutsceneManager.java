package main;

import java.awt.Graphics2D;

import entity.PlayerDummy;
import monster.MON_SpiderLord;
import object.OBJ_Iron_Door;

public class CutsceneManager {
	GamePanel gp;
	Graphics2D g2;
	
	// SCENE
	public int sceneNum;
	public int scenePhase;
	
	// VALUES
	public final int NA = 0;
	public final int spiderLord = 1;
	
	public CutsceneManager (GamePanel gp) {
		this.gp = gp;
	}
	public void draw(Graphics2D g2) {
		this.g2 = g2;
		
		switch (sceneNum) {
		case spiderLord: scene_spiderLord();
		}
	}
	public void scene_spiderLord() {
		if (scenePhase == 0) {
			
			gp.bossBattleOn = true;
			
			for (int i = 0; i < gp.obj[1].length; i++) {
				if (gp.obj[gp.currentMap][i] == null) {
					gp.playSE(2);
					gp.obj[gp.currentMap][i] = new OBJ_Iron_Door(gp);
					gp.obj[gp.currentMap][i].worldX = 11 * gp.tileSize;
				   	gp.obj[gp.currentMap][i].worldY = 12 * gp.tileSize;
				   	gp.obj[gp.currentMap][i].temp = true;
				   	break;
				}
			}
			
			gp.player.drawing = false;
			
			scenePhase++;
			
			for (int i = 0; i < gp.npc[1].length; i++) {
				if (gp.npc[gp.currentMap][i] == null) {
					gp.npc[gp.currentMap][i] = new PlayerDummy(gp);
					gp.npc[gp.currentMap][i].worldX = gp.player.worldX;
					gp.npc[gp.currentMap][i].worldY = gp.player.worldY;
					gp.npc[gp.currentMap][i].direction = gp.player.direction;
					break;
				}
			}
		}
		if (scenePhase == 1) {
			
			if (gp.player.worldY > gp.tileSize * 16) {
				gp.player.worldX += 2;
				if (gp.player.worldX > gp.tileSize * 16) {
					scenePhase++;
				}
			} else {
				gp.player.worldY += 2;
			}
		}
		if (scenePhase == 2) {
			for (int i = 0; i < gp.monster[1].length; i++) {
				if (gp.monster[gp.currentMap][i] != null && gp.monster[gp.currentMap][i].name.equals(MON_SpiderLord.monName)) {
					gp.monster[gp.currentMap][i].sleep = false;
					gp.ui.npc = gp.monster[gp.currentMap][i];
					scenePhase++;
				}
			}
		}
		if (scenePhase == 3) {
			gp.ui.drawDialogueScreen(g2);
			
		}
		if (scenePhase == 4) {
			if (gp.player.worldY < gp.tileSize * 12) {
				gp.player.worldX -= 2;
				if (gp.player.worldX < gp.tileSize * 13) {
					scenePhase++;
				}
			} else {
				gp.player.worldY -= 2;
			}
		}
		if (scenePhase == 5) {
			
			for (int i = 0; i < gp.npc[1].length; i++) {
				if (gp.npc[gp.currentMap][i] != null && gp.npc[gp.currentMap][i].name.equals(PlayerDummy.npcName)) {
					gp.player.worldX = gp.npc[gp.currentMap][i].worldX;
					gp.player.worldY = gp.npc[gp.currentMap][i].worldY;
					
					gp.npc[gp.currentMap][i] = null;
					break;
				}
			}
			
			gp.player.drawing = true;
			
			sceneNum = NA;
			scenePhase = 0;
			gp.gameState = gp.playState;
			gp.skeletonLordCutsceneSeen = true;
		}
	}
}
