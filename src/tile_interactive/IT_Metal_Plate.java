package tile_interactive;

import main.GamePanel;

public class IT_Metal_Plate extends InteractiveTile {
	
	GamePanel gp;
	public static final String itName = "Metal Plate";
	
	public IT_Metal_Plate(GamePanel gp, int col, int row) {
		super(gp, col, row);
		this.gp = gp;
		
		this.worldX = gp.tileSize * col;
		this.worldY = gp.tileSize * row;
		
		down1 = setup("/tiles_interactive/plate_up", gp.tileSize, gp.tileSize);
		image = setup("/tiles_interactive/plate_up", gp.tileSize, gp.tileSize);
		image2 = setup("/tiles_interactive/plate_down", gp.tileSize, gp.tileSize);
		
		solidArea.x = 0;
		solidArea.y = 0;
		solidAreaDefaultX = 0;
		solidAreaDefaultY = 0;
		solidArea.width = 0;
		solidArea.height = 0;
	}
	public void update() {
		super.update();
		int xDistance = Math.abs(worldX - gp.player.worldX);
		int yDistance = Math.abs(worldY - gp.player.worldY);
		int distance = Math.max(xDistance, yDistance);
		
		down1 = image;
		
		if (distance < 20) {
			down1 = image2;
		}
	}
}