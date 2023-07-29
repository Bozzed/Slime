package object;
import entity.Entity;
import main.GamePanel;

public class OBJ_Torch extends Entity{
	public OBJ_Torch (GamePanel gp) {
		super(gp);
		type = obstacle_type;
		name = "Torch";
		
		down1 = setup("/npc/torch_down_1", gp.tileSize, gp.tileSize);
		down1 = setup("/npc/torch_down_2", gp.tileSize, gp.tileSize);
		
		update = true;
		
		dialogueSet = -1;

		solidArea.x = 0;
		solidArea.y = 0;
		solidArea.width = 48;
		solidArea.height = 48;
		collision = true;
		defaultSpeed = 0;
		speed = 0;
	}
	public void interact(Entity entity) {
		
	}
}