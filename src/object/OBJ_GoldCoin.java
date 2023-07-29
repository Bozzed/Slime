package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_GoldCoin extends Entity {
	
	GamePanel gp;
	
	public static final String objName = "Gold_Coin";
	
	public OBJ_GoldCoin(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		name = objName;
		
		type = pickupOnly_type;
		
		down1 = setup("/objects/gold_coin", gp.tileSize, gp.tileSize);
		
		value = 10;
		
		speed = 8;
	}
	public void use(Entity entity) {
		gp.ui.addMessage("+" + value +" money");
		entity.money += value;
		gp.ui.showMoney = true;
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
