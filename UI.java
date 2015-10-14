import java.awt.*;

public class UI{
	public UI(int w, int h){
		width=w;
		height=h;
	}
	
	public void givePlayerStats(int h, int s){
		health=h;
		score=s;
	}
	
	public void drawUI(Graphics g){
		drawHealthBar(g);
		drawScore(g);
	}
	public void drawHealthBar(Graphics g){
		if(health>=50){
			g.setColor(new Color(255-255*(health-50)/50,220,0));
		} else if(health<50){
			g.setColor(new Color(255,220*health/50,0));
		}
		g.fill3DRect(width-200,0,health*2,25,true);
	}
	public void drawScore(Graphics g){
		g.setColor(new Color(200,100,0));
		g.drawString("SCORE:  X "+score,width-100,35);
	}
	
	int health;
	int score;
	int width;
	int height;
}