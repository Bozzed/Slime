package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_HealthSyringe extends Entity {
	public static final String objName = "Health Syringe";
	GamePanel gp;
	public OBJ_HealthSyringe(GamePanel gp) {
		super(gp);
		this.gp = gp;
		name = objName;
		
		down1 = setup("/objects/health_syringe", gp.tileSize, gp.tileSize);
		
		description = "[" + name + "]\nHeals 5 health.\nPress ENTER to use.";
		
		stackable = true;
		
		type = consumable_type;
		
		price = 5;
	}
	public void use(Entity entity) {
		entity.life += 5;
		
		if (entity.life > entity.maxLife) {
			entity.life = entity.maxLife;
		}
	}
}