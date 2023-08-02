package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import ai.PathFinder;
import data.Config;
import data.SaveLoad;
import entity.Entity;
import entity.EntityGenerator;
import entity.Player;
import environment.EnvironmentManager;
import object.OBJ_Chest;
import tile.Map;
import tile.TileManager;
import tile_interactive.InteractiveTile;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable {

	// SCREEN SETTINGS
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3;
    public final int tileSize = originalTileSize * scale; // 48x48 tile
    public int maxScreenCol = 20;
    public int maxScreenRow = 13;
    public int screenWidth = tileSize * maxScreenCol; // 960 pixels
    public int screenHeight = tileSize * maxScreenRow;// 576 pixels
    
    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;
    
    public boolean fullscreen = false;
    
    // FULL SCREEN
    BufferedImage screen;
    public Graphics2D g2;
    
    // WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;
    public final int maxMap = 10;
    public int currentMap = 4;
    
    // AREA
    public int currentArea;
    public int nextArea;
    public final int outside = 50;
    public final int indoor = 51;
    public final int dungeon = 52;
    
    // DEBUG
    public boolean debug = true;
    // FPS LIMIT
    public int FPS = 60;
    
    // TILE MANAGER
    public TileManager tileM = new TileManager(this);
    
    PointerInfo mInfo = MouseInfo.getPointerInfo();
	Point point = mInfo.getLocation();
	public int mouseX;
	public int mouseY;
	public String mouseDir;
    
    // SOUND
    public Sound sound = new Sound();
    
    // KEY HANDLER
    public KeyHandler keyH = new KeyHandler(this);
    
    // MOUSE HANDLER
    public MouseHandler mouseH = new MouseHandler(this);
    
    // MAP
    Map map = new Map(this);
    
    // GAME THREAD
    Thread gameThread;
    
    // CUTSCENE MANAGER
    public CutsceneManager csManager = new CutsceneManager(this);
    
    // COLLISION CHECKER
    public CollisionChecker cChecker = new CollisionChecker(this);

    // ENVIRONMENT MANAGER
    public EnvironmentManager eManager = new EnvironmentManager(this);
    // PATH FINDER
    public PathFinder pFinder = new PathFinder(this);
    
    // ASSET SETTER
    public AssetSetter aSetter = new AssetSetter(this);
    
    // GAME HANDLERS
    public Player player = new Player(this, keyH);
    public Entity obj[][] = new Entity[maxMap][10];
    public Entity npc[][] = new Entity[maxMap][5];
    public Entity monster[][] = new Entity[maxMap][10];
    public InteractiveTile iTile[][] = new InteractiveTile[maxMap][50];
    public Entity projectile[][] = new Entity[maxMap][5];
//  public ArrayList<Projectile> projectileList = new ArrayList<>();
    public ArrayList<Entity> particleList = new ArrayList<>();
    ArrayList<Entity> entityList = new ArrayList<>();
    Config config = new Config(this);
    
    // SAVE AND LOAD
    public SaveLoad saveLoad = new SaveLoad(this);
    
    // UI
    public UI ui = new UI(this);
    
    // ENTITY GENERATOR
    public EntityGenerator eGenerator = new EntityGenerator(this);
    
    // EVENT HANDLER
    public EventHandler eHandler = new EventHandler(this);
    
    // GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int statusState = 4;
    public final int gameOverState = 6;
    public final int transitionState = 7;
    public final int tradeState = 8;
    public final int sleepState = 9;
    public final int cutsceneState = 11;
    public final int optionsState = 12;
    public final int mapState = 13;
    
    public boolean bossBattleOn = false;
    public boolean skeletonLordCutsceneSeen = false;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.addMouseListener(mouseH);
        this.setFocusable(true);
    }
    public void retry () {
    	player.setDefaultPositions();
    	player.restoreAfterDeath();
    	removeTempObjects();
    	bossBattleOn = false;
    }
    public void setupGame() {
    	aSetter.setObject();
    	aSetter.setNPC();
    	aSetter.setMON();
    	aSetter.setInteractiveTile();
    	eManager.setup();
    	gameState = titleState;
    	currentArea = outside;
    	
    	screen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
    	g2 = (Graphics2D)screen.getGraphics();
    	
    	if (fullscreen == true) {
    		setFullScreen();
    	}
    }
    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();
    }
    public void setFullScreen() {
    	GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    	GraphicsDevice gd = ge.getDefaultScreenDevice();
    	gd.setFullScreenWindow(Main.window);
    	
    	screenWidth2 = Main.window.getWidth();
    	screenHeight2 = Main.window.getHeight();
    }
    @Override
    public void run() {
        
    	double drawInterval = 1000000000/FPS;
    	double delta = 0;
    	long lastTime = System.nanoTime();
    	long currentTime;
    	long timer = 0;
    	long drawCount = 0;
    	
    	while (gameThread != null) {
    		currentTime = System.nanoTime();
    		
    		delta += (currentTime - lastTime) / drawInterval;
    		timer += (currentTime - lastTime);
    		lastTime = currentTime;
    		
    		if (delta >= 1) {
    			update();
    			drawToTempScreen();
    			drawToScreen();
    			delta--;
    			drawCount++;
    		}
    		if (timer >= 1000000000) {
    			System.out.println("FPS: " + drawCount);
    			drawCount = 0;
    			timer = 0;
    		}
    	}
    }
    public void update() {
    	if (gameState == playState) {
    		getMouseValues();
    		// PLAYER
    		player.update();
    		
    		// NPC
            for (int i = 0; i < npc[1].length; i++) {
            	if (npc[currentMap][i] != null) {
            		npc[currentMap][i].update();
            	}
            }
            for (int i = 0; i < obj[1].length; i++) {
            	if (obj[currentMap][i] != null && obj[currentMap][i].dropped == true 
            			|| obj[currentMap][i] != null && obj[currentMap][i].name == OBJ_Chest.objName 
            			|| obj[currentMap][i] != null && obj[currentMap][i].type == obj[currentMap][i].pickupOnly_type
            			|| obj[currentMap][i] != null && obj[currentMap][i].update == true
            			&& obj[currentMap][i].inCamera() == true) {
            		obj[currentMap][i].down2 = obj[currentMap][i].down1;
            		obj[currentMap][i].left1 = obj[currentMap][i].down1;
            		obj[currentMap][i].up1 = obj[currentMap][i].down1;
            		obj[currentMap][i].update();
            	}
            	if (obj[currentMap][i] != null && obj[currentMap][i].update == true && obj[currentMap][i].inCamera() == true) {
            		obj[currentMap][i].update();
            	}
            }
            for (int i = 0; i < monster[1].length; i++) {
            	if (monster[currentMap][i] != null) {
            		if (monster[currentMap][i].alive == true && monster[currentMap][i].dying == false && monster[currentMap][i].inCamera() == true) {
            			monster[currentMap][i].update();
            		}
            		if (monster[currentMap][i].alive == false && monster[currentMap][i].inCamera() == true) {
            			monster[currentMap][i] = null;
            		}
            	}
            }
            for (int i = 0; i < projectile[1].length; i++) {
            	if (projectile[currentMap][i] != null) {
            		if (projectile[currentMap][i].alive == true && projectile[currentMap][i].inCamera() == true) {
                		projectile[currentMap][i].update();
                	}
                	if (projectile[currentMap][i].alive == false && projectile[currentMap][i].inCamera() == true) {
                		projectile[currentMap][i] = null;
                	}
            	}            	
            }
            for (int i = 0; i < particleList.size(); i++) {
            	if (particleList.get(i) != null) {
            		if (particleList.get(i).alive == true) {
            			particleList.get(i).update();
                	}
                	if (particleList.get(i).alive == false) {
                		particleList.remove(i);
                	}
            	}            	
            }
            for (int i = 0; i < iTile[1].length; i++) {
            	if (iTile[currentMap][i] != null && iTile[currentMap][i].inCamera() == true) {
            		iTile[currentMap][i].update();
            	}
            }
            eManager.update();
    	}
    	if (gameState == pauseState) {}
    	if (player.life > player.maxLife) {
			player.life = player.maxLife;
		}
    }
    public void drawToScreen() {
    	Graphics g = getGraphics();
    	g.drawImage(screen, 0, 0, screenWidth2, screenHeight2, null);
    	g.dispose();
    }
    public void drawToTempScreen() {
    	
    	BufferedImage mouse = null;
		
		try {
			mouse = ImageIO.read(getClass().getResourceAsStream("/mouse/MouseIdle.gif"));
		//	image = uTool.scaleImage(image, tileSize, tileSize);
		} catch (IOException e) {
			e.printStackTrace();
		}
	//	g2.drawImage(image, mouseX, mouseY, tileSize*4, tileSize*4, null);
		setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
		new ImageIcon(mouse).getImage(),
		new Point(0,0),"custom cursor"));
		
        // TITLE SCREEN
        if (gameState == titleState) {
        	ui.draw(g2);
        }
        
        // OTHER
        else {
            // TILE
            tileM.draw(g2);
            
            // INTERACTIVE TILE
            for (int i = 0; i < iTile[1].length; i++) {
            	if (iTile[currentMap][i] != null) {
            		iTile[currentMap][i].draw(g2);
            	}
            }
            
            // ADD ENTITIES TO LIST
            entityList.add(player);
            
            for (int i = 0; i < npc[1].length; i++) {
            	if (npc[currentMap][i] != null) {
            		entityList.add(npc[currentMap][i]);
            	}
            }
            
            for (int i = 0; i < monster[1].length; i++) {
            	if (monster[currentMap][i] != null) {
            		entityList.add(monster[currentMap][i]);
            	}
            }
            
            for (int i = 0; i < projectile[1].length; i++) {
            	if (projectile[currentMap][i] != null) {
            		entityList.add(projectile[currentMap][i]);
            	}
            }
            for (int i = 0; i < particleList.size(); i++) {
            	if (particleList.get(i) != null) {
            		entityList.add(particleList.get(i));
            	}
            }
            
            for (int i = 0; i < obj[1].length; i++) {
            	if (obj[currentMap][i] != null) {
            		entityList.add(obj[currentMap][i]);
            	}
            }
            
            // SORT
            Collections.sort(entityList, new Comparator<Entity>() {
            	@Override
            	public int compare(Entity e1, Entity e2) {
            		int result = Integer.compare(e2.worldY, e2.worldY);
            		
            		return result;
                }
            });
            
            // DRAW
            for (int i = 0; i < entityList.size(); i++) {
            	if (entityList.get(i).inCamera() == true) {
            		entityList.get(i).draw(g2);
            	}
            }
            
            // EMPTY LIST
            entityList.clear();
            	
            
            // EM
            eManager.draw(g2);
            
            // UI
            ui.draw(g2);
            
            // CUTSCENE
            csManager.draw(g2);
            
            if (debug == true) {
            	g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));
            	g2.setColor(Color.white);
            	
            	int x = 10;
            	int y = 400;
            	
            	g2.drawString("WorldX: " + player.worldX, x, y); y += 20;
            	g2.drawString("WorldY: " + player.worldY, x, y); y += 20;
            	g2.drawString("Col: " + (player.worldX + player.solidArea.x)/tileSize, x, y); y += 20;
            	g2.drawString("Row: " + (player.worldY + player.solidArea.y)/tileSize, x, y); y += 20;
            }
        }
    }
    public void playMusic(int i) {
    	sound.setFile(i);
    	sound.play();
    	sound.loop();
    }
    public void stopMusic() {
    	sound.stop();
    }
    public void playSE(int i) {
    	sound.setFile(i);
    	sound.play();
    }
    public void removeTempObjects() {
    	for (int mapNum = 0; mapNum < maxMap; mapNum++) {
    		for (int i = 0; i < obj[1].length; i++) {
    			if (obj[currentMap][i] != null && obj[currentMap][i].temp == true) {
    				obj[currentMap][i] = null;
    			}
    		}
    	}
    }
    public void changeArea() {
    	currentArea = nextArea;
    }
    public void getMouseValues() {
    	mouseX = (int) point.getX();
    	mouseY = (int) point.getY();
    }
}
