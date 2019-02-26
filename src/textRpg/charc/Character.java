package textRpg.charc;

public class Character {
	
	private String name;	
	private int hp;
	private int maxhp;	
	private int level;
	private int attack_p;
	private int X,Y;
	
	public Character(String name, int hp, int level, int attack_p, int x, int y) {
		this.name = name;
		this.hp = hp;
		this.maxhp = hp;
		this.level = level;
		this.attack_p = attack_p;
		X = x;
		Y = y;
	}

	public int distance(Character hero, Character mon) {
		
		int hx = hero.getX();
		int hy = hero.getY();
		int mx = mon.getX();
		int my = mon.getY();
		
		int dx = Math.abs(hx - mx);
		int dy = Math.abs(hy - my);
		
		return dx + dy;		
		
	}
	
	
	public int getMaxhp() {
		return maxhp;
	}

	public void setMaxhp(int maxhp) {
		this.maxhp = maxhp;
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		
		if( hp < 0 );
		else this.hp = hp;
		
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getAttack_p() {
		return attack_p;
	}

	public void setAttack_p(int attack_p) {
		this.attack_p = attack_p;
	}

	public int getX() {
		return X;
	}

	public void setX(int x) {
		X = x;
	}

	public int getY() {
		return Y;
	}

	public void setY(int y) {
		Y = y;
	}
}
