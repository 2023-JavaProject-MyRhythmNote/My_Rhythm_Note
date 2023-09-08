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

public class Game extends Thread{
	private final int FRAME_WIDTH = 1400;  //가로 크기
	private final int FRAME_HEIGHT = 900;  //세로 크기
	MP3Player mp3 = new MP3Player();  //노래 
	String imagePath = System.getProperty("user.dir")+"/src/images/";  //이미지 상대 경로
	String musicPath = System.getProperty("user.dir")+"/src/musics/";  //음악 상대 경로
	JPanel gamePanel = new JPanel();  //게임화면 패널
	Image game_Screen = new ImageIcon(imagePath+"Game_Screen.png").getImage();  //게임화면 이미지

	//이펙트 바 이미지
	Image EffectBar_S;
	Image EffectBar_D;
	Image EffectBar_F;
	Image EffectBar_J;
	Image EffectBar_K; 
	Image EffectBar_L;
	
	public ArrayList<Note> noteArrayList = new ArrayList<>();  //노트를 담는 arraylist
	
	Font font1 = new Font("TDTDTadakTadak",Font.PLAIN,150);  //노래 제목, 가수 폰트
	Font font2 = new Font("TDTDTadakTadak",Font.PLAIN,90);  //노트 폰트

	//노트 이미지
	Image noteS = new ImageIcon(imagePath+"Note_S.png").getImage();
	Image noteD = new ImageIcon(imagePath+"Note_D.png").getImage();
	Image noteF = new ImageIcon(imagePath+"Note_F.png").getImage();
	Image noteJ = new ImageIcon(imagePath+"Note_J.png").getImage();
	Image noteK = new ImageIcon(imagePath+"Note_K.png").getImage();
	Image noteL = new ImageIcon(imagePath+"Note_L.png").getImage();
	
	public Game() {
		Music.music = new Music("NewJeans", "ETA"); //테스트
		start();  //노트 내려오기 시작
//		mp3.play(musicPath+Music.music.getTitle()+".mp3");  //노래 재생 시작
	}
	
	
	//키보드 노트?
	public void drawScreen(Graphics2D g) {
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);  //안티 앨리어싱 설정(화질 좋아지게)
		
		g.setColor(Color.WHITE);  //글자 색 설정
		
		g.drawImage(game_Screen,0, 0, FRAME_WIDTH, FRAME_HEIGHT,null);  //게임화면 이미지
		
		g.setFont(font1);  //노래 가수, 제목 폰트 설정
		g.drawString(Music.music.getSinger() + " - " + Music.music.getTitle(), 250, 160);  //노래 가수, 제목
		g.setFont(font2);  //노트 폰트 설정
		
		//노트 이미지
		g.drawImage(noteS, 80, 730, 200, 130,null);
		g.drawImage(noteD, 290, 730, 200, 130, null);
		g.drawImage(noteF, 500, 730, 200, 130, null);
		g.drawImage(noteJ, 710, 730, 200, 130, null);
		g.drawImage(noteK, 920, 730, 200, 130, null);
		g.drawImage(noteL, 1130, 730, 200, 130, null);
		
		//노트 알파벳
		g.drawString("S", 160, 820);
		g.drawString("D", 370, 820);
		g.drawString("F", 580, 820);
		g.drawString("J", 790, 820);
		g.drawString("K", 1000, 820);
		g.drawString("L", 1210, 820);
		
		//흰색 노트
		for(int i = 0; i<noteArrayList.size(); i++) {
			Note note = noteArrayList.get(i);
			note.drawNote(g);
		}
		
		//키보드 눌렀을때 효과
		g.drawImage(EffectBar_S, 80, 200, 200, 700,null);
		g.drawImage(EffectBar_D, 290, 195, 200, 700,null);
		g.drawImage(EffectBar_F, 500, 195, 200, 700,null);
		g.drawImage(EffectBar_J, 710, 190, 200, 710,null);
		g.drawImage(EffectBar_K, 920, 185, 200, 710,null);
		g.drawImage(EffectBar_L, 1130, 195, 200, 700,null);
		
		g.dispose();
		
	}//drawScreen()
	
	//노트를 내려오게 하는 메서드
	public void dropNote() {
		NoteList[] notelist = {new NoteList("S", 2000), new NoteList("F", 2500),new NoteList("D",3000),new NoteList("K",3000)};  //test
		for (NoteList item : notelist) {
		    Note note = new Note(item);
		    note.start();
		    noteArrayList.add(note);
		}
	}
	
	@Override
	public void run() {
		dropNote();
	}
	
	public void removeNote(String noteType) {  //노트 삭제 메서드
	    for (int i = 0; i < noteArrayList.size(); i++) {
	        Note note = noteArrayList.get(i);
	        if (note.getNoteType().equals(noteType)) {
	            noteArrayList.remove(i);
	            i--; // 노트를 제거했으니 인덱스를 하나 줄임
	        }
	    }
	}
	//Pressed
	public void pressed_S() {
		EffectBar_S = new ImageIcon(imagePath+"EffectBar_S.png").getImage();
		removeNote("S");
	}
	public void pressed_D() {
		EffectBar_D = new ImageIcon(imagePath+"EffectBar_D.png").getImage();
		removeNote("D");
	}
	public void pressed_F() {
		EffectBar_F = new ImageIcon(imagePath+"EffectBar_F.png").getImage();
		removeNote("F");
	}
	public void pressed_J() {
		EffectBar_J = new ImageIcon(imagePath+"EffectBar_J.png").getImage();
		removeNote("J");
	}
	public void pressed_K() {
		EffectBar_K = new ImageIcon(imagePath+"EffectBar_K.png").getImage();
		removeNote("K");
	}
	public void pressed_L() {
		EffectBar_L = new ImageIcon(imagePath+"EffectBar_L.png").getImage();
		removeNote("L");
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
