package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Heart extends Entity {
	
	GamePanel gp;
	
	public static final String objName = "Heart";
	
	public OBJ_Heart(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		name = objName;
		
		down1 = setup("/objects/heart_full", gp.tileSize, gp.tileSize);
		
		type = pickupOnly_type;
		
		value = 2;
		
		speed = 8;
		
		down1 = setup("/objects/heart_full", gp.tileSize, gp.tileSize);
		
		image = setup("/objects/heart_full", gp.tileSize, gp.tileSize);
		image2 = setup("/objects/heart_half", gp.tileSize, gp.tileSize);
		image3 = setup("/objects/heart_empty", gp.tileSize, gp.tileSize);
	}
	public void use(Entity entity) {
		gp.ui.addMessage("+" + value +" life");
		entity.life += value;
	}
	public void update() {
		if (dropped == true) {
			jump();
		}
		if (dropped == false) {
			goToPlayer();
		}
	}
}
