package data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import main.GamePanel;

public class Config {
	
	GamePanel gp;
	
	public Config(GamePanel gp) {
		this.gp = gp;
	}
	
	public void saveConfig() {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("config.txt"));
			
			// BORDERLESS WINDOW
			if (gp.borderless == true) {
				bw.write("true");
			}
			if (gp.borderless == false) {
				bw.write("false");
			}
			bw.newLine();
			
			// SOUND
			bw.write(String.valueOf(gp.sound.volumeScale));
			bw.newLine();
			
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadConfig() {
		try {
			BufferedReader br = new BufferedReader(new FileReader("config.txt"));
			
			String s = br.readLine();
			
			// BORDERLESS WINDOW
			if (s.equals("true")) {
				gp.borderless = true;
			}
			if (s.equals("false")) {
				gp.borderless = false;
			}
			
			// SOUND
			s = br.readLine();
			gp.sound.volumeScale = Integer.parseInt(s);
			
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
