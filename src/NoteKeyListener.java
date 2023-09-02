import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

	public class NoteKeyListener extends KeyAdapter{
		
		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyChar() == 's') {
				Main.game.pressed_S();
			}
			if(e.getKeyChar() == 'd') {
				Main.game.pressed_D();
			}
			if(e.getKeyChar() == 'f') {
				Main.game.pressed_F();
			}
			if(e.getKeyChar() == 'j') {
				Main.game.pressed_J();
			}
			if(e.getKeyChar() == 'k') {
				Main.game.pressed_K();
			}
			if(e.getKeyChar() == 'l') {
				Main.game.pressed_L();
			}
			Main.game.repaint();
		}
		
		public void keyReleased(KeyEvent e) {
			if(e.getKeyChar() == 's') {
				Main.game.released_S();
			}
			if(e.getKeyChar() == 'd') {
				Main.game.released_D();
			}
			if(e.getKeyChar() == 'f') {
				Main.game.released_F();
			}
			if(e.getKeyChar() == 'j') {
				Main.game.released_J();
			}
			if(e.getKeyChar() == 'k') {
				Main.game.released_K();
			}
			if(e.getKeyChar() == 'l') {
				Main.game.released_L();
			}
			Main.game.repaint();
		}
	}