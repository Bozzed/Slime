package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shortsword extends Entity {
	public static final String objName = "Shortsword";
	public OBJ_Shortsword(GamePanel gp) {
		super(gp);
		name = objName;
		
		type = melee_type;
		
		down1 = setup("/objects/shortsword", gp.tileSize, gp.tileSize);
		
		attackValue = 2;
		
		price = 10;
		
		description = "[" + name + "]\nLightweight but quite\nshort.";
		
		attackArea.width = 30;
		attackArea.height = 30;
		
		knockBackPower = 5;
	}
}
