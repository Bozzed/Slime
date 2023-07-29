package object;

import java.awt.image.BufferedImage;

import entity.Entity;
import main.GamePanel;

public class OBJ_Aggrovate extends Entity {
	
	public static final String npcName = "Aggrovate";
	
	public BufferedImage picture = setup("/objects/aggrovate", gp.tileSize, gp.tileSize);
	
	public OBJ_Aggrovate(GamePanel gp) {
		super(gp);
		down1 = setup("/objects/aggrovate", gp.tileSize, gp.tileSize);
		down2 = setup("/objects/aggrovate", gp.tileSize, gp.tileSize);
		type = decor_type;
		name = npcName;
		collision = false;
	}
	public void update() {
		super.update();
		aggrovateOnCounter++;
		if (aggrovateOnCounter == 50) {
			for (int i = 0; i < gp.npc[1].length; i++) {
				if (gp.npc[gp.currentMap][i] == this) {
					gp.npc[gp.currentMap][i] = null;
					break;
				}
			}
		}
	}
}
