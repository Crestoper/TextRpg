package textRpg.charc;

import textRpg.resource.Config;

public class Hero extends Character {
	private int a_distance = 6;
	private int s_distance = 3;
	private int a_attackP ;
	private int s_attackP ;
	
	public Hero(Builder builder) {
		super(builder.name, builder.hp, builder.level, builder.attack_p, builder.x, builder.y);
		
		a_attackP = super.getAttack_p();
		s_attackP = super.getAttack_p() * 2;
	}
	
	public static class Builder{
		private String name; 
		private int hp;
		private int level;
		private int attack_p;
		private int x;
		private int y;
		
		public Builder name(String s) {
			name = s;
			return this;
		}
		
		public Builder hp(int s) {
			hp = s;
			return this;
		}
		
		public Builder level(int s) {
			level = s;
			return this;
		}
		
		public Builder attack_p(int s) {
			attack_p = s;
			return this;
		}
		
		public Builder x(int s) {
			x = s;
			return this;
		}
		
		public Builder y(int s) {
			y = s;
			return this;
		}
		
		public Hero build() {
			return new Hero(this);
		}
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
