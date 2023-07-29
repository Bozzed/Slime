package entity;

import main.GamePanel;

public class PlayerDummy extends Entity{
	
	public static final String npcName = "Dummy";

	public PlayerDummy(GamePanel gp) {
		super(gp);
		name = npcName;
		
		getImage();
	}
	public void getImage() {
		up1 = setup("/player/up_1", gp.tileSize, gp.tileSize);
		up2 = setup("/player/up_2", gp.tileSize, gp.tileSize);
		down1 = setup("/player/down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/player/down_2", gp.tileSize, gp.tileSize);
		left1 = setup("/player/left_1", gp.tileSize, gp.tileSize);
		left2 = setup("/player/left_2", gp.tileSize, gp.tileSize);
		right1 = setup("/player/right_1", gp.tileSize, gp.tileSize);
		right2 = setup("/player/right_2", gp.tileSize, gp.tileSize);
	}
}
