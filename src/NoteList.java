
public class NoteList {
	String noteType;
	int speed;
	
	public NoteList(String noteType, int speed) {
		this.noteType = noteType;
		this.speed = speed;
	}
	
	String getNoteType() {
		return noteType;
	}
}
