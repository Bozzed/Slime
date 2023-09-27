package tile_interactive;

import java.awt.Color;

import entity.Entity;
import main.GamePanel;

public class IT_DestructableWall extends InteractiveTile {
	
	GamePanel gp;
	
	public IT_DestructableWall(GamePanel gp, int col, int row) {
		super(gp, col, row);
		this.gp = gp;
		
		this.worldX = gp.tileSize * col;
		this.worldY = gp.tileSize * row;
		
		life = 2;
		
		this.solidArea.x = 0;
		this.solidArea.y = 0;
		this.solidArea.width = 48;
		this.solidArea.height = 48;
		
		down1 = setup("/tiles_interactive/weak_wall", gp.tileSize, gp.tileSize);
		destructable = true;
	}
	public boolean isCorrectItem(Entity entity) {
		boolean isCorrectItem = false;
		if (entity.hand.type == pickaxe_type) {
			isCorrectItem = true;
		}
		return isCorrectItem;
	}
	public Color getParticleColor() {
		Color color = new Color(129, 129, 129);
		return color;
	}
	public int getParticleSize() {
		int size = 10;
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