package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Sryinge extends Entity {
	public static final String objName = "Sryinge";
	GamePanel gp;
	public OBJ_Sryinge(GamePanel gp) {
		super(gp);
		this.gp = gp;
		name = objName;
		
		down1 = setup("/objects/srynge", gp.tileSize, gp.tileSize);
		
		description = "[" + name + "]\nHeals 5 health.\nPress ENTER to use.";
		
		stackable = true;
		
		type = consumable_type;
	}
	public void use(Entity entity) {
		entity.life += 5;
		
		if (entity.life > entity.maxLife) {
			entity.life = entity.maxLife;
		}
	}
}