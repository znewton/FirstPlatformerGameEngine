import java.awt.*;

public class EnvObj{
	
	public EnvObj(int xx, int yy, int w, int h, String n, Color c, boolean col){
		x=xx;
		y=yy;
		width=w;
		height=h;
		name=n;
		color =c;
		collision=col;
		hitbox = new Hitbox(x, y, width, height);
	}
	
	/*---------DATA---------*/
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public int getWidth(){
		return width;
	}
	public int getHeight(){
		return height;
	}
	public String getName(){
		return name;
	}
	public boolean collidable(){
		return collision;
	}
	public Hitbox getHitbox(){
		return hitbox;
	}
	public void setXY(int xx, int yy){
		x=xx;
		y=yy;
		hitbox.setX(x);
		hitbox.setY(y);
	}
	
	public int getCamX(){
		return cameraX;
	}
	public int getCamY(){
		return cameraY;
	}
	
	/*---------GRAPHICS----------*/
	public void draw(Graphics g){
		g.setColor(color);
		g.fillRect(x-cameraX,y-cameraY,width,height);
	}
	public void moveDraw(int xx, int yy){
		cameraX=xx;
		cameraY=yy;
	}
	
	
	
	Color color;
	boolean collision;
	String name;
	int x;
	int cameraX;
	int y;
	int cameraY;
	int width;
	int height;
	Hitbox hitbox;
}