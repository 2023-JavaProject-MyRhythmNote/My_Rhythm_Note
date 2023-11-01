import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import javazoom.jl.player.MP3Player;

public class Game extends Thread{
	private final int FRAME_WIDTH = 1400;  //가로 크기
	private final int FRAME_HEIGHT = 900;  //세로 크기
	MP3Player mp3 = new MP3Player();  //노래 재생
	String imagePath = System.getProperty("user.dir")+"/src/images/";  //이미지 상대 경로
	String musicPath = System.getProperty("user.dir")+"/src/musics/";  //음악 상대 경로
	JPanel gamePanel = new JPanel();  //게임화면 패널
	Image game_Screen = new ImageIcon(imagePath+"Game_Screen.png").getImage();  //게임화면 이미지
	int combo;  //콤보
	int bestCombo;  //최고 콤보
	int score;  //점수
	int countGood;  //굿 카운트
	int countPerfect;  //퍼펙트 카운트
	int countBad;  //배드 카운트
	
	//이펙트 바 이미지
	Image EffectBar_S;
	Image EffectBar_D;
	Image EffectBar_F;
	Image EffectBar_J;
	Image EffectBar_K; 
	Image EffectBar_L;
	
	public ArrayList<Note> noteArrayList = new ArrayList<>();  //노트를 담는 arrayList
	
	Font font1 = new Font("TDTDTadakTadak",Font.PLAIN,110);  //노래 제목, 가수 폰트
	Font font2 = new Font("TDTDTadakTadak",Font.PLAIN,90);  //노트 폰트
	Font font3 = new Font("TDTDTadakTadak",Font.PLAIN,40);  //점수,콤보 폰트
	
	//노트 이미지
	Image noteS = new ImageIcon(imagePath+"Note_S.png").getImage();
	Image noteD = new ImageIcon(imagePath+"Note_D.png").getImage();
	Image noteF = new ImageIcon(imagePath+"Note_F.png").getImage();
	Image noteJ = new ImageIcon(imagePath+"Note_J.png").getImage();
	Image noteK = new ImageIcon(imagePath+"Note_K.png").getImage();
	Image noteL = new ImageIcon(imagePath+"Note_L.png").getImage();
	
	Timer judgementTimer; //노트 판정 효과 타이머
	
	//판정 이미지
	Image perfect;
	Image good;
	Image bad;
	
	public Game() {
	    //3초 대기
	    Timer startGameTimer = new Timer(1100, new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            mp3.play(musicPath + Music.music.getTitle() + ".mp3");  //노래 시작
	            start();  //시작
	        }
	    });
	    startGameTimer.setRepeats(false); // 타이머가 한 번만 실행되도록 설정
	    startGameTimer.restart(); // 타이머 시작
		//노트 판정 효과 나오는 시간 조절
	    judgementTimer = new Timer(70, new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            perfect = null;
	            good = null;
	            bad = null;
	        }
	    });
	    judgementTimer.setRepeats(false); // 타이머가 한 번만 실행되도록 설정
	}
	
	//게임 화면 그리기
	public void drawScreen(Graphics g) {
		g.setColor(Color.WHITE);  //글자 색 설정
		
		g.drawImage(game_Screen,0, 0, FRAME_WIDTH, FRAME_HEIGHT,null);  //게임화면 이미지
		
		g.setFont(font1);  //노래 가수, 제목 폰트 설정
		FontMetrics titleFontMetrics = g.getFontMetrics(g.getFont());
		int stringWidth = titleFontMetrics.stringWidth(Music.music.getSinger() + " - " + Music.music.getTitle());  //길이 구함
		g.drawString(Music.music.getSinger() + " - " + Music.music.getTitle(), (FRAME_WIDTH- stringWidth)/2, 160);  //노래 가수, 제목
		
		//노트 이미지
		g.drawImage(noteS, 80, 730, 200, 130,null);
		g.drawImage(noteD, 290, 730, 200, 130, null);
		g.drawImage(noteF, 500, 730, 200, 130, null);
		g.drawImage(noteJ, 710, 730, 200, 130, null);
		g.drawImage(noteK, 920, 730, 200, 130, null);
		g.drawImage(noteL, 1130, 730, 200, 130, null);
		
		g.setFont(font2);  //노트 폰트 설정
		
		//노트 알파벳
		g.drawString("S", 160, 820);
		g.drawString("D", 370, 820);
		g.drawString("F", 580, 820);
		g.drawString("J", 790, 820);
		g.drawString("K", 1000, 820);
		g.drawString("L", 1210, 820);
		
		g.setFont(font3);  //크기 좀 작게 변경
		g.setColor(Color.YELLOW);  //노란색으로 변경
		
		g.drawString("SCORE : "+score, 1100, 250);  //스코어
		g.drawString("COMBO : " + combo,1100, 300);  //콤보
		if(combo>bestCombo) bestCombo = combo;  //최고 콤보 수 
		
		//흰색 노트
		for(int i = 0; i<noteArrayList.size(); i++) {
			Note note = noteArrayList.get(i);
			note.drawNote(g);
		}

	    missNote(830);  //노트의 y 좌표가 830을 넘어가면 삭제되도록 함
		
		//키보드 눌렀을때 효과
		g.drawImage(EffectBar_S, 80, 200, 200, 700,null);
		g.drawImage(EffectBar_D, 290, 195, 200, 700,null);
		g.drawImage(EffectBar_F, 500, 195, 200, 700,null);
		g.drawImage(EffectBar_J, 710, 190, 200, 710,null);
		g.drawImage(EffectBar_K, 920, 185, 200, 710,null);
		g.drawImage(EffectBar_L, 1130, 195, 200, 700,null);
		
		//판정 이미지
		g.drawImage(perfect,550,200,300,200,null);
		g.drawImage(good,550,210,300,180,null);
		g.drawImage(bad,550,190,300,180,null);
		
		g.dispose();
		
	}//drawScreen()
	
	//노트를 내려오게 하는 메서드
	public void dropNote() {
		NoteList[] notelist = null;  //노트 리스트
		
		if(Music.music.getTitle().equals("NIGHT DANCER")) {
			Main.NOTE_SPEED = 7;  //노트 스피드 설정
			int bpm = 117; // 노래의 BPM
			double beatDuration = 60.0 / bpm; // 1 비트당 시간 (초 단위)
			int noteDuration = (int) (beatDuration * 1000); // 1 비트당 시간 (밀리초 단위)
			notelist = new NoteList[]{
				    new NoteList("F", noteDuration * 2), new NoteList("K", noteDuration * 5),
				    new NoteList("D", noteDuration * 6), new NoteList("S", noteDuration * 8),
				    new NoteList("L", noteDuration * 9), new NoteList("S", noteDuration * 10),
				    new NoteList("K", noteDuration * 12), new NoteList("F", noteDuration * 13),
				    new NoteList("J", noteDuration * 14), new NoteList("L", noteDuration * 15),
				    new NoteList("S", noteDuration * 18), new NoteList("K", noteDuration * 20),
				    new NoteList("L", noteDuration * 22), new NoteList("S", noteDuration * 24),
				    new NoteList("J", noteDuration * 27), new NoteList("D", noteDuration * 30),
				    new NoteList("J", noteDuration * 32), new NoteList("F", noteDuration * 34),
				    new NoteList("S", noteDuration * 38), new NoteList("K", noteDuration * 40),
				    new NoteList("L", noteDuration * 42), new NoteList("K", noteDuration * 45),
				    new NoteList("J", noteDuration * 46), new NoteList("K", noteDuration * 47),
				    new NoteList("J", noteDuration * 50), new NoteList("L", noteDuration * 51),
				    new NoteList("S", noteDuration * 54), new NoteList("F", noteDuration * 56),
				    new NoteList("D", noteDuration * 58), new NoteList("S", noteDuration * 60),
				    new NoteList("F", noteDuration * 62), new NoteList("F", noteDuration * 65),
				    new NoteList("K", noteDuration * 66), new NoteList("L", noteDuration * 68),
				    new NoteList("F", noteDuration * 70), new NoteList("D", noteDuration * 72),
				    new NoteList("S", noteDuration * 74), new NoteList("L", noteDuration * 77),
				    new NoteList("K", noteDuration * 78), new NoteList("J", noteDuration * 79),
				    new NoteList("K", noteDuration * 82), new NoteList("L", noteDuration * 86),
				    new NoteList("K", noteDuration * 90), new NoteList("S", noteDuration * 92)
				};
		}else if(Music.music.getTitle().equals("3D")) {
			Main.NOTE_SPEED = 8;  //노트 스피드 설정
			int bpm = 107; // 노래의 BPM
			double beatDuration = 60.0 / bpm; // 1 비트당 시간 (초 단위)
			int noteDuration = (int) (beatDuration * 1000); // 1 비트당 시간 (밀리초 단위)
			notelist = new NoteList[]{
				    new NoteList("S", noteDuration * 2),
				    new NoteList("D", noteDuration * 4),
				    new NoteList("D", noteDuration * 5),
				    new NoteList("J", noteDuration * 6),
				    new NoteList("J", noteDuration * 7),
				    new NoteList("L", noteDuration * 8),
				    new NoteList("L", noteDuration * 9),
				    new NoteList("K", noteDuration * 9 + noteDuration/2),
				    new NoteList("D", noteDuration * 12),
				    new NoteList("S", noteDuration * 14),
				    new NoteList("F", noteDuration * 16),
				    new NoteList("L", noteDuration * 18),
				    new NoteList("J", noteDuration * 20),
				    new NoteList("F", noteDuration * 21),
				    new NoteList("K", noteDuration * 22),
				    new NoteList("S", noteDuration * 23),
				    new NoteList("F", noteDuration * 25),
				    new NoteList("D", noteDuration * 27),
				    new NoteList("F", noteDuration * 29),
				    new NoteList("J", noteDuration * 31),
				    new NoteList("D", noteDuration * 31 + noteDuration/2),
				    new NoteList("K", noteDuration * 33),
				    new NoteList("L", noteDuration * 34),
				    new NoteList("S", noteDuration * 35),
				    new NoteList("S", noteDuration * 37),
				    new NoteList("L", noteDuration * 38 + noteDuration/2),
				    new NoteList("F", noteDuration * 41),
				    new NoteList("J", noteDuration * 42),
				    new NoteList("L", noteDuration * 42 + noteDuration/2),
				    new NoteList("K", noteDuration * 43),
				    new NoteList("L", noteDuration * 43 + noteDuration/2),
				    new NoteList("D", noteDuration * 44),
				    new NoteList("L", noteDuration * 45),
				    new NoteList("S", noteDuration * 47),
				    new NoteList("L", noteDuration * 48),
				    new NoteList("F", noteDuration * 49),
				    new NoteList("D", noteDuration * 51),
				    new NoteList("K", noteDuration * 53),
				    new NoteList("F", noteDuration * 55),
				    new NoteList("J", noteDuration * 57),
				    new NoteList("L", noteDuration * 59),
				    new NoteList("F", noteDuration * 61),
				    new NoteList("D", noteDuration * 63),
				    new NoteList("J", noteDuration * 65),
				    new NoteList("K", noteDuration * 67),
				    new NoteList("S", noteDuration * 69),
				    new NoteList("L", noteDuration * 71),
				    new NoteList("K", noteDuration * 73),
				    new NoteList("S", noteDuration * 75),
				    new NoteList("F", noteDuration * 77),
				    new NoteList("D", noteDuration * 79),
				    new NoteList("S", noteDuration * 81),
				    new NoteList("J", noteDuration * 83),
				    new NoteList("D", noteDuration * 85),
				    new NoteList("F", noteDuration * 87),
				    new NoteList("J", noteDuration * 89),
				    new NoteList("L", noteDuration * 91)
				};
		}else if(Music.music.getTitle().equals("ETA")) {
			Main.NOTE_SPEED = 8;  //노트 스피드 설정
			int bpm = 135; // 노래의 BPM
			double beatDuration = 60.0 / bpm; // 1 비트당 시간 (초 단위)
			int noteDuration = (int) (beatDuration * 1000); // 1 비트당 시간 (밀리초 단위)
			notelist = new NoteList[]{  //노트 리스트에서 노트 찍기
					new NoteList("F", 0),
					new NoteList("S", noteDuration),
				    new NoteList("S", noteDuration * 2),
				    new NoteList("S", noteDuration * 2 + noteDuration/2),
				    new NoteList("D", noteDuration * 3),
				    new NoteList("J", noteDuration * 3 + noteDuration/2),
				    new NoteList("K", noteDuration * 4),
				    new NoteList("L", noteDuration * 4 + noteDuration/2),
				    new NoteList("K", noteDuration * 5),
				    new NoteList("D", noteDuration * 6),
				    new NoteList("S", noteDuration * 6 + noteDuration/2),
				    new NoteList("F", noteDuration * 7),
				    new NoteList("K", noteDuration * 8),
				    new NoteList("L", noteDuration * 8 + noteDuration/2),
				    new NoteList("K", noteDuration * 9),
				    new NoteList("S", noteDuration * 10),
				    new NoteList("D", noteDuration * 10 + noteDuration/2),
				    new NoteList("F", noteDuration * 11),
				    new NoteList("J", noteDuration * 12),
				    new NoteList("J", noteDuration * 13),
				    new NoteList("L", noteDuration * 13 + noteDuration/2),
				    new NoteList("S", noteDuration * 14),
				    new NoteList("F", noteDuration * 15),
				    new NoteList("D", noteDuration * 16),
				    new NoteList("K", noteDuration * 17),
				    new NoteList("L", noteDuration * 17 + noteDuration/2),
				    new NoteList("K", noteDuration * 18),
				    new NoteList("K", noteDuration * 19),
				    new NoteList("F", noteDuration * 19 + noteDuration/2),
				    new NoteList("S", noteDuration * 20),
				    new NoteList("D", noteDuration * 21),
				    new NoteList("J", noteDuration * 22),
				    new NoteList("L", noteDuration * 22 + noteDuration/2),
				    new NoteList("K", noteDuration * 23),
				    new NoteList("L", noteDuration * 24),
				    new NoteList("S", noteDuration * 25 + noteDuration/2),
				    new NoteList("L", noteDuration * 26),
				    new NoteList("F", noteDuration * 27),
				    new NoteList("J", noteDuration * 28),
				    new NoteList("S", noteDuration * 29),
				    new NoteList("K", noteDuration * 30),
				    new NoteList("J", noteDuration * 31),
				    new NoteList("F", noteDuration * 32),
				    new NoteList("J", noteDuration * 32 + noteDuration/2),
				    new NoteList("L", noteDuration * 33),
				    new NoteList("D", noteDuration * 34),
				    new NoteList("F", noteDuration * 35),
				    new NoteList("D", noteDuration * 36),
				    new NoteList("S", noteDuration * 37),
				    new NoteList("L", noteDuration * 37 + noteDuration/2),
				    new NoteList("K", noteDuration * 38),
				    new NoteList("S", noteDuration * 39),
				    new NoteList("F", noteDuration * 40),
				    new NoteList("D", noteDuration * 41),
				    new NoteList("J", noteDuration * 42),
				    new NoteList("K", noteDuration * 43),
				    new NoteList("L", noteDuration * 43 + noteDuration/2),
				    new NoteList("L", noteDuration * 44),
				    new NoteList("K", noteDuration * 45),
				    new NoteList("J", noteDuration * 46),
				    new NoteList("D", noteDuration * 47),
				    new NoteList("F", noteDuration * 47 + noteDuration/2),
				    new NoteList("J", noteDuration * 48),
				    new NoteList("L", noteDuration * 49),
				    new NoteList("K", noteDuration * 49 + noteDuration/2),
				    new NoteList("S", noteDuration * 50),
				    new NoteList("D", noteDuration * 51),
				    new NoteList("F", noteDuration * 52),
				    new NoteList("F", noteDuration * 52 + noteDuration/2),
				    new NoteList("S", noteDuration * 53),
				    new NoteList("D", noteDuration * 54),
				    new NoteList("S", noteDuration * 55 + noteDuration/2),
				    new NoteList("L", noteDuration * 56),
				    new NoteList("J", noteDuration * 57),
				    new NoteList("K", noteDuration * 58),
				    new NoteList("L", noteDuration * 59),
				    new NoteList("S", noteDuration * 60),
				    new NoteList("J", noteDuration * 61),
				    new NoteList("K", noteDuration * 62),
				    new NoteList("J", noteDuration * 62 + noteDuration/2),
				    new NoteList("L", noteDuration * 63),
				    new NoteList("F", noteDuration * 64),
				    new NoteList("S", noteDuration * 65),
				    new NoteList("F", noteDuration * 66),
				    new NoteList("D", noteDuration * 67),
				    new NoteList("L", noteDuration * 67 + noteDuration/2),
				    new NoteList("K", noteDuration * 68),
				    new NoteList("J", noteDuration * 69),
				    new NoteList("D", noteDuration * 70),
				    new NoteList("S", noteDuration * 71),
				    new NoteList("K", noteDuration * 72),
				    new NoteList("K", noteDuration * 73),
				    new NoteList("J", noteDuration * 73 + noteDuration/2),
				    new NoteList("S", noteDuration * 74),
				    new NoteList("F", noteDuration * 75),
				    new NoteList("D", noteDuration * 76),
				    new NoteList("K", noteDuration * 77),
				    new NoteList("L", noteDuration * 77 + noteDuration/2),
				    new NoteList("J", noteDuration * 78),
				    new NoteList("S", noteDuration * 79),
				    new NoteList("F", noteDuration * 79 + noteDuration/2),
				    new NoteList("D", noteDuration * 80),
				    new NoteList("S", noteDuration * 81),
				    new NoteList("D", noteDuration * 82),
				    new NoteList("F", noteDuration * 82 + noteDuration/2),
				    new NoteList("J", noteDuration * 83),
				    new NoteList("K", noteDuration * 84),
				    new NoteList("D", noteDuration * 85 + noteDuration/2),
				    new NoteList("L", noteDuration * 86),
				    new NoteList("J", noteDuration * 87),
				    new NoteList("K", noteDuration * 88),
				    new NoteList("D", noteDuration * 89),
				    new NoteList("J", noteDuration * 90),
				    new NoteList("L", noteDuration * 91),
				    new NoteList("S", noteDuration * 92),
				    new NoteList("F", noteDuration * 92 + noteDuration/2),
				    new NoteList("K", noteDuration * 93),
				    new NoteList("D", noteDuration * 94),
				    new NoteList("F", noteDuration * 95),
				    new NoteList("J", noteDuration * 96),
				    new NoteList("L", noteDuration * 97),
				    new NoteList("J", noteDuration * 97 + noteDuration/2),
				    new NoteList("K", noteDuration * 98),
				    new NoteList("L", noteDuration * 99),
				    new NoteList("D", noteDuration * 100),
				    new NoteList("D", noteDuration * 101),
				    new NoteList("L", noteDuration * 102),
				    new NoteList("J", noteDuration * 103),
				    new NoteList("L", noteDuration * 103 + noteDuration/2),
				    new NoteList("S", noteDuration * 104),
				    new NoteList("F", noteDuration * 105),
				    new NoteList("D", noteDuration * 106),
				    new NoteList("K", noteDuration * 107),
				    new NoteList("L", noteDuration * 107 + noteDuration/2),
				    new NoteList("K", noteDuration * 108),
				    new NoteList("L", noteDuration * 109),
				    new NoteList("S", noteDuration * 109 + noteDuration/2),
				    new NoteList("F", noteDuration * 110),
				    new NoteList("D", noteDuration * 111),
				    new NoteList("J", noteDuration * 112),
				    new NoteList("S", noteDuration * 112 + noteDuration/2),
				    new NoteList("K", noteDuration * 113),
				    new NoteList("L", noteDuration * 114),
				    new NoteList("S", noteDuration * 115 + noteDuration/2),
				    new NoteList("S", noteDuration * 116),
				    new NoteList("D", noteDuration * 117),
				    new NoteList("L", noteDuration * 118),
				    new NoteList("K", noteDuration * 119),
				    new NoteList("J", noteDuration * 120),
				    new NoteList("J", noteDuration * 121),
				    new NoteList("K", noteDuration * 122),
				    new NoteList("S", noteDuration * 122 + noteDuration/2),
				    new NoteList("D", noteDuration * 123),
				    new NoteList("F", noteDuration * 124),
				    new NoteList("F", noteDuration * 125),
				    new NoteList("D", noteDuration * 126),
				    new NoteList("S", noteDuration * 127),
				    new NoteList("S", noteDuration * 127 + noteDuration/2),
				    new NoteList("L", noteDuration * 128),
				    new NoteList("D", noteDuration * 129),
				    new NoteList("K", noteDuration * 130),
				    new NoteList("F", noteDuration * 131),
				    new NoteList("J", noteDuration * 132),
				    new NoteList("D", noteDuration * 133),
				    new NoteList("J", noteDuration * 133 + noteDuration/2),
				    new NoteList("S", noteDuration * 134),
				    new NoteList("K", noteDuration * 135),
				    new NoteList("D", noteDuration * 136),
				    new NoteList("F", noteDuration * 137),
				    new NoteList("L", noteDuration * 137 + noteDuration/2),
				    new NoteList("K", noteDuration * 138),
				    new NoteList("S", noteDuration * 138 + noteDuration/2),
				    new NoteList("F", noteDuration * 139),
				    new NoteList("D", noteDuration * 140),
				    new NoteList("J", noteDuration * 141),
				    new NoteList("K", noteDuration * 142),
				    new NoteList("L", noteDuration * 143),
				    new NoteList("S", noteDuration * 144),
				    new NoteList("F", noteDuration * 144 + noteDuration/2),
				    new NoteList("D", noteDuration * 145),
				    new NoteList("J", noteDuration * 146),
				    new NoteList("L", noteDuration * 147),
				    new NoteList("D", noteDuration * 148),
				    new NoteList("J", noteDuration * 149),
				    new NoteList("L", noteDuration * 149 + noteDuration/2),
				    new NoteList("D", noteDuration * 150),
				    new NoteList("F", noteDuration * 151),
				    new NoteList("K", noteDuration * 151 + noteDuration/2),
				    new NoteList("L", noteDuration * 152),
				    new NoteList("J", noteDuration * 153),
				    new NoteList("S", noteDuration * 154),
				    new NoteList("J", noteDuration * 155),
				    new NoteList("L", noteDuration * 155 + noteDuration/2),
				    new NoteList("S", noteDuration * 156),
				    new NoteList("D", noteDuration * 157),
				    new NoteList("F", noteDuration * 158),
				    new NoteList("K", noteDuration * 159),
				    new NoteList("L", noteDuration * 160),
				    new NoteList("S", noteDuration * 161),
				    new NoteList("F", noteDuration * 161 + noteDuration/2),
				    new NoteList("D", noteDuration * 162),
				    new NoteList("J", noteDuration * 163),
				    new NoteList("L", noteDuration * 163 + noteDuration/2),
				    new NoteList("D", noteDuration * 164),
				    new NoteList("K", noteDuration * 165),
				    new NoteList("J", noteDuration * 166),
				    new NoteList("K", noteDuration * 166 + noteDuration/2),
				    new NoteList("J", noteDuration * 167),
				    new NoteList("S", noteDuration * 168),
				    new NoteList("F", noteDuration * 169),
				    new NoteList("S", noteDuration * 170),
				    new NoteList("D", noteDuration * 170 + noteDuration/2),
				    new NoteList("L", noteDuration * 171),
				    new NoteList("S", noteDuration * 172),
				    new NoteList("F", noteDuration * 174),
				    new NoteList("K", noteDuration * 175 + noteDuration/2),
				    new NoteList("S", noteDuration * 178),
				    new NoteList("D", noteDuration * 180),
				    new NoteList("F", noteDuration * 183),
				    new NoteList("J", noteDuration * 185),
				    new NoteList("K", noteDuration * 187),
				    new NoteList("L", noteDuration * 189),
				    new NoteList("S", noteDuration * 192),
				    new NoteList("F", noteDuration * 195),
				    new NoteList("K", noteDuration * 197),
				    new NoteList("D", noteDuration * 199),
				    new NoteList("F", noteDuration * 200),
				    new NoteList("L", noteDuration * 200 + noteDuration/2)
			};  //test
		}
		for (NoteList item : notelist) {
		    Note note = new Note(item);  //노트리스트 배열에서 하나씩 가지고 와서 넣음
		    note.start();  //Note 클래스 스레드 시작
		    noteArrayList.add(note);  //노트 arrayList에 추가
		}
	}
	
	@Override
	public void run() {
		dropNote();  //노트 내려오게 함
	}
	
	//노트 제거, 판정 메서드
	public void Judge(String noteType) {
		for (int i = 0; i < noteArrayList.size(); i++) {
			Note note = noteArrayList.get(i);
			//노트 제거
			if (note.getY() >=120 && note.getNoteType().equals(noteType)) {
				//노트 판정
		        //perfect
		        if (note.getY() >=720 && note.getY() <=750 && note.getNoteType().equals(noteType)) {
		        	perfect = new ImageIcon(imagePath+"Perfect.png").getImage();
		        	score += 1000;  //1000점 증가
		        	combo++;  //콤보 수 증가
		        	countPerfect++;  //퍼펙트 수 증가
		        }//good
		        else if (note.getY() >=700 && note.getY() <=760 && note.getNoteType().equals(noteType)) {
		        	good = new ImageIcon(imagePath+"Good.png").getImage();
		        	score += 800;  //800점 증가
		        	combo++;  //콤보 수 증가
		        	countGood++;  //굿 수 증가
		        }//bad
		        else if (note.getY() < 700 || note.getY() >760 && note.getNoteType().equals(noteType)) {
		        	bad = new ImageIcon(imagePath+"Bad.png").getImage();
		        	combo = 0;  //콤보 수 초기화
		        	countBad++;  //배드 수 증가
		        }
		        
		        noteArrayList.remove(i);  //노트 arrayList에서 삭제
		        judgementTimer.restart();
		        
		        if(combo>bestCombo) bestCombo = combo;  //최고 콤보 수 업데이트
		        
		        goScoreScreen();
		        
	            break;  //노트 중복 삭제 안되도록 
			}
	    }
	}
	
	public void missNote(int missY) {  //miss 노트 객체 삭제 메서드
	    for (int i = 0; i < noteArrayList.size(); i++) {
	        Note note = noteArrayList.get(i);
	        if (note.getY() >= missY) {  //미스 라인을 지나면
	        	noteArrayList.remove(i);
	        	bad = new ImageIcon(imagePath+"Bad.png").getImage();  //배드 효과
	        	judgementTimer.restart();
	            countBad++;  //배드 수 증가
	            combo = 0; //콤보 수 초기화
	            i--; // 노트를 제거했으므로 인덱스를 하나 줄임
	        }
	        goScoreScreen();
	    }
	}
	
	//점수 화면으로 이동하는 메서드
	public void goScoreScreen() {
		if(noteArrayList.isEmpty()) {  //게임이 끝나면 점수 화면으로 이동
			Timer timer = new Timer(4000, new ActionListener() {
		        @Override
		        public void actionPerformed(ActionEvent e) {
		        	Main.screen.isGame = false;  //이제 게임 아님
					mp3.stop();  //test
					Main.screen.generateScore(countPerfect,countGood,countBad,bestCombo,score);  //점수 화면 생성
					Main.screen.scorePanel.setVisible(true);  //점수 화면 패널 보이게
		        }
			});
			timer.restart();
			timer.setRepeats(false);
		}
	}
	//Pressed
	public void pressed_S() {
		EffectBar_S = new ImageIcon(imagePath+"EffectBar_S.png").getImage();
		Judge("S");
	}
	public void pressed_D() {
		EffectBar_D = new ImageIcon(imagePath+"EffectBar_D.png").getImage();
		Judge("D");
	}
	public void pressed_F() {
		EffectBar_F = new ImageIcon(imagePath+"EffectBar_F.png").getImage();
		Judge("F");
	}
	public void pressed_J() {
		EffectBar_J = new ImageIcon(imagePath+"EffectBar_J.png").getImage();
		Judge("J");
	}
	public void pressed_K() {
		EffectBar_K = new ImageIcon(imagePath+"EffectBar_K.png").getImage();
		Judge("K");
	}
	public void pressed_L() {
		EffectBar_L = new ImageIcon(imagePath+"EffectBar_L.png").getImage();
		Judge("L");
	}
	public void pressed_ESC() {
		mp3.stop();  //노래 정지
		try {
			Thread.sleep(700);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        Main.screen.isGame = false;  //이제 게임 아님
        Main.screen.generateSelectSong();  //노래 고르는 화면 생성
        Main.screen.selectSongPanel.setVisible(true);  //노래 고르는 화면 보이게
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
