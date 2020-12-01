package object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class HealthBar extends Rectangle {
	
	final double maxHealth = 50.0;
	double currentHealth = 50.0;
	double healthWidth = this.w;
	Color color = Color.GREEN;
	
	public HealthBar() {
		this.setPosition(0, 0);
		this.setSize(1, 1);
	}
	
	public HealthBar(double x, double y, double w, double h) {
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
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public void decreaseHealth() {
		this.currentHealth--;
	}
	
	public double getPercentage() {
		double percentage = this.currentHealth / this.maxHealth;
		return percentage;
		
	}
	public void update() {
		double tempW = this.w;
		if (this.currentHealth == 0) {
			this.healthWidth = 0;
		} else {
			this.healthWidth = (tempW * this.currentHealth) / this.maxHealth;
		}
		
	    if (getPercentage() < 0.25) {
	    	setColor(Color.RED);
	    }
	    if (getPercentage() > 0.25 && getPercentage() < 0.5) {
	    	setColor(Color.YELLOW);
	    }
	}
	
	public void render(GraphicsContext context) {
		
		context.save();
		context.translate(-(this.w/2), -(this.h/2));
		context.setFill(color);
		context.fillRect(this.x, this.y, this.healthWidth, this.h);
		
		context.restore();
	}
}
