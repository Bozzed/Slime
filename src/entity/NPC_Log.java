package entity;
import main.GamePanel;

public class NPC_Log extends Entity{
	public NPC_Log (GamePanel gp) {
		super(gp);
		type = 1;
		name = "Log";
		
		getImage();
		setDialogue();
		
		dialogueSet = -1;

		solidArea.x = 0;
		solidArea.y = 0;
		solidArea.width = 64;
		solidArea.height = 64;
		defaultSpeed = 0;
		speed = defaultSpeed;
	}
	
	public void getImage() {
		up1 = setup("/npc/log_up_1", gp.tileSize*2, gp.tileSize*2);
		up2 = setup("/npc/log_up_2", gp.tileSize*2, gp.tileSize*2);
		down1 = setup("/npc/log_down_1", gp.tileSize*2, gp.tileSize*2);
		down2 = setup("/npc/log_down_2", gp.tileSize*2, gp.tileSize*2);
		left1 = setup("/npc/log_right_1", gp.tileSize*2, gp.tileSize*2);
		left2 = setup("/npc/log_right_2", gp.tileSize*2, gp.tileSize*2);
		right1 = setup("/npc/log_left_1", gp.tileSize*2, gp.tileSize*2);
		right2 = setup("/npc/log_left_2", gp.tileSize*2, gp.tileSize*2);
	}
	public void setAction() {
		moveTowardPlayer(60);
	}
	public void setDialogue() {
		dialogues[0][0] = "I'm a log...\nLogs don't have much to sell these days.";
		dialogues[0][1] = "I should have some new stuff soon though.";
		dialogues[0][2] = "Sigh, I'll never be as good of a trader as the\nmerchant in the woods.";
	}
	public void speak() {
		facePlayer();
		startDialogue(this, dialogueSet);
		
		dialogueSet++;
		
		if (dialogueSet > 1) {
			dialogueSet = 0;
		}
	}
}