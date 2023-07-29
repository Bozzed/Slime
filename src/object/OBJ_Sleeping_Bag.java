package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Sleeping_Bag extends Entity {
	public static final String objName = "Sleeping Bag";
	public OBJ_Sleeping_Bag(GamePanel gp) {
		super(gp);
		
		type = consumable_type;
		name = objName;
		description = "[" + name + "]\nPress ENTER to use.\nSleep until moring.";
		price = 20;
		stackable = true;
		
		down1 = setup("/objects/sleeping_bag", gp.tileSize, gp.tileSize);
	}
	public void use(Entity entity) {
		gp.gameState = gp.sleepState;
		gp.player.life = gp.player.maxLife;
		
		gp.player.getPlayerSleepImage(down1);
	}
}