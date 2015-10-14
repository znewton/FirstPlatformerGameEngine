public class Hitbox{
	public Hitbox(int xx, int yy, int w, int h){
		x=xx;
		y=yy;
		width=w;
		height=h;
	}
	/*-------DATA---------*/
	public void setX(int xx){
		x=xx;
	}
	public void setY(int yy){
		y=yy;
	}
	public int getLeft(){
		return x;
	}
	public int getTop(){
		return y;
	}
	public int getRight(){
		return x+width;
	}
	public int getBottom(){
		return y+height;
	}
	
	/*-------COLLISIONS--------*/
	public String getCollision(Hitbox h){
		//System.out.println("h Top: " +h.getTop());
		//System.out.println("h Bottom: " +h.getBottom());
		//System.out.println("this Bottom: " +this.getBottom());
		//System.out.println("this Top: " +this.getTop());
		if((this.getBottom() >= h.getTop()) && (this.getTop() < h.getTop()) && ((this.getLeft() <h.getRight()) && (this.getRight()>h.getLeft()))){	//Bottom
			//System.out.println("bottom collision");
			return "bottom";
		}
		
		if((this.getTop() <= h.getBottom()) && (this.getBottom() > h.getBottom()) && (this.getLeft() <h.getRight()) && (this.getRight()>h.getLeft())){	//Top
			//System.out.println("top collision");
			return "top";
		}
		if((this.getRight() >= h.getLeft()) && (this.getRight() < h.getRight()) && (this.getTop()<h.getBottom()) && (this.getBottom()>h.getTop())){			//Right
			//System.out.println("right collision");
			return "right";
		}
		if((this.getLeft() <= h.getRight()) && (this.getLeft()>h.getLeft()) && (this.getTop()<h.getBottom()) && (this.getBottom()>h.getTop())){	//Left
			//System.out.println("left collision");
			return "left";
		}
		return "null";
	}
	
	int x;
	int y;
	int width;
	int height;
}