package textRpg.event;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import textRpg.charc.Character;
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

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		
		if(con.getHero() == null) {
			if(keyCode == 10) {
				win = Windows.getInstance();
				win.setHero();
				apendMessage(Messages.MSG_EX_1_1.getString(), 0);
			}
		}else{
			if(endFlag == Messages.FLAG_N.getInt()) {
				event(keyCode);

				win.setView();
			}
		}
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

	
	
	private void inputEvent(int e) {
		String message = "";
		
		if(con.getHero().getHp() > 0){
			if(e == 65) {
				if(attack(con.getMonNow(), Messages.FLAG_ATTACK_HERO_2.getInt()) == Messages.FLAG_Y.getInt()) message = con.getHero().getName() + Messages.MSG_ATTACK_Y.getString();
				else message = con.getHero().getName() + Messages.MSG_ATTACK_N.getString();
			
			}else if(e == 83) {
				if(attack(con.getMonNow(), Messages.FLAG_ATTACK_HERO_1.getInt()) == Messages.FLAG_Y.getInt()) message = con.getHero().getName() + Messages.MSG_ATTACK_Y.getString();
				else message = con.getHero().getName() + Messages.MSG_ATTACK_N.getString();
			
			}else if(e == 37 || e == 38 || e == 39 || e == 40){
				heroMove(e);
				message = con.getHero().getName() + Messages.MSG_MOVE.getString();
			}else {
				return;
			}
		}
		
		apendMessage(message , 1);
	}
	
	private void monsterEvent() {
		String message =  con.getMonNow().getName() + Messages.MSG_MOVE.getString();
		
		monMoving();
		
		if(attack(con.getHero(), Messages.FLAG_ATTACK_MON.getInt()) == Messages.FLAG_Y.getInt()) message +=  con.getMonNow().getName() + Messages.MSG_ATTACK_Y.getString();
		
		apendMessage(message, 1);
	}
	
	private void heroStatusCheck() {
		String message = "";
		if(con.getLife() > 1) {
			if(con.getHero().getHp() <= 0) {
				con.getHero().setHp(con.getHero().getMaxhp());
				con.setLife();
				message = con.getHero().getName() + Messages.MSG_HP_ZERO.getString() + Messages.MSG_LIFE_MINUS.getString();
				
				apendMessage(message, 1);
			}
		}else{
			endFlag = Messages.FLAG_Y.getInt();
			message = con.getHero().getName() + Messages.MSG_HP_ZERO.getString() + Messages.MSG_GAME_OVER.getString();
			con.setLife();
			
			apendMessage(message, 1);

			save();
		}
	}
	
	private void  monsterStatusCheck() {
		if(con.getMonNow().getHp() <= 0){
			String message = "";
			
			String name = con.getMonNow().getName();
			message = name + Messages.MSG_HP_ZERO.getString() + name + Messages.MSG_STAGE_CLEAR.getString();
			
			levelUp();
			message += Messages.MSG_LEVEL_UP.getString();
					
			if(con.getMonNow() == con.getMonster("mon1")){
				 con.setMonNow("mon2");
				 
				 message += con.getMonNow().getName() + Messages.MSG_MON_APPEAR.getString();
			
			}else if(con.getMonNow() == con.getMonster("mon2")) {
				con.setMonNow("monKing");
				
				message += con.getMonNow().getName() + Messages.MSG_MON_APPEAR.getString();
			
			}else{
				endFlag = Messages.FLAG_Y.getInt();
				message += Messages.MSG_GAME_CLEAR.getString();
				
				apendMessage(message, 1);
				apendMessage(Messages.MSG_EX_3.getString(), 0);
				
				save();
			}
			
			apendMessage(message, 1);
		}
	}
	
	private void save() {
		int flag = win.saveFlag();
		if(flag == Messages.FLAG_N.getInt()) {
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
	
	private void apendMessage(String message, int flag) {
		if(flag == Messages.FLAG_Y.getInt()) win.apendHistoryDisplay(message);

		battleHistory.add(message);
		
		win.clearInputText();
	}
	
	
	//캐릭터
	public void levelUp() {
		int maxhp = con.getHero().getMaxhp();
		con.getHero().setMaxhp((int)(maxhp * 1.6));
		con.getHero().setHp(con.getHero().getMaxhp());
		con.getHero().setLevel(con.getHero().getLevel() + 1);
		con.getHero().setA_attackP((int)(con.getHero().getA_attackP() * 2.4));
		con.getHero().setS_attackP((int)(con.getHero().getS_attackP() * 2.4));
		
	}
	
	public void heroMove(int direction) {
		int x = con.getHero().getX();
		int y = con.getHero().getY();
		
		if(direction == 37 && x != 2) {
			con.getHero().setX(x - 1);

		}else if(direction == 39 && x != con.getX() - 1) {
			con.getHero().setX(x + 1);

		}else if(direction == 38 && y != 2) {
			con.getHero().setY(y - 1);

		}else if(direction == 40 && y != con.getY() - 1) {
			con.getHero().setY(y + 1);
		}
	}
	
	public int attack(Character c, int attackType) {
		int dist = con.getHero().distance(con.getHero(), con.getMonNow());
		int aDist = 0;
		int aPoint = 0;
		int result = 0; 
		
		if(attackType == Messages.FLAG_ATTACK_HERO_1.getInt()) {
			aDist = con.getHero().getA_distance();
			aPoint = con.getHero().getA_attackP();

		}else if(attackType == Messages.FLAG_ATTACK_HERO_2.getInt()) {
			aDist = con.getHero().getS_distance();
			aPoint = con.getHero().getS_attackP();
		
		}else if(attackType == Messages.FLAG_ATTACK_MON.getInt()) {
			aDist = con.getMonNow().getAttackDistance();
			aPoint = con.getMonNow().getAttackDistance();
		}
		
		if(dist <= aDist) {
			int hp = c.getHp()- aPoint;

			if(hp <= 0) hp = 0;
			
			c.setHp(hp);
			
			result = 1;
		}
		
		return result;
	}
	
	public void monMoving() {
		int xy = (int)(Math.random() * 2) + 1;
		int sign = (int)(Math.random() * 2) + 1;
		int moving = (int)(Math.random() * 2) + 1;
		
		int x = con.getMonNow().getX();
		int y = con.getMonNow().getY();
		
		int move;
				
		if(xy == 1) {
			if((sign == 1 && x == 30) || (sign == 1 && x == 29 && moving == 2)) {
				
			}else if((sign == 2 && x == 1) || (sign == 2 && x == 2 && moving == 2)) {
				
			}else if(sign == 1) {
				move = x + moving;

				if(move != con.getX()) con.getMonNow().setX(move);
			
			}else {
				move = x - moving;
			
				if(move != 1) con.getMonNow().setX(move);
			}
		}else {
			if((sign == 1 && y == 10) || (sign == 1 && y == 9 && moving == 2)) {
				
			}else if((sign == 2 && y == 1) || (sign == 2 && y == 2 && moving == 2)) {
				
			}else if(sign == 1) {
				move = y + moving;

				if(move != con.getY()) con.getMonNow().setY(move);
			
			}else {
				move = y - moving;
			
				if(move != 1) con.getMonNow().setY(move);
			}
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}
}
