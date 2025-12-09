import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class PowerUp 
{
    public int x, y, size = 20;
    public int type; 

    private Pong pong;

    public PowerUp(Pong pong)
    {
        this.pong = pong;
        Random r = new Random();
        x = r.nextInt(pong.width - size);
        y = r.nextInt(pong.height - size);
        type = r.nextInt(2);
    }

    public void render(Graphics g)
    {
        if(type == 0)
            g.setColor(Color.RED);
        else
            g.setColor(Color.GREEN);

        g.fillOval(x, y, size, size);
    }

    public boolean checkCollision(Ball b)
    {
        return b.x < x + size && b.x + b.width > x &&
               b.y < y + size && b.y + b.height > y;
    }
}
