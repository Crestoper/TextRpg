package textRpg.event;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import textRpg.charc.Hero;
import textRpg.charc.Monster;
import textRpg.event.BattleHistory;
import textRpg.event.BattleHistory.History;
import textRpg.resource.Config;
import textRpg.resource.Messages;
import textRpg.window.Windows;

public class GameEvent  implements KeyListener{
	public static int endFlag = Messages.FLAG_N.getInt();
	
	BattleHistory battleHistory = new BattleHistory();
	
	Windows win = null;
	Config con = Config.getInstance();
	
	Monster monNow = con.getMonster("mon1");
	Hero hero = null;

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(hero == null) {
			if(keyCode == 10) {
				win = Windows.getInstance();
				setHero();
			}
		}else{
			if(endFlag == Messages.FLAG_N.getInt()) {
				event(keyCode);

				setView();
			}
		}
	}
	
	private void setHero() {
		String bath = "";
		
		hero = con.setHero(win.getInputText());
		
		bath = hero.getName() + Messages.MSG_EX_2.getString();

		apendBath(Messages.MSG_EX_1_1.getString(), 0);
		win.setExplainDisplay(bath);
		win.clearInputText();
		
		setView();
	}
	
	private void event(int e) {
		inputEvent(e);
		monsterEvent();
		statusCheck();
	}
	
	private void statusCheck() {
		heroStatusCheck();
		monsterStatusCheck();
	}

	public void setView() {
		win.setState(hero, monNow);
		win.setMap(hero, monNow);
	}
	
	private void inputEvent(int e) {
		String bath = "";
		 
		if(hero.getHp() > 0){
			if(e == 65) {
				if(hero.attack(monNow) == Messages.FLAG_Y.getInt()) bath = hero.getName() + Messages.MSG_ATTACK_Y.getString();
				else bath = hero.getName() + Messages.MSG_ATTACK_N.getString();
			
			}else if(e == 83) {
				if(hero.attack2(monNow) == Messages.FLAG_Y.getInt()) bath = hero.getName() + Messages.MSG_ATTACK_Y.getString();
				else bath = hero.getName() + Messages.MSG_ATTACK_N.getString();
			
			}else if(e == 37 || e == 38 || e == 39 || e == 40){
				hero.move(e);
				bath = hero.getName() + Messages.MSG_MOVE.getString();
			}
		}
		
		apendBath(bath , 1);
	}
	
	private void monsterEvent() {
		String bath =  monNow.getName() + Messages.MSG_MOVE.getString();
		
		monNow.autoMoving();
		
		if(monNow.attack(hero) == Messages.FLAG_Y.getInt()) bath +=  monNow.getName() + Messages.MSG_ATTACK_Y.getString();
		
		apendBath(bath, 1);
	}
	
	private void heroStatusCheck() {
		String bath = "";
		if(con.getLife() > 1) {
			if(hero.getHp() <= 0) {
				hero.setHp(hero.getMaxhp());
				con.setLife();
				bath = hero.getName() + Messages.MSG_HP_ZERO.getString() + Messages.MSG_LIFE_MINUS.getString();
				
				apendBath(bath, 1);
			}
		}else{
			endFlag = Messages.FLAG_Y.getInt();
			bath = hero.getName() + Messages.MSG_HP_ZERO.getString() + Messages.MSG_GAME_OVER.getString();
			con.setLife();
			
			apendBath(bath, 1);

			save();
		}
	}
	
	private void  monsterStatusCheck() {
		String bath = "";
		
		if(monNow.getHp() <= 0){
			String name = monNow.getName();
			bath = name + Messages.MSG_HP_ZERO.getString() + name + Messages.MSG_STAGE_CLEAR.getString();
			
			hero.levelUp();
			bath += Messages.MSG_LEVEL_UP.getString();
					
			if(monNow == con.getMonster("mon1")){
				 monNow = con.getMonster("mon2");
				 
				 bath += monNow.getName() + Messages.MSG_MON_APPEAR.getString();
			
			}else if(monNow == con.getMonster("mon2")) {
				monNow = con.getMonster("monKing");
				
				bath += monNow.getName() + Messages.MSG_MON_APPEAR.getString();
			
			}else{
				endFlag = Messages.FLAG_Y.getInt();
				bath += Messages.MSG_GAME_CLEAR.getString();
				
				apendBath(bath, 1);
				apendBath(Messages.MSG_EX_3.getString(), 0);
				
				save();
			}
		}
		
		apendBath(bath, 1);
	}
	
	private void save() {
		int flag = win.saveFlag();
		if(flag == 0) {
			FileWriter fw = null;
			
			try {
				File path = new File("");
				
				fw = new FileWriter(new File(path + "BattleHistory.txt"));
	
				for(History s : battleHistory.get()) {
					fw.write(s.get());
				}
				
			} catch (Exception e) {
				System.out.println("Save시 에러 발생");
			}finally {
				if(fw != null)
					try {
						fw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
		}
	}
	
	int i = 1;
	private void apendBath(String message, int flag) {
		if(flag == 1) win.apendHistoryDisplay(message);

		battleHistory.add(message);
		
		win.clearInputText();
	}
	
	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}
}
