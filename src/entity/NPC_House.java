package entity;
import main.GamePanel;

public class NPC_House extends Entity{
	public NPC_House (GamePanel gp) {
		super(gp);
		type = npc_type;
		name = "House";
		
		getImage();

		solidArea.x = 0;
		solidArea.y = 0;
		solidArea.width = 240;
		solidArea.height = 240;
		defaultSpeed = 0;
		speed = defaultSpeed;
	}
	
	public void getImage() {
		down1 = setup("/npc/house", gp.tileSize*5, gp.tileSize*5);
		down2 = setup("/npc/house", gp.tileSize*5, gp.tileSize*5);
	}
}