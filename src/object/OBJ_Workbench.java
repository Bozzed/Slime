package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Workbench extends Entity {
	public static final String objName = "Workbench";
	GamePanel gp;
	public OBJ_Workbench(GamePanel gp) {
		super(gp);
		this.gp = gp;
		name = objName;
		
		down1 = setup("/objects/workbench", gp.tileSize, gp.tileSize);
		
		description = "[" + name + "]\nUsed to craft\nitems from raw\nmaterials.";
		
		stackable = false;
		
		type = consumable_type;
		
		price = 5;
	}
	public void use(Entity entity) {
		gp.gameState = gp.workbenchState;
		gp.ui.playerSlotCol = 1;
		gp.ui.playerSlotRow = 0;
	}
}