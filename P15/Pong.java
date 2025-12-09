import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.awt.FontMetrics;

import javax.swing.JFrame;
import javax.swing.Timer;

public class Pong implements ActionListener, KeyListener
{

	public static Pong pong;
	public int width = 700, height = 700;
	public Renderer renderer;
	public Paddle player1;
	public Paddle player2;
	public Ball ball;
	public boolean bot = false, selectingDifficulty;
	public boolean w, s, up, down;
	public int gameStatus = 0, scoreLimit = 7, playerWon; // 0 = Rules (Halaman aturan), 1 = Menu, 2 = Playing (Main), 3 = Paused, 4 = Game Over
	public int botDifficulty, botMoves, botCooldown = 0;
	public Random random;
	public JFrame jframe;
	public PowerUp powerUp;
	public Campaign campaign = new Campaign();
	
	public Pong()
	{
		Timer timer = new Timer(20, this);
		random = new Random();

		jframe = new JFrame("Pong");

		renderer = new Renderer();

		jframe.setSize(width + 15, height + 35);
		jframe.setVisible(true);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.add(renderer);
		jframe.addKeyListener(this);

		timer.start();
	}

	public void start()
	{
		gameStatus = 2;
		player1 = new Paddle(this, 1);
		player2 = new Paddle(this, 2);
		ball = new Ball(this);
		powerUp = new PowerUp(this);
	}

	public void update()
	{
		if (player1.score >= scoreLimit)
		{
			playerWon = 1;
			gameStatus = 4;
		}

		if (player2.score >= scoreLimit)
		{
			gameStatus = 3;
			playerWon = 2;
		}

		if (w)
		{
			player1.move(true);
		}
		if (s)
		{
			player1.move(false);
		}

		if (!bot)
		{
			if (up)
			{
				player2.move(true);
			}
			if (down)
			{
				player2.move(false);
			}
		}
		else
		{
			if (botCooldown > 0)
			{
				botCooldown--;

				if (botCooldown == 0)
				{
					botMoves = 0;
				}
			}

			if (botMoves < 10)
			{
				if (player2.y + player2.height / 2 < ball.y)
				{
					player2.move(false);
					botMoves++;
				}

				if (player2.y + player2.height / 2 > ball.y)
				{
					player2.move(true);
					botMoves++;
				}

				if (botDifficulty == 0)
				{
					botCooldown = 20;
				}
				if (botDifficulty == 1)
				{
					botCooldown = 15;
				}
				if (botDifficulty == 2)
				{
					botCooldown = 10;
				}
			}
		}

		ball.update(player1, player2);

                if (powerUp != null && powerUp.checkCollision(ball))
                {
                    if (powerUp.type == 0)
                {
                    if (ball.motionX < 10 && ball.motionY < 10)
                    {
                        ball.motionX *= 2;
                        ball.motionY *= 2;
                    }
                }
            
                else if (powerUp.type == 1)
                {
                    if (player1.height < 400)
                    {
                        player1.height += 40;
                        player2.height += 40;
                    }
                }

                
                    powerUp = new PowerUp(this); 
                }
                
                if (player1.score >= campaign.getScoreTarget() || 
                    player2.score >= campaign.getScoreTarget())
                {
                    campaign.nextLevel();
                    start(); 
                }

	}

	public void render(Graphics2D g)
        {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, width, height);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
            // RULES SCREEN
            if (gameStatus == 0)
            {
                drawRules(g);
            }
        
            // MAIN MENU
            if (gameStatus == 1)
            {
                g.setColor(Color.WHITE);
                g.setFont(new Font("Arial", Font.BOLD, 30));
            
                FontMetrics fm = g.getFontMetrics();
            
                String text1 = "Press SPACE to Play";
                String text2 = "Press B to Play with Bot";
            
                int x1 = (width - fm.stringWidth(text1)) / 2;
                int x2 = (width - fm.stringWidth(text2)) / 2;
            
                int y = height / 2;
            
                g.drawString(text1, x1, y);
                g.drawString(text2, x2, y + 40);
                        }
        
            // PAUSED
            if (gameStatus == 3)
            {
                g.setColor(Color.WHITE);
                g.setFont(new Font("Arial", 1, 50));
                g.drawString("PAUSED", width / 2 - 100, height / 2);
            }
        
            // GAME PLAY
            if (gameStatus == 2)
            {
                g.setColor(Color.WHITE);
                g.drawLine(width / 2, 0, width / 2, height);
        
                g.setFont(new Font("Arial", 1, 50));
                g.drawString(String.valueOf(player1.score), width / 2 - 90, 50);
                g.drawString(String.valueOf(player2.score), width / 2 + 65, 50);
        
                player1.render(g);
                player2.render(g);
                ball.render(g);
        
                if (powerUp != null)
                {
                    powerUp.render(g);
                }
            }
        
            // GAME OVER
            if (gameStatus == 4)
            {
                g.setColor(Color.WHITE);
                g.setFont(new Font("Arial", 1, 50));
                g.drawString("GAME OVER", width / 2 - 160, 100);
                g.drawString("Player " + playerWon + " Wins!", width / 2 - 200, 180);
        
                g.setFont(new Font("Arial", 1, 30));
                g.drawString("Press SPACE to Return to Menu", width / 2 - 230, height / 2);
            }
        }

	
	public void drawRules(Graphics2D g)
        {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", 1, 22));
        
            int y = 150;
        
            FontMetrics fm = g.getFontMetrics();
            String title = "=== GAME RULES ===";
            int x = (width - fm.stringWidth(title)) / 3;
            g.drawString(title, x, y); y += 40;
            g.drawString("Player 1: W = Up, S = Down", 150, y); y += 30;
            g.drawString("Player 2: UP = Up, DOWN = Down", 150, y); y += 30;
            g.drawString("SPACE = Start / Pause / Resume", 150, y); y += 30;
            g.drawString("ESC = Back to Menu", 150, y); y += 30;
            g.drawString("B = Play with Bot", 150, y); y += 30;
            g.drawString("Power-Up:", 150, y); y += 25;
            g.drawString("- Red   = Speed Boost", 180, y); y += 25;
            g.drawString("- Green = Bigger Paddle", 180, y);
            
            g.setFont(new Font("Arial", 1, 30));
            g.drawString("Press SPACE to Continue", width/2 - 200, height - 100);
        }
        
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (gameStatus == 2)
		{
			update();
		}

		renderer.repaint();
	}

	public static void main(String[] args)
	{
		pong = new Pong();
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		int id = e.getKeyCode();

		if (id == KeyEvent.VK_W)
		{
			w = true;
		}
		else if (id == KeyEvent.VK_S)
		{
			s = true;
		}
		else if (id == KeyEvent.VK_UP)
		{
			up = true;
		}
		else if (id == KeyEvent.VK_DOWN)
		{
			down = true;
		}
		else if (id == KeyEvent.VK_RIGHT)
		{
			if (selectingDifficulty)
			{
				if (botDifficulty < 2)
				{
					botDifficulty++;
				}
				else
				{
					botDifficulty = 0;
				}
			}
			else if (gameStatus == 0)
			{
				scoreLimit++;
			}
		}
		else if (id == KeyEvent.VK_LEFT)
		{
			if (selectingDifficulty)
			{
				if (botDifficulty > 0)
				{
					botDifficulty--;
				}
				else
				{
					botDifficulty = 2;
				}
			}
			else if (gameStatus == 0 && scoreLimit > 1)
			{
				scoreLimit--;
			}
		}
		else if (id == KeyEvent.VK_ESCAPE && (gameStatus == 2 || gameStatus == 3))
		{
			gameStatus = 0;
		}
		else if (id == KeyEvent.VK_B && gameStatus == 1)
		{
			bot = true;
			selectingDifficulty = true;
			start(); 
		}
		else if (id == KeyEvent.VK_SPACE)
                {
                    if (gameStatus == 0)
                    {
                        gameStatus = 1; // Rules to Menu
                    }
                    else if (gameStatus == 1)
                    {
                        bot = false;
                        start();        // Menu to Game
                    }
                    else if (gameStatus == 2)
                    {
                        gameStatus = 3; // Pause
                    }
                    else if (gameStatus == 3)
                    {
                        gameStatus = 2; // Resume
                    }
                    else if (gameStatus == 4)
                    {
                        gameStatus = 1; // Back to Menu
                    }
                }

	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		int id = e.getKeyCode();

		if (id == KeyEvent.VK_W)
		{
			w = false;
		}
		else if (id == KeyEvent.VK_S)
		{
			s = false;
		}
		else if (id == KeyEvent.VK_UP)
		{
			up = false;
		}
		else if (id == KeyEvent.VK_DOWN)
		{
			down = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e)
	{

	}

}
