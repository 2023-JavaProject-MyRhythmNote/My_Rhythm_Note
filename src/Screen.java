import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import javazoom.jl.player.MP3Player;

public class Screen extends JFrame{
	public static Game game;
	Image screenImg;  //더블 버퍼링용 이미지
	Graphics screenGraphics;  //더블 버퍼링용 그래픽
	boolean isGame = false;  //게임 스크린이냐?
	
	private final int FRAME_WIDTH = 1400;  //가로 크기
	private final int FRAME_HEIGHT = 900;  //세로 크기
	int musicIndex = 0;  //노래 인덱스
	
	String imagePath = System.getProperty("user.dir")+"/src/images/";  //이미지 상대 경로
	ImageIcon startImg = new ImageIcon(imagePath + "Start_Screen.png"); //시작화면 이미지
	ImageIcon clickButtonImg = new ImageIcon(imagePath + "Click_Button.png");  //버튼 클릭 이미지
	
	JButton signInButton = new JButton("로그인",new ImageIcon(imagePath+"Button.png"));  //로그인 버튼
	JButton gameRuleButton = new JButton(new ImageIcon(imagePath+"GameRule_Button.png"));  //게임방법 버튼
	JButton signUpButton = new JButton("회원가입",new ImageIcon(imagePath+"Button.png"));  //회원가입 버튼
	
	JLabel startScreenLabel = new JLabel(startImg);  //시작화면 라벨
	JLabel logoTextLabel1 = new JLabel("MY", JLabel.CENTER);  //로고 라벨1  "MY"
	JLabel logoTextLabel2 = new JLabel("RHYTHM", JLabel.CENTER);  //로고 라벨2  "RHYTHM"
	JLabel logoTextLabel3 = new JLabel("NOTE", JLabel.CENTER);  //로고 라벨3  "NOTE"
	JLabel noPasswordLabel = new JLabel("<html><font color='red'>닉네임이나 비밀번호를 잘못 입력했어!</font></html>", JLabel.CENTER);  //불일치 안내 라벨
	JLabel overlapNicknameLabel = new JLabel("<html><font color='red'>앗! 중복인 닉네임이야 다른 걸로 해줘~</font></html>", JLabel.CENTER);  //중복 안내 라벨
	JLabel checkPasswordLabel = new JLabel("<html><font color='red'>앗! 비밀번호가 일치하지 않아~</font></html>", JLabel.CENTER);  //비번 불일치 안내 라벨
	
	JPanel startPanel = new JPanel();  //시작화면 패널
	JPanel signInPanel = new JPanel();  //로그인 패널
	JPanel signUpPanel = new JPanel();  //회원가입 패널
	JPanel gameRulePanel = new JPanel();  //게임방법 패널
	JPanel selectSongPanel = new JPanel();  //노래 선택 패널
	JPanel rankingPanel = new JPanel();  //랭킹 패널
	
	JTextField signInNicknameTF = new JTextField();  //로그인 닉네임 텍스트 필드
	JTextField signInPasswordTF = new JTextField();  //로그인 비밀번호 텍스트 필드
	JTextField signUpNicknameTF = new JTextField();  //회원가입 닉네임 텍스트 필드
	JTextField signUpPasswordTF = new JTextField();  //회원가입 비밀번호 텍스트 필드
	JTextField signUpPasswordCheckTF = new JTextField();  //회원가입 비밀번호 확인 텍스트 필드
	
	Font font1 = new Font("TDTDTadakTadak",Font.PLAIN,250);   //로고 폰트
	Font buttonFont = new Font("TDTDTadakTadak",Font.PLAIN,80);   //버튼 폰트
	
	User user = new User();  //유저
	ArrayList<Music> musicList = new ArrayList<Music>();  //노래 리스트
	MP3Player highlightPlayer = new MP3Player();  //노래 하이라이트 재생용 MP3Player
	
	public Screen() {
		musicList.add(new Music("imase","NIGHT DANCER"));  //NIGHT DANCER 추가
		musicList.add(new Music("정국","3D"));  //3D 추가
		musicList.add(new Music("NewJeans","ETA"));  //ETA 추가  
		
		/*set*/
		startPanel.setLayout(null);   //시작화면
		setFocusable(true);
		//로그인 버튼
		signInButton.setBounds(540,650,320,75);
		signInButton.setFont(buttonFont);
		signInButton.setHorizontalTextPosition(JButton.CENTER);
		signInButton.setRolloverIcon(clickButtonImg);  //호버링시 이미지 변경
		//회원가입 버튼
		signUpButton.setBounds(540,750,320,75);
		signUpButton.setFont(buttonFont);
		signUpButton.setHorizontalTextPosition(JButton.CENTER);
		signUpButton.setRolloverIcon(clickButtonImg);  //호버링시 이미지 변경
		//게임방법 버튼
		gameRuleButton.setBounds(300,500,150,150);
		gameRuleButton.setFont(buttonFont);
		gameRuleButton.setHorizontalTextPosition(JButton.CENTER);
		//로고 라벨1
		logoTextLabel1.setFont(font1);
		logoTextLabel1.setBounds(0, -300, FRAME_WIDTH, FRAME_HEIGHT);
		//로고 라벨2
		logoTextLabel2.setFont(font1);
		logoTextLabel2.setBounds(0, -100, FRAME_WIDTH, FRAME_HEIGHT);
		//로고 라벨3
		logoTextLabel3.setFont(font1);
		logoTextLabel3.setBounds(0, 100, FRAME_WIDTH, FRAME_HEIGHT);
		
		//시작화면 라벨
		startScreenLabel.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
		
		/*버튼 투명하게*/
		//로그인 버튼
		transparencyButton(signInButton);
		//회원가입 버튼
		transparencyButton(signUpButton);
		//게임방법 버튼
		transparencyButton(gameRuleButton);
		
		/*add*/
		addKeyListener(new NoteKeyListener());  //키 리스너 추가
		//로그인 버튼 액션리스너
		signInButton.addActionListener(e->{
			startPanel.setVisible(false);  //시작화면 숨김
			generateSignIn();  //로그인 화면 생성
			signInPanel.setVisible(true);  //로그인 화면 보이게
		});
		//회원가입 버튼 액션리스너
		signUpButton.addActionListener(e->{
			startPanel.setVisible(false);  //시작화면 숨김
			generateSignUp();  //회원가입 화면 생성
            signUpPanel.setVisible(true);  //회원가입 화면 보이게
		});
		//게임방법 버튼 액션리스너
		gameRuleButton.addActionListener(e->{
			startPanel.setVisible(false);  //시작화면 숨김
        	generateGameRule();  //게임방법 화면 생성
        	gameRulePanel.setVisible(true);  //게임방법 화면 보이게
		});
		startPanel.add(signInButton);  //로그인 버튼
		startPanel.add(signUpButton);  //회원가입 버튼
		startPanel.add(gameRuleButton);  //게임방법 버튼
		startPanel.add(logoTextLabel1);  //로고 라벨1
		startPanel.add(logoTextLabel2);  //로고 라벨2
		startPanel.add(logoTextLabel3);  //로고 라벨3
		startPanel.add(startScreenLabel);  //시작화면 라벨
		add(startPanel);  //시작화면 패널
		
		//기본 설정
		setTitle("Screen");  //제목 설정
		setSize(FRAME_WIDTH, FRAME_HEIGHT);  //사이즈 설정
		setLocationRelativeTo(null);  //윈도우 창 정중앙에
		setResizable(false);  //화면 크기 조정 불가
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void paint(Graphics g) {
		screenImg = createImage(FRAME_WIDTH,FRAME_HEIGHT);  //더블 버퍼링용 이미지 생성
		screenGraphics = screenImg.getGraphics();  //게임 그래픽에 생성
		screenDraw((Graphics2D)screenGraphics);  //화면 그리기
		g.drawImage(screenImg, 0, 0, null);  //마지막에 이미지를 추가
		g.dispose();
	}
	
	
	void screenDraw(Graphics2D g){  //화면 그리는 메서드
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);  //안티 앨리어싱 설정(화질 좋아지게)
		if(isGame) {  //지금 게임 실행이면?
			game.drawScreen(g);  //게임 화면 나옴
			 repaint(); 
		}else {  //게임 실행이 아니라면
			paintComponents(g);
		}
	}
	
	//버튼 투명하게 만드는 메서드
	private void transparencyButton(JButton button) {
		button.setOpaque(false);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		button.setFocusPainted(false);
	}
	
	//로그인 패널 만드는 메서드
	private void generateSignIn() {
		ImageIcon signInImg = new ImageIcon(imagePath + "Login_Screen.png");  //로그인 화면 이미지
		JLabel signInScreenLabel = new JLabel(signInImg);  //로그인 화면 라벨
		JLabel signInText = new JLabel("로그인");  //로그인 텍스트 라벨
		JLabel nickNameText = new JLabel("닉네임 : ");  //닉네임 텍스트 라벨
		JLabel passwordText = new JLabel("비밀번호 : ");  //비밀번호 텍스트 라벨
		
		Font font = new Font("TDTDTadakTadak",Font.PLAIN,200);  //폰트
		Font font2 = new Font("TDTDTadakTadak",Font.PLAIN,120);  // 조금 작은 폰트
		Font textFont = new Font("TDTDTadakTadak",Font.PLAIN,60);  //텍스트 필드용 폰트
		Font guideFont = new Font("TDTDTadakTadak",Font.PLAIN,40);  //비번 불일치 안내 폰트
		JButton OKButton = new JButton("로그인!",new ImageIcon(imagePath+"Button.png"));  //확인 버튼
		
		/*set*/
		signInPanel.setLayout(null);  //로그인 화면 패널
		signInScreenLabel.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);  //로그인 화면 라벨
		//로그인 텍스트 라벨
		signInText.setFont(font);
		signInText.setBounds(0, -350, FRAME_WIDTH, FRAME_HEIGHT);
		signInText.setHorizontalAlignment(JLabel.CENTER);
		//닉네임 텍스트 라벨
		nickNameText.setFont(font2);
		nickNameText.setBounds(200, 320, 600, 150);
		//비밀번호 텍스트 라벨
		passwordText.setFont(font2);
		passwordText.setBounds(105, 480, 600, 150);
		//닉네임 텍스트 필드
		signInNicknameTF.setFont(textFont);
		signInNicknameTF.setBounds(600, 355, 300, 80);
		//비밀번호 텍스트 필드
		signInPasswordTF.setFont(textFont);
		signInPasswordTF.setBounds(600, 515, 300, 80);
		//확인버튼
		OKButton.setFont(buttonFont);
		OKButton.setBounds(540,700,320,75);
		OKButton.setHorizontalTextPosition(JButton.CENTER);
		OKButton.setRolloverIcon(clickButtonImg);  //호버링시 이미지 변경
		transparencyButton(OKButton);  //버튼 투명하게
		//불일치 안내 라벨
		noPasswordLabel.setFont(guideFont);
		noPasswordLabel.setBounds(200, 600, 1000, 100);
		noPasswordLabel.setVisible(false);
		/*add*/
		signInPanel.add(noPasswordLabel);  //불일치 안내 라벨
		signInPanel.add(signInText);  //로그인 텍스트 라벨
		signInPanel.add(nickNameText);  //닉네임 텍스트 라벨
		signInPanel.add(passwordText);  //비번 텍스트 라벨
		signInPanel.add(signInNicknameTF);  //닉네임 텍스트 필드
		signInPanel.add(signInPasswordTF);  //비번 텍스트 필드
		
		OKButton.addActionListener(e->{
			doSignIn();  //로그인 기능
		});
		
		signInPanel.add(OKButton);  //확인 버튼
		signInPanel.add(signInScreenLabel);
		add(signInPanel);
		signInPanel.setVisible(false);
	}
	
	//로그인 기능 메서드
	public void doSignIn() {
		DB db = new DB();
		db.connect();  //db 연결
		boolean signInSuccess = false;  //로그인 성공했는지
		
		user.setNickName(signInNicknameTF.getText());  //닉네임 설정
		user.setPassword(signInPasswordTF.getText());  //비번 설정
		
		String sql = "SELECT * FROM user WHERE nickname = '" + user.getNickName() + "'";  //닉네임이 있는지 확인용 sql문
		ResultSet result;
		
		try {
			result = db.stmt.executeQuery(sql);
			if(result.next()) {  //닉네임이 있으면
				if(user.getPassword().equals(result.getString("password"))){ //입력한 비번과 계정 비번이 일치하면 로그인 성공
					noPasswordLabel.setVisible(false);  // 불일치 안내 숨김
					signInSuccess = true;  //로그인 성공
				}else {
					noPasswordLabel.setVisible(true);  // 불일치 안내
				}
			}else {  //닉네임 없으면
				noPasswordLabel.setVisible(true);  // 불일치 안내
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		db.closeConnection();  //db 연결 해제
		
		if(signInSuccess) {
			signInPanel.setVisible(false);  //로그인 화면 숨김
			generateSelectSong();  //노래 선택 화면 생성
			selectSongPanel.setVisible(true);  //노래 선택 화면 보이게
		}
	}
	
	//회원가입 패널 만드는 메서드
	private void generateSignUp() {
		ImageIcon signUpImg = new ImageIcon(imagePath + "Join_Screen.png");  //회원가입 화면 이미지
		JLabel signUpScreenLabel = new JLabel(signUpImg);  //회원가입 화면 라벨
		JLabel signUpText = new JLabel("회원가입");  //회원가입 텍스트 라벨
		JLabel nickNameText = new JLabel("닉네임 : ");  //닉네임 텍스트 라벨
		JLabel passwordText = new JLabel("비밀번호 : ");  //비밀번호 텍스트 라벨
		JLabel passwordCheckText = new JLabel("비밀번호 확인 : ");  //비밀번호 확인 텍스트 라벨
		
		Font font = new Font("TDTDTadakTadak",Font.PLAIN,200);  //폰트
		Font font2 = new Font("TDTDTadakTadak",Font.PLAIN,100);  // 조금 작은 폰트
		Font textFont = new Font("TDTDTadakTadak",Font.PLAIN,60);  //텍스트 필드용 폰트
		Font guideFont = new Font("TDTDTadakTadak",Font.PLAIN,40);  //불일치 안내 폰트
		
		JButton OKButton = new JButton("회원가입!",new ImageIcon(imagePath+"Button.png"));  //확인 버튼
		
		/*set*/
		signUpPanel.setLayout(null);  //회원가입 화면 패널
		signUpScreenLabel.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);  //회원가입 화면 라벨
		//회원가입 텍스트 라벨
		signUpText.setFont(font);
		signUpText.setBounds(0, -350, FRAME_WIDTH, FRAME_HEIGHT);
		signUpText.setHorizontalAlignment(JLabel.CENTER);
		//닉네임 텍스트 라벨
		nickNameText.setFont(font2);
		nickNameText.setBounds(360, 225, 600, 150);
		//비밀번호 텍스트 라벨
		passwordText.setFont(font2);
		passwordText.setBounds(285, 370, 600, 150);
		//비밀번호 확인 텍스트 라벨
		passwordCheckText.setFont(font2);
		passwordCheckText.setBounds(105, 515, 600, 150);
		//닉네임 텍스트 필드
		signUpNicknameTF.setFont(textFont);
		signUpNicknameTF.setBounds(700, 260, 300, 80);
		//비밀번호 텍스트 필드
		signUpPasswordTF.setFont(textFont);
		signUpPasswordTF.setBounds(700, 405, 300, 80);
		//비밀번호 확인 텍스트 필드
		signUpPasswordCheckTF.setFont(textFont);
		signUpPasswordCheckTF.setBounds(700, 550, 300, 80);
		//확인버튼
		OKButton.setFont(buttonFont);
		OKButton.setBounds(540,700,320,75);
		OKButton.setHorizontalTextPosition(JButton.CENTER);
		OKButton.setRolloverIcon(clickButtonImg);  //호버링시 이미지 변경
		transparencyButton(OKButton);  //버튼 투명하게
		//중복 닉네임 안내 라벨
		overlapNicknameLabel.setFont(guideFont);
		overlapNicknameLabel.setBounds(200, 610, 1000, 100);
		overlapNicknameLabel.setVisible(false);
		//비번 불일치 안내 라벨
		checkPasswordLabel.setFont(guideFont);
		checkPasswordLabel.setBounds(200, 610, 1000, 100);
		checkPasswordLabel.setVisible(false);
		
		/*add*/
		OKButton.addActionListener(e->{
			doSignUp();  //회원가입 기능
		});
		signUpPanel.add(signUpText);  //로그인 텍스트 라벨
		signUpPanel.add(nickNameText);  //닉네임 텍스트 라벨
		signUpPanel.add(passwordText);  //비번 텍스트 라벨
		signUpPanel.add(passwordCheckText);  //비번 확인 텍스트 라벨
		signUpPanel.add(signUpNicknameTF);  //닉네임 텍스트 필드
		signUpPanel.add(signUpPasswordTF);  //비번 텍스트 필드
		signUpPanel.add(signUpPasswordCheckTF);  //비번 확인 텍스트 필드
		signUpPanel.add(overlapNicknameLabel);  //중복 닉네임 안내 라벨
		signUpPanel.add(checkPasswordLabel);  //비번 불일치 안내 라벨
		signUpPanel.add(OKButton);  //확인 버튼
		signUpPanel.add(signUpScreenLabel);
		add(signUpPanel);
		signUpPanel.setVisible(false);
	}
	
	// 회원가입 기능 메서드
	public void doSignUp() {
		DB db = new DB();
		db.connect(); // db 연결
		boolean isOverlap = false;  //닉네임이 중복인가
		boolean signUpSuccess = false;  //회원가입 성공했는지
		
		user.setNickName(signUpNicknameTF.getText()); // 닉네임 설정
		user.setPassword(signUpPasswordTF.getText()); // 비번 설정
		
		//중복 닉네임인지 확인 
		String sql = "SELECT * FROM user WHERE nickname = '"+user.getNickName()+"'";// 닉네임이 있는지 확인용 sql문
		ResultSet checkResult;
		
		try {
			checkResult = db.stmt.executeQuery(sql);
			if(checkResult.next()) {  //중복 닉네임이 있다면
				checkPasswordLabel.setVisible(false);
				overlapNicknameLabel.setVisible(true);  //중복 닉네임 안내
				isOverlap = true;  //중복임
			}else {  //중복되는 닉네임이 없다면
				overlapNicknameLabel.setVisible(false);  //중복 닉네임 안내 숨김
				isOverlap = false;  //중복아님
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//비번과 확인용 비번이 불일치인지 확인
		if(!signUpPasswordTF.getText().equals(signUpPasswordCheckTF.getText()) && !isOverlap) {  //비번과 확인용 비번이 불일치라면 & 닉네임이 중복이 아니라면
			checkPasswordLabel.setVisible(true);   //중복 닉네임 안내
		}else {  //일치하면
			checkPasswordLabel.setVisible(false);  //중복 닉네임 안내 숨김
		}
		
		//계정 정보 DB에 넣기
		sql = "INSERT INTO user (nickname, password) VALUES ('"+user.getNickName()+"', '"+user.getPassword()+"')";  //데이터 넣는 sql문
		try {
			db.stmt.executeUpdate(sql);
			signUpSuccess = true;  //회원가입 성공
		} catch (SQLException e) {
			e.printStackTrace();
		}
		db.closeConnection(); // db 연결 해제
		
		if(signUpSuccess) {  //회원가입 성공시
			signUpPanel.setVisible(false);  //회원가입 화면 숨김
			generateSelectSong();  //노래 선택 화면 생성
			selectSongPanel.setVisible(true);  //노래 선택 화면 보이게
		}
	}
	
	//게임방법 패널 만드는 메서드
	private void generateGameRule() {
		ImageIcon gameRuleImg = new ImageIcon(imagePath + "GameRule_Screen.png");  //게임방법 화면 이미지
		
		JLabel gameRuleScreenLabel = new JLabel(gameRuleImg);  //게임방법 화면 라벨
		JLabel logoTextLabel1 = new JLabel("MY", JLabel.CENTER);  //로고 라벨1  "MY"
		JLabel logoTextLabel2 = new JLabel("RHYTHM", JLabel.CENTER);  //로고 라벨2  "RHYTHM"
		JLabel logoTextLabel3 = new JLabel("NOTE", JLabel.CENTER);  //로고 라벨3  "NOTE"
		JLabel gameRuleText = new JLabel("게임방법!");  //게임방법 텍스트 라벨
		JLabel gameRuleLabel1 = new JLabel("조작키 S  D  F  J  K  L",JLabel.CENTER);  //게임방법 라벨 1
		JLabel gameRuleLabel2 = new JLabel("1. 내려오는 노트에 맞춰 키보드를 눌러보자!",JLabel.CENTER);  //게임방법 라벨 2
		JLabel gameRuleLabel3 = new JLabel("<html>2. 정확도에 따라 <font color=#00BFFF'>PERFECT</font>, <font color='#04B404'>GOOD</font>, <font color='red'>BAD</font>로 판정돼.</html>",JLabel.CENTER);  //게임방법 라벨 3
		JLabel gameRuleLabel4 = new JLabel("<html>3. 난이도는 <font color=#00BFFF'>EASY</font>, <font color='#FFDB58'>NORMAL</font>, <font color='red'>HARD</font>가 있어.</html>",JLabel.CENTER);  //게임방법 라벨 4
		JLabel gameRuleLabel5 = new JLabel("4. 멋진 리듬을 보여주고 랭킹에 올라가보자!",JLabel.CENTER);  //게임방법 라벨 5
		
		JButton OKButton = new JButton("알겠어!",new ImageIcon(imagePath+"Button.png"));  //확인 버튼
		
		Font font = new Font("TDTDTadakTadak",Font.PLAIN,200);  //폰트
		Font font2 = new Font("TDTDTadakTadak",Font.PLAIN,60);  // 조금 작은 폰트
		Font textFont = new Font("TDTDTadakTadak",Font.PLAIN,70);  //게임방법 설명 폰트
		
		/*set*/
		gameRulePanel.setLayout(null);
		gameRuleScreenLabel.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);  //게임방법 화면 라벨
		//게임방법! 텍스트 라벨
		gameRuleText.setFont(font);
		gameRuleText.setBounds(0, -360, FRAME_WIDTH, FRAME_HEIGHT);
		gameRuleText.setHorizontalAlignment(JLabel.CENTER);
		//로고 라벨
		logoTextLabel1.setFont(font2);
		logoTextLabel1.setBounds(110, 20, 200, 100);
		logoTextLabel2.setFont(font2);
		logoTextLabel2.setBounds(110, 68, 200, 100);
		logoTextLabel3.setFont(font2);
		logoTextLabel3.setBounds(110, 116, 200, 100);
		//게임방법 설명 텍스트 라벨
		gameRuleLabel1.setFont(textFont);
		gameRuleLabel1.setBounds(0, 200, 1400, 200);
		
		gameRuleLabel2.setFont(textFont);
		gameRuleLabel2.setBounds(0, 280, 1400, 200);
		
		gameRuleLabel3.setFont(textFont);
		gameRuleLabel3.setBounds(0, 370, 1400, 200);
		
		gameRuleLabel4.setFont(textFont);
		gameRuleLabel4.setBounds(0, 450, 1400, 200);
		
		gameRuleLabel5.setFont(textFont);
		gameRuleLabel5.setBounds(0, 540, 1400, 200);
		//확인버튼
		OKButton.setFont(buttonFont);
		OKButton.setBounds(540,730,320,75);
		OKButton.setHorizontalTextPosition(JButton.CENTER);
		OKButton.setRolloverIcon(clickButtonImg);  //호버링시 이미지 변경
		transparencyButton(OKButton);  //버튼 투명하게
		
		/*add*/
		gameRulePanel.add(logoTextLabel1);  //로고 텍스트 라벨1
		gameRulePanel.add(logoTextLabel2);  //로고 텍스트 라벨2
		gameRulePanel.add(logoTextLabel3);  //로고 텍스트 라벨3
		gameRulePanel.add(gameRuleText);  //게임방법! 텍스트 라벨
		gameRulePanel.add(gameRuleLabel1);  //게임방법 설명 텍스트 라벨1
		gameRulePanel.add(gameRuleLabel2);  //게임방법 설명 텍스트 라벨2
		gameRulePanel.add(gameRuleLabel3);  //게임방법 설명 텍스트 라벨3
		gameRulePanel.add(gameRuleLabel4);  //게임방법 설명 텍스트 라벨4
		gameRulePanel.add(gameRuleLabel5);  //게임방법 설명 텍스트 라벨5
		OKButton.addActionListener(e->{
			gameRulePanel.setVisible(false);  //게임방법 화면 숨김
        	startPanel.setVisible(true);  //시작화면 보이게
		});  //액션리스너
		gameRulePanel.add(OKButton);  //확인 버튼
		gameRulePanel.add(gameRuleScreenLabel);  //게임방법 화면 라벨
		add(gameRulePanel);
		gameRulePanel.setVisible(false);
	}
	
	//노래 선택 패널 만드는 메서드
	public void generateSelectSong() {
		String musicPath = System.getProperty("user.dir")+"/src/musics/";  //음악 상대 경로
		
		ImageIcon selectSongImg = new ImageIcon(imagePath + "SelectSong_Screen.png");  //노래 선택 화면 이미지
		ImageIcon clickRightButtonImg = new ImageIcon(imagePath + "Click_Right_Button.png");  //오른쪽 버튼 클릭 이미지
		ImageIcon albumImg = new ImageIcon(imagePath + "Album_"+musicList.get(0).getTitle()+".png");  //노래 리스트에 있는 첫번째 노래 앨범 이미지
		ImageIcon clickLeftButtonImg = new ImageIcon(imagePath + "Click_Left_Button.png");  //왼쪽 버튼 클릭 이미지
		ImageIcon clickRankingButtonImg = new ImageIcon(imagePath + "Click_Ranking_Button.png");  //랭킹보기 버튼 클릭 이미지
		
		JLabel selectSongLabel = new JLabel(selectSongImg);  //노래 선택 화면 라벨
		JLabel albumLabel = new JLabel(albumImg);  //앨범 커버 라벨
		JLabel screenNameLabel = new JLabel("노래 선택");  //노래 선택 글자 라벨
		JLabel titleLabel = new JLabel(musicList.get(0).getTitle());  //노래 리스트에 있는 첫번째 노래 제목 가져옴
		JLabel singerLabel = new JLabel(musicList.get(0).getSinger());  //노래 리스트에 있는 첫번째 노래 가수 가져옴
		
		JButton rightButton = new JButton(new ImageIcon(imagePath + "Right_Button.png"));  //오른쪽 버튼
		JButton leftButton = new JButton(new ImageIcon(imagePath + "Left_Button.png"));  //왼쪽 버튼
		JButton selectButton = new JButton("선택!", new ImageIcon(imagePath + "Button.png"));  //선택 버튼
		JButton rankingButton = new JButton("랭킹보기",new ImageIcon(imagePath + "Ranking_Button.png"));  //랭킹보기 버튼
		
		Font font = new Font("TDTDTadakTadak",Font.PLAIN,80);   //화면 폰트
		Font titleFont = new Font("TDTDTadakTadak",Font.PLAIN,75);  //제목 폰트
		Font singerFont = new Font("TDTDTadakTadak",Font.PLAIN,60);  //가수 폰트
		
		highlightPlayer.play(musicPath+musicList.get(musicIndex).getTitle()+" highlight.mp3");  //노래 재생
		
		/*set*/
		selectSongPanel.setLayout(null);
		selectSongLabel.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);  //노래 선택 화면 라벨
		//노래 제목
		titleLabel.setBounds(0,0,1400,100);
		titleLabel.setHorizontalAlignment(JLabel.CENTER);  //중앙 정렬
		titleLabel.setFont(titleFont);  //폰트 설정
		//노래 가수
		singerLabel.setBounds(0,80,1400,100);
		singerLabel.setHorizontalAlignment(JLabel.CENTER);  //중앙 정렬
		singerLabel.setFont(singerFont);  //폰트 설정
		//노래 선택 글자 라벨
		screenNameLabel.setBounds(75,0,300,100);
		screenNameLabel.setFont(font);  //폰트 설정
		//오른쪽 버튼
		rightButton.setBounds(1050, 400, 270, 130);
		transparencyButton(rightButton);  //버튼 투명하게
		rightButton.setRolloverIcon(clickRightButtonImg);  //호버링시 이미지 변경
		rightButton.setActionCommand("오른쪽");
		//왼쪽 버튼
		leftButton.setBounds(100, 400, 270, 130);
		transparencyButton(leftButton);  //버튼 투명하게
		leftButton.setRolloverIcon(clickLeftButtonImg);  //호버링시 이미지 변경
		leftButton.setActionCommand("왼쪽");
		//앨범 커버
		albumLabel.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);  //앨범 커버 라벨
		//선택 버튼
		selectButton.setBounds(540,700,320,100);
		transparencyButton(selectButton);  //버튼 투명하게
		selectButton.setFont(buttonFont);  //폰트 설정
		selectButton.setHorizontalTextPosition(JButton.CENTER);
		selectButton.setRolloverIcon(clickButtonImg);  //호버링시 이미지 변경
		//랭킹보기 버튼
		rankingButton.setBounds(1050,0,320,80);
		transparencyButton(rankingButton);  //버튼 투명하게
		rankingButton.setFont(buttonFont);  //폰트 설정
		rankingButton.setHorizontalTextPosition(JButton.CENTER);
		rankingButton.setRolloverIcon(clickRankingButtonImg);  //호버링시 이미지 변경
		
		//액션 리스너
		ActionListener selelctbuttonListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand().equals("오른쪽")) {
					if(musicIndex<musicList.size()-1)  //노래 리스트 길이보다 작다면 인덱스 증가
						musicIndex++;
				}else if(e.getActionCommand().equals("왼쪽")) {
					if(musicIndex>0)   //0보다 크다면 인덱스 감소
						musicIndex--;
				}
				ImageIcon albumChangeImg = new ImageIcon(imagePath + "Album_"+ musicList.get(musicIndex).getTitle()+".png");  //앨범 커버 이미지
				titleLabel.setText(musicList.get(musicIndex).getTitle());  //노래 제목 교체
				singerLabel.setText(musicList.get(musicIndex).getSinger());  //노래 가수 교체
				albumLabel.setIcon(albumChangeImg);  //앨범 커버 이미지 교체

				if(highlightPlayer.isPlaying()) {  //이전 노래가 재생중이라면
					highlightPlayer.stop();  //노래 정지
				}
				
				//노래 끊기는 게 어색해서 넣음
				try {
					Thread.sleep(350);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				highlightPlayer.play(musicPath+musicList.get(musicIndex).getTitle()+" highlight.mp3");  //하이라이트 재생
			}
		};
		
		/*add*/
		rightButton.addActionListener(selelctbuttonListener);  //오른쪽 버튼 액션 리스너
		leftButton.addActionListener(selelctbuttonListener);  //왼쪽 버튼 액션 리스너
		 //랭킹 버튼 액션 리스너
		rankingButton.addActionListener(e->{ 
			selectSongPanel.setVisible(false);  //노래 선택 화면 숨김
			generateRanking(musicIndex);  //랭킹 화면 생성 메서드
			rankingPanel.setVisible(true);  //랭킹 화면 보이게
		}); 
		
		//선택 버튼 액션 리스너
		selectButton.addActionListener(e->{
			highlightPlayer.stop();  //하이라이트 재생 멈춤
			Music.music = new Music(musicList.get(musicIndex).getSinger(),musicList.get(musicIndex).getTitle());  //노래 설정
			removeAll();  //전 화면 다 지워버림
			isGame = true;  //게임이다.
			game = new Game();  
			game.start();  //게임 시작
			setLocation(1000, 1000);  //윈도우 창 멀리 보내버림
			setLocationRelativeTo(null);  //윈도우 창 다시 정중앙에
		});
		
		selectSongPanel.add(screenNameLabel);  //노래 선택 라벨
		selectSongPanel.add(titleLabel);  //제목 라벨
		selectSongPanel.add(singerLabel);  //가수 라벨
		selectSongPanel.add(selectButton);  //선택 버튼
		selectSongPanel.add(rightButton);  //오른쪽 버튼
		selectSongPanel.add(leftButton);  //왼쪽 버튼
		selectSongPanel.add(rankingButton);  //랭킹보기 버튼
		selectSongPanel.add(albumLabel);  //앨범 커버 라벨
		selectSongPanel.add(selectSongLabel);  //노래 선택 화면 라벨
		add(selectSongPanel);
		selectSongPanel.setVisible(false);
	}
	
	//랭킹 패널 만드는 메서드
	public void generateRanking(int index) {
		DB db = new DB();
		db.connect();
		String[] user = new String[5];  //닉네임 배열
		int[] score = new int[5];  //점수 배열
		String table = "";  //테이블 변수
		int i = 0;
		
		ImageIcon rankingEasyImg = new ImageIcon(imagePath + "Ranking_Easy_Screen.png");  //랭킹 쉬움 화면 이미지
		ImageIcon rankingNormalImg = new ImageIcon(imagePath + "Ranking_Normal_Screen.png");  //랭킹 보통 화면 이미지
		ImageIcon rankingHardImg = new ImageIcon(imagePath + "Ranking_Hard_Screen.png");  //랭킹 어려움 화면 이미지
		ImageIcon albumImg = new ImageIcon(imagePath +musicList.get(index).getTitle()+"_Ranking.png");  //해당 노래 앨범 이미지
		
		JLabel rankingLabel = new JLabel(rankingEasyImg);  //랭킹 화면 라벨
		JLabel albumLabel = new JLabel(albumImg);  //앨범 이미지 라벨
		JLabel titleLabel = new JLabel(musicList.get(index).getTitle());  //노래 제목 라벨
		JLabel singerLabel = new JLabel(musicList.get(index).getSinger());  //노래 가수 라벨
		JLabel firstLabel = new JLabel();  //1위 닉네임 라벨
		JLabel secondLabel = new JLabel();  //2위 닉네임 라벨
		JLabel thirdLabel = new JLabel();  //3위 닉네임 라벨
		JLabel fourthLabel = new JLabel();  //4위 닉네임 라벨
		JLabel fifthLabel = new JLabel();  //5위 닉네임 라벨
		JLabel firstScoreLabel = new JLabel();  //1위 점수 라벨
		JLabel secondScoreLabel = new JLabel();  //2위 점수 라벨
		JLabel thirdScoreLabel = new JLabel();  //3위 점수 라벨
		JLabel fourthScoreLabel = new JLabel();  //4위 점수 라벨
		JLabel fifthScoreLabel = new JLabel();  //5위점수  라벨
		
		JButton backButton = new JButton("돌아가기", new ImageIcon(imagePath+"Button.png"));  //돌아가기 버튼
		
		Font rankingFont = new Font("TDTDTadakTadak",Font.PLAIN,80);   //순위 폰트
		Font titleFont = new Font("TDTDTadakTadak",Font.PLAIN,70);   //제목 폰트
		Font singerFont = new Font("TDTDTadakTadak",Font.PLAIN,55);   //가수 폰트
		
		switch(musicList.get(index).getTitle()) {  //랭킹 화면 라벨 이미지 설정
		case "NIGHT DANCER" : rankingLabel.setIcon(rankingEasyImg);  //나이트댄서면 쉬움 화면 이미지로
			table = "ranking_night_dancer"; break;
		case "3D" : rankingLabel.setIcon(rankingNormalImg);  //3D면 보통 화면 이미지로
			table = "ranking_3d"; break;
		case "ETA" : rankingLabel.setIcon(rankingHardImg);  //ETA면 어려움 화면 이미지로
			table = "ranking_eta"; break;
		}
		
		String sql = "SELECT * FROM "+ table +" ORDER BY score DESC limit 5";  //점수를 기준으로 오름차순하는 sql문
		ResultSet result;
		
		try {
			result = db.stmt.executeQuery(sql);
			while (result.next()) {
				user[i] = result.getString("user");  //닉네임 저장
				score[i] = result.getInt("score");  //점수 저장
				i++;
		    }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		db.closeConnection();  //db 연결 해제
		
		/*set*/
		rankingPanel.setLayout(null);
		rankingLabel.setBounds(0,0,FRAME_WIDTH,FRAME_HEIGHT);  //랭킹 화면 라벨
		albumLabel.setBounds(0,0,FRAME_WIDTH,FRAME_HEIGHT);  //앨범 이미지 라벨
		//돌아가기 버튼
		backButton.setFont(buttonFont);
		backButton.setBounds(540,730,320,75);
		backButton.setHorizontalTextPosition(JButton.CENTER);
		backButton.setRolloverIcon(clickButtonImg);  //호버링시 이미지 변경
		transparencyButton(backButton);  //버튼 투명하게
		//제목 라벨
		titleLabel.setFont(titleFont);  //폰트 설정
		FontMetrics titleFontMetrics = titleLabel.getFontMetrics(titleLabel.getFont());
		int titleWidth = titleFontMetrics.stringWidth(titleLabel.getText());  //제목 길이 구함
		titleLabel.setBounds(320 - (titleWidth/2), 140, titleWidth, 100);
		//가수 라벨
		singerLabel.setFont(singerFont);  //폰트 설정
		FontMetrics singerFontMetrics = singerLabel.getFontMetrics(singerLabel.getFont());
		int singerWidth = singerFontMetrics.stringWidth(singerLabel.getText());  //가수 길이 구함
		singerLabel.setBounds(320 - (singerWidth/2), 195, singerWidth+100, 100);
		//순위 닉네임 라벨
		firstLabel.setFont(rankingFont);  //폰트 설정
		firstLabel.setText(user[0]);
		firstLabel.setBounds(750,260,500,100);
		
		secondLabel.setFont(rankingFont);  //폰트 설정
		secondLabel.setText(user[1]);
		secondLabel.setBounds(750,350,500,100);
		
		thirdLabel.setFont(rankingFont);  //폰트 설정
		thirdLabel.setText(user[2]);
		thirdLabel.setBounds(750,420,500,100);
		
		fourthLabel.setFont(rankingFont);  //폰트 설정
		fourthLabel.setText(user[3]);
		fourthLabel.setBounds(750,500,500,100);
		
		fifthLabel.setFont(rankingFont);  //폰트 설정
		fifthLabel.setText(user[4]);
		fifthLabel.setBounds(750,590,500,100);
		//순위 점수 라벨
		firstScoreLabel.setFont(rankingFont);  //폰트 설정
		firstScoreLabel.setText(String.valueOf(score[0]));
		firstScoreLabel.setBounds(1000,260,500,100);
		
		secondScoreLabel.setFont(rankingFont);  //폰트 설정
		secondScoreLabel.setText(String.valueOf(score[1]));
		secondScoreLabel.setBounds(1000,350,500,100);
		
		thirdScoreLabel.setFont(rankingFont);  //폰트 설정
		thirdScoreLabel.setText(String.valueOf(score[2]));
		thirdScoreLabel.setBounds(1000,420,500,100);
		
		fourthScoreLabel.setFont(rankingFont);  //폰트 설정
		fourthScoreLabel.setText(String.valueOf(score[3]));
		fourthScoreLabel.setBounds(1000,500,500,100);
		
		fifthScoreLabel.setFont(rankingFont);  //폰트 설정
		fifthScoreLabel.setText(String.valueOf(score[4]));
		fifthScoreLabel.setBounds(1000,590,500,100);
		
		/*add*/
		backButton.addActionListener(e->{
			rankingPanel.removeAll();  //랭킹 화면 지워버림
			selectSongPanel.setVisible(true);    //노래 선택 화면 보이게
		});
		
		rankingPanel.add(firstLabel);  //1위 닉네임 라벨
		rankingPanel.add(secondLabel);  //2위 닉네임 라벨
		rankingPanel.add(thirdLabel);  //3위 닉네임 라벨
		rankingPanel.add(fourthLabel);  //4위 닉네임 라벨
		rankingPanel.add(fifthLabel);  //5위 닉네임 라벨
		rankingPanel.add(firstScoreLabel);  //1위 점수 라벨
		rankingPanel.add(secondScoreLabel);  //2위 점수 라벨
		rankingPanel.add(thirdScoreLabel);  //3위 점수 라벨
		rankingPanel.add(fourthScoreLabel);  //4위 점수 라벨
		rankingPanel.add(fifthScoreLabel);  //5위 점수 라벨
		rankingPanel.add(titleLabel);  //제목 라벨
		rankingPanel.add(singerLabel);  //가수 라벨
		rankingPanel.add(backButton);  //돌아가기 버튼
		rankingPanel.add(albumLabel);  //앨범 이미지 라벨
		rankingPanel.add(rankingLabel);  //랭킹 화면 라벨
		add(rankingPanel);
		rankingPanel.setVisible(false);
	}
}