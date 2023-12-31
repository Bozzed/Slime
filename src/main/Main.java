package main;

import java.awt.Taskbar;
import java.awt.Taskbar.State;

import javax.swing.*;

public class Main {
	
	public static JFrame window;
	
    public static void main(String[] args) throws Exception {
        
    	System.setProperty("sun.java2d.opengl", "true");
    	
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        
        @SuppressWarnings("unused")
		Taskbar taskbar = Taskbar.getTaskbar();
        
        window.setTitle("Slimes");
        new Main().setIcon();

        GamePanel gamePanel = new GamePanel();
        gamePanel.config.loadConfig();
        window.add(gamePanel);
        
    //    if (gamePanel.fullscreen == true) {
    //   	window.setUndecorated(true);
    //    }

        window.pack();
        
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        
        gamePanel.setupGame();
        gamePanel.startGameThread();
        
    //    taskbar.setWindowProgressState(window, State.ERROR);
    //    taskbar.setWindowProgressValue(window, 50); 
    }
    public void setIcon() {
    	ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("icon.png"));
    	window.setIconImage(icon.getImage());
    }
}
