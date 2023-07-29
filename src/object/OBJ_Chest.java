package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Chest extends Entity{
	public static final String objName = "Chest";
	
	GamePanel gp;
	
	int openCounter = 0;
	public OBJ_Chest(GamePanel gp, Entity loot1, Entity loot2) {
		// GAME PANEL
		super(gp);
		this.gp = gp;
		open = false;
		
		// PROPERTIES
		name = objName;
		this.loot1 = loot1;
		this.loot2 = loot2;
		type = obstacle_type;
		down1 = setup("/objects/chest", gp.tileSize, gp.tileSize);
		image = setup("/objects/chest_half", gp.tileSize, gp.tileSize);
		image2 = setup("/objects/chest_open", gp.tileSize, gp.tileSize);
		collision = true;
		
		// SOLID AREA
		solidArea.x = 0;
		solidArea.y = 0;
		solidArea.width = 48;
		solidArea.height = 48;
		
		setDialogue();
	}
	public void setDialogue() {
		dialogues[0][0] = "You open the chest and find a " + loot1.name + "!\nYou take it!";
		dialogues[1][0] = "It's open.";
		dialogues[2][0] = "You open the chest and find a " + loot1.name + "!\nYou canot any any more, you put it back.";
	}
	public void interact(Entity entity) {
		if (open == false) {
			gp.playSE(4);
			if (loot1.type == pickupOnly_type) {
				if (loot1 != null) {
					dropItem(loot1, 2);
				}
				if (loot2 != null) {
					dropItem(loot2, -2);
				}
				down1 = image;
				open = true;
			} else {
				if (gp.player.inventory.size() != gp.player.maxInventorySize) {
					down1 = image2;
					gp.player.canObtainItem(loot1);
					gp.playSE(6);
					startDialogue(this, 0);
					open = true;
				} else {
					startDialogue(this, 2);
				}
			}
			
		}
		else {
			down1 = image2;
			startDialogue(this, 1);
		}
	}
	public void update() {
		if (loot1.type == pickupOnly_type) {
			if (getTileDistance(gp.player) < 2 && open == false) {
				interact(this);
			}
		}
	}
}