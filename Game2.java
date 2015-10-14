import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

@SuppressWarnings("serial")
public class Game2 extends JPanel{
	public static final int FRAME_HEIGHT = 768;	//25 frame height pixels used up by header
	public static final int FRAME_WIDTH = 1366;		// 1 meter = 25 pixels
	
	
	public Game2(){		//constructs levels and adds player
		level = 0;
		
		player = new Player(10,10);
		ui = new UI(FRAME_WIDTH,FRAME_HEIGHT);
		
		levels.add(new Level(200,200,1920,1536,"Play Ground"));
		cameras.add(new Camera(0,0, FRAME_WIDTH, FRAME_HEIGHT, levels.get(0).getWidth()));
		player.setXY(levels.get(level).getStartX(),levels.get(level).getStartY());
		
		
		
		//levels.get(0).addEnvObj(0,500,3840,100,"Floor", new Color(0,0,0), true);
		levels.get(0).addEnvObj(0,500,300,100,"GapFloor", new Color(0,0,0), true);
		levels.get(0).addEnvObj(460,500,3840,100,"GapFloor", new Color(0,0,0), true);
		levels.get(0).addEnvObj(800,-500,200,850,"Wall", new Color(0,0,0), true);
		levels.get(0).addEnvObj(465,-500,200,850,"Wall", new Color(0,0,0), true);
		//levels.get(0).addEnvObj(1200,100,200,100,"Platform", new Color(0,0,0), true);
		//levels.get(0).addEnvObj(1400,000,200,100,"Platform", new Color(0,0,0), true);
		levels.get(0).addPickup(732,300,25,25,"coin",null,1);
		levels.get(0).addPickup(732,0,25,25,"coin",null,5);
		levels.get(0).addPickup(732,-500,25,25,"coin",null,10);
		levels.get(0).addPickup(900,-550,25,25,"heal",null,10);
		
		levels.get(0).addEnemy(800,450, 25, 50, "BasicEnemy", new Color(200,0,0), "walk", 10, 300, 0, 1);
		//levels.get(0).addEnemy(900,450, 25, 50, "BasicEnemy", new Color(200,100,0), "walk", 10, 200, 0, 1);
		//levels.get(0).addEnemy(1300,400, 25, 50, "BasicEnemy", new Color(200,0,0), "walk", 10, 200, 0, 1);
		
		addKeyListener(new KeyAdapter(){ //necessary for key presses and releases
			@Override
			public void keyPressed(KeyEvent evt){ //for when key is pressed
				switch(evt.getKeyCode()){
					case KeyEvent.VK_UP: //keys are coded as "KeyEvent.VK_" and then the key name or letter in caps
						player.jump();
						break;
					case KeyEvent.VK_RIGHT:
						player.accRight();
						break;
					case KeyEvent.VK_LEFT:
						player.accLeft();
						break;
					case KeyEvent.VK_W: //keys are coded as "KeyEvent.VK_" and then the key name or letter in caps
						player.jump();
						break;
					case KeyEvent.VK_D:
						player.accRight();
						break;
					case KeyEvent.VK_A:
						player.accLeft();
						break;
					case KeyEvent.VK_SHIFT:
						player.run();
						break;
					case KeyEvent.VK_P:
						if(running){
							running = false;
							System.out.println("PAUSE");
						} else{
							running = true;
							System.out.println("PLAY");
						}
						break;
					case KeyEvent.VK_BACK_SPACE:
						running=true;
						gameover=false;
						player.heal(100);
						player.setXY(levels.get(level).getStartX(),levels.get(level).getStartY());
						counter=0;
				}
			}
			@Override
			public void keyReleased(KeyEvent evt){ //for when key is released
				switch(evt.getKeyCode()){
					case KeyEvent.VK_RIGHT:
						player.decRight();
						break;
					case KeyEvent.VK_LEFT:
						player.decLeft();
						break;
					case KeyEvent.VK_D:
						player.decRight();
						break;
					case KeyEvent.VK_A:
						player.decLeft();
						break;
					case KeyEvent.VK_SHIFT:
						player.walk();
						break;
				}
			}
		});
		
		setFocusable(true);
	}
	
	@Override
	public void paint(Graphics g){
		if(running){
			super.paint(g);			//resets canvas
			
			
			
			player.move();
			cameras.get(level).move(player.getX(),player.getY());
			levels.get(level).moveLevel(cameras.get(level).getX(), cameras.get(level).getY()); //moves Environment
			
			player.giveCamXY(cameras.get(level).getX(), cameras.get(level).getY());
			player.calcFallDamage();
			checkPlayerCollisions();
			checkEnemyCollisions();
			
			ui.givePlayerStats(player.getHealth(),player.getScore());
			
			
			levels.get(level).drawLevel(g);		//draws level
			player.draw(g);						//draws player
			ui.drawUI(g);
			
			if(player.getHealth()<=0){
				gameover=true;
				running=false;
			}
			if(player.getY()>FRAME_HEIGHT){
				gameover=true;
				running=false;
			}
			//System.out.println((levels.get(level).getEnvObj(0).getName()) + " y= " + levels.get(level).getEnvObj(0).getY());
		} else if(gameover){
			gameOver(g);
		} else if(levelIntro){
			levelIntro(g);
		}
	}
	
	public void checkPlayerCollisions(){
		String c;
		Hitbox h;
		EnvObj o;
		Pickup p;
		Enemy e;
		player.isGrounded(false);
		player.isWalled("null");
		for(int i=0;i<levels.get(level).getObjListSize();i++){
			h=levels.get(level).getEnvObj(i).getHitbox(); 	//gets object hitbox
			o=levels.get(level).getEnvObj(i);
			c=player.getHitbox().getCollision(h);			//checks player hitbox with object hitbox
			if(!(c.equals("null"))){
				if(c.equals("bottom")){
					player.isGrounded(true);
					player.setXY(player.getX(),(h.getTop()-(player.getHitbox().getBottom()-player.getHitbox().getTop())));
					player.applyFallDamage();
				} else if(c.equals("top")){
					player.stopY();
					player.setXY(player.getX(),h.getBottom());
				} else if(c.equals("right")){
					player.stopX();
					player.isWalled("right");
					player.setXY((h.getLeft()-(player.getHitbox().getRight()-player.getHitbox().getLeft())), player.getY());
				} else if(c.equals("left")){
					player.stopX();
					player.isWalled("left");
					player.setXY(h.getRight(), player.getY());
				}
			}
		}
		for(int i=0;i<levels.get(level).getPupListSize();i++){
			h=levels.get(level).getPickup(i).getHitbox(); 	//gets object hitbox
			p=levels.get(level).getPickup(i);
			c=player.getHitbox().getCollision(h);
			if(!c.equals("null") && p.isExisting()){
				if(p.isHeal()){
					player.heal(p.getValue());
				} else if(p.isCoin()){
					player.addScore(p.getValue());
				}
				p.used();
			}else if(!p.isExisting()){
				p.remove();
				if(p.isRemovable()){
					levels.get(level).removePickup(i);
					i--;
				}
			}
		}
		for(int i=0;i<levels.get(level).getEneListSize();i++){
			h=levels.get(level).getEnemy(i).getHitbox(); 	//gets object hitbox
			e=levels.get(level).getEnemy(i);
			c=player.getHitbox().getCollision(h);
			if(!(c.equals("null") && e.isAlive())){
				if(c.equals("bottom")){
					player.setXY(player.getX(),(h.getTop()-(player.getHitbox().getBottom()-player.getHitbox().getTop())));
					player.miniJump();
					e.killed();
				} else if(c.equals("top")){
					player.stopY();
					player.setXY(player.getX(),h.getBottom());
					player.miniJumpDown();
				} else if(c.equals("right")){
					player.stopX();
					player.setXY((h.getLeft()-(player.getHitbox().getRight()-player.getHitbox().getLeft())), player.getY());
					player.jumpBack("right");
					player.takeDamage(e.getDamage());
				} else if(c.equals("left")){
					player.stopX();
					player.setXY(h.getRight(), player.getY());
					player.jumpBack("left");
					player.takeDamage(e.getDamage());
				} else if(!e.isAlive()){
					e.remove();
					if(e.isRemovable()){
						levels.get(level).removeEnemy(i);
						i--;
					}
				}
			}
		}
	}
	
	public void gameOver(Graphics g){
		counter++;
		g.setColor(new Color(0,0,0));
		g.fillRect(0,0,FRAME_WIDTH,FRAME_HEIGHT);
		if(counter>40){
			g.setColor(new Color(255,255,255));
			g.drawString("GAME  OVER",FRAME_WIDTH/2-10,FRAME_HEIGHT/2-3);
		}
		if(counter>100){
			g.setColor(new Color(255,255,255));
			g.drawString("Press BACKSPACE to restart level...",FRAME_WIDTH/2-50,FRAME_HEIGHT/2-3+30);
		}
	}
	
	public void levelIntro(Graphics g){
		counter++;
		g.setColor(new Color(0,0,0));
		g.fillRect(0,0,FRAME_WIDTH,FRAME_HEIGHT);
		if(counter>20 && counter<120){
			g.setColor(new Color(255,255,255));
			g.drawString(levels.get(level).getName(),FRAME_WIDTH/2-10,FRAME_HEIGHT/2-3);
		}
		if(counter==140){
			levelIntro=false;
			running=true;
			counter=0;
		}
	}
	
	public void checkEnemyCollisions(){
		for(int j=0;j<levels.get(level).getEneListSize();j++){
			String c;
			Hitbox h;
			EnvObj o;
			Enemy e;
			Enemy enemy=levels.get(level).getEnemy(j);
			enemy.isGrounded(false);
			for(int i=0;i<levels.get(level).getObjListSize();i++){
				h=levels.get(level).getEnvObj(i).getHitbox(); 	//gets object hitbox
				o=levels.get(level).getEnvObj(i);
				c=enemy.getHitbox().getCollision(h);			//checks enemy hitbox with object hitbox
				if(!(c.equals("null"))){
					if(c.equals("bottom")){
						enemy.isGrounded(true);
						enemy.setXY(enemy.getX(),(h.getTop()-(enemy.getHitbox().getBottom()-enemy.getHitbox().getTop())));
					} else if(c.equals("right")){
						enemy.changeDir("left");
						enemy.setXY((h.getLeft()-(enemy.getHitbox().getRight()-enemy.getHitbox().getLeft())), enemy.getY());
					} else if(c.equals("left")){
						enemy.changeDir("right");
						enemy.setXY(h.getRight(), enemy.getY());
					}
				}
			}
			for(int i=0;i<levels.get(level).getEneListSize();i++){
				h=levels.get(level).getEnemy(i).getHitbox(); 	//gets object hitbox
				e=levels.get(level).getEnemy(i);
				c=enemy.getHitbox().getCollision(h);
				if(!(c.equals("null") && e.isAlive())){
					if(c.equals("bottom")){
						enemy.isGrounded(true);
						enemy.setXY(enemy.getX(),(h.getTop()-(enemy.getHitbox().getBottom()-enemy.getHitbox().getTop())));
					} else if(c.equals("right")){
						enemy.changeDir("left");
						enemy.setXY((h.getLeft()-(enemy.getHitbox().getRight()-enemy.getHitbox().getLeft())), enemy.getY());
					} else if(c.equals("left")){
						enemy.changeDir("right");
						enemy.setXY(h.getRight(), enemy.getY());
					}
				}
			}
		}
	}
	
	public static void main(String[] args) throws InterruptedException{
		JFrame frame = new JFrame("Game2");
		Game2 game= new Game2();
		frame.add(game);
		game.setSize(FRAME_WIDTH,FRAME_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(FRAME_WIDTH,FRAME_HEIGHT);
		frame.setResizable(false);
		frame.setVisible(true);
		
		
		
		while(true){
			game.repaint();
			Thread.sleep(1000/60);
		}
	}
	
	int counter;
	int score;
	boolean levelIntro=false;
	boolean running =true;
	boolean gameover=false;
	int level=0;
	Player player;
	Camera camera;
	UI ui;
	ArrayList<Camera> cameras = new ArrayList<Camera>();
	ArrayList<Level> levels = new ArrayList<Level>();
}