import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
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
	
	Font font1 = new Font("TDTDTadakTadak",Font.PLAIN,150);  //노래 제목, 가수 폰트
	Font font2 = new Font("TDTDTadakTadak",Font.PLAIN,90);  //노트 폰트
	Font font3 = new Font("TDTDTadakTadak",Font.PLAIN,40);  //점수,콤보 폰트
	
	//노트 이미지
	Image noteS = new ImageIcon(imagePath+"Note_S.png").getImage();
	Image noteD = new ImageIcon(imagePath+"Note_D.png").getImage();
	Image noteF = new ImageIcon(imagePath+"Note_F.png").getImage();
	Image noteJ = new ImageIcon(imagePath+"Note_J.png").getImage();
	Image noteK = new ImageIcon(imagePath+"Note_K.png").getImage();
	Image noteL = new ImageIcon(imagePath+"Note_L.png").getImage();
	
	Timer judgmentTimer; //노트 판정 효과 타이머
	
	//판정 이미지
	Image perfect;
	Image good;
	Image bad;
	
	public Game() {
		Music.music = new Music("NewJeans","ETA");
		mp3.play(musicPath+Music.music.getTitle()+".mp3");  //노래 재생 시작
		start();  //게임 시작
		//노트 판정 효과 나오는 시간 조절
	    judgmentTimer = new Timer(70, new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            perfect = null;
	            good = null;
	            bad = null;
	        }
	    });
	    judgmentTimer.setRepeats(false); // 타이머가 한 번만 실행되도록 설정
	}
	
	//게임 화면 그리기
	public void drawScreen(Graphics2D g) {
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);  //안티 앨리어싱 설정(화질 좋아지게)
		
		g.setColor(Color.WHITE);  //글자 색 설정
		
		g.drawImage(game_Screen,0, 0, FRAME_WIDTH, FRAME_HEIGHT,null);  //게임화면 이미지
		
		g.setFont(font1);  //노래 가수, 제목 폰트 설정
		g.drawString(Music.music.getSinger() + " - " + Music.music.getTitle(), 250, 160);  //노래 가수, 제목
		
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

	    missNote(850);  //노트의 y 좌표가 820을 넘어가면 삭제되도록 함
	    
		//키보드 눌렀을때 효과
		g.drawImage(EffectBar_S, 80, 200, 200, 700,null);
		g.drawImage(EffectBar_D, 290, 195, 200, 700,null);
		g.drawImage(EffectBar_F, 500, 195, 200, 700,null);
		g.drawImage(EffectBar_J, 710, 190, 200, 710,null);
		g.drawImage(EffectBar_K, 920, 185, 200, 710,null);
		g.drawImage(EffectBar_L, 1130, 195, 200, 700,null);
		
		//판정 이미지
		g.drawImage(perfect,550,200,300,200,null);
		g.drawImage(good,550,210,300,150,null);
		g.drawImage(bad,550,190,300,180,null);
		
		g.dispose();
		
	}//drawScreen()
	
	//노트를 내려오게 하는 메서드
	public void dropNote() {
		NoteList[] notelist = {  //노트 리스트에서 노트 찍기
				new NoteList("S", 3000),new NoteList("S",3500)
				,new NoteList("S",4000),new NoteList("D",4300),new NoteList("K",4800)
				,new NoteList("F",5000),new NoteList("J",5500),new NoteList("S",6000)
				,new NoteList("L",6500),new NoteList("D",7000),new NoteList("L",7500)
				,new NoteList("D",8000),new NoteList("J",8500),new NoteList("L",9000)
				,new NoteList("S",9500),new NoteList("F",10000),new NoteList("K",10500)
		};  //test
		
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
		        if (note.getY() >=720 && note.getY() <=740 && note.getNoteType().equals(noteType)) {
		        	perfect = new ImageIcon(imagePath+"Perfect.png").getImage();
		        	score += 1000;  //1000점 증가
		        	combo++;  //콤보 수 증가
		        	countPerfect++;  //퍼펙트 수 증가
		        }//good
		        else if (note.getY() >=700 && note.getY() <=750 && note.getNoteType().equals(noteType)) {
		        	good = new ImageIcon(imagePath+"Good.png").getImage();
		        	score += 800;  //800점 증가
		        	combo++;  //콤보 수 증가
		        	countGood++;  //굿 수 증가
		        }//bad
		        else if (note.getY() < 700 || note.getY() >750 && note.getNoteType().equals(noteType)) {
		        	bad = new ImageIcon(imagePath+"Bad.png").getImage();
		        	combo = 0;  //콤보 수 초기화
		        	countBad++;  //배드 수 증가
		        }
		        noteArrayList.remove(i);  //노트 arrayList에서 삭제
		        judgmentTimer.restart();
	            break;  //노트 중복 삭제 안되도록 
			}
	    }
	}
	
	public void missNote(int missY) {  //miss 노트 객체 삭제 메서드
	    for (int i = 0; i < noteArrayList.size(); i++) {
	        Note note = noteArrayList.get(i);
	        if (note.getY() >= missY) {  //미스 라인을 지나면
	        	bad = new ImageIcon(imagePath+"Bad.png").getImage();  //배드 효과
	        	judgmentTimer.restart();
	            noteArrayList.remove(i);
	            countBad++;  //배드 수 증가
	            combo = 0; //콤보 수 초기화
	            i--; // 노트를 제거했으므로 인덱스를 하나 줄임
	        }
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
