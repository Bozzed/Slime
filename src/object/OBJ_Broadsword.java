package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Broadsword extends Entity {
	public static final String objName = "Broadsword";
	public OBJ_Broadsword(GamePanel gp) {
		super(gp);
		name = objName;
		
		type = broadsword_type;
		
		down1 = setup("/objects/broadsword", gp.tileSize, gp.tileSize);
		
		attackValue = 10;
		
		price = 70;
		
		description = "[" + name + "]\nHeavy and lethal.";
		
		attackArea.width = 30;
		attackArea.height = 30;
		
		knockBackPower = 10;
	}
}
