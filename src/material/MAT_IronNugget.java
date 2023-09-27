package material;

import entity.Entity;
import main.GamePanel;

public class MAT_IronNugget extends Entity {
	
	GamePanel gp;
	
	public static final String objName = "Iron Nugget";
	
	public MAT_IronNugget(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		name = objName;
		
		down1 = setup("/objects/iron_nugget", gp.tileSize, gp.tileSize);
		
		type = material_type;
		
		price = 10;
		
		stackable = true;
	}
	public void use(Entity entity) {
		
	}
}