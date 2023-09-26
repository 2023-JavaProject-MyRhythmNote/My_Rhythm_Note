import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.swing.*;

public class StartScreen extends JFrame {
    static Image background; // 이미지를 저장할 변수 선언

    static JPanel startScreen = new JPanel() {
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            g.drawImage(background, 0, 0, null); // 이미지를 그림
        }
    };
    
    // 버튼
    JButton login;
    ImageIcon btn_login = new ImageIcon(getClass().getResource("/images/Button.png"));
    ImageIcon btn_login2 = new ImageIcon(getClass().getResource("/images/Click_Button.png"));
    JButton join;
    ImageIcon btn_join = new ImageIcon(getClass().getResource("/images/Button.png"));
    ImageIcon btn_join2 = new ImageIcon(getClass().getResource("/images/Click_Button.png"));
    
    // 생성자
    public StartScreen() {
        StartScreenView();
    }
   
    public void StartScreenView() {
        setTitle("Start Screen");
        setSize(1400, 900);
        setResizable(false);
        setLocationRelativeTo(null); // 창이 가운데 나오게
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // 버튼 (로그인)
        login = new JButton(btn_login);
        login.setBorderPainted(false); // 버튼 테두리 설정해제
        login.setPreferredSize(new Dimension(330, 75)); // 버튼 크기 지정
        login.setRolloverIcon(btn_login2); // 호버링 시 이미지 변경
        
        // 버튼 (회원가입)
        join = new JButton(btn_join);
        join.setBorderPainted(false); // 버튼 테두리 설정해제
        join.setPreferredSize(new Dimension(330, 75)); // 버튼 크기 지정
        join.setRolloverIcon(btn_join2); // 호버링 시 이미지 변경
        
        // 로그인 버튼에 ActionListener 추가
        login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 로그인 버튼이 클릭되었을 때 수행할 동작을 여기에 추가
            	  new Login();
            }
        });
        
        // 회원가입 버튼에 ActionListener 추가
        join.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 회원가입 버튼이 클릭되었을 때 수행할 동작을 여기에 추가
            	 new Join();
            }
        });
        
        startScreen.add(login); // 패널에 버튼 붙여주기
        startScreen.add(join); // 패널에 버튼 붙여주기
        
        try {
          background = new ImageIcon(StartScreen.class.getResource("/images/Start_Screen.png")).getImage(); // 이미지 초기화
        } catch (Exception e) {
          e.printStackTrace();
        }
        add(startScreen); // 패널을 프레임에 추가
        
        setVisible(true);
    }

    
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            StartScreen startScreen = new StartScreen();
//        });
//    }
}


