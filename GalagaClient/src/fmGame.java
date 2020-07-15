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

		img1 = getToolkit().getImage("�����1.png");// �̹����ޱ�
		img2 = getToolkit().getImage("�����2.png");
		img3 = getToolkit().getImage("�����3.png");

		if(Airplane.equals("1")) {
			Me = img1;
		}
		else if(Airplane.equals("2")) {
			Me = img2;
		}
		else if(Airplane.equals("3")) {
			Me = img3;
		}
		Bulletimg = getToolkit().getImage("�Ʊ��Ѿ�.png");
		EnemyImg = getToolkit().getImage("��1.png");

		addKeyListener(this);// �÷��̼ӵ�
		PlayTime playtime = new PlayTime();
		Thread time1 = new Thread(playtime);
		time1.start();



		Music music2 = new Music();// �뷡Ʋ�� Thread
		Thread music = new Thread(music2);// music.start();
		music.start();
		// ----------------------
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(380, 900);
		setBackground(Color.gray);

		setVisible(true);
		size = getSize();
		fakeimg = createImage(380, 900);// ������ �̹��������
		this.G = G;
		this.GS = GS;

	}

	class PlayTime implements Runnable {
		public void run() {
			long nowTime, drawTime;

			nowTime = System.currentTimeMillis();// �����ð� �ޱ�

			drawTime = nowTime + 500;
			while (true) {
				nowTime = System.currentTimeMillis();

				if (drawTime < nowTime) {
					drawTime = nowTime + 10;// 0.1�ʸ��� �ޱ�
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
		fake.fillRect(0, 0, size.width, size.height);	// �ܻ������
		fake.setColor(Color.black);						// ��� ������

		fake.drawImage(Me, pos.x, pos.y, this);		// ����

		fake.setColor(Color.white);// �Ͼ�۾� �Ʒ���,��ġ
		fake.drawString("����Ű=�̵�       �����̽���=����", 100, 400);
		fake.setColor(Color.white);// �Ͼ�۾� �Ʒ���,��ġ
		String life2 = Integer.toString(G.life);
		fake.drawString("life: " + life2, 320, 60);
		fake.setColor(Color.white);// �Ͼ�۾� �Ʒ���,��ġ
		//String speed2 = Long.toString(speed);
		//fake.drawString("seed:" + speed2, 260, 60);
		fake.setColor(Color.white);// �Ͼ�۾� �Ʒ���,��ġ
		String kill2 = Long.toString(G.kill);
		fake.drawString("kill:" + kill2, 220, 60);

		// ------------------

		for (int i = 0; i < G.enemy.size(); i++) {// ����ŭ for��
			fake.drawImage(EnemyImg, G.enemy.get(i).pos.x, G.enemy.get(i).pos.y, this);// �Ѿ�
		}
		for (int i = 0; i < G.bullet.size(); i++) {// ����ŭ for��
			fake.drawImage(Bulletimg, G.bullet.get(i).pos.x, G.bullet.get(i).pos.y, this);// �Ѿ�
		}


		if (G.team.Img.equals("1")) {
			fake.drawImage(img1, G.team.pos.x, G.team.pos.y, this);// ���׸���
		} else if (G.team.Img.equals("2")) {
			fake.drawImage(img2, G.team.pos.x, G.team.pos.y, this);// ���׸���
		} else if (G.team.Img.equals("3")) {
			fake.drawImage(img3, G.team.pos.x, G.team.pos.y, this);// ���׸���
		}
		g.drawImage(fakeimg, 0, 0, this);// guiâ�� �����

	}
	class Music implements Runnable {// http://blog.naver.com/PostView.nhn?blogId=helloworld8&logNo=220076589506
		public void run() {

			try {
				AudioInputStream ais = AudioSystem.getAudioInputStream(new File("Alan Walker - Fade [NCS Release].wav"));//���� �̸��ֱ�
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
			// system.out.println("��");
			break;
		case KeyEvent.VK_RIGHT:
			key[1] = 1;
			// system.out.println("������");
			break;
		case KeyEvent.VK_DOWN:
			key[2] = 1;
			// system.out.println("�Ʒ�");
			break;
		case KeyEvent.VK_LEFT:
			key[3] = 1;
			// system.out.println("����");
			break;
		case KeyEvent.VK_SPACE:
			key[4] = 1;
			// system.out.println("�Ѿ�");
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