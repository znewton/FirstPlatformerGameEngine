import java.awt.*;

public class Enemy{
	public static final int movAdj=3/2;
	
	public Enemy(int xx, int yy, int w, int h, String n, Color c, String t, int d, int r, int f, int s){
		x=xx;
		y=yy;
		width=w;
		height=h;
		name=n;
		color=c;
		xStart=xx;
		type=t;
		damage=d;
		xRange=r;
		jumpFreq=f;
		xVel=s;
		
		hitbox=new Hitbox(xx,yy,w,h);
	}
	
	/*--------DATA---------*/
	public boolean isAlive(){
		return alive;
	}
	public boolean isRemovable(){
		return removable;
	}
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
		return true;
	}
	public Hitbox getHitbox(){
		return hitbox;
	}
	public int getDamage(){
		return damage;
	}
	public void setXY(int xx, int yy){
		x=xx;
		y=yy;
		hitbox.setX(x);
		hitbox.setY(y);
	}
	
	public void killed(){
		alive=false;
	}
	public void remove(){
		removable=true;
	}
	
	/*---------MOVEMENT--------*/
	public void move(){
		if(grounded){
			yVel=0;
		} else if(!grounded){
			if(yVel<yVelCap+20){
				yVel+=2;
			}
		}
		if(x+width>=xStart+xRange){
			right=false;
			left=true;
		} else if(x<=xStart){
			left=false;
			right=true;
		}
		if(type.equals("walk")){
			if(right){
				x+=xVel*movAdj;
			} else if(left){
				x-=xVel*movAdj;
			}
		} else if(type.equals("jump")){
			
		}
		y+=yVel*movAdj;
		hitbox.setX(x);
		hitbox.setY(y);
	}
	
	/*-----------GRAPHICS----------*/
	public void draw(Graphics g){
		g.setColor(color);
		//System.out.println("("+x+","+y+")");
		g.fillRect(x-cameraX,y-cameraY,width,height);
	}
	public void moveDraw(int xx, int yy){
		cameraX=xx;
		cameraY=yy;
	}
	
	/*--------COLLISIONS---------*/
	public void isGrounded(boolean n){
		grounded=n;
	}
	public void changeDir(String n){
		if(n.equals("right")){
			right=true;
			left=false;
		} else if(n.equals("left")){
			right=false;
			left=true;
		}
	}
	
	String type;
	String name;
	Hitbox hitbox;
	boolean alive=true;
	boolean removable=false;
	boolean right=true;
	boolean left=false;
	boolean grounded=false;
	Color color;
	int xRange;
	int xStart;
	int width;
	int height;
	int damage;
	int yVel;
	int yVelCap;
	int jumpFreq;
	int xVel;
	int x;
	int y;
	int cameraX;
	int cameraY;
	
}