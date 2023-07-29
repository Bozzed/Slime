package entity;

import main.GamePanel;
import object.OBJ_Axe;
import object.OBJ_Bow;
import object.OBJ_Broadsword;
import object.OBJ_Flashlight;
import object.OBJ_Gate;
import object.OBJ_Glock_17;
import object.OBJ_GoldCoin;
import object.OBJ_Iron_Door;
import object.OBJ_Pickaxe;
import object.OBJ_Shortsword;
import object.OBJ_Rock;
import object.OBJ_SilverCoin;
import object.OBJ_Sleeping_Bag;
import object.OBJ_Sryinge;

public class EntityGenerator {
	GamePanel gp;
	
	public EntityGenerator(GamePanel gp) {
		this.gp = gp;
	}
	public Entity getObject(String itemName) {
		
		Entity obj = null;
		switch (itemName) {
		case OBJ_Axe.objName: obj = new OBJ_Axe(gp);
			break;
		case OBJ_Flashlight.objName: obj = new OBJ_Flashlight(gp);
			break;
		case OBJ_Glock_17.objName: obj = new OBJ_Glock_17(gp);
			break;
		case OBJ_Shortsword.objName: obj = new OBJ_Shortsword(gp);
			break;
		case OBJ_Rock.objName: obj = new OBJ_Rock(gp);
			break;
		case OBJ_Sleeping_Bag.objName: obj = new OBJ_Sleeping_Bag(gp);
			break;
		case OBJ_Sryinge.objName: obj = new OBJ_Sryinge(gp);
			break;
		case OBJ_Gate.objName: obj = new OBJ_Gate(gp);
			break;
		case OBJ_Pickaxe.objName: obj = new OBJ_Pickaxe(gp);
			break;
		case OBJ_Iron_Door.objName: obj = new OBJ_Iron_Door(gp);
			break;
		case OBJ_GoldCoin.objName: obj = new OBJ_GoldCoin(gp);
			break;
		case OBJ_SilverCoin.objName: obj = new OBJ_SilverCoin(gp);
			break;
		case OBJ_Broadsword.objName: obj = new OBJ_Broadsword(gp);
			break;
		case OBJ_Bow.objName: obj = new OBJ_Bow(gp);
			break;
		}
		return obj;
	}
}
