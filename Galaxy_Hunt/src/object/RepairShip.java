package object;

public class RepairShip extends Sprite{
	public int maxHP;
	public int HP;
	public String fileName = "object/resources/healshiptiny.png";
	
	public RepairShip() {
		this.position = new Vector();
		this.velocity = new Vector();
		this.elapsedTime = 0;
		this.type = "repairShip";
        setImage(fileName);
	}
	
    public RepairShip(int maxHP) {
    	this();
        this.maxHP = maxHP;
        this.HP = maxHP;
    }
    
    public void hpDecrease() {
    	this.HP--;
    }
    
}
