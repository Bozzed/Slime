package tile_interactive;

import java.awt.Color;

import entity.Entity;
import main.GamePanel;

public class IT_DeadFlower extends InteractiveTile {
	
	GamePanel gp;
	
	public IT_DeadFlower(GamePanel gp, int col, int row) {
		super(gp, col, row);
		this.gp = gp;
		
		this.worldX = gp.tileSize * col;
		this.worldY = gp.tileSize * row;
		
		life = 1;
		
		down1 = setup("/tiles_interactive/dead_flower", gp.tileSize, gp.tileSize);
		destructable = true;
	}
	public void playSE() {

	}
	public boolean isCorrectItem(Entity entity) {
		boolean isCorrectItem = true;
		return isCorrectItem;
	}
	public Color getParticleColor() {
		Color color = new Color(9, 32, 5);
		return color;
	}
	public int getParticleSize() {
		int size = 4;
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