package object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Rectangle {
	double x;
	double y;
	double w;
	double h;
	
	public Rectangle() {
		this.setPosition(0, 0);
		this.setSize(1, 1);
	}
	
	public Rectangle(double x, double y, double w, double h) {
		this.setPosition(x, y);
		this.setSize(w, h);
	}
	
	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public void setSize(double w, double h) {
		this.w = w;
		this.h = h;
	}
	
	public boolean intersects(Rectangle other) {
		boolean doesntOverlap = this.x + this.w < other.x || 
				other.x + other.w < this.x || this.y + this.w < other.y ||
				other.y + other.h < this.y;
		return !doesntOverlap;
	}
	
	public void update() {
		//this.position.add(this.velocity.x * deltaTime, this.velocity.y * deltaTime);
	}
	
	public void render(GraphicsContext context) {
		
		context.save();
		
		context.translate(-(this.w/2), -(this.h/2));

		context.restore();
	}
}
