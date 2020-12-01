package object;

import javafx.scene.image.Image;

public class Enemy extends Sprite{
	public int maxHP;
	public int HP;
	
	public Enemy() {
		this.position = new Vector();
		this.velocity = new Vector();
		this.elapsedTime = 0;
	}
	
    public Enemy(String type) {
    	this();
        this.type = type;
        this.maxHP = hpSelector(this.type);
        this.HP = hpSelector(this.type);
		Image imageCon = new Image(selectFileName(this.type));
		this.setImage(imageCon);
    }
    
    public String selectFileName(String type) {
    	switch(type){
    	case "Basic":
    		return "/object/resources/basicEnemy.png";
    	case "Warrior":
    		return "/object/resources/warriorEnemy.png";
    	case "Centurion":
    		return "/object/resources/centurionEnemy.png";
    	default:
    		return "/object/resources/basicEnemy.png";
    	}
    }
    
    public int hpSelector(String type) {
    	switch(type){
    	case "Basic":
    		return 1;
    	case "Warrior":
    		return 2;
    	case "Centurion":
    		return 3;
    	default:
    		return 1;
    	}
    }
    
    public void hpDecrease() {
    	this.HP--;
    }
}

