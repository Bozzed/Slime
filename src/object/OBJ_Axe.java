package object;

import entity.Entity;
import main.GamePanel;
import material.MAT_IronNugget;
import material.MAT_Stick;

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
		price = 10;
		
		craftMat1 = new MAT_Stick(gp);
		craftMat2 = new MAT_IronNugget(gp);
	}
}
