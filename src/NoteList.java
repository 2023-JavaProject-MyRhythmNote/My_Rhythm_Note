
public class NoteList {
	String noteType;  //노트의 타입
	int startTime;  //노트가 나오는 시간
	
	public NoteList(String noteType, int startTime) {
		this.noteType = noteType;
		this.startTime = startTime;
	}
	
	String getNoteType() {
		return noteType;
	}
	int getStartTime() {
		return startTime;
	}
}
