package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Gate extends Entity {
	public static final String objName = "Gate";
	
	public OBJ_Gate(GamePanel gp) {
		// GAME PANEL
		super(gp);
		this.gp = gp;
		
		// PROPERTIES
		name = objName;
		type = obstacle_type;
		down1 = setup("/objects/fence", gp.tileSize, gp.tileSize);
		image = setup("/objects/fence", gp.tileSize, gp.tileSize);
		image2 = setup("/objects/fence_open", gp.tileSize, gp.tileSize);
		collision = true;
		
		// SOLID AREA
		solidArea.x = 0;
		solidArea.y = 16;
		solidArea.width = 48;
		solidArea.height = 32;
		setDialogue();
	}
	public void setDialogue() {
		dialogues[0][0] = "The gate creaks open.";
	}
	public void interact(Entity entity) {
		startDialogue(this, 0);
		collision = false;
		down1 = image2;
	}
}
