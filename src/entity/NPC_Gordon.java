package entity;

import main.GamePanel;

public class NPC_Gordon extends Entity{
	
	public NPC_Gordon (GamePanel gp) {
		super(gp);
		name = "Gordon";
		type = npc_type;
		speed = 0;
		
		getImage();
		setDialogue();
	}
	public void getImage() {
		down1 = setup("/npc/gordon_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/npc/gordon_down_1", gp.tileSize, gp.tileSize);
	}
	public void setAction() {
		
		direction = "down";
	}
	public void setDialogue() {
		dialogues[0][0] = "Where am I?";
	}
	public void speak() {
		startDialogue(this, 0);
	}
}