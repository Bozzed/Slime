package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Iron_Door extends Entity{
	public static final String objName = "Iron Door";
	GamePanel gp;
	public OBJ_Iron_Door(GamePanel gp) {
		// GAME PANEL
		super(gp);
		this.gp = gp;
		
		// PROPERTIES
		name = objName;
		type = obstacle_type;
		down1 = setup("/objects/iron_door", gp.tileSize, gp.tileSize);
		collision = true;
		
		// SOLID AREA
		solidArea.x = 0;
		solidArea.y = 16;
		solidArea.width = 48;
		solidArea.height = 32;
		
		setDialogue();
	}
	public void setDialogue() {
		dialogues[0][0] = "It won't budge.";
	}
	public void interact(Entity entity) {
		startDialogue(this, 0);
	}
}
