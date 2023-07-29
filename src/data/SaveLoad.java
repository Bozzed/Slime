package data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import main.GamePanel;

public class SaveLoad {
	GamePanel gp;
	
	public SaveLoad (GamePanel gp) {
		this.gp = gp;
	}
	public void save() {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("save.dat")));
			
			DataStorage ds = new DataStorage();
			
			ds.level = gp.player.level;
			ds.maxLife = gp.player.maxLife;
			ds.life = gp.player.life;
			ds.strength = gp.player.strength;
			ds.dexterity = gp.player.dexterity;
			ds.exp = gp.player.exp;
			ds.nextLevelExp = gp.player.nextLevelExp;
			ds.money = gp.player.money;
			
			for (int i = 0; i < gp.player.inventory.size(); i++) {
				ds.itemNames.add(gp.player.inventory.get(i).name);
				ds.itemAmounts.add(gp.player.inventory.get(i).amount);
			}
			
			ds.currentWeaponSlot = gp.player.getCurrentWeaponSlot();
			
			ds.mapObjectNames = new String[gp.maxMap][gp.obj[1].length];
			ds.mapObjectOpened = new boolean[gp.maxMap][gp.obj[1].length];
			
			for (int mapNum = 0; mapNum < gp.maxMap; mapNum++) {
				for (int i = 0; i < gp.obj[1].length; i++) {
					if (gp.obj[mapNum][i] == null) {
						ds.mapObjectNames[mapNum][i] = "NA";
					}
					else {
						ds.mapObjectNames[mapNum][i] = gp.obj[mapNum][i].name;
						ds.mapObjectOpened[mapNum][i] = gp.obj[mapNum][i].open;
						
					}
				}
			}
			
			// WRITE
			oos.writeObject(ds);
		} catch (Exception e) {
			System.out.println("Save Error.");
		}
	}
	public void load() {
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("save.dat")));
			
			DataStorage ds = (DataStorage)ois.readObject();
			
			gp.player.level = ds.level;
			gp.player.maxLife = ds.maxLife;
			gp.player.life = ds.life;
			gp.player.strength = ds.strength;
			gp.player.dexterity = ds.dexterity;
			gp.player.exp = ds.exp;
			gp.player.nextLevelExp = ds.nextLevelExp;
			gp.player.money = ds.money;
			
			gp.player.inventory.clear();
			
			for (int i = 0; i < ds.itemNames.size(); i++) {
				gp.player.inventory.add(gp.eGenerator.getObject(ds.itemNames.get(i)));
				gp.player.inventory.get(i).amount = ds.itemAmounts.get(i);
			}
			
			gp.player.currentWeapon = gp.player.inventory.get(ds.currentWeaponSlot);
			
			gp.player.getAttack();
			gp.player.getPlayerAttackImage();
			
			for (int mapNum = 0; mapNum < gp.maxMap; mapNum++) {
				for (int i = 0; i < gp.obj[1].length; i++) {
					if (ds.mapObjectNames[mapNum][i] == "NA" ) {
						gp.obj[mapNum][i] = null;
					}
					else {
						gp.obj[mapNum][i] = gp.eGenerator.getObject(ds.mapObjectNames[mapNum][i]);
						if (gp.obj[mapNum][i] != null) {
							gp.obj[mapNum][i].open = ds.mapObjectOpened[mapNum][i];
						}					
						if (gp.obj[mapNum][i].open == true) {
							gp.obj[mapNum][i].down1 = gp.obj[mapNum][i].image;
						}
					}
				}
			}
			
		} catch (Exception e) {
			System.out.println("Load Error.");
		}
	}
}
