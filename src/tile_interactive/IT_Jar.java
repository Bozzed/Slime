package tile_interactive;

import java.awt.Color;

import entity.Entity;
import main.GamePanel;

public class IT_Jar extends InteractiveTile {
	
	GamePanel gp;
	
	public IT_Jar(GamePanel gp, int col, int row) {
		super(gp, col, row);
		this.gp = gp;
		
		this.worldX = gp.tileSize * col;
		this.worldY = gp.tileSize * row;
		
		life = 1;
		
		solidArea.width = 48 - 9;
		solidArea.height = 48 - 9;
		
		down1 = setup("/tiles_interactive/jar", gp.tileSize, gp.tileSize);
		destructable = true;
	}
	public InteractiveTile getDestroyedForm () {
		InteractiveTile tile = new IT_JarLoot(gp, worldX/gp.tileSize, worldY/gp.tileSize);
		return tile;
	}
	public boolean isCorrectItem(Entity entity) {
		boolean isCorrectItem = true;
		return isCorrectItem;
	}
	public Color getParticleColor() {
		Color color = new Color(200, 184, 184);
		return color;
	}
	public int getParticleSize() {
		int size = 8;
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