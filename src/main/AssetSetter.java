package main;

import entity.NPC_Merchant;
import monster.RedSlime;
import monster.BlueSlime;
import monster.GreenSlime;
import entity.NPC_Big_Rock;
import entity.NPC_Gordon;
import entity.NPC_House;
import entity.NPC_Log;
import object.OBJ_Axe;
import object.OBJ_GoldCoin;
import object.OBJ_HealthSyringe;
import object.OBJ_Chest;
import object.OBJ_SilverCoin;
import object.OBJ_Sleeping_Bag;
import object.OBJ_Table;
import tile_interactive.IT_DeadFlower;
import tile_interactive.IT_DestructableWall;
import tile_interactive.IT_Fungi;
import tile_interactive.IT_Jar;
import tile_interactive.IT_Metal_Plate;
import tile_interactive.IT_YoungTree;

public class AssetSetter {
	
	GamePanel gp;
	
	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}
	public void setInteractiveTile() {
		int mapNum = 0;
		int i = 0;
		gp.iTile[mapNum][i] = new IT_YoungTree(gp, 17, 29);i++;
		gp.iTile[mapNum][i] = new IT_YoungTree(gp, 17, 30);i++;
		gp.iTile[mapNum][i] = new IT_YoungTree(gp, 17, 31);i++;
		gp.iTile[mapNum][i] = new IT_YoungTree(gp, 18, 31);i++;
		gp.iTile[mapNum][i] = new IT_YoungTree(gp, 19, 31);i++;
		gp.iTile[mapNum][i] = new IT_YoungTree(gp, 20, 31);i++;
		gp.iTile[mapNum][i] = new IT_DestructableWall(gp, 8, 22);i++;
		gp.iTile[mapNum][i] = new IT_Jar(gp, 10, 23);i++;
		
		mapNum = 1;
//		gp.iTile[mapNum][i] = new IT_Metal_Plate(gp, 21, 33);i++;
		
		mapNum = 4;
		i = 0;
		gp.iTile[mapNum][i] = new IT_Metal_Plate(gp, 23, 23);i++;
		gp.iTile[mapNum][i] = new IT_DeadFlower(gp, 32, 44);i++;
		gp.iTile[mapNum][i] = new IT_DeadFlower(gp, 32, 46);i++;
		gp.iTile[mapNum][i] = new IT_DeadFlower(gp, 28, 44);i++;
		gp.iTile[mapNum][i] = new IT_DeadFlower(gp, 23, 47);i++;
		gp.iTile[mapNum][i] = new IT_DeadFlower(gp, 14, 42);i++;
		gp.iTile[mapNum][i] = new IT_DeadFlower(gp, 4, 45);i++;
		gp.iTile[mapNum][i] = new IT_Fungi(gp, 5, 45);i++;
		gp.iTile[mapNum][i] = new IT_Jar(gp, 4, 19);i++;
		
		gp.iTile[mapNum][i] = new IT_YoungTree(gp, 39, 31);i++;
		gp.iTile[mapNum][i] = new IT_YoungTree(gp, 39, 32);i++;
		gp.iTile[mapNum][i] = new IT_YoungTree(gp, 39, 33);i++;
		gp.iTile[mapNum][i] = new IT_YoungTree(gp, 40, 33);i++;
		gp.iTile[mapNum][i] = new IT_YoungTree(gp, 40, 34);i++;
		gp.iTile[mapNum][i] = new IT_YoungTree(gp, 40, 35);i++;
		gp.iTile[mapNum][i] = new IT_YoungTree(gp, 40, 36);i++;
	}
	public void setObject() {
		int mapNum = 0;
		int i = 0;
		gp.obj[mapNum][i] = new OBJ_Axe(gp);
		gp.obj[mapNum][i].worldX = 8 * gp.tileSize;
		gp.obj[mapNum][i].worldY = 23 * gp.tileSize;
		i++;
		gp.obj[mapNum][i] = new OBJ_Chest(gp, new OBJ_HealthSyringe(gp), null);
		gp.obj[mapNum][i].worldX = 29 * gp.tileSize;
    	gp.obj[mapNum][i].worldY = 17 * gp.tileSize;
		i++;
		gp.obj[mapNum][i] = new OBJ_Chest(gp, new OBJ_Sleeping_Bag(gp), null);
		gp.obj[mapNum][i].worldX = 22 * gp.tileSize;
    	gp.obj[mapNum][i].worldY = 36 * gp.tileSize;
		i++;
		
		mapNum = 2;
		gp.obj[mapNum][i] = new OBJ_Table(gp, null);
		gp.obj[mapNum][i].worldX = 21 * gp.tileSize;
    	gp.obj[mapNum][i].worldY = 20 * gp.tileSize;
		i++;
		
		mapNum = 1;
		
		mapNum = 3;
		i = 0;
//		gp.obj[mapNum][i] = new OBJ_Iron_Door(gp);
//		gp.obj[mapNum][i].worldX = 31 * gp.tileSize;
//	   	gp.obj[mapNum][i].worldY = 9 * gp.tileSize;
//	   	gp.obj[mapNum][i].temp = true;
//		i++;
		
		mapNum = 4;
		i = 0;
		gp.obj[mapNum][i] = new OBJ_Chest(gp, new OBJ_GoldCoin(gp), new OBJ_SilverCoin(gp));
		gp.obj[mapNum][i].worldX = 23 * gp.tileSize;
    	gp.obj[mapNum][i].worldY = 15 * gp.tileSize;
		i++;
	//	gp.obj[mapNum][i] = new OBJ_Torch(gp);
	//	gp.obj[mapNum][i].worldX = 22 * gp.tileSize;
    //	gp.obj[mapNum][i].worldY = 15 * gp.tileSize;
	//	i++;
		gp.obj[mapNum][i] = new OBJ_Chest(gp, new OBJ_GoldCoin(gp), new OBJ_SilverCoin(gp));
		gp.obj[mapNum][i].worldX = 2 * gp.tileSize;
    	gp.obj[mapNum][i].worldY = 15 * gp.tileSize;
		i++;
		
		gp.obj[mapNum][i] = new OBJ_Chest(gp, new OBJ_Axe(gp), null);
		gp.obj[mapNum][i].worldX = 39 * gp.tileSize;
    	gp.obj[mapNum][i].worldY = 30 * gp.tileSize;
		i++;
		
		gp.obj[mapNum][i] = new OBJ_Chest(gp, new OBJ_Axe(gp), null);
		gp.obj[mapNum][i].worldX = 49 * gp.tileSize;
    	gp.obj[mapNum][i].worldY = 40 * gp.tileSize;
		i++;
		
//		gp.obj[mapNum][i] = new OBJ_Iron_Door(gp);
//		gp.obj[mapNum][i].worldX = 20 * gp.tileSize;
//	   	gp.obj[mapNum][i].worldY = 23 * gp.tileSize;
//	   	gp.obj[mapNum][i].temp = true;
//		i++;
	}
	public void setNPC () {
		int mapNum = 0;
		int i = 0;
	//	gp.npc[mapNum][i] = new NPC_Cloak(gp);
	//	gp.npc[mapNum][i].worldX = gp.tileSize * 23;
	//	gp.npc[mapNum][i].worldY = gp.tileSize * 23;
    //	i++;
    	gp.npc[mapNum][i] = new NPC_Log(gp);
		gp.npc[mapNum][i].worldX = (gp.tileSize * 6) - (gp.tileSize/2);
		gp.npc[mapNum][i].worldY = gp.tileSize * 23;
    	i++;
		mapNum = 1;
		

		mapNum = 2;
		i = 0;		
		gp.npc[mapNum][i] = new NPC_Merchant(gp);
		gp.npc[mapNum][i].worldX = gp.tileSize * 21;
		gp.npc[mapNum][i].worldY = gp.tileSize * 19;
		i++;
		mapNum = 4;
		i = 0;
		
		gp.npc[mapNum][i] = new NPC_Big_Rock(gp);
		gp.npc[mapNum][i].worldX = gp.tileSize * 23;
		gp.npc[mapNum][i].worldY = gp.tileSize * 27;
    	i++;
		
		gp.npc[mapNum][i] = new NPC_House(gp);
		gp.npc[mapNum][i].worldX = gp.tileSize * 11;
		gp.npc[mapNum][i].worldY = gp.tileSize * 15;
		i++;
		gp.npc[mapNum][i] = new NPC_Gordon(gp);
		gp.npc[mapNum][i].worldX = gp.tileSize * 0;
		gp.npc[mapNum][i].worldY = gp.tileSize * 40;
		i++;
//		gp.npc[mapNum][i] = new NPC_Big_Rock(gp);
//		gp.npc[mapNum][i].worldX = gp.tileSize * 26;
//		gp.npc[mapNum][i].worldY = gp.tileSize * 23;
//   	i++;
	}
	public void setMON () {
		int mapNum = 0;
		int i = 0;
		gp.monster[mapNum][i] = new GreenSlime(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize * 23;
		gp.monster[mapNum][i].worldY = gp.tileSize * 15;
		i++;
		gp.monster[mapNum][i] = new RedSlime(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize * 32;
		gp.monster[mapNum][i].worldY = gp.tileSize * 41;
		i++;
		
		mapNum = 3;
		
		mapNum = 4;
		i = 0;
	//	gp.monster[mapNum][i] = new MON_GreenSlime(gp);
	//	gp.monster[mapNum][i].worldX = gp.tileSize * 25;
	//	gp.monster[mapNum][i].worldY = gp.tileSize * 25;
	//	i++;	
	//	gp.monster[mapNum][i] = new MON_PurpleSlime(gp);
	//	gp.monster[mapNum][i].worldX = gp.tileSize * 9;
	//	gp.monster[mapNum][i].worldY = gp.tileSize * 27;
	//	i++;
	}
	public void setQuest1() {
		int i = 0;
		
		int mapNum = 4;
		
		i = 0;
		gp.monster[mapNum][i] = new GreenSlime(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize * 25;
		gp.monster[mapNum][i].worldY = gp.tileSize * 21;
		i++;	
		gp.monster[mapNum][i] = new BlueSlime(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize * 21;
		gp.monster[mapNum][i].worldY = gp.tileSize * 21;
		i++;
	}
}
