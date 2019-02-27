package textRpg.window;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import textRpg.charc.Hero;
import textRpg.charc.Monster;
import textRpg.event.GameEvent;
import textRpg.resource.Config;
import textRpg.resource.Messages;

public class Windows extends JFrame{
	private Windows() {
		setPannel();
	}
	
	private static class WindowsLazyHolder{
		public static final Windows wInstance = new Windows();
	}
	
	public static Windows getInstance() {
        return WindowsLazyHolder.wInstance;
	}
	
	GameEvent event = new GameEvent();
	Config con = Config.getInstance();
	
	JPanel mainPane = new JPanel();
	JLabel distanceDisplay = new JLabel();
	JTextField inputDisplay = new JTextField();
	JTextArea heroDisplay = new JTextArea();
	JTextArea explainDisplay = new JTextArea();
	JTextArea battlehistoryDisplay = new JTextArea();
	JTextArea mapDisplay = new JTextArea();
	JTextArea monster1 = new JTextArea();
	JTextArea monster2 = new JTextArea();
	JTextArea monsterking = new JTextArea();
	JScrollPane scrollPane = new JScrollPane(battlehistoryDisplay);
	
	public void setPannel() {
		setSize(820,600);
		setTitle("textRPG");	
		setBackground(Color.WHITE);
		setDefaultCloseOperation(3);
		setLocationRelativeTo(null);
		
		distanceDisplay.setBorder(new TitledBorder("Distance"));
		distanceDisplay.setSize(220, 120);
		distanceDisplay.setLocation(10, 10);
		
		heroDisplay.setBorder(new TitledBorder("Hero State"));
		heroDisplay.setSize(220, 150);
		heroDisplay.setLocation(10, 125);
		
		explainDisplay.setBorder(new TitledBorder("Game Explain"));
		explainDisplay.setSize(220, 200);
		explainDisplay.setLocation(10, 270);
		
		battlehistoryDisplay.setBorder(new TitledBorder("Battle History"));
		battlehistoryDisplay.setSize(205, 400);
		battlehistoryDisplay.setLocation(195, 10);
		battlehistoryDisplay.setEditable(false);
		
		monster1.setBorder(new TitledBorder("Monster1"));
		monster1.setSize(120, 160);
		monster1.setLocation(430, 10);
		
		monster2.setBorder(new TitledBorder("Monster2"));
		monster2.setSize(120, 160);
		monster2.setLocation(550, 10);
		
		monsterking.setBorder(new TitledBorder("Monster King"));
		monsterking.setSize(120, 160);
		monsterking.setLocation(670, 10);
		
		mapDisplay.setBorder(new TitledBorder("Map"));
		mapDisplay.setSize(360, 300);
		mapDisplay.setLocation(430, 170);
		
		scrollPane.setSize(200, 460);
		scrollPane.setLocation(230, 10);
		
		inputDisplay.setBorder(new TitledBorder("input"));
		inputDisplay.setSize(780, 80);
		inputDisplay.setLocation(10, 470);
		inputDisplay.addKeyListener(event);
		
		mainPane.setBorder(new TitledBorder("mainP"));
		mainPane.setLayout(null);
		mainPane.setLocation(0, 0);
		mainPane.add(distanceDisplay);
		mainPane.add(heroDisplay);
		mainPane.add(explainDisplay);
		mainPane.add(monster1);
		mainPane.add(monster2);
		mainPane.add(monsterking);
		mainPane.add(mapDisplay);
		mainPane.add(scrollPane);
		mainPane.add(inputDisplay);
		
		getContentPane().add(mainPane, null);
		
		setVisible(true);
		
		inputDisplay.requestFocus();
		
		explainDisplay.append(Messages.MSG_EX_1_1.getString() + Messages.MSG_EX_1_2.getString());
	}
	
	public void apendHistoryDisplay(String bath){
		battlehistoryDisplay.append(bath);
	}
	
	public void clearInputText() {
		inputDisplay.setText("");
	}
	
	public void setView() {
		setState(con.getHero(), con.getMonNow());
		setMap(con.getHero(), con.getMonNow());
	}
	
	public void setHero() {
		String message = "";

		con.setHero(inputDisplay.getText());
		
		message = con.getHero().getName() + Messages.MSG_EX_2.getString();
		
		explainDisplay.setText(message);
		inputDisplay.setText("");
		
		setView();
	}
	
	public void setState(Hero hero, Monster monNow) {
		if(event.endFlag == Messages.FLAG_N.getInt()){
			heroDisplay.setText("Life : " + con.getLife() + "\n");
			heroDisplay.append("Level : " + hero.getLevel() + "\n");
			heroDisplay.append("Hp : " + hero.getHp() + "\n");
			heroDisplay.append("공격력(근) : " + hero.getA_attackP() + "\n");
			heroDisplay.append("공격력(원) : " + hero.getS_attackP() + "\n");
		
		}else {
			heroDisplay.setText(Messages.MSG_GAME_OVER.getString());
			explainDisplay.setText(Messages.MSG_GAME_OVER.getString());
		}
	
		if(monNow == con.getMonster("mon1")) {
			setMonsterInfo(monster1, monNow);

		}else if(monNow == con.getMonster("mon2")) {
			monster1.setText(Messages.MSG_MON_DIE.getString());
			
			setMonsterInfo(monster2, monNow);
		
		}else if(monNow == con.getMonster("monKIng")){
			if(monNow.getHp() > 0) {
				monster2.setText(Messages.MSG_MON_DIE.getString());
				
				setMonsterInfo(monsterking, monNow);
				
			}else {
				monsterking.setText(Messages.MSG_MON_DIE.getString());
				explainDisplay.setText(Messages.MSG_EX_3.getString());
				inputDisplay.setText("");
			}
		}
		
		int dist = hero.distance(hero, monNow);
		distanceDisplay.setText(Integer.toString(dist));
	}
	
	private void setMonsterInfo(JTextArea target, Monster monNow){
		target.setText("Hp : " + monNow.getHp() + "\n");
		target.append("공격력 : " + monNow.getAttack_p() + "\n");
		target.append("공격거리 : " + monNow.getAttackDistance() + "\n");
	}
	
	public void setMap(Hero hero, Monster monNow) {
		mapDisplay.setText("");
		for(int y = 1; y <= 17; y++) {
			for(int x = 1; x <= 35; x++) {
				if(x == hero.getX() && y == hero.getY()) mapDisplay.append("Ｈ");
				else if(x == monNow.getX() && y == monNow.getY()) mapDisplay.append("Ｍ");
				else if(x == 1 || x == con.getX() || y == 1 || y == con.getY()) mapDisplay.append("▧");
				else mapDisplay.append("□");
			}
			mapDisplay.append("\n");
		}
	}
	
	public int saveFlag() {
		return JOptionPane.showConfirmDialog(null, Messages.MSG_SAVE_YN.getString(), Messages.MSG_SAVE.getString(), JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
	}
}
