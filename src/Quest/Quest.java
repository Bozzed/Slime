package Quest;

import entity.Entity;
import main.GamePanel;

public class Quest {
	
	GamePanel gp;
	
	public Entity reqEntity;
	public int reqEntityAmount;
	int moneyReward;
	int expReward;
	
	public Quest(GamePanel gp, Entity reqEntity, int reqEntityAmount, int moneyReward, int expReward) {
		this.gp = gp;
		
		this.reqEntity = reqEntity;
		this.reqEntityAmount = reqEntityAmount;
		this.moneyReward = moneyReward;
		this.expReward = expReward;
	}
	
	public void complete() {
		gp.player.money += moneyReward;
		gp.player.exp += expReward;
		gp.player.amountQuestComplete = 0;
		
		gp.player.quest = null;
		
		gp.ui.addMessage("Quest Complete!");
		gp.ui.addMessage("Earned $" + moneyReward + "!");
		gp.ui.addMessage("Gained " + expReward + "EXP!");
	}

}
