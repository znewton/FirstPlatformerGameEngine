import java.awt.*;
import javax.swing.*;

public class Player{
	public static final int HEIGHT = 50;
	public static final int WIDTH = 18;
	public static final int movAdj=3/2;
	
	
	public Player(int xx, int yy){
		x=xx;
		y=yy;
		hitbox = new Hitbox(x, y, WIDTH, HEIGHT);
	}
	/*moves in meters per second
	25 pixels=1 meter
	3 frame/50 milliseconds
	(1m/1s)*(25p/1m)*(1s/1000ms)*(50ms/3f);
	1 meter per second =  pixels per frame	
	*/
	
	/*-------DATA---------*/
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public int getXVel(){
		return xVel;
	}
	public int getYVel(){
		return yVel;
	}
	public int getMovAdj(){
		return movAdj;
	}
	public Hitbox getHitbox(){
		return hitbox;
	}
	public int getHealth(){
		return health;
	}
	public int getScore(){
		return score;
	}
	
	public void giveCamXY(int xx, int yy){
		cameraX=xx;
		cameraY=yy;
	}
	public void setXY(int xx,int yy){
		x=xx;
		y=yy;
	}
	
	
	
	/*--------GRAPHICS--------*/
	public void draw(Graphics g){
		g.setColor(new Color(0,50,200));
		//health-=1;
		g.fillRect(x-cameraX,y-cameraY,WIDTH,HEIGHT);
	}
	
	/*--------MOVEMENT--------*/
	public void move(){
		if(grounded){
			yVel=0;
			leftAcc=2;
			rightAcc=2;
			if(xVel>0 && !right){
				leftAcc=1;
				xVel-=leftAcc;
				leftAcc=2;
			} else if(xVel<0 && !left){
				rightAcc=1;
				xVel+=rightAcc;
				rightAcc=2;
			}
		} else if(!grounded){
			
			if((wallJumpRight || wallJumpLeft) && yVel>0){
				yVel+=1/2;
			} else if(yVel<yVelCap+20){
				yVel+=2;
			}
			
		}
		if(xVel>xVelCap && !right){
			xVel-=leftAcc;
		} else if(xVel<-xVelCap && !left){
			xVel+=rightAcc;
		} else if(xVel>xVelCap && right){
			xVel-=leftAcc;
		} else if(xVel<-xVelCap && left){
			xVel+=rightAcc;
		}
		
		if(right && xVel<xVelCap){
			xVel+=rightAcc;
		} else if(left && xVel>-xVelCap){
			xVel-=leftAcc;
		}
		
		x+=xVel*movAdj;
		hitbox.setX(x);
		y+=yVel*movAdj;
		hitbox.setY(y);
	}
	public void stopY(){
		yVel=0;
	}
	public void accLeft(){
		left=true;
		wallJumpRight=false;
	}
	public void accRight(){
		right=true;
		wallJumpLeft=false;
	}
	public void stopX(){
		xVel=0;
	}
	public void run(){
		xVelCap=6;
	}
	public void walk(){
		xVelCap=4;
	} 
	public void jump(){
		if(grounded){
			y-=10;
			grounded =false;
			yVel=-yVelCap;
		} else if(wallJumpLeft){ //current climbable limit=160 pixel width between walls
			y-=10;
			x+=1;
			xVelCap=4;
			rightAcc=2;
			leftAcc=1;
			xVel=xVelCap*5;
			if(yVel<0){	
				yVel=-yVelCap;
			} else{
				yVel-=yVelCap;
			}
			wallJumpLeft=false;
		} else if(wallJumpRight){
			y-=10;
			x-=1;
			xVelCap=4;
			leftAcc=2;
			rightAcc=1;
			xVel=-xVelCap*5;
			if(yVel<0){	
				yVel=-yVelCap;
			} else{
				yVel-=yVelCap;
			}
			wallJumpRight=false;
		}
	}
	public void miniJump(){
		y-=10;
		yVel=-15;
	}
	public void miniJumpDown(){
		y+=10;
		yVel=5;
	}
	public void jumpBack(String n){
		if(n.equals("right")){
			xVel=-xVelCap*2;
		} else if(n.equals("left")){
			xVel=xVelCap*2;
		}
	}
	public void decRight(){
		right=false;
	}
	public void decLeft(){
		left=false;
	}
	
	/*-------COLLISIONS--------*/
	public void isGrounded(boolean n){
		//System.out.println("Grounded: " + n);
		grounded=n;
	}
	public void isWalled(String n){
		if(!n.equals("right")){
			wallJumpRight=false;
		}
		if(!n.equals("left")){	
			wallJumpLeft=false;
		}
		if(n.equals("right") && right){
			wallJumpRight=true;
		}else if(n.equals("left") && left){
			wallJumpLeft=true;
		}/* else if(n.equals("null")){
			wallJumpRight=false;
			wallJumpLeft=false;
		}*/
	}
	public void calcFallDamage(){
		fallDamage=0;
		if(yVel>yVelCap+5){
			fallDamage=yVel-(yVelCap+yVelCap/2);
		}
	}
	public void applyFallDamage(){
		if(fallDamage>0){
			health-=fallDamage;
		}
		if(health<0){
			health=0;
		}
	}
	public void heal(int n){
		health+=n;
		if(health>100){
			health=100;
		}
	}
	public void addScore(int n){
		score += n;
	}
	public void takeDamage(int n){
		health-=n;
		if(health<0){
			health=0;
		}
	}
	
	Hitbox hitbox;
	boolean right;
	boolean left;
	boolean grounded;
	boolean wallJumpLeft;
	boolean wallJumpRight;
	int fallDamage;
	int health=100;
	int score;
	int leftAcc=2;
	int rightAcc=2;
	int yVel;
	int xVel;
	int yVelCap = 30;
	int xVelCap=4;
	int x;
	int y;
	int cameraX;
	int cameraY;
}