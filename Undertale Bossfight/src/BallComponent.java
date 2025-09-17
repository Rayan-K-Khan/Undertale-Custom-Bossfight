//Rayan Khan
//Mr. Segall
//AP Comp Sci Period 9
//June 5, 2024

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.MouseListener;
import java.util.Random;

public class BallComponent extends JComponent implements Runnable, KeyListener, MouseListener
{
	private static final long serialVersionUID = 1L;
	protected int x_pos, obsXPos, obsXPos2;
	protected int x_speed, obsXSpeed, obsXSpeed2;
	protected int y_pos, obsYPos, obsYPos2;
	protected int y_speed, obsYSpeed, obsYSpeed2;
	protected int fWidth;
	protected int fHeight;
	protected BufferedImage heart, imageBG, imageBox, fatherTime, clock, imageGreenBG, imageGameOver, imageIFrame, imageAttackMeter;
	protected BufferedImage hourglass, squareClock, stopwatch, alarmClock, attackMeterBox, buttons, attackBar;
	protected BufferedImage imageCoward, imageFunky, GameOver, GameWin;
	Random r = new Random();
	protected StopWatch w;
	
	JPanel hpBar, hpBar2, playerPanel, buttonPanel, hpPanel, hpPanel2, hpBoss, buttonPanel2, buttonPanel3, buttonPanel4, bossPanel, itemPanel, winPanel;
	JLabel hpNum, hpText, background, hpBossNum, itemLabel, bossLabel, winLabel;
	JButton button, buttonHP, button2, button3, button4, itemButton;
	JProgressBar bar, bar2;
	
	protected int diagXPos, diagXSpeed;
	protected int diagYPos, diagYSpeed;

	protected int horizXPos, horizXSpeed, horizYPos;
	protected int horizXPos2, horizXSpeed2, horizYPos2;
	
	protected boolean iFrame = false;
	protected int playerHp, bossHp;
	protected boolean attack = false;
	protected boolean stopBar = false;
	protected boolean playerTurn = false;
	protected boolean mercy = false, item=false, act=false, pie = false, enter = true, bossDead = false, win = false, playerDead = false;
	
	protected int pieNum = 3;
	
	//TIME OF BOSS ATTACKS
	protected int t = 20000;
	
	protected int barSpeed = 10;
	protected int barXPos = 225;
	
	private static final Font UNDERTALE = new Font("Press_Start_2P", Font.TYPE1_FONT, 24);
	
	StopWatch transition = new StopWatch();
	StopWatch i = new StopWatch();
	Attack d = new Attack();
	Merciful m = new Merciful();
	Food f = new Food();
	Action a = new Action();
	Pie p = new Pie();
	
	public BallComponent()
	{	
		//PLAYER HEALTH
		playerHp = 30;
		
		//BOSS HEALTH
		bossHp = 1000;	
		
		x_pos = 500;
		y_pos = 300;
		
		/*x_speed = 10;
		y_speed = 10;*/
		
		fWidth = 1000;
		fHeight = 600;
		
		//OBJECTS MOVE PATTERNS
		obsXPos = r.nextInt(170)+415;
		obsYPos = -100;
		obsYSpeed = r.nextInt(5)+2;
		
		obsXPos2 = r.nextInt(170)+415;
		obsYPos2 = 600;
		obsYSpeed2 = r.nextInt(5)+2;
		
		diagXSpeed = r.nextInt(2)+4;
		diagYSpeed = r.nextInt(2)+4;
		
		horizXSpeed = r.nextInt(5)+2;
		horizXPos = -100;
		horizYPos = r.nextInt(200)+200;
		
		horizXSpeed2 = r.nextInt(5)+2;
		horizXPos2 = 1100;
		horizYPos2 = r.nextInt(200)+200;
		
		w = new StopWatch();
		
		int x = r.nextInt(2);
		
		if (x == 0)
		{
			diagXPos = -100;
		}
		
		else
		{
			diagXPos = 700;
			diagXSpeed*=-1;
		}
		
		attack = false;
		
		addMouseListener(this);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		
		//WIN PANEL
		winPanel = new JPanel();
		winPanel.setBounds(0, 0, 1000, 600);
		winPanel.setBackground(Color.black);
		
		winLabel = new JLabel("YOU WIN!");
		winLabel.setBackground(Color.black);
		winLabel.setForeground(Color.white);
		winLabel.setPreferredSize(new Dimension(300, 500));
		winLabel.setFont(new Font("Press_Start_2P", Font.TYPE1_FONT, 60));
		
		//PLAYER BAR
		hpBar = new JPanel();
		hpBar.setBounds(450, 420, 80, 25);
		hpBar.setBackground(Color.black);
		add(hpBar);	
		
		bar = new JProgressBar(0, 30);
		bar.setPreferredSize(new Dimension(80, 25));
		hpBar.add(bar);	
		bar.setValue(30);
		bar.setBackground(Color.red);
		bar.setForeground(Color.yellow);
		
		playerPanel = new JPanel();
		playerPanel.setBounds(450, 420, 30, 25);
		playerPanel.setBackground(Color.black);
		playerPanel.setLayout(new GridLayout(1, 2));
		add(playerPanel);
		
		//BOSS BAR
		hpBar2 = new JPanel();
		hpBar2.setBounds(410, 165, 200, 20);
		hpBar2.setBackground(Color.black);
		add(hpBar2);	
		
		bar2 = new JProgressBar(0, bossHp);
		bar2.setPreferredSize(new Dimension(200, 20));
		hpBar2.add(bar2);	
		bar2.setValue(bossHp);
		bar2.setBackground(Color.gray);
		bar2.setForeground(Color.green);
		
		bossPanel = new JPanel();
		bossPanel.setBounds(410, 50, 200, 80);
		bossPanel.setBackground(Color.white);
		add(bossPanel);	
		bossPanel.setOpaque(false);
		
		bossLabel = new JLabel();
		bossLabel.setText("");
		bossLabel.setFont(new Font("Press_Start_2P", Font.TYPE1_FONT, 70));
		bossLabel.setForeground(Color.red);
		bossPanel.add(bossLabel);
		
		//FIGHT BUTTON
		buttonPanel = new JPanel();
		buttonPanel.setBounds(111, 470, 150, 80);
		buttonPanel.setBackground(Color.black);
		
		buttonPanel.setOpaque(false);	
		
		add(buttonPanel);
		
		button = new JButton("");
		button.setPreferredSize(new Dimension(150, 60));
		button.setBackground(Color.red);
		button.setForeground(Color.orange);
		
		button.setOpaque(false);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
	
		button.setFocusPainted(false);
		button.addActionListener(d);
		buttonPanel.add(button);
		
		//ACT BUTTON
		buttonPanel2 = new JPanel();
		buttonPanel2.setBounds(111, 470, 570, 80);
		buttonPanel2.setBackground(Color.black);
		
		buttonPanel2.setOpaque(false);	
		
		add(buttonPanel2);
		
		button2 = new JButton("");
		button2.setPreferredSize(new Dimension(150, 60));
		button2.setBackground(Color.red);
		button2.setForeground(Color.orange);
		
		button2.setOpaque(false);
		button2.setContentAreaFilled(false);
		button2.setBorderPainted(false);
	
		button2.setFocusPainted(false);
		button2.addActionListener(a);
		buttonPanel2.add(button2);
		
		//ITEM BUTTON
		buttonPanel3 = new JPanel();
		buttonPanel3.setBounds(111, 470, 1005, 80);
		buttonPanel3.setBackground(Color.black);
		
		buttonPanel3.setOpaque(false);	
		
		add(buttonPanel3);
		
		button3 = new JButton("");
		button3.setPreferredSize(new Dimension(150, 60));
		button3.setBackground(Color.red);
		button3.setForeground(Color.orange);
		
		button3.setOpaque(false);
		button3.setContentAreaFilled(false);
		button3.setBorderPainted(false);
	
		button3.setFocusPainted(false);
		button3.addActionListener(f);
		buttonPanel3.add(button3);
		
		//MERCY BUTTON
		buttonPanel4 = new JPanel();
		buttonPanel4.setBounds(111, 470, 1430, 80);
		buttonPanel4.setBackground(Color.black);
		add(buttonPanel4);
		
		button4 = new JButton("");
		button4.setPreferredSize(new Dimension(150, 60));
		button4.setBackground(Color.red);
		button4.setForeground(Color.orange);
		
		button4.setOpaque(false);
		button4.setContentAreaFilled(false);
		button4.setBorderPainted(false);
		
		button4.setFocusPainted(false);
		button4.addActionListener(m);
		buttonPanel4.add(button4);
		buttonPanel4.setOpaque(false);
		
		//HP PLAYER
		hpPanel = new JPanel();
		hpPanel.setBounds(540, 420, 80, 30);
		hpPanel.setBackground(Color.black);
		hpPanel.setLayout(new GridLayout(1, 4));
		add(hpPanel);
		
		hpNum = new JLabel(playerHp + " / 30");
		hpNum.setFont(UNDERTALE);
		hpNum.setForeground(Color.white);
		hpPanel.add(hpNum);
		
		hpPanel2 = new JPanel();
		hpPanel2.setBounds(410, 420, 40, 30);
		hpPanel2.setBackground(Color.black);
		hpPanel2.setLayout(new GridLayout(1, 4));
		add(hpPanel2);
		
		hpText = new JLabel("HP");
		hpText.setFont(UNDERTALE);
		hpText.setForeground(Color.white);
		hpPanel2.add(hpText);
		
		//ITEM PIE PANEL
		itemPanel = new JPanel();
		itemPanel.setBounds(436, 280, 150, 50);
		itemPanel.setBackground(Color.black);
		itemPanel.setLayout(new GridLayout(1, 4));
		add(itemPanel);
		itemPanel.setOpaque(false);
			
		itemButton = new JButton("");
		itemButton.setPreferredSize(new Dimension(150, 60));
		itemButton.setBackground(Color.black);
		itemButton.setForeground(Color.white);
		
	
		itemButton.setOpaque(false);
		itemButton.setContentAreaFilled(false);
		itemButton.setBorderPainted(false);
		
		itemButton.setFocusPainted(false);
		itemButton.addActionListener(p);
		itemPanel.add(itemButton);
	
		try
		{			
		
			//OBJECT IMAGES
			clock = ImageIO.read(Test.class.getResourceAsStream("obs1.png"));
			hourglass = ImageIO.read(Test.class.getResourceAsStream("obs4.png"));
			squareClock = ImageIO.read(Test.class.getResourceAsStream("obs3.png"));
			stopwatch = ImageIO.read(Test.class.getResourceAsStream("obs5.png"));
			alarmClock = ImageIO.read(Test.class.getResourceAsStream("obs2.png"));
			
			//GAME IMAGES
			imageBG = ImageIO.read(Test.class.getResourceAsStream("bg.png"));
			imageBox = ImageIO.read(Test.class.getResourceAsStream("undertale battle box.png"));
			heart = ImageIO.read(Test.class.getResourceAsStream("undertale heart.png"));
			imageGreenBG = ImageIO.read(Test.class.getResourceAsStream("greenrects.jpg"));
			fatherTime = ImageIO.read(Test.class.getResourceAsStream("monster.png"));
			imageIFrame = ImageIO.read(Test.class.getResourceAsStream("iframe heart.png"));
			imageAttackMeter = ImageIO.read(Test.class.getResourceAsStream("attack meter.png"));
			attackMeterBox = ImageIO.read(Test.class.getResourceAsStream("attackMeterBox.png"));
			buttons = ImageIO.read(Test.class.getResourceAsStream("undertalebuttons.jpg"));
			attackBar = ImageIO.read(Test.class.getResourceAsStream("attackBar.png"));
			
			//SPECIAL IMAGES
			imageCoward = ImageIO.read(Test.class.getResourceAsStream("coward.jpg"));
			imageFunky = ImageIO.read(Test.class.getResourceAsStream("funky.jpg"));
			GameOver = ImageIO.read(Test.class.getResourceAsStream("game over.png"));
			GameWin = ImageIO.read(Test.class.getResourceAsStream("youwin.png"));
		
			
		}
		catch (IOException | IllegalArgumentException e)
		{
			e.printStackTrace();
		}
			
	}
	
	public boolean gameOver()
	{
		if (playerHp <= 0)
		{
			return true;
		}
		
		if (bossHp <= 0)
		{
			return true;
		}
		
		return false;
	}
	
	public void damageTaken()
	{
		playerHp-=3;
		bar.setValue(playerHp);	
		hpNum.setText(playerHp + " / 30");
	}
	
	public void bossDamage(int num)
	{
		bossHp-=num;
		bar2.setValue(bossHp);
	}
	
	public void attackBoss()
	{
		attack = true;
	}
	
	public void Mercy()
	{
		mercy = true;
	}
	
	public void Act()
	{
		act = true;
	}
	
	public void Item()
	{
		item = true;
	}
	
	public void eatPie()
	{
		pie = true;
	}
	
	public void regenHealth()
	{
		if (pieNum > 0)
		{
			playerHp+=10;
			
			if (playerHp > 30)
			{
				playerHp = 30;
			}
			
			bar.setValue(playerHp);	
			hpNum.setText(playerHp + " / 30");		
			pieNum--;
			itemButton.setOpaque(false);
			itemButton.setEnabled(false);
			button.setEnabled(false);
			button2.setEnabled(false);
			button3.setEnabled(false);
			button4.setEnabled(false);
		}
	}
	
	public class Attack implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{		
			attackBoss();
		}
	}
	
	public class Action implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{		
			Act();
		}
	}
	
	public class Merciful implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{		
			Mercy();
		}
	}
	
	public class Food implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{		
			Item();
		}
	}
	
	public class Pie implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{		
			eatPie();
		}
	}
	
	public void startAnimation()
	{
 		Thread t = new Thread(this);
		t.start();
	}
	
	public Rectangle bounds(int x, int y)
	{
		return (new Rectangle(x, y, 65, 65));
	}
	
	public Rectangle boundsHeart(int x, int y)
	{
		return (new Rectangle(x, y, 20, 20));
	}
	
	public Rectangle moveBounds(int x, int y)
	{
		return (new Rectangle(x, y));
	}
	
	public void paintComponent(Graphics g)
	{
		//BOSS' TURN
		
		if (bossHp<=0)
		{
			win = true;
		}		
		
		if (w.getElapsedTime()>2000)
		{
			itemButton.setOpaque(false);
			itemButton.setContentAreaFilled(false);
			itemButton.setBorderPainted(false);
			itemButton.setText("");
			bossLabel.setText("");
		}
		
		Graphics2D g2dheart = (Graphics2D) g;
		Rectangle h = boundsHeart(x_pos, y_pos);
		
		if(w.getElapsedTime()<t)
		{
			button.setEnabled(false);
			button2.setEnabled(false);
			button3.setEnabled(false);
			button4.setEnabled(false);
			itemButton.setEnabled(false);
			attack = false;
		}
		
		if (w.getElapsedTime() > t)
		{
			h = null;
			button.setEnabled(true);
			button2.setEnabled(true);
			button3.setEnabled(true);
			button4.setEnabled(true);
		}
		
		if (iFrame == false)
		{
			g2dheart.fillRect(x_pos, y_pos, 20, 20);
			g2dheart.setColor(Color.GREEN);
		}
		
		Graphics2D g2dobs1 = (Graphics2D) g;
		Graphics2D g2dobs2 = (Graphics2D) g;
		Graphics2D g2dobs3 = (Graphics2D) g;
		Graphics2D g2dobs4 = (Graphics2D) g;
		Graphics2D g2dobs5 = (Graphics2D) g;
		
		if (w.getElapsedTime() < 2000)
		{
			g2dobs1.fillRect(-1000, -1000, 65, 65);
			g2dobs2.fillRect(-1000, -1000, 65, 65);
			g2dobs3.fillRect(-1000, -1000, 65, 65);
			g2dobs4.fillRect(-1000, -1000, 65, 65);
			g2dobs5.fillRect(-1000, -1000, 65, 65);
		}
		
		if (w.getElapsedTime() < t && w.getElapsedTime() > 2000 && win==false)
		{
		g2dobs1.fillRect(obsXPos, obsYPos, 65, 65);
		g2dobs1.setColor(Color.YELLOW);
			
		g2dobs2.fillRect(obsXPos2, obsYPos2, 65, 65);
		g2dobs2.setColor(Color.YELLOW);
		
		g2dobs3.fillRect(horizXPos, horizYPos, 65, 65);
		g2dobs3.setColor(Color.YELLOW);
		
		g2dobs4.fillRect(horizXPos2, horizYPos2, 65, 65);
		g2dobs4.setColor(Color.YELLOW);
		
		g2dobs5.fillRect(diagXPos, diagYPos, 65, 65);
		g2dobs5.setColor(Color.RED);
		
		}
			
		//COLLISION
	if (w.getElapsedTime() > 2000 && win==false)
	{	
		Rectangle obs1 = bounds(obsXPos, obsYPos);
		Rectangle obs2 = bounds(obsXPos2, obsYPos2);
		Rectangle obs3 = bounds(horizXPos, horizYPos);
		Rectangle obs4 = bounds(horizXPos2, horizYPos2);
		Rectangle obs5 = bounds(diagXPos, diagYPos);
		
		if(h != null && (h.intersects(obs1) || h.intersects(obs2) || h.intersects(obs3) || h.intersects(obs4) || h.intersects(obs5)))
		{
			if (iFrame == false)
			{
				damageTaken();
				iFrame = true;
				g2dheart.clearRect(x_pos, y_pos, 20, 20);
				i.start();
			}
			
		}
		
		if (i.getElapsedTime() > 2000)
		{	
			i.reset();
			iFrame = false;
		}
	}
	
		g.drawImage(imageBG, 0, 0, null);
		
		if (w.getElapsedTime()<t && win==false)
		{
			g.drawImage(imageBox, 410, 210, null);
		}
		
		if (gameOver()== false && win == false)
		{
		g.drawImage(imageGreenBG, 150, 0, null);
		
		g.drawImage(fatherTime, 410, 0, null);
		
		g.drawImage(buttons, 105, 470, null);
		}
			
		if (iFrame == false && w.getElapsedTime()<t && win==false)
		{
			g.drawImage(heart, x_pos, y_pos, null);
		}
		
		if(iFrame == true && w.getElapsedTime()<t && win==false)
		{
			g.drawImage(imageIFrame, x_pos, y_pos, null);
		}
			
		if (w.getElapsedTime() < t && w.getElapsedTime() > 2000 && win==false)
		{
			g.drawImage(clock, obsXPos, obsYPos, null);
		
			g.drawImage(hourglass, diagXPos, diagYPos, null);
		
			g.drawImage(squareClock, obsXPos2, obsYPos2, null);
			
			g.drawImage(stopwatch, horizXPos, horizYPos, null);
			
			g.drawImage(alarmClock, horizXPos2, horizYPos2, null);
		}
		
		//LOSE GAME
		if (gameOver() && win == false)
		{	
			g.drawImage(GameOver, 0, 0, null);
			
			buttonPanel.setOpaque(false);
			button.setOpaque(false);
			button.setContentAreaFilled(false);
			button.setBorderPainted(false);
			
			buttonPanel2.setOpaque(false);
			button2.setOpaque(false);
			button2.setContentAreaFilled(false);
			button2.setBorderPainted(false);
			
			buttonPanel3.setOpaque(false);
			button3.setOpaque(false);
			button3.setContentAreaFilled(false);
			button3.setBorderPainted(false);
			
			buttonPanel4.setOpaque(false);
			button4.setOpaque(false);
			button4.setContentAreaFilled(false);
			button4.setBorderPainted(false);
			
			itemPanel.setOpaque(false);
			itemButton.setOpaque(false);
			itemButton.setContentAreaFilled(false);
			itemButton.setBorderPainted(false);
			
			hpBar.setOpaque(false);
			hpBar2.setOpaque(false);
			playerPanel.setOpaque(false);
			bar.setOpaque(false);
			bar.setBorderPainted(false);
			hpNum.setText("");
			hpText.setText("");
			hpPanel.setOpaque(false);
			
			hpPanel2.setOpaque(false);
			bar2.setOpaque(false);
			bar2.setBorderPainted(false);
			bar2.setValue(0);
			playerDead = true;
		}
		
		//WIN GAME
		if (gameOver() && win == true && w.getElapsedTime()>2000)
		{	
			add(winPanel);
			winPanel.add(winLabel);
			
			buttonPanel.setOpaque(false);
			button.setOpaque(false);
			button.setContentAreaFilled(false);
			button.setBorderPainted(false);
			
			buttonPanel2.setOpaque(false);
			button2.setOpaque(false);
			button2.setContentAreaFilled(false);
			button2.setBorderPainted(false);
			
			buttonPanel3.setOpaque(false);
			button3.setOpaque(false);
			button3.setContentAreaFilled(false);
			button3.setBorderPainted(false);
			
			buttonPanel4.setOpaque(false);
			button4.setOpaque(false);
			button4.setContentAreaFilled(false);
			button4.setBorderPainted(false);
			
			itemPanel.setOpaque(false);
			itemButton.setOpaque(false);
			itemButton.setContentAreaFilled(false);
			itemButton.setBorderPainted(false);
			
			hpBar.setOpaque(false);
			playerPanel.setOpaque(false);
			bar.setOpaque(false);
			bar.setBorderPainted(false);
			hpNum.setText("");
			hpText.setText("");
			hpPanel.setOpaque(false);
			bar.setValue(0);
			
			hpPanel2.setOpaque(false);
			bar2.setOpaque(false);
			bar2.setBorderPainted(false);
			bar2.setValue(0);
			bossDead = true;
		}	
		
		//PLAYER'S TURN	
		if (w.getElapsedTime() >= t && win==false)
		{
			
			g.drawImage(clock, -300, -300, null);
			
			g.drawImage(hourglass, -300, -300, null);
			
			g.drawImage(squareClock, -300, -300, null);
			
			g.drawImage(stopwatch, -300, -300, null);
			
			g.drawImage(alarmClock, -300, -300, null);
			
			h = null;
			
			playerTurn = true;
			
			
			g.drawImage(attackMeterBox, 210, 210, null);
			
			//MERCY BUTTON (FATHER TIME CALLS YOU A COWARD)
			if (mercy)
			{
				itemButton.setOpaque(false);
				itemButton.setContentAreaFilled(false);
				itemButton.setBorderPainted(false);
				
				g.drawImage(imageCoward, 290, 282, null);
				
				transition.start();
				
				if (transition.getElapsedTime()>2000)
				{
					transition.stop();
					transition.reset();
					mercy = false;
				}
			}
			
			//ACT BUTTON
			if (act)
			{
				itemButton.setOpaque(false);
				itemButton.setContentAreaFilled(false);
				itemButton.setBorderPainted(false);
				
				g.drawImage(imageFunky, 262, 290, null);
				
				transition.start();
				
				if (transition.getElapsedTime()>2000)
				{
					transition.stop();
					transition.reset();
					act = false;
				}
			}
		
			//ITEM BUTTON
			if (item)
			{
				
				itemButton.setEnabled(true);	
				itemButton.setOpaque(true);
				itemButton.setContentAreaFilled(true);
				itemButton.setBorderPainted(true);
				
				//PIES HEAL 10 HP
				if (pieNum>0)
				{
					button.setEnabled(false);
					button2.setEnabled(false);
					button3.setEnabled(false);
					button4.setEnabled(false);
				}
				
				//PIES WILL RUN OUT AFTER 3 ARE CONSUMED
				if (pieNum == 0)
				{
					itemButton.setEnabled(false);
					button.setEnabled(true);
					button2.setEnabled(true);
					button3.setEnabled(true);
					button4.setEnabled(true);
					transition.start();
					
					if (transition.getElapsedTime() > 2000)
					{
						transition.stop();
						transition.reset();
						item = false;
					}
				}
				
				itemButton.setText("Pies:  " + pieNum + " / 3");
				
				if (pie && pieNum > 0)
				{
					regenHealth();							
					item = false;
					pie = false;
					playerTurn = false;
					w.stop();
					w.reset();
					w.start();			
				}
			}
			
			//ATTACK BUTTON
			if (attack)
			{
				itemButton.setOpaque(false);
				itemButton.setContentAreaFilled(false);
				itemButton.setBorderPainted(false);
				
				button.setEnabled(false);
				button2.setEnabled(false);
				button3.setEnabled(false);
				button4.setEnabled(false);
			
				if (playerTurn)
				{
					g.drawImage(imageAttackMeter, 225, 220, null);
					g.drawImage(attackBar, barXPos, 235, null);
				}
				
				//PLAYER MUST TIME ATTACK BAR BY CLICKING
				if (stopBar)
				{
					if (barXPos >= 498 && barXPos <= 506)
					{
						System.out.println("150 damage");
						
						bossLabel.setText(""+150);
						
						bossDamage(150);
						
						playerTurn = false;
						attack = false;			
						stopBar = false;
						barXPos = 225;
						barSpeed = 10;
						w.stop();
						w.reset();
						w.start();	
						
					}
					
					else if ((barXPos > 506 && barXPos <= 530) || (barXPos < 498 && barXPos >= 474))
					{
						System.out.println("100 damage");
						
						bossLabel.setText(""+100);
						
						bossDamage(100);
						
						playerTurn = false;
						attack = false;			
						stopBar = false;
						barXPos = 225;
						barSpeed = 10;
						w.stop();
						w.reset();
						w.start();	
					}
					
					else if ((barXPos > 530 && barXPos <= 615) || (barXPos < 474 && barXPos >= 389))
					{
						System.out.println("75 damage");
						
						bossLabel.setText(""+75);
						
						bossDamage(75);
						
						playerTurn = false;
						attack = false;			
						stopBar = false;
						barXPos = 225;
						barSpeed = 10;
						w.stop();
						w.reset();
						w.start();	
					}
					
					else
					{
						System.out.println("Miss");
						
						bossLabel.setText("MISS");
						
							playerTurn = false;
							attack = false;			
							stopBar = false;
							barXPos = 225;
							barSpeed = 10;
							w.stop();
							w.reset();
							w.start();	
					}
		
				}
							
			}
			
		}	
	}	
	
	//HEART MOVEMENT
	public void keyPressed(KeyEvent e)
	{
		System.out.println(e.getKeyCode());
		
		
		System.out.println(e.getKeyCode());
		if (e.getKeyCode() == 68 || e.getKeyCode() == 39)
		{
			x_pos+=5;
		}
		
		System.out.println(e.getKeyCode());
		if (e.getKeyCode() == 87 || e.getKeyCode() == 38)
		{
			y_pos-=5;
		}
		
		System.out.println(e.getKeyCode());
		if (e.getKeyCode() == 83 || e.getKeyCode() == 40)
		{
			y_pos+=5;
		}
		
		System.out.println(e.getKeyCode());
		if (e.getKeyCode() == 65 || e.getKeyCode() == 37)
		{
			x_pos-=5;
		}
	
	}
	
	public void keyReleased(KeyEvent e)
	{
	}
	
	public void keyTyped(KeyEvent e)
	{
	}
	
	//GAME WILL CONTINUE UNTIL PLAYER OR BOSS DIES
	public void run()
	{
		w.start();
	
		while (playerDead==false && bossDead == false)
		{
			
		if (w.getElapsedTime() > 2000)
		{			
			try
			{
				if (x_pos>=585)
				{
					//x_speed*=-1;
					x_pos = 585;
				}
				
				if (x_pos <=415)
				{
					//x_speed*=-1;
						
					x_pos = 415;
				}
				
				if (y_pos<=215)
				{
					//y_speed*=-1;
						y_pos = 215;
				}
				
				if (y_pos >= 385)
				{
					//y_speed*=-1;
						y_pos = 385;	
				}
				
			
				//x_pos+=x_speed;
				obsYPos+=obsYSpeed; //up
				
				horizXPos+=horizXSpeed; //right
				
				horizXPos2-=horizXSpeed2; //left
				
				obsYPos2-=obsYSpeed2; //down
				
				diagXPos+=diagXSpeed;//diagonal
				diagYPos+=diagYSpeed;//diagonal
				
				if (horizXPos >= 1010)
				{
					horizYPos = r.nextInt(200)+200;
					horizXSpeed = r.nextInt(5)+2;
					horizXPos = -100;
				}
				
				if (horizXPos2 <= -100)
				{
					horizYPos2 = r.nextInt(200)+200;
					horizXSpeed2 = r.nextInt(5)+2;
					horizXPos2 = 1100;
				}
				
				if (obsYPos >= 650)
				{
					obsXPos = r.nextInt(170)+415;
					obsYSpeed = r.nextInt(5)+2;
					obsYPos = -100;
				}
				
				if (obsYPos2 <= -100)
				{
					obsXPos2 = r.nextInt(170)+415;
					obsYSpeed2 = r.nextInt(5)+2;
					obsYPos2 = 600;
				}
				
				if (diagYPos <= 0 || diagYPos >= 510)
				{
					diagYSpeed*=-1;
				}
				
				
					
				if (diagXPos >= 1010 || diagXPos <= -100)
				{
					diagXSpeed = r.nextInt(2)+4;
					diagYSpeed = r.nextInt(2)+4;
					
					int x = r.nextInt(2);
					
					if (x == 0)
					{
						diagXPos = -100;
					}
					
					else
					{
						diagXPos = 1010;
						diagXSpeed*=-1;
					}
					
					diagYPos = r.nextInt(510);
					
					if (w.getElapsedTime() > t)
					{
						
					}
					
				}
				
				if (attack)
				{
					if(stopBar)
					{
						barSpeed = 0;
					}
					
					barXPos+=barSpeed;
					
					if (barXPos >= 780 || barXPos <= 220)
					{
						barSpeed*=-1;
					}
						
					
				}
					
				repaint();
				Thread.sleep(20);
				
			}
			catch (InterruptedException e)
			{
			
			}
		}
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	
		
	}

	@Override
	//ATTACK BAR CLICK DETECTOR
	public void mouseReleased(MouseEvent arg0) {
		System.out.println(x_pos + ", " + y_pos);
		
		if (attack)
		{
			stopBar = true;
		}	
	}
}