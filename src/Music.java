
public class Music extends Thread{
	private String singer;  //가수
	private String title;  //제목
	static Music music;
	
	public Music(String singer, String title) {  //생성자
		this.singer = singer;
		this.title = title;
	}
	//Getter Setter
	public String getSinger() {
		return singer;
	}
	public void setSinger(String singer) {
		this.singer = singer;	
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}