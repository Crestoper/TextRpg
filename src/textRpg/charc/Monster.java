package textRpg.charc;

import textRpg.resource.Config;

public class Monster extends Character{
	
	private int attackDistance;
	
	public Monster(String name, int level) {
		super(name, (int)(30 * 1.6 * level), level, (int)(6 * 1.6 * level), 18, 2);
		this.setAttackDistance(2);
	}

	public int getAttackDistance() {
		return attackDistance;
	}

	public void setAttackDistance(int attackDistance) {
		this.attackDistance = attackDistance;
	}
}
