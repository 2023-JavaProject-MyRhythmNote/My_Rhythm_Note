import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

	public class NoteKeyListener extends KeyAdapter{
//		MP3Player key_mp3 = new MP3Player();  //test
		@Override  //키보드 눌렀을때 
		public void keyPressed(KeyEvent e) {
			if(Screen.game == null) return;
			
			if(e.getKeyChar() == 's') {
				Screen.game.pressed_S();
			}
			if(e.getKeyChar() == 'd') {
				Screen.game.pressed_D();
			}
			if(e.getKeyChar() == 'f') {
				Screen.game.pressed_F();
			}
			if(e.getKeyChar() == 'j') {
				Screen.game.pressed_J();
			}
			if(e.getKeyChar() == 'k') {
				Screen.game.pressed_K();
			}
			if(e.getKeyChar() == 'l') {
				Screen.game.pressed_L();
			}
		}
		//눌렀던 키보드를 뗐을때
		public void keyReleased(KeyEvent e) {
			if(Screen.game == null) return;
			
			if(e.getKeyChar() == 's') {
				Screen.game.released_S();
			}
			if(e.getKeyChar() == 'd') {
				Screen.game.released_D();
			}
			if(e.getKeyChar() == 'f') {
				Screen.game.released_F();
			}
			if(e.getKeyChar() == 'j') {
				Screen.game.released_J();
			}
			if(e.getKeyChar() == 'k') {
				Screen.game.released_K();
			}
			if(e.getKeyChar() == 'l') {
				Screen.game.released_L();
			}
		}
	}