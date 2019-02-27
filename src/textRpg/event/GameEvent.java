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
		String message = "";
		
		hero = con.setHero(win.getInputText());
		
		message = hero.getName() + Messages.MSG_EX_2.getString();

		apendMessage(Messages.MSG_EX_1_1.getString(), 0);
		win.setExplainDisplay(message);
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
		String message = "";
		 
		if(hero.getHp() > 0){
			if(e == 65) {
				if(hero.attack(monNow) == Messages.FLAG_Y.getInt()) message = hero.getName() + Messages.MSG_ATTACK_Y.getString();
				else message = hero.getName() + Messages.MSG_ATTACK_N.getString();
			
			}else if(e == 83) {
				if(hero.attack2(monNow) == Messages.FLAG_Y.getInt()) message = hero.getName() + Messages.MSG_ATTACK_Y.getString();
				else message = hero.getName() + Messages.MSG_ATTACK_N.getString();
			
			}else if(e == 37 || e == 38 || e == 39 || e == 40){
				hero.move(e);
				message = hero.getName() + Messages.MSG_MOVE.getString();
			}
		}
		
		apendMessage(message , 1);
	}
	
	private void monsterEvent() {
		String message =  monNow.getName() + Messages.MSG_MOVE.getString();
		
		monNow.autoMoving();
		
		if(monNow.attack(hero) == Messages.FLAG_Y.getInt()) message +=  monNow.getName() + Messages.MSG_ATTACK_Y.getString();
		
		apendMessage(message, 1);
	}
	
	private void heroStatusCheck() {
		String message = "";
		if(con.getLife() > 1) {
			if(hero.getHp() <= 0) {
				hero.setHp(hero.getMaxhp());
				con.setLife();
				message = hero.getName() + Messages.MSG_HP_ZERO.getString() + Messages.MSG_LIFE_MINUS.getString();
				
				apendMessage(message, 1);
			}
		}else{
			endFlag = Messages.FLAG_Y.getInt();
			message = hero.getName() + Messages.MSG_HP_ZERO.getString() + Messages.MSG_GAME_OVER.getString();
			con.setLife();
			
			apendMessage(message, 1);

			save();
		}
	}
	
	private void  monsterStatusCheck() {
		String message = "";
		
		if(monNow.getHp() <= 0){
			String name = monNow.getName();
			message = name + Messages.MSG_HP_ZERO.getString() + name + Messages.MSG_STAGE_CLEAR.getString();
			
			hero.levelUp();
			message += Messages.MSG_LEVEL_UP.getString();
					
			if(monNow == con.getMonster("mon1")){
				 monNow = con.getMonster("mon2");
				 
				 message += monNow.getName() + Messages.MSG_MON_APPEAR.getString();
			
			}else if(monNow == con.getMonster("mon2")) {
				monNow = con.getMonster("monKing");
				
				message += monNow.getName() + Messages.MSG_MON_APPEAR.getString();
			
			}else{
				endFlag = Messages.FLAG_Y.getInt();
				message += Messages.MSG_GAME_CLEAR.getString();
				
				apendMessage(message, 1);
				apendMessage(Messages.MSG_EX_3.getString(), 0);
				
				save();
			}
		}
		
		apendMessage(message, 1);
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
	private void apendMessage(String message, int flag) {
		if(flag == 1) win.apendHistoryDisplay(message);

		battleHistory.add(message);
		
		win.clearInputText();
	}
	
	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}
}
