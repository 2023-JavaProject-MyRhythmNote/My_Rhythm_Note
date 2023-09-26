import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class GameRule extends JFrame {
    static Image background; // 이미지를 저장할 변수 선언

    static JPanel ruleScreen = new JPanel() {
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            g.drawImage(background, 0, 0, null); // 이미지를 그림
        }
    };
    
    // 버튼
    JButton rule;
    ImageIcon btn_rule = new ImageIcon(getClass().getResource("/images/Button.png"));
    ImageIcon btn_rule2 = new ImageIcon(getClass().getResource("/images/Click_Button.png"));
    
    // 생성자
    public GameRule() {
        GameRuleView();
    }
   
    public void GameRuleView() {
        setTitle("Start Screen");
        setSize(1400, 900);
        setResizable(false);
        setLocationRelativeTo(null); // 창이 가운데 나오게
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // 버튼
        rule = new JButton(btn_rule);
        rule.setBorderPainted(false); // 버튼 테두리 설정해제
        rule.setPreferredSize(new Dimension(330, 75)); // 버튼 크기 지정
        rule.setRolloverIcon(btn_rule2); // 호버링 시 이미지 변경
        
        // 버튼에 ActionListener 추가
        rule.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 버튼이 클릭되었을 때 수행할 동작을 여기에 추가
            }
        });
        
        ruleScreen.add(rule); // 패널에 버튼 붙여주기

        try {
            background = new ImageIcon(GameRule.class.getResource("/images/GameRule_Screen.png")).getImage(); // 이미지 초기화
          } catch (Exception e) {
            e.printStackTrace();
          }
          add(ruleScreen); // 패널을 프레임에 추가
          
          setVisible(true);
    }

    
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//        	GameRule gameRule = new GameRule();
//        });
//    }
}