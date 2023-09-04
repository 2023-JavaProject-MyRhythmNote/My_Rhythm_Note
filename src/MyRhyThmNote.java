import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class MyRhyThmNote extends JFrame {
	
	public static Game game;
	private final int FRAME_WIDTH = 1400;  //가로 크기
	private final int FRAME_HEIGHT = 900;  //세로 크기
	Image gameImg;  //더블 버퍼링용 이미지
	Graphics2D gameGraphics;  //더블 버퍼링용 그래픽
	boolean isGame = true;  //게임 스크린이냐?
	
	public MyRhyThmNote() {
		game = new Game();
		addKeyListener(new NoteKeyListener());
		//기본 설정
		setTitle("My Rhythm Note");
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setLocationRelativeTo(null);  //윈도우 창 정중앙에
		setResizable(false);  //화면 크기 조정 불가
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void paint(Graphics g) {
		gameImg = createImage(FRAME_WIDTH,FRAME_HEIGHT);
		gameGraphics = (Graphics2D) gameImg.getGraphics();
		gameGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);  //안티 앨리어싱 설정(화질 좋아지게)
		screenDraw(gameGraphics);
		g.drawImage(gameImg, 0, 0, null);  //마지막에 이미지를 추가
		repaint();
		g.dispose();
	}
	
	
	void screenDraw(Graphics2D g){  //화면 그리는 메서드
		if(isGame) {
			game.drawScreen(gameGraphics);
		}
	}
}
