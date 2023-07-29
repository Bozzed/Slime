package object;

import java.awt.Graphics2D;

import entity.Entity;
import main.GamePanel;

public class OBJ_Table extends Entity {
	
	public static final String objName = "Table";
	
	GamePanel gp;
	
	public Entity loot;
	public OBJ_Table(GamePanel gp, Entity loot) {
		// GAME PANEL
		super(gp);
		this.gp = gp;
		this.loot = loot;
		
		// PROPERTIES
		name = objName;
		type = obstacle_type;
		down1 = setup("/objects/table", gp.tileSize, gp.tileSize);
		collision = true;
		dialogueSet = -1;
		
		// SOLID AREA
		solidArea.x = 0;
		solidArea.y = 0;
		solidArea.width = 48;
		solidArea.height = 48;
		setItems();
		setDialogue();
	}
	public void setItems() {
		inventory.add(loot);
	}
	public void setDialogue() {
		if (loot != null) {
			dialogues[0][0] = "Would you like to purchase " + loot.name + " for\n$" + loot.price + "?";
		}
	}
	public void interact(Entity entity) {
		startDialogue(this, dialogueSet);
	}
	public void draw(Graphics2D g2) {
		super.draw(g2);
		if (inventory.get(0) != null) {
			for (int i = 0; i < gp.obj[1].length; i++) {
				if (gp.obj[gp.currentMap][i] == null) {
					gp.obj[gp.currentMap][i] = loot;
					gp.obj[gp.currentMap][i].worldX = worldX;
					gp.obj[gp.currentMap][i].worldY = worldY;
					gp.obj[gp.currentMap][i].pickupable = false;
				}
			}
		}
	}
}
