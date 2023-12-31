import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javazoom.jl.player.MP3Player;

	public class NoteKeyListener extends KeyAdapter{
		MP3Player key_mp3 = new MP3Player();
		
		@Override  //키보드 눌렀을때 
		public void keyPressed(KeyEvent e) {
			key_mp3.play(System.getProperty("user.dir")+"/src/musics/Key_Effect.mp3");  //키보드 효과음
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
			//ESC 키 눌렀을 때 게임 종료
	        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
	        	Screen.game.pressed_ESC();
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