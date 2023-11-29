package entity;

import java.util.ArrayList;

import main.GamePanel;
import object.OBJ_Iron_Door;
import tile_interactive.IT_Metal_Plate;
import tile_interactive.InteractiveTile;

public class NPC_Big_Rock extends Entity{
	
	public static final String npcName = "Big Rock";

	public NPC_Big_Rock (GamePanel gp) {
		super(gp);
		type = npc_type;
		name = npcName;
		
		getImage();
		setDialogue();
		
		defaultSpeed = 4;
		speed = defaultSpeed;

		solidArea.x = 8;
		solidArea.y = 16;
		solidArea.width = 30;
		solidArea.height = 30;
	}
	
	public void getImage() {
		up1 = setup("/npc/bigrock_down_1", gp.tileSize, gp.tileSize);
		up2 = setup("/npc/bigrock_down_1", gp.tileSize, gp.tileSize);
		down1 = setup("/npc/bigrock_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/npc/bigrock_down_1", gp.tileSize, gp.tileSize);
		left1 = setup("/npc/bigrock_down_1", gp.tileSize, gp.tileSize);
		left2 = setup("/npc/bigrock_down_1", gp.tileSize, gp.tileSize);
		right1 = setup("/npc/bigrock_down_1", gp.tileSize, gp.tileSize);
		right2 = setup("/npc/bigrock_down_1", gp.tileSize, gp.tileSize);
	}
	public void setAction() {
		
	}
	public void move(String d) {
		this.direction = d;
		
		checkCollision();
		
		if (collisionOn == false) {
			switch(direction) {
			case"up":worldY -= speed; break;
			case"down":worldY += speed; break;
			case"left":worldX -= speed; break;
			case"right":worldX += speed; break;			
			}
		}
		
		detectPlate();
	}
	public void update() {
		
	}
	public void setDialogue() {
		dialogues[0][0] = "That's a big rock!";
	}
	public void speak() {
		facePlayer();
		startDialogue(this, dialogueSet);
	}
	public void detectPlate() {
		ArrayList<InteractiveTile> plateList = new ArrayList<>();
		ArrayList<Entity> rockList = new ArrayList<>();
		
		for (int i = 0; i < gp.iTile[1].length; i++) {
			if (gp.iTile[gp.currentMap][i] != null &&
					gp.iTile[gp.currentMap][i].name != null &&
					gp.iTile[gp.currentMap][i].name.equals(IT_Metal_Plate.itName)) {
				plateList.add(gp.iTile[gp.currentMap][i]);
			}
		}
		
		for (int i = 0; i < gp.npc[1].length; i++) {
			if (gp.npc[gp.currentMap][i] != null &&
					gp.npc[gp.currentMap][i].name.equals(NPC_Big_Rock.npcName)) {
				rockList.add(gp.npc[gp.currentMap][i]);
			}
		}
		
		int count = 0;
		
		for (int i = 0; i < plateList.size(); i++) {
			int xDistance = Math.abs(worldX - plateList.get(i).worldX);
			int yDistance = Math.abs(worldY - plateList.get(i).worldY);
			int distance = Math.max(xDistance, yDistance);
			
			if (distance < 20) {
				if (linkedEntity == null) {
					linkedEntity = plateList.get(i);
				}
			} else {
				if (linkedEntity == plateList.get(i)) {
					linkedEntity = null;
				}
			}
		}
		
		for (int i = 0; i < rockList.size(); i++) {
			if (rockList.get(i).linkedEntity != null) {
				count++;
			}
		}
		if (count == rockList.size()) {
			for (int i = 0; i < gp.obj[1].length; i++) {
				if (gp.obj[gp.currentMap][i] != null && gp.obj[gp.currentMap][i].name.equals(OBJ_Iron_Door.objName)) {
					gp.obj[gp.currentMap][i] = null;
				}
			}
		}
	}
}
