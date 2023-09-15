import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

	public class NoteKeyListener extends KeyAdapter{
		@Override  //키보드 눌렀을때 
		public void keyPressed(KeyEvent e) {
			MyRhyThmNote.game.key_mp3.play(System.getProperty("user.dir")+"/src/musics/Key_Effect.mp3");
			if(e.getKeyChar() == 's') {
				MyRhyThmNote.game.pressed_S();
			}
			if(e.getKeyChar() == 'd') {
				MyRhyThmNote.game.pressed_D();
			}
			if(e.getKeyChar() == 'f') {
				MyRhyThmNote.game.pressed_F();
			}
			if(e.getKeyChar() == 'j') {
				MyRhyThmNote.game.pressed_J();
			}
			if(e.getKeyChar() == 'k') {
				MyRhyThmNote.game.pressed_K();
			}
			if(e.getKeyChar() == 'l') {
				MyRhyThmNote.game.pressed_L();
			}
		}
		//눌렀던 키보드를 뗐을때
		public void keyReleased(KeyEvent e) {
			if(e.getKeyChar() == 's') {
				MyRhyThmNote.game.released_S();
			}
			if(e.getKeyChar() == 'd') {
				MyRhyThmNote.game.released_D();
			}
			if(e.getKeyChar() == 'f') {
				MyRhyThmNote.game.released_F();
			}
			if(e.getKeyChar() == 'j') {
				MyRhyThmNote.game.released_J();
			}
			if(e.getKeyChar() == 'k') {
				MyRhyThmNote.game.released_K();
			}
			if(e.getKeyChar() == 'l') {
				MyRhyThmNote.game.released_L();
			}
		}
	}