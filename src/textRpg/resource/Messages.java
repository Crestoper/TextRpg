package textRpg.resource;

public enum Messages {
	 MSG_EX_1_1("EX_1_1","안녕하세요.\ntextRPG에 오신것을 환영합니다.\n몬스터들로 부터 마을을 구하시고\n우리의 왕이되어주세요!\n")
	,MSG_EX_1_2("EX_1_2", "입력창에 이름을 입력하시고\n엔터키를 누르시면 게임이 시작됩니다.\n")
	,MSG_EX_2("EX_2", "님 게임이 시작되었습니다.\n몬스터가 출현했습니다.\n이동 : 방향키\n원거리 공격(거리 6) : a\n근거리 공격(거리 3) : s\n")
	,MSG_EX_3("EX_3", "보스를 잡았습니다.\n마을에 평화가 깃듭니다.\n")
	,MSG_GAME_OVER("GAMEOVER", "Game Over!\n")
	,MSG_GAME_CLEAR("CLEAR", "Game Clear!\n")
	,MSG_STAGE_CLEAR("CLEAR", " Stage Clear!\n")
	,MSG_LEVEL_UP("LEVEL_UP", "Level Up!\n")
	,MSG_ATTACK_Y("ATTACK", "의 공격이 성공했습니다.\n")
	,MSG_ATTACK_N("ATTACK", "의 공격이 실패했습니다.\n")
	,MSG_MOVE("MOVE", "이 이동했습니다.\n")
	,MSG_HP_ZERO("HP", "의 HP가 0이 되었습니다.\n")
	,MSG_LIFE_MINUS("LIFE", "Life가 1 차감됩니다.\n")
	,MSG_MON_APPEAR("APPEAR", "이 출현했습니다.\n")
	,MSG_MON_DIE("DIE", "Die")
	,MSG_SAVE_YN("SAVE","기록을 남기시겠습니까?\n저장위치 : 프로젝트 폴더\n파일명 : BattleHistory.txt")
	,MSG_SAVE("SAVE", "결과 기록")
	,FLAG_Y("Y", 1)
	,FLAG_N("N", 0)
	;
	
	private String key;
	private Object val;
	
	Messages(String s, Object o){
		this.key = s;
		this.val = o;
	}

	public String getKey() {
		return key;
	}
	
	public String getString() {
		return val.toString();
	}
	
	public int getInt(){
		return (int) val;
	}
}
