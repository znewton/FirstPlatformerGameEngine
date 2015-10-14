import java.awt.*;
import javax.swing.*;
import java.util.*;

public class Level{
	public Level(int x, int y, int w, int h, String n){
		startX=x;
		startY=y;
		width=w;
		height=h;
		name=n;
	}
	
	/*------DATA--------*/
	public int getStartX(){
		return startX;
	}
	public int getStartY(){
		return startY;
	}
	public int getWidth(){
		return width;
	}
	public int getHeight(){
		return height;
	}
	public int getObjListSize(){
		return eol.size();
	}
	public int getPupListSize(){
		return pup.size();
	}
	public int getEneListSize(){
		return ene.size();
	}
	public EnvObj getEnvObj(int i){
		return eol.get(i);
	}
	public Pickup getPickup(int i){
		return pup.get(i);
	}
	public Enemy getEnemy(int i){
		return ene.get(i);
	}
	public void removePickup(int i){
		pup.remove(i);
	}
	public void removeEnemy(int i){
		ene.remove(i);
	}
	public void addEnvObj(int xx, int yy, int w, int h, String n, Color c, boolean col){
		eol.add(new EnvObj(xx, yy, w, h, n, c, col));
	}
	public void addPickup(int xx, int yy, int w, int h, String n, Color c, int v){
		pup.add(new Pickup(xx, yy, w, h, n, c, v));
	}
	public void addEnemy(int xx, int yy, int w, int h, String n, Color c, String t, int d, int r, int f, int s){
		ene.add(new Enemy(xx, yy, w, h, n, c, t, d, r, f, s));
	}
	public String getName(){
		return name;
	}
	
	/*--------GRAPHICS-----------*/
	public void drawLevel(Graphics g){
		for(int i=0;i<eol.size();i++){
			eol.get(i).draw(g);
		}
		for(int i=0;i<pup.size();i++){
			pup.get(i).draw(g);
		}
		for(int i=0;i<ene.size();i++){
			ene.get(i).draw(g);
			//System.out.println("drawing "+i);
		}
	}
	public void moveLevel(int xx, int yy){
		for(int i=0;i<eol.size();i++){
			eol.get(i).moveDraw(xx,yy);
		}
		for(int i=0;i<pup.size();i++){
			pup.get(i).moveDraw(xx,yy);
		}
		for(int i=0;i<ene.size();i++){
			ene.get(i).moveDraw(xx,yy);
			ene.get(i).move();
		}
	}
	
	int startX;
	int startY;
	int height;
	int width;
	String name;
	ArrayList<EnvObj> eol = new ArrayList<EnvObj>();
	ArrayList<Pickup> pup = new ArrayList<Pickup>();
	ArrayList<Enemy> ene = new ArrayList<Enemy>();
}