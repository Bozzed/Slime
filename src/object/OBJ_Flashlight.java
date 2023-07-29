package object;

import main.GamePanel;

import entity.Entity;

public class OBJ_Flashlight extends Entity {
	
	public static final String objName = "Flashlight";
	
	public OBJ_Flashlight(GamePanel gp) {
		super(gp);
		
		type = light_type;
		name = objName;
		down1 = setup("/objects/flashlight", gp.tileSize, gp.tileSize);
		description = "[" + name + "]\nCreates a light\n source.";
		price = 15;
		lightRadius = 250;
	}
}
