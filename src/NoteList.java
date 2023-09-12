
public class NoteList {
	String noteType;  //노트의 타입
	int startTime;  //노트가 나오는 시간
	
	public NoteList(String noteType, int startTime) {  //생성자
		this.noteType = noteType;
		this.startTime = startTime;
	}
	
	//getter
	String getNoteType() {
		return noteType;
	}
	int getStartTime() {
		return startTime;
	}
}
