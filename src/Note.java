import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;

import javax.swing.ImageIcon;
import javax.swing.RepaintManager;

//노트가 내려오는 클래스
public class Note extends Thread{
	String imagePath = System.getProperty("user.dir")+"/src/images/";  //이미지 상대 경로
	String musicPath = System.getProperty("user.dir")+"/src/musics/";  //음악 상대 경로
	Image noteImage = new ImageIcon(imagePath+"Note.png").getImage();  //노트 이미지
	int x, y = 150;  //노트의 x, y 위치
	String noteType;  //노트의 타입
	
	
	public Note(String noteType) {
		this.noteType = noteType;
		
		if(noteType.equals("S")) {
			this.x = 80; 
		}
		if(noteType.equals("D")) {
			this.x = 290; 
		}
		if(noteType.equals("F")) {
			this.x = 500; 
		}
		if(noteType.equals("J")) {
			this.x = 710; 
		}
		if(noteType.equals("K")) {
			this.x = 920; 
		}
		if(noteType.equals("L")) {
			this.x = 1130; 
		}
		
	}
	
	//노트를 그래픽에 그림
	public void drawNote(Graphics2D g){
		g.drawImage(noteImage, this.x, this.y,200,130, null);
	}
	
	//노트의 y좌표를 증가시켜줌
	@Override
	public void run() {
		//test
		while(true) {
			this.y += 20;
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}//while
	}
	
}
