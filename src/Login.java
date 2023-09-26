import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Login extends JFrame {
    static Image background; // 이미지를 저장할 변수 선언

    static JPanel loginScreen = new JPanel() {
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
    
    // 생성자
    public Login() {
        loginScreenView();
    }
   
    public void loginScreenView() {
        setTitle("Login Screen");
        setSize(1400, 900);
        setResizable(false);
        setLocationRelativeTo(null); // 창이 가운데 나오게
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // 버튼
        login = new JButton(btn_login);
        login.setBorderPainted(false); // 버튼 테두리 설정해제
        login.setPreferredSize(new Dimension(330, 75)); // 버튼 크기 지정
        login.setRolloverIcon(btn_login2); // 호버링 시 이미지 변경
        
        // 버튼에 ActionListener 추가
        login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 버튼이 클릭되었을 때 수행할 동작을 여기에 추가
                // 노래 선택화면으로 이동
            }
        });
        
        loginScreen.add(login); // 패널에 버튼 붙여주기

        try {
            background = new ImageIcon(Login.class.getResource("/images/Login_Screen.png")).getImage(); // 이미지 초기화
          } catch (Exception e) {
            e.printStackTrace();
          }
          add(loginScreen); // 패널을 프레임에 추가
          
          setVisible(true);
    }
    
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//        	Login loginScreen = new Login();
//        });
//    }
}