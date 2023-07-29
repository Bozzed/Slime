package tile_interactive;

import java.awt.Color;

import entity.Entity;
import main.GamePanel;

public class IT_Fungi extends InteractiveTile {
	
	GamePanel gp;
	
	public IT_Fungi(GamePanel gp, int col, int row) {
		super(gp, col, row);
		this.gp = gp;
		
		this.worldX = gp.tileSize * col;
		this.worldY = gp.tileSize * row;
		
		life = 1;
		dialogueSet = -1;
		
		down1 = setup("/tiles_interactive/fungi", gp.tileSize, gp.tileSize);
		destructable = true;
		dialogues[0][0] = "The fungus emits a dangerous gas cloud.";
	}
	public void playSE() {

	}
	public boolean isCorrectItem(Entity entity) {
		boolean isCorrectItem = true;
		gp.player.life -= 10;
		startDialogue(this, 0);
		
		return isCorrectItem;
	}
	public Color getParticleColor() {
		Color color = new Color(156, 91, 47);
		return color;
	}
	public int getParticleSize() {
		int size = 12;
		return size;
	}
	public int getParticleSpeed() {
		int speed = 1;
		return speed;
	}
	public int getParticleMaxLife() {
		int maxLife = 20;
		return maxLife;
	}
}