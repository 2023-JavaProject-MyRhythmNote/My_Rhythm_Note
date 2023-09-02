import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.RepaintManager;
import javax.swing.Timer;

import javazoom.jl.player.MP3Player;

public class Game extends JFrame{
	private final int FRAME_WIDTH = 1400;  //가로 크기
	private final int FRAME_HEIGHT = 900;  //세로 크기
	MP3Player mp3 = new MP3Player();  //노래 
	String imagePath = System.getProperty("user.dir")+"/src/images/";  //이미지 상대 경로
	String musicPath = System.getProperty("user.dir")+"/src/musics/";  //음악 상대 경로
	JPanel gamePanel = new JPanel();  //게임화면 패널
	Graphics2D gameScreen_g;  //더블 버퍼링을 위해
	
	//이펙트 바 이미지
	Image EffectBar_S;
	Image EffectBar_D;
	Image EffectBar_F;
	Image EffectBar_J;
	Image EffectBar_K; 
	Image EffectBar_L;
	Image game_Screen = new ImageIcon(imagePath+"Game_Screen.png").getImage();
	
	public Game() {
		JLabel gameScreenLabel = new JLabel(new ImageIcon(imagePath+"Game_Screen.png"));  //게임화면 스크린
		JLabel singer_title_Label;  //가수,제목 라벨
		Font font = new Font("TDTDTadakTadak",Font.PLAIN,150);  //폰트
		
		Music.music = new Music("NewJeans", "ETA"); //테스트
//		mp3.play(musicPath+Music.music.getTitle()+".mp3");  //노래 재생 시작
		
		//set
		gamePanel.setLayout(null);
		singer_title_Label = new JLabel(Music.music.getSinger() + " - " + Music.music.getTitle());  //가수,제목 라벨
		singer_title_Label.setHorizontalAlignment(JLabel.CENTER);  //중앙 정렬
		
		//Font
		singer_title_Label.setFont(font);  //가수,제목 라벨
		
		//color
		singer_title_Label.setForeground(Color.WHITE);  //가수,제목 라벨
		
		//setBounds
		gameScreenLabel.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);  //게임화면 라벨
		singer_title_Label.setBounds(0, -20, FRAME_WIDTH, 250);   //가수,제목 라벨
		
		//add
		gamePanel.add(singer_title_Label);  //가수,제목 라벨
		gamePanel.add(gameScreenLabel);  //게임화면 라벨
		addKeyListener(new NoteKeyListener());  //노트 키 리스너
		
		add(gamePanel);  //게임화면 패널
        
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
		Image gameImg = createImage(getWidth(),getHeight());
		gameScreen_g = (Graphics2D) gameImg.getGraphics();
		drawScreen(gameScreen_g);
		g.drawImage(gameImg, 0, 0, null);  //마지막에 이미지를 추가
	}
	
	//키보드 노트?
	public void drawScreen(Graphics2D g) {
		paintComponents(gameScreen_g);	
		gameScreen_g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);  //안티 앨리어싱 설정(화질 좋아지게)
		
		//노트 이미지
		Image noteS = new ImageIcon(imagePath+"Note_S.png").getImage();
		Image noteD = new ImageIcon(imagePath+"Note_D.png").getImage();
		Image noteF = new ImageIcon(imagePath+"Note_F.png").getImage();
		Image noteJ = new ImageIcon(imagePath+"Note_J.png").getImage();
		Image noteK = new ImageIcon(imagePath+"Note_K.png").getImage();
		Image noteL = new ImageIcon(imagePath+"Note_L.png").getImage();
		
		int imageY = 730;  //이미지 y위치
		Font font = new Font("TDTDTadakTadak",Font.PLAIN,90);  //폰트
		
		gameScreen_g.setFont(font);  //폰트 설정
		gameScreen_g.setColor(Color.WHITE);  //글자 색 설정
		

		//노트 이미지
		gameScreen_g.drawImage(noteS, 80, imageY, 200, 130,null);
		gameScreen_g.drawImage(noteD, 290, imageY, 200, 130, null);
		gameScreen_g.drawImage(noteF, 500, imageY, 200, 130, null);
		gameScreen_g.drawImage(noteJ, 710, imageY, 200, 130, null);
		gameScreen_g.drawImage(noteK, 920, imageY, 200, 130, null);
		gameScreen_g.drawImage(noteL, 1130, imageY, 200, 130, null);

		//노트 알파벳
		gameScreen_g.drawString("S", 160, 820);
		gameScreen_g.drawString("D", 370, 820);
		gameScreen_g.drawString("F", 580, 820);
		gameScreen_g.drawString("J", 790, 820);
		gameScreen_g.drawString("K", 1000, 820);
		gameScreen_g.drawString("L", 1210, 820);
		
		//키보드 눌렀을때 효과
		gameScreen_g.drawImage(EffectBar_S, 80, 225, 200, 700,null);
		gameScreen_g.drawImage(EffectBar_D, 290, 225, 200, 700,null);
		gameScreen_g.drawImage(EffectBar_F, 500, 225, 200, 700,null);
		gameScreen_g.drawImage(EffectBar_J, 710, 217, 200, 700,null);
		gameScreen_g.drawImage(EffectBar_K, 920, 217, 200, 700,null);
		gameScreen_g.drawImage(EffectBar_L, 1130, 225, 200, 700,null);
		
		g.dispose();
		gameScreen_g.dispose();
	}
	
	
	//Pressed
	public void pressed_S() {
		EffectBar_S = new ImageIcon(imagePath+"EffectBar_S.png").getImage();
	}
	public void pressed_D() {
		EffectBar_D = new ImageIcon(imagePath+"EffectBar_D.png").getImage();
	}
	public void pressed_F() {
		EffectBar_F = new ImageIcon(imagePath+"EffectBar_F.png").getImage();
	}
	public void pressed_J() {
		EffectBar_J = new ImageIcon(imagePath+"EffectBar_J.png").getImage();
	}
	public void pressed_K() {
		EffectBar_K = new ImageIcon(imagePath+"EffectBar_K.png").getImage();
	}
	public void pressed_L() {
		EffectBar_L = new ImageIcon(imagePath+"EffectBar_L.png").getImage();
	}
	
	//Released
	public void released_S() {
		EffectBar_S = null;
	}
	public void released_D() {
		EffectBar_D = null;
	}
	public void released_F() {
		EffectBar_F = null;
	}
	public void released_J() {
		EffectBar_J = null;
	}
	public void released_K() {
		EffectBar_K  = null;
	}
	public void released_L() {
		EffectBar_L = null;
	}
	
}
