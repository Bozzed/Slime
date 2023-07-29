package tile;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class TileManager {
	
	GamePanel gp;
	public Tile[] tile;
	public int mapTileNum[][][];
	boolean drawPath = true;
	
	public TileManager(GamePanel gp) {
		this.gp = gp;
		tile = new Tile[100];
		mapTileNum = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
		
		getTileImage();
		if (gp.currentMap == 1) {
			loadMap("/maps/worldV1.txt", 1);
		}
		if (gp.currentMap == 2) {
			loadMap("/maps/interiorV0.txt", 2);
		}
		if (gp.currentMap == 4) {
			loadMap("/maps/worldV3.txt", 4);
		}
	}
	public void getTileImage() {
		setup(0, "darkness", true);
		setup(1, "stairs_up", false);
		setup(2, "floor_wood", false);
		setup(3, "dirt", false);
		setup(4, "tree", true);
		setup(5, "grass01", false);
		setup(6, "grass00", false);
		setup(7, "stairs_down", false);
		setup(8, "wall_stone", true);
		setup(9, "hut", true);
		setup(00, "darkness", true);
		setup(10, "stairs_up", false);
		setup(11, "water01", true);
		setup(12, "water02", true);
		setup(13, "water03", true);
		setup(14, "water04", true);
		setup(15, "water00", true);
		setup(16, "water05", true);
		setup(17, "water06", true);
		setup(18, "water07", true);
		setup(19, "water08", true);
		setup(20, "floor_wood", false);
		setup(21, "polluted_sand", false);
		setup(30, "dirt", false);
		setup(31, "stonewall00", true);
		setup(32, "stonewall01", true);
		setup(33, "stonewall02", true);
		setup(34, "stonewall03", true);
		setup(35, "stonefloor00", false);
		setup(36, "stonewall04", true);
		setup(37, "stonewall05", true);
		setup(38, "stonewall06", true);
		setup(39, "stonewall07", true);
		setup(40, "tree", true);
		setup(41, "cave_entrance", false);
		setup(42, "dirt_wall", true);
		setup(43, "dirt_wall_grass", true);
		
		// GRASS
		setup(60, "grass00", false);
		setup(61, "grass01", false);
		
		// GRASS WITH DIRT
		setup(52, "grass02", false);
		setup(53, "grass03", false);
		setup(54, "grass04", false);
		setup(55, "grass05", false);
		setup(56, "grass06", false);
		setup(57, "grass07", false);
		setup(58, "grass08", false);
		setup(59, "grass09", false);
		setup(50, "grass10", false);
		setup(51, "grass11", false);
		setup(72, "grass12", false);
		setup(83, "grass13", false);
		
		setup(70, "stairs_down", false);
		setup(80, "wall_stone", true);
		setup(81, "floor_tile", false);
		setup(90, "hut", true);
	}

	public void setup(int index, String imageName, boolean collision) {
		UtilityTool uTool = new UtilityTool();
		try {
			tile[index] = new Tile();
			tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName + ".gif"));
			tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
			tile[index].collision = collision;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadMap(String filePath, int map) {
		try {
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col = 0;
			int row = 0;
			
			while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
				String line = br.readLine();
				
				while (col < gp.maxWorldCol) {
					
					String numbers[] = line.split(" ");
					
					int num = Integer.parseInt(numbers[col]);
					mapTileNum[map][col][row] = num;
					col++;
				}
				if (col == gp.maxWorldCol) {
					col = 0;
					row++;
				}
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void draw(Graphics2D g2) {
		
		int worldCol = 0;
		int worldRow = 0;
		
		while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
			
			int tileNum = mapTileNum[gp.currentMap][worldCol][worldRow];
			
			int worldX = worldCol * gp.tileSize;
			int worldY = worldRow * gp.tileSize;
			int screenX = worldX - gp.player.worldX + gp.player.screenX;
			int screenY = worldY - gp.player.worldY + gp.player.screenY;
			
			// STOP MOVING SCREEN
			if (gp.player.screenX > gp.player.worldX) {
				screenX = worldX;
			}
			if (gp.player.screenY > gp.player.worldY) {
				screenY = worldY;
			}
			int rightOffset = gp.screenWidth - gp.player.screenX;
			if (rightOffset > gp.worldWidth - gp.player.worldX) {
				screenX = gp.screenWidth - (gp.worldWidth - worldX);
			}
			
			int bottomOffset = gp.screenHeight - gp.player.screenY;
			if (bottomOffset > gp.worldHeight - gp.player.worldY) {
				screenY = gp.screenHeight - (gp.worldHeight - worldY);
			}
			
			if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && 
				worldX - gp.tileSize < gp.player.worldX + gp.player.screenX && 
				worldY + gp.tileSize > gp.player.worldY - gp.player.screenY && 
				worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
				g2.drawImage(tile[tileNum].image, screenX, screenY, null);
			}
			else if (gp.player.screenX > gp.player.worldX ||
					 gp.player.screenY > gp.player.worldY ||
					 rightOffset > gp.worldWidth - gp.player.worldX ||
					 bottomOffset > gp.worldHeight - gp.player.worldY) {
				g2.drawImage(tile[tileNum].image, screenX, screenY, null);
			}
			worldCol++;
			
			if (worldCol == gp.maxWorldCol) {
				worldCol = 0;
				worldRow++;
			}
		}
		if (drawPath == true) {
			g2.setColor(new Color(255, 0, 0, 70));
			
			for (int i = 0; i < gp.pFinder.pathList.size(); i++) {
				int worldX = gp.pFinder.pathList.get(i).col * gp.tileSize;
				int worldY = gp.pFinder.pathList.get(i).row * gp.tileSize;
				int screenX = worldX - gp.player.worldX + gp.player.screenX;
				int screenY = worldY - gp.player.worldY + gp.player.screenY;
				
				g2.fillRect(screenX, screenY, gp.tileSize, gp.tileSize);
			}
		}
	}
}
