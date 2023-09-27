package tile_interactive;

import java.awt.Color;

import entity.Entity;
import main.GamePanel;

public class IT_YoungTree extends InteractiveTile {
	
	GamePanel gp;
	
	public IT_YoungTree(GamePanel gp, int col, int row) {
		super(gp, col, row);
		this.gp = gp;
		
		this.worldX = gp.tileSize * col;
		this.worldY = gp.tileSize * row;
		
		life = 1;
		
		down1 = setup("/tiles_interactive/young_tree", gp.tileSize, gp.tileSize);
		destructable = true;
	}
	public void playSE() {

	}
	public InteractiveTile getDestroyedForm () {
		InteractiveTile tile = new IT_Trunk(gp, worldX/gp.tileSize, worldY/gp.tileSize);
		return tile;
	}
	public boolean isCorrectItem(Entity entity) {
		boolean isCorrectItem = false;
		if (entity.hand.type == axe_type) {
			isCorrectItem = true;
		}
		return isCorrectItem;
	}
	public Color getParticleColor() {
		Color color = new Color(65, 50, 30);
		return color;
	}
	public int getParticleSize() {
		int size = 6;
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
