package textRpg.charc;

import textRpg.resource.Config;

public class Monster extends Character{
	
	private int attackDistance;
	
	public Monster(String name, int level) {
		super(name, (int)(30 * 1.6 * level), level, (int)(6 * 1.6 * level), 18, 2);
		this.setAttackDistance(2);
	}
	
	public int attack(Character real) {
		int result = 0;
		int dist = distance(this, real);
		
		if(dist <= attackDistance) {
			int hp = real.getHp();
			hp = hp - this.getAttack_p();	
			if(hp <= 0) hp = 0;
			real.setHp(hp);
			
			result = 1;
		}
		
		return result;
	}

	
	public void autoMoving() {
		Config con = Config.getInstance();
		
		int xy = (int)(Math.random() * 2) + 1;
		int sign = (int)(Math.random() * 2) + 1;
		int moving = (int)(Math.random() * 2) + 1;
		
		int x = getX();
		int y = getY();
		
		int move;
				
		if(xy == 1) {
			if((sign == 1 && x == 30) || (sign == 1 && x == 29 && moving == 2)) {
				
			}else if((sign == 2 && x == 1) || (sign == 2 && x == 2 && moving == 2)) {
				
			}else if(sign == 1) {
				move = x + moving;

				if(move != con.getX())setX(move);
			
			}else {
				move = x - moving;
			
				if(move != 1) setX(move);
			}
		}else {
			if((sign == 1 && y == 10) || (sign == 1 && y == 9 && moving == 2)) {
				
			}else if((sign == 2 && y == 1) || (sign == 2 && y == 2 && moving == 2)) {
				
			}else if(sign == 1) {
				move = y + moving;

				if(move != con.getY()) setY(move);
			
			}else {
				move = y - moving;
			
				if(move != 1) setY(move);
			}
		}
	}

	public int getAttackDistance() {
		return attackDistance;
	}

	public void setAttackDistance(int attackDistance) {
		this.attackDistance = attackDistance;
	}
}
