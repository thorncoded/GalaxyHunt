package object;

public class Player extends Sprite {
	public Player() {
		this.position = new Vector();
		this.velocity = new Vector();
		this.elapsedTime = 0;
	}
	
    public Player(String type, String fileName) {
    	this();
        this.type = type;
        setImage(fileName);
    }
    
    public void update(double deltaTime) {
		this.position.add(this.velocity.x * deltaTime, this.velocity.y * deltaTime);
		this.wrap(500);
	}
}
