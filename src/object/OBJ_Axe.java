package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Axe extends Entity {
	
	public static final String objName = "Woodcutter's Axe";
	
	public OBJ_Axe(GamePanel gp) {
		super(gp);
		name = objName;
	    type = axe_type;
		down1 = setup("/objects/axe", gp.tileSize, gp.tileSize);
		attackValue = 10;
		description = "[" + name + "]\nA large axe.";
		attackArea.width = 36;
		attackArea.height = 36;
		collision = true;
		knockBackPower = 10;
	}
}
