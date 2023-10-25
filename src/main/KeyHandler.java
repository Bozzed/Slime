package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
	
	GamePanel gp;	
	public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, shotKeyPressed, spacePressed;
	
	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		//TITLE STATE
		if (gp.gameState == gp.titleState) {
			titleState(code);
		}
		// PLAY STATE
		else if (gp.gameState == gp.playState) {
			playState(code);
		}
		// TRADE STATE
		else if (gp.gameState == gp.tradeState) {
			tradeState(code);
		}
		// PAUSE STATE
		else if (gp.gameState == gp.pauseState) {
			pauseState(code);
		}
		
		// DIALOGUE STATE
		else if (gp.gameState == gp.dialogueState || gp.gameState == gp.cutsceneState) {
			dialogueState(code);
		}
		
		// STATUS STATE
		else if (gp.gameState == gp.statusState) {
			statusState(code);
		}
		else if (gp.gameState == gp.gameOverState) {
			gameOverState(code);
		}
		else if (gp.gameState == gp.optionsState) {
			optionsState(code);
		}
		else if (gp.gameState == gp.mapState) {
			mapState(code);
		}
		else if (gp.gameState == gp.workbenchState) {
			if (code == KeyEvent.VK_ENTER) {
				gp.player.selectWorkbenchItem();
			}
			if (code == KeyEvent.VK_ESCAPE) {
				gp.gameState = gp.playState;
			}
		}
	}
	public void mapState(int code) {
		if (code == KeyEvent.VK_M) {
			gp.gameState = gp.playState;
		}
	}
	public void playerInventory(int code) {
		if (code == KeyEvent.VK_W) {
			if (gp.ui.playerSlotRow != 0) {
				gp.ui.playerSlotRow--;
				gp.playSE(2);
			}
		}
		if (code == KeyEvent.VK_A) {
			if (gp.ui.playerSlotCol != 0) {
				gp.ui.playerSlotCol--;
				gp.playSE(2);
			}
		}
		if (code == KeyEvent.VK_S) {
			if (gp.ui.playerSlotRow != 3) {
				gp.ui.playerSlotRow++;
				gp.playSE(2);
			}
		}
		if (code == KeyEvent.VK_D) {
			if (gp.ui.playerSlotCol != 4) {
				gp.ui.playerSlotCol++;
				gp.playSE(2);
			}
		}
		if (code == KeyEvent.VK_E) {
			gp.gameState = gp.playState;
			gp.ui.playerSlotRow = 0;
			gp.ui.playerSlotCol = 0;
		}
		if (code == KeyEvent.VK_ENTER) {
			gp.player.selectItem();
			gp.playSE(2);
		}
	}
	public void npcInventory(int code) {
		if (code == KeyEvent.VK_UP) {
			if (gp.ui.npcSlotRow != 0) {
				gp.ui.npcSlotRow--;
				gp.playSE(2);
			}
		}
		if (code == KeyEvent.VK_LEFT) {
			if (gp.ui.npcSlotCol != 0) {
				gp.ui.npcSlotCol--;
				gp.playSE(2);
			}
		}
		if (code == KeyEvent.VK_DOWN) {
			if (gp.ui.npcSlotRow != 3) {
				gp.ui.npcSlotRow++;
				gp.playSE(2);
			}
		}
		if (code == KeyEvent.VK_RIGHT) {
			if (gp.ui.npcSlotCol != 4) {
				gp.ui.npcSlotCol++;
				gp.playSE(2);
			}
		}
		
	}
	public void tradeState (int code) {
		if (code == KeyEvent.VK_ENTER) {
			enterPressed = true;
		}
		if (gp.ui.subState == 0) {
			if (code == KeyEvent.VK_UP) {
				gp.ui.commandNum--;
				if (gp.ui.commandNum < 0) {
					gp.ui.commandNum = 2;
				}
			}
			if (code == KeyEvent.VK_DOWN) {
				gp.ui.commandNum++;
				if (gp.ui.commandNum > 2) {
					gp.ui.commandNum = 0;
				}
			}
		}
		if (gp.ui.subState == 1) {
			npcInventory(code);
			if (code == KeyEvent.VK_ESCAPE) {
				gp.ui.subState = 0;
			}
		}if (gp.ui.subState == 2) {
			playerInventory(code);
			if (code == KeyEvent.VK_ESCAPE) {
				gp.ui.subState = 0;
			}
		}
	}
	public void gameOverState(int code) {
		if (code == KeyEvent.VK_UP) {
			gp.ui.commandNum--;
			if (gp.ui.commandNum < 0) {
				gp.ui.commandNum = 0;
			}
			gp.playSE(2);
		}
		if (code == KeyEvent.VK_DOWN) {
			gp.ui.commandNum++;
			if (gp.ui.commandNum > 1) {
				gp.ui.commandNum = 1;
			}
			gp.playSE(2);
		}
		if (code == KeyEvent.VK_ENTER) {
			if (gp.ui.commandNum == 0) {
				gp.gameState = gp.playState;
				gp.retry();
			}
			if (gp.ui.commandNum == 1) {
				gp.gameState = gp.titleState;
				System.exit(0);
			}
		}
	}
	public void titleState(int code) {
		if (code == KeyEvent.VK_W) {
			if (gp.ui.commandNum != 0) {gp.ui.commandNum--;}
		}
        if (code == KeyEvent.VK_S) {
        	if (gp.ui.commandNum != 1) {gp.ui.commandNum++;}
		}
        if (code == KeyEvent.VK_ENTER) {
        	switch(gp.ui.commandNum) {
        	case 0:
        		gp.setupGame();
        		gp.gameState = gp.playState;
        		break;
        	case 1:
        		gp.saveLoad.load();
        		gp.gameState = gp.playState;
        		break;
        	}
        }
	}
	public void playState(int code) {
		if (code == KeyEvent.VK_W) {upPressed = true;}
        if (code == KeyEvent.VK_S) {downPressed = true;}
        if (code == KeyEvent.VK_A) {leftPressed = true;}
        if (code == KeyEvent.VK_D) {rightPressed = true;}
        if (code == KeyEvent.VK_P) {gp.gameState = gp.pauseState;}
        if (code == KeyEvent.VK_ESCAPE) {gp.gameState = gp.optionsState;}
        if (code == KeyEvent.VK_Q) {shotKeyPressed = true;}
        if (code == KeyEvent.VK_SPACE) {spacePressed = true;}
        if (code == KeyEvent.VK_ENTER) {enterPressed = true;}
        if (code == KeyEvent.VK_M) {gp.gameState = gp.mapState;}
        if (code == KeyEvent.VK_E) {gp.gameState = gp.statusState;}
        if (code == KeyEvent.VK_R) {
        	switch (gp.currentMap) {
        	case 1:gp.tileM.loadMap("/maps/worldV1.txt", 1); break;
        	case 2:gp.tileM.loadMap("/maps/interiorV0.txt", 2); break;
        	case 4:gp.tileM.loadMap("/maps/worldV3.txt", 4); break;
        	}
        }
        if (code == KeyEvent.VK_F3) {
        	if (gp.debug == true) {
        		gp.debug = false;
        	} else {
        		gp.debug = true;
        	}
        }
	}
	public void pauseState (int code) {
		if (code == KeyEvent.VK_P) {gp.gameState = gp.playState;}
	}
	public void dialogueState (int code) {
		if (code == KeyEvent.VK_ENTER) {
			enterPressed = true;
		}
	}
	public void statusState (int code) {
		playerInventory(code);
	}
	public void optionsState (int code) {
		if (code == KeyEvent.VK_ESCAPE) {
			gp.gameState = gp.playState;
		}
		if (code == KeyEvent.VK_ENTER) {
			enterPressed = true;
			gp.playSE(2);
		}
		if (code == KeyEvent.VK_A) {
			if (gp.ui.subState == 0) {
				if (gp.ui.commandNum == 1 && gp.sound.volumeScale > 0) {
					gp.sound.volumeScale--;
					gp.sound.checkVolume();
					gp.playSE(2);
				}
				if (gp.ui.commandNum == 2 && gp.sound.volumeScale < 5) {
					gp.sound.volumeScale--;
					gp.sound.checkVolume();
					gp.playSE(2);
				}
			}
		}
		if (code == KeyEvent.VK_D) {
			if (gp.ui.subState == 0) {
				if (gp.ui.commandNum == 1 && gp.sound.volumeScale < 5) {
					gp.sound.volumeScale++;
					gp.sound.checkVolume();
				}
				if (gp.ui.commandNum == 2 && gp.sound.volumeScale < 5) {
					gp.sound.volumeScale++;
					gp.sound.checkVolume();
				}
			}
		}
		int maxCommandNum = 0;
		switch (gp.ui.subState) {
		case 0: maxCommandNum = 5;
		}
		if (code == KeyEvent.VK_W) {
			gp.ui.commandNum--;
			if (gp.ui.commandNum < 0) {
				gp.ui.commandNum = maxCommandNum;
				gp.playSE(2);
			}
		}
		if (code == KeyEvent.VK_S) {
			gp.ui.commandNum++;
			if (gp.ui.commandNum > maxCommandNum) {
				gp.ui.commandNum = 0;
				gp.playSE(2);
			}
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		
		if (code == KeyEvent.VK_W) {upPressed = false;}
        if (code == KeyEvent.VK_S) {downPressed = false;}
        if (code == KeyEvent.VK_A) {leftPressed = false;}
        if (code == KeyEvent.VK_D) {rightPressed = false;}
        if (code == KeyEvent.VK_Q) {shotKeyPressed = false;}
        if (code == KeyEvent.VK_ENTER) {enterPressed = false;}
        if (code == KeyEvent.VK_SPACE) {spacePressed = false;}
    }
}