package tile_interactive;

import java.util.Random;

import main.GamePanel;
import object.OBJ_Heart;
import object.OBJ_GoldCoin;

public class IT_JarLoot extends InteractiveTile {
	GamePanel gp;
	
	public IT_JarLoot(GamePanel gp, int col, int row) {
		super(gp, col, row);
		this.gp = gp;
		
		this.worldX = gp.tileSize * col;
		this.worldY = gp.tileSize * row;
		
		life = 1;
	}
	public void update() {
		for (int i = 0; i < gp.obj[1].length; i++) {
			if (gp.obj[gp.currentMap][i] == null) {
				int ii = new Random().nextInt(100) + 1;
				if (ii > 50) {
					gp.obj[gp.currentMap][i] = new OBJ_Heart(gp);
					gp.obj[gp.currentMap][i].worldX = worldX;
					gp.obj[gp.currentMap][i].worldY = worldY;
					gp.obj[gp.currentMap][i].dropped = true;
					break;
				}
				if (ii < 50) {
					
				}
			}
		}
		for (int i = 0; i < gp.iTile[1].length; i++) {
			if (gp.iTile[gp.currentMap][i] == this) {
				gp.iTile[gp.currentMap][i] = null;
			}
		}
	}
}
