package entity;

import java.util.Random;
import main.GamePanel;

public class NPC_Cloak extends Entity{
	public NPC_Cloak (GamePanel gp) {
		super(gp);
		type = 1;
		name = "Cloak";
		
		getImage();
		setDialogue();
		
		dialogueSet = -1;

		solidArea.x = 3;
		solidArea.y = 18;
		solidArea.width = 42;
		solidArea.height = 30;
		defaultSpeed = 1;
		speed = defaultSpeed;
	}
	
	public void getImage() {
		up1 = setup("/npc/cloak_up", gp.tileSize, gp.tileSize);
		up2 = setup("/npc/cloak_up", gp.tileSize, gp.tileSize);
		down1 = setup("/npc/cloak_down", gp.tileSize, gp.tileSize);
		down2 = setup("/npc/cloak_down", gp.tileSize, gp.tileSize);
		left1 = setup("/npc/cloak_left", gp.tileSize, gp.tileSize);
		left2 = setup("/npc/cloak_left", gp.tileSize, gp.tileSize);
		right1 = setup("/npc/cloak_right", gp.tileSize, gp.tileSize);
		right2 = setup("/npc/cloak_right", gp.tileSize, gp.tileSize);
	}
	public void setAction() {
		if (onPath == true) {
			searchPath(getGoalCol(gp.player), getGoalRow(gp.player));
		} else {
			getRandomDirection(120);
		}
	}
	public void setDialogue() {
		
		dialogues[0][0] = "[Goulish noises]";
		dialogues[0][1] = "Enter the dread...";
		dialogues[0][2] = "Pass the unfed...";
		dialogues[0][3] = "Resist the dead...";
		dialogues[0][4] = "Don't go to bed...";
		dialogues[0][5] = "[Goulish noises]";
	}
	public void speak() {
		facePlayer();
		startDialogue(this, dialogueSet);
		onPath = true;
		
		dialogueSet++;
		
		if (dialogueSet > 1) {
			dialogueSet = 0;
		}
	}
}
