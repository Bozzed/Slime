package ai;

import entity.Entity;

public class SimplePathFinder {
	Entity searcher;
	
	Entity target;
	
	public SimplePathFinder(Entity searcher, Entity target) {
		this.searcher = searcher;
		this.target = target;
	}
	public void goToTarget(int speed) {
		searcher.checkCollision();
		if (searcher.collisionOn == false) {
			if (target.worldX > searcher.worldX) {
				searcher.worldX += speed;
			}
			if (target.worldX < searcher.worldX) {
				searcher.worldX -= speed;
			}
			if (target.worldY > searcher.worldY) {
				searcher.worldY += speed;
			}
			if (target.worldY < searcher.worldY) {
				searcher.worldY -= speed;
			}
		}
	}
}
