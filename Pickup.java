import java.awt.*;

public class Pickup extends EnvObj{
	
	public Pickup(int xx, int yy, int w, int h, String n, Color c, int v){
		super(xx,yy,w,h,n,c,false);
		value = v;
		if(n.substring(0,4).equals("coin")){
			coin=true;
		} else if(n.substring(0,4).equals("heal")){
			heal=true;
		}
	}
	
	/*------DATA-------*/
	public boolean isHeal(){
		return heal;
	}
	public boolean isCoin(){
		return coin;
	}
	public int getValue(){
		return value;
	}
	public boolean isExisting(){
		return existing;
	}
	public boolean isRemovable(){
		return removable;
	}
	
	public void used(){
		existing=false;
	}
	public void remove(){
		removable=true;
	}
	
	/*------GRAPHICS-----*/
	public void draw(Graphics g){
		if(coin){
			if(value==1){
				g.setColor(new Color(200,120,0));
			} else if(value==5){
				g.setColor(new Color(150,150,150));
			} else if(value==10){
				g.setColor(new Color(255,200,0));
			}
			g.fillOval(super.getX()-super.getCamX(),super.getY()-super.getCamY(),25,25);
		} else if(heal){
			g.setColor(new Color(0,220,0));
			g.fillRect(super.getX()+7-super.getCamX(),super.getY()-super.getCamY(),11,25);
			g.fillRect(super.getX()-super.getCamX(),super.getY()+7-super.getCamY(),25,11);
		}
	}
	
	boolean coin=false;
	boolean heal=false;
	boolean existing=true;
	boolean removable=false;
	int value;
}