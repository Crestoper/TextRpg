package textRpg.event;

public class BattleHistory {
	private String seq_no;
	private String time;
	private String content;
	
	public String getSeq_no() {
		return seq_no;
	}
	public void setSeq_no(int i) {
		this.seq_no = "[" + i + "]";
	}
	public String getTime() {
		return time;
	}
	public void setTime(String l) {
		this.time = l;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
