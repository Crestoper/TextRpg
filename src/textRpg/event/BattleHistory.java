package textRpg.event;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class BattleHistory {
	private final ArrayList<History> battleHistory = new ArrayList<History>();
	
	int i = 1;
	
	public class History{
		private String seq_no;
		private String time;
		private String content;
		
		public void setSeq_no(int i) {
			this.seq_no = "[" + i + "]";
		}
		public void setTime(String l) {
			this.time = l;
		}
		public void setContent(String content) {
			this.content = content;
		}
		
		public String get() {
			String line = System.getProperty("line.separator");
			String message = seq_no + " " + time + line + content;
			
			return message;
		}
	}
	
	public void add(String message){
		History his = new History();
		
		SimpleDateFormat format = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
		String line = System.getProperty("line.separator");
		
		his.setSeq_no(i++);
		his.setTime(format.format (System.currentTimeMillis()));
		his.setContent(message.replace("!", "!" + line).replace(".", "." + line));
		
		battleHistory.add(his);
	}
	
	public ArrayList<History> get() {
		return battleHistory;
	}
}
