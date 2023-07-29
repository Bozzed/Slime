package entity;

import java.awt.Graphics2D;

import main.GamePanel;

public class Shadow extends Entity{
	
	public static final String npcName = "Shadow";

	public Shadow(GamePanel gp) {
		super(gp);
		name = npcName;
		collision = false;
		
		getImage();
	}
	public void getImage() {
		up1 = setup("/npc/shadow", gp.tileSize, gp.tileSize);
		up2 = setup("/npc/shadow", gp.tileSize, gp.tileSize);
		down1 = setup("/npc/shadow", gp.tileSize, gp.tileSize);
		down2 = setup("/npc/shadow", gp.tileSize, gp.tileSize);
		left1 = setup("/npc/shadow", gp.tileSize, gp.tileSize);
		left2 = setup("/npc/shadow", gp.tileSize, gp.tileSize);
		right1 = setup("/npc/shadow", gp.tileSize, gp.tileSize);
		right2 = setup("/npc/shadow", gp.tileSize, gp.tileSize);
	}
	public void draw(Graphics2D g2) {
		changeAlpha(g2, 0.2f);
		super.draw(g2);
	}
}
