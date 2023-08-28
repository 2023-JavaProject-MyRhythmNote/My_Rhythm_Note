import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javazoom.jl.player.MP3Player;

public class Game extends JFrame{
	private final int FRAME_WIDTH = 1400;  //가로 크기
	private final int FRAME_HEIGHT = 900;  //세로 크기
	MP3Player mp3 = new MP3Player();  //노래 
	String imagePath = System.getProperty("user.dir")+"/src/images/";  //이미지 상대 경로
	String musicPath = System.getProperty("user.dir")+"/src/musics/";  //음악 상대 경로
	JPanel gamePanel = new JPanel();  //게임화면 패널
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
		
		add(gamePanel);  //게임화면 패널
		
		//기본 설정
		setTitle("게임 화면");
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setLocationRelativeTo(null);  //윈도우 창 정중앙에
		setResizable(true);  //화면 크기 조정 불가
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	//키보드 노트?
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);  //안티 앨리어싱 설정
		Image noteS = new ImageIcon(imagePath+"Note_S.png").getImage();
		Image noteD = new ImageIcon(imagePath+"Note_D.png").getImage();
		Image noteF = new ImageIcon(imagePath+"Note_F.png").getImage();
		Image noteJ = new ImageIcon(imagePath+"Note_J.png").getImage();
		Image noteK = new ImageIcon(imagePath+"Note_K.png").getImage();
		Image noteL = new ImageIcon(imagePath+"Note_L.png").getImage();
		int imageX = 730;  //이미지 x위치
		Font font = new Font("TDTDTadakTadak",Font.PLAIN,90);  //폰트
		g2.setFont(font);
		g2.setColor(Color.WHITE);
		
		//노트 이미지
		g2.drawImage(noteS, 70, imageX, 200, 130,null);
		g2.drawImage(noteD, 290, imageX, 200, 130, null);
		g2.drawImage(noteF, 510, imageX, 200, 130, null);
		g2.drawImage(noteJ, 730, imageX, 200, 130, null);
		g2.drawImage(noteK, 950, imageX, 200, 130, null);
		g2.drawImage(noteL, 1170, imageX, 200, 130, null);
		//노트 알파벳
		g2.drawString("S", 150, 820);
		g2.drawString("D", 370, 820);
		g2.drawString("F", 590, 820);
		g2.drawString("J", 810, 820);
		g2.drawString("K", 1030, 820);
		g2.drawString("L", 1250, 820);
		
		g.dispose();
		g2.dispose();
	}
}
