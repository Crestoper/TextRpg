package textRpg.resource;

import textRpg.charc.Hero;
import textRpg.charc.Monster;

public class Config {
	private Config(){}
	
	private static class ConfigLazyHolder{
		public static final Config cInstance = new Config();
	}
	
	public static Config getInstance() {
        return ConfigLazyHolder.cInstance;
	}
	
	private final int map_x = 35;
	private final int map_y = 17;
	
	private final Monster mon1 = new Monster("Monster1",1);
	private final Monster mon2 = new Monster("Monster2",2);
	private final Monster monKing = new Monster("Monster King",3);
	
	private int life = 3;
	
	private Monster monNow = mon1;
	
	private Hero hero = null;
	
	public void setHero(String name) {
		hero = new Hero.Builder().name(name).hp(50).level(1).attack_p(7).x(18).y(16).build(); 
	}
	
	public Hero getHero() {
		return hero;
	}
	
	public Monster getMonster(String name) {
		Monster mon = null;
		
		switch(name) {
			case "mon1" : mon = mon1; break;
			case "mon2" : mon = mon2; break;
			case "monKing" : mon = monKing; break;
			default : mon = monKing;
		}
		
		return mon;
	}
	
	public void setMonNow(String name) {
		Monster mon = null;
		
		switch(name) {
			case "mon1" : mon = mon1; break;
			case "mon2" : mon = mon2; break;
			case "monKing" : mon = monKing; break;
			default : mon = monKing;
		}
		
		monNow = mon;
	}
	
	public Monster getMonNow() {
		return monNow;
	}
	
	
	
	public int getLife() {
		return life;
	}
	
	public void setLife() {
		life--;
	}
	
	public int getX() {
		return map_x;
	}
	
	public int getY() {
		return map_y;
	}



}
