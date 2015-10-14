import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.net.URL;
import javax.imageio.*;
import javax.swing.*;

public class DrawTest extends JPanel{
	public DrawTest(){
		
		try {
			img = ImageIO.read(new File("8BitTest.jpg"));
		} catch (IOException e) {
		}
	}
	
	public void paint(Graphics g){
		super.paint(g);
		
		g.drawImage(img,10,10,10+300,10+300,0,0,1,1,null);
	}
	
	public static void main(String[] Args) throws InterruptedException{
		JFrame frame = new JFrame("DrawTest");
		DrawTest test= new DrawTest();
		frame.add(test);
		test.setSize(500,500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500,500);
		frame.setResizable(false);
		frame.setVisible(true);
		
		while(true){
			test.repaint();
			Thread.sleep(1000/60);
		}
	}
	
	BufferedImage img = null;
}