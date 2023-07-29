package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Pickaxe extends Entity {
	
	public static final String objName = "Miners's Pickaxe";
	
	public OBJ_Pickaxe(GamePanel gp) {
		super(gp);
		name = objName;
	    type = pickaxe_type;
		down1 = setup("/objects/pickaxe", gp.tileSize, gp.tileSize);
		attackValue = 1;
		description = "[" + name + "]\nRusty and weak\nbut should do the job..";
		attackArea.width = 36;
		attackArea.height = 36;
		collision = true;
		knockBackPower = 2;
		price = 50;
	}
}
