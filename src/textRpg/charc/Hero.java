package textRpg.charc;

import textRpg.resource.Config;

public class Hero extends Character {
	private int a_distance = 6;
	private int s_distance = 3;
	private int a_attackP ;
	private int s_attackP ;
	
	public Hero(String name) {
		super(name, 50, 1, 7, 18, 15);
		
		a_attackP = super.getAttack_p();
		s_attackP = super.getAttack_p() * 2;
	}
	
	public Hero(String name, int hp, int level, int attack_p, int x, int y) {
		super(name, hp, level, attack_p, x, y);
		
		a_attackP = super.getAttack_p();
		s_attackP = super.getAttack_p() * 2;
	}
	
	public void levelUp() {
		int maxhp = this.getMaxhp();
		this.setMaxhp((int)(maxhp * 1.6));
		this.setHp(this.getMaxhp());
		this.setLevel(this.getLevel() + 1);
		this.setA_attackP((int)(this.getA_attackP() * 2.4));
		this.setS_attackP((int)(this.getS_attackP() * 2.4));
		
	}
	
	public void move(int direction) {
		Config con = Config.getInstance();
		
		if(direction == 37 && this.getX() != 2) {
			this.setX(this.getX() - 1);

		}else if(direction == 39 && this.getX() != con.getX() - 1) {
			this.setX(this.getX() + 1);

		}else if(direction == 38 && this.getY() != 2) {
			this.setY(this.getY() - 1);

		}else if(direction == 40 && this.getY() != con.getY() - 1) {
			this.setY(this.getY() + 1);
		}
	}
	
	//원거리공격
	public int attack(Character real) {
		int result = 0;
		int dist = distance(this, real);
		
		if(dist <= a_distance) {
			int hp = real.getHp()- this.getA_attackP();

			if(hp <= 0) hp = 0;
			
			real.setHp(hp);
			
			result = 1;
		}
		
		return result;
	}
	
	public int attack2(Character real) {
		int result = 0;
		int dist = distance(this, real);
		
		if(dist <= s_distance) {
			int hp = real.getHp() - this.getS_attackP();

			if(hp <= 0) hp = 0;
			
			real.setHp(hp);
			
			result = 1;
		}
		
		return result;
	}
	
	
	public int getA_distance() {
		return a_distance;
	}

	public void setA_distance(int a_distance) {
		this.a_distance = a_distance;
	}

	public int getS_distance() {
		return s_distance;
	}

	public void setS_distance(int s_distance) {
		this.s_distance = s_distance;
	}

	public int getA_attackP() {
		return a_attackP;
	}

	public void setA_attackP(int a_attackP) {
		this.a_attackP = a_attackP;
	}

	public int getS_attackP() {
		return s_attackP;
	}

	public void setS_attackP(int s_attackP) {
		this.s_attackP = s_attackP;
	}
}
