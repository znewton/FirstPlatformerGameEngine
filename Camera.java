public class Camera{
	public Camera(int xx, int yy, int w, int h, int mx){
		x=xx;
		y=yy;
		width=w;
		height=h;
		xMax=mx;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public void move(int xx, int yy){
		if((xx<(3*width/7)+x) && x>xMin){
			//System.out.println("Camera Y: "+y);
			x=xx-3*width/7;
		}
		if((xx>(4*width/7)+x) && x<xMax-width){
			x=xx-4*width/7;
		}
		if((yy<(height/3)+y)){
			y=yy-height/3;
		}
		if((yy>((2*height)/3)+y)){
			y=yy-2*height/3;
		}
	}
	
	int x;
	int y;
	int width;
	int height;
	int xMin=0;
	int xMax;
}