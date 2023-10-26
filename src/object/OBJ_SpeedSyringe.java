package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_SpeedSyringe extends Entity {
	public static final String objName = "Speed Sryinge";
	GamePanel gp;
	public OBJ_SpeedSyringe(GamePanel gp) {
		super(gp);
		this.gp = gp;
		name = objName;
		
		down1 = setup("/objects/speed_syringe", gp.tileSize, gp.tileSize);
		
		description = "[" + name + "]\nIncreases speed.\nPress ENTER to use.";
		
		stackable = true;
		
		type = consumable_type;
		
		price = 5;
	}
	public void use(Entity entity) {
		entity.speed += 2;
		
		if (entity.speed > 6) {
			entity.speed = 6;
		}
	}
}