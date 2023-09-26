import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class Screen extends JFrame{
	private final int FRAME_WIDTH = 1400;  //가로 크기
	private final int FRAME_HEIGHT = 900;  //세로 크기
	String imagePath = System.getProperty("user.dir")+"/src/images/";  //이미지 상대 경로
	ImageIcon startImg = new ImageIcon(imagePath + "Start_Screen.png"); //시작화면 이미지
	ImageIcon clickButtonImg = new ImageIcon(imagePath + "Click_Button.png");  //버튼 클릭 이미지
	JButton signInButton = new JButton("로그인",new ImageIcon(imagePath+"Button.png"));  //로그인 버튼
	JButton ruleButton = new JButton(new ImageIcon(imagePath+"GameRule_Button.png"));  //게임방법 버튼
	JButton signUpButton = new JButton("회원가입",new ImageIcon(imagePath+"Button.png"));  //회원가입 버튼
	
	JLabel startScreenLabel = new JLabel(startImg);  //시작화면 라벨
	
	Font font1 = new Font("TDTDTadakTadak",Font.PLAIN,250);   //로고 폰트
	Font font2 = new Font("TDTDTadakTadak",Font.PLAIN,80);   //버튼 폰트
	
	public Screen() {
		/*setBounds*/
		setLayout(null);
		//로그인 버튼
		signInButton.setBounds(520,650,320,75);
		signInButton.setFont(font2);
		signInButton.setHorizontalTextPosition(JButton.CENTER);
		signInButton.setRolloverIcon(clickButtonImg);  //호버링시 이미지 변경
		//회원가입 버튼
		signUpButton.setBounds(520,750,320,75);
		signUpButton.setFont(font2);
		signUpButton.setHorizontalTextPosition(JButton.CENTER);
		signUpButton.setRolloverIcon(clickButtonImg);  //호버링시 이미지 변경
		
		//게임방법 버튼
		ruleButton.setBounds(300,500,150,150);
		ruleButton.setFont(font2);
		ruleButton.setHorizontalTextPosition(JButton.CENTER);
		//시작화면 라벨
		startScreenLabel.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
		
		/*버튼 투명하게*/
		//로그인 버튼
		transparencyButton(signInButton);
		//회원가입 버튼
		transparencyButton(signUpButton);
		//게임방법 버튼
		transparencyButton(ruleButton);
		
		//버튼 액션리스너
		ActionListener buttonListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("로그인")) {
                    
                } else if (e.getActionCommand().equals("회원가입")) {
                    
                }
				
			}
		};
		
		/*add*/
		signInButton.addActionListener(buttonListener);
		signUpButton.addActionListener(buttonListener);
		add(signInButton);  //로그인 버튼
		add(signUpButton);  //회원가입 버튼
		add(ruleButton);  //게임방법 버튼
		add(startScreenLabel);  //시작화면 라벨
		
		//기본 설정
		setTitle("Screen");  //제목 설정
		setSize(FRAME_WIDTH, FRAME_HEIGHT);  //사이즈 설정
		setLocationRelativeTo(null);  //윈도우 창 정중앙에
		setResizable(false);  //화면 크기 조정 불가
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	//버튼 투명하게 만드는 메서드
	public void transparencyButton(JButton button) {
		button.setOpaque(false);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		button.setFocusPainted(false);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		g.setFont(font1);  //로고 폰트 설정
		
		//로고
		g.drawString("MY", 600,250);
		g.drawString("RHYTHM", 340,450);
		g.drawString("NOTE", 450,650);
	}
	
}
