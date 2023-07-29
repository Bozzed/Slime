package entity;

import Quest.Quest;
import main.GamePanel;

public class NPC_Gordon extends Entity{
	
	Quest quest;
	public NPC_Gordon (GamePanel gp, Quest quest) {
		super(gp);
		name = "Gordon";
		type = npc_type;
		speed = 0;
		this.quest = quest;
		
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
		dialogues[0][0] = "Hey you, you look like a strong man!\n I've got a quest for you! ";
		dialogues[0][1] = "Go defeat " + quest.reqEntityAmount + " " + quest.reqEntity.name + "!";
	}
	public void speak() {
		startDialogue(this, 0);
		
		gp.player.quest = quest;
	}
}