package material;

import entity.Entity;
import main.GamePanel;

public class MAT_Stick extends Entity {
	
	GamePanel gp;
	
	public static final String objName = "Stick";
	
	public MAT_Stick(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		name = objName;
		
		down1 = setup("/objects/stick", gp.tileSize, gp.tileSize);
		
		price = 2;
		
		stackable = true;
		
		type = material_type;
	}
	public void use(Entity entity) {
		
	}
}