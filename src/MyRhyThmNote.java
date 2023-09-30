import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import javax.swing.JFrame;

public class MyRhyThmNote extends JFrame {
	
	public static Game game;
	private final int FRAME_WIDTH = 1400;  //가로 크기
	private final int FRAME_HEIGHT = 900;  //세로 크기
	String imagePath = System.getProperty("user.dir")+"/src/images/";  //이미지 상대 경로
	Image gameImg;  //더블 버퍼링용 이미지
	Graphics2D gameGraphics;  //더블 버퍼링용 그래픽
	boolean isGame = false;  //게임 스크린이냐?
	
	
	public MyRhyThmNote() {
		addKeyListener(new NoteKeyListener());  //키 리스너 추가
		game = new Game();  //게임화면 실행 test
		
		//기본 설정
		setTitle("My Rhythm Note");  //제목 설정
		setSize(FRAME_WIDTH, FRAME_HEIGHT);  //사이즈 설정
		setLocationRelativeTo(null);  //윈도우 창 정중앙에
		setResizable(false);  //화면 크기 조정 불가
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void paint(Graphics g) {
		gameImg = createImage(FRAME_WIDTH,FRAME_HEIGHT);  //더블 버퍼링용 이미지 생성
		gameGraphics = (Graphics2D) gameImg.getGraphics();  //게임 그래픽에 생성
		gameGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);  //안티 앨리어싱 설정(화질 좋아지게)
		screenDraw(gameGraphics);  //화면 그리기
		g.drawImage(gameImg, 0, 0, null);  //마지막에 이미지를 추가
		repaint();  //계속 리페인트 해줌
		g.dispose();
	}
	
	
	void screenDraw(Graphics2D g){  //화면 그리는 메서드
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);  //안티 앨리어싱 설정(화질 좋아지게)
		if(isGame) {  //지금 게임 실행이면?
			game.drawScreen(gameGraphics);  //게임 화면 나옴
		}else {  //게임 실행이 아니라면
		}
	}
}
