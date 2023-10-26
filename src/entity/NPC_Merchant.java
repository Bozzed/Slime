package entity;

import main.GamePanel;
import material.MAT_IronNugget;
import object.OBJ_Broadsword;
import object.OBJ_Flashlight;
import object.OBJ_Glock_17;
import object.OBJ_HealthSyringe;
import object.OBJ_Sleeping_Bag;
import object.OBJ_SpeedSyringe;

public class NPC_Merchant extends Entity{
	public NPC_Merchant (GamePanel gp) {
		super(gp);
		name = "Merchant";
		type = npc_type;
		
		getImage();
		setDialogue();
		setItems();
	}
	public void getImage() {
		down1 = setup("/npc/merchant_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/npc/merchant_down_2", gp.tileSize, gp.tileSize);
	}
	public void setAction() {
		
		direction = "down";
	}
	public void setDialogue() {
		dialogues[0][0] = "Hehehe...\nYou found me!\nHow can I help you?";
		dialogues[1][0] = "Hehehe...\nSee you soon!";
		dialogues[2][0] = "You don't have enough money for this, hehe.";
		dialogues[3][0] = "You're carrying to much stuff, hehe.";
		dialogues[4][0] = "You can't sell equipted items, hehe...";
	}
	public void speak() {
		startDialogue(this, 0);
		gp.gameState = gp.tradeState;
		
		gp.ui.npc = this;
	}
	public void setItems() {
		inventory.add(new OBJ_Broadsword(gp));
		inventory.add(new OBJ_Glock_17(gp));
		inventory.add(new OBJ_Flashlight(gp));
		inventory.add(new OBJ_HealthSyringe(gp));
		inventory.add(new OBJ_SpeedSyringe(gp));
		inventory.add(new OBJ_Sleeping_Bag(gp));
		inventory.add(new MAT_IronNugget(gp));
	}
}
