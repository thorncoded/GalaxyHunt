package object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Sprite {
	public Vector position;
	public Vector velocity;
	public double width;
	public double height;
	public Image image;
	public String type;
	public double elapsedTime;
	
	
	public Sprite() {
		this.position = new Vector();
		this.velocity = new Vector();
		this.elapsedTime = 0;
	}
	
    public Sprite(String type, String fileName) {
    	this();
        this.type = type;
        setImage(fileName);
    }
    
    public void setImage(Image i) {
    	image = i;
    	width = i.getWidth();
    	height = i.getHeight();
    }
    
	public void setImage(String fileName) {
		Image i = new Image(fileName);
		setImage(i);
	}
	
	public void setVelocity(double x, double y) {
		this.velocity.set(x,y);
	}
	
	public void setPosition(double x, double y) {
		this.position.set(x,y);
	}
	
	public void addVelocity(double dx, double dy) {
		this.velocity.add(dx, dy);
	}
	
	public void wrap(double screenWidth) {
		if (this.position.x + this.width < 0)
			this.position.x = screenWidth;
		else if (this.position.x > screenWidth)
			this.position.x = -this.width;
	}
	
	public void update(double deltaTime) {
		this.elapsedTime += deltaTime;
		this.position.add(this.velocity.x * deltaTime, this.velocity.y * deltaTime);
	}
	
	public void render(GraphicsContext context) {
		context.save();
		
		context.translate(this.position.x, this.position.y);
		context.translate(-(this.image.getWidth()/2), -(this.image.getHeight()/2));
		//context.drawImage(image, this.position.x, this.position.y);
		context.drawImage(image, 0, 0);
		context.restore();
	}
	
    public Rectangle getBoundary()
    {
        return new Rectangle(this.position.x,this.position.y,width,height);
    }

    public boolean intersects(Sprite s)
    {
        return this.getBoundary().intersects( s.getBoundary() );
    }
}
