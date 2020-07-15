import java.awt.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;

class fmGame extends JFrame implements KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GameSocket 	GS;
	private Game		G;
	private Image		Me;

	Image img1, img2, img3, Bulletimg, EnemyImg, fakeimg;
	int key[] = { 0, 0, 0, 0, 0 };
	Point pos = new Point(190, 830);
	Dimension size;
	Graphics fake;





	public fmGame(GameSocket GS, Game G, String Airplane) {
		super("testing");
		setResizable(false);

		img1 = getToolkit().getImage("비행기1.png");// 이미지받기
		img2 = getToolkit().getImage("비행기2.png");
		img3 = getToolkit().getImage("비행기3.png");

		if(Airplane.equals("1")) {
			Me = img1;
		}
		else if(Airplane.equals("2")) {
			Me = img2;
		}
		else if(Airplane.equals("3")) {
			Me = img3;
		}
		Bulletimg = getToolkit().getImage("아군총알.png");
		EnemyImg = getToolkit().getImage("적1.png");

		addKeyListener(this);// 플레이속도
		PlayTime playtime = new PlayTime();
		Thread time1 = new Thread(playtime);
		time1.start();



		Music music2 = new Music();// 노래틀기 Thread
		Thread music = new Thread(music2);// music.start();
		music.start();
		// ----------------------
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(380, 900);
		setBackground(Color.gray);

		setVisible(true);
		size = getSize();
		fakeimg = createImage(380, 900);// 임의의 이미지만들기
		this.G = G;
		this.GS = GS;

	}

	class PlayTime implements Runnable {
		public void run() {
			long nowTime, drawTime;

			nowTime = System.currentTimeMillis();// 현제시간 받기

			drawTime = nowTime + 500;
			while (true) {
				nowTime = System.currentTimeMillis();

				if (drawTime < nowTime) {
					drawTime = nowTime + 10;// 0.1초마다 받기
					try {
						if(action()) {
							GS.Sendpos();
						}
						GS.SendBullet();
					}catch(Exception e ) {
						if(action()) {
							GS.Sendpos();
						}
						GS.SendBullet();
					}

					repaint();
				}
				/*
				if (sendTime < nowTime) {
					sendTime = nowTime + 20;
					GS.Sendpos();
				}
				 */
			
			}
		}
	}
	public void paint(Graphics g) {

		if (fakeimg == null) {
			return;
		}

		fake = fakeimg.getGraphics();
		if (fake == null) {
			return;
		}
		fake.fillRect(0, 0, size.width, size.height);	// 잔상지우기
		fake.setColor(Color.black);						// 배경 검은색

		fake.drawImage(Me, pos.x, pos.y, this);		// 유저

		fake.setColor(Color.white);// 하얀글씨 아래글,위치
		fake.drawString("방향키=이동       스페이스바=공격", 100, 400);
		fake.setColor(Color.white);// 하얀글씨 아래글,위치
		String life2 = Integer.toString(G.life);
		fake.drawString("life: " + life2, 320, 60);
		fake.setColor(Color.white);// 하얀글씨 아래글,위치
		//String speed2 = Long.toString(speed);
		//fake.drawString("seed:" + speed2, 260, 60);
		fake.setColor(Color.white);// 하얀글씨 아래글,위치
		String kill2 = Long.toString(G.kill);
		fake.drawString("kill:" + kill2, 220, 60);

		// ------------------

		for (int i = 0; i < G.enemy.size(); i++) {// 적만큼 for문
			fake.drawImage(EnemyImg, G.enemy.get(i).pos.x, G.enemy.get(i).pos.y, this);// 총알
		}
		for (int i = 0; i < G.bullet.size(); i++) {// 적만큼 for문
			fake.drawImage(Bulletimg, G.bullet.get(i).pos.x, G.bullet.get(i).pos.y, this);// 총알
		}


		if (G.team.Img.equals("1")) {
			fake.drawImage(img1, G.team.pos.x, G.team.pos.y, this);// 적그리기
		} else if (G.team.Img.equals("2")) {
			fake.drawImage(img2, G.team.pos.x, G.team.pos.y, this);// 적그리기
		} else if (G.team.Img.equals("3")) {
			fake.drawImage(img3, G.team.pos.x, G.team.pos.y, this);// 적그리기
		}
		g.drawImage(fakeimg, 0, 0, this);// gui창에 덮어쓰기

	}
	class Music implements Runnable {// http://blog.naver.com/PostView.nhn?blogId=helloworld8&logNo=220076589506
		public void run() {

			try {
				AudioInputStream ais = AudioSystem.getAudioInputStream(new File("Alan Walker - Fade [NCS Release].wav"));//파일 이름넣기
				Clip clip =AudioSystem.getClip();
				clip.stop();
				clip.open(ais);
				clip.start();
			}catch(Exception e) {}


		}

	}

	public boolean action() {
		if (key[0] == 1) {
			if (pos.y <= 35) {
			}
			else {
				pos.y -= 4;
			}
			return true;
		}
		if (key[1] == 1) {
			if (pos.x >= 320) {
			} else {
				pos.x += 4;
			}
			return true;
		}
		if (key[2] == 1) {
			if (pos.y >= 840) {
			} else {
				pos.y += 4;
			}
			return true;
		}
		if (key[3] == 1) {
			if (pos.x <= 10) {
			}
			else {
				pos.x -= 4;
			}
			return true;
		}
		return false;
	}



	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			key[0] = 1;
			// system.out.println("위");
			break;
		case KeyEvent.VK_RIGHT:
			key[1] = 1;
			// system.out.println("오른쪽");
			break;
		case KeyEvent.VK_DOWN:
			key[2] = 1;
			// system.out.println("아래");
			break;
		case KeyEvent.VK_LEFT:
			key[3] = 1;
			// system.out.println("왼쪽");
			break;
		case KeyEvent.VK_SPACE:
			key[4] = 1;
			// system.out.println("총알");
			break;
		}

	}

	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			key[0] = 0;
			break;
		case KeyEvent.VK_RIGHT:
			key[1] = 0;
			break;
		case KeyEvent.VK_DOWN:
			key[2] = 0;
			break;
		case KeyEvent.VK_LEFT:
			key[3] = 0;
			break;
		case KeyEvent.VK_SPACE:
			key[4] = 0;

			break;
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}