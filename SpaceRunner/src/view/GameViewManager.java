package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.Score;
import model.SmallInfoLabel;
import object.Enemy;
import object.HealthBar;
import object.Player;
import object.RepairShip;
import object.Sprite;

public class GameViewManager {
	
	private BorderPane gamePane;
	private Scene gameScene;
	private Stage gameStage;
	
	private static final int GAME_WIDTH = 500;
	private static final int GAME_HEIGHT = 666;
	
	private Stage menuStage;
	private Player ship;
	private Sprite bg;
	private HealthBar healthbar;
	private AnimationTimer gameTimer;
	Canvas canvas;
	GraphicsContext context; 
	
	private Score score;
	private double velocityMultiplier = 1;
	private String Screen = "GAME";

	ArrayList<String> keyPressedList = new ArrayList<String>();
	
	//discrete input
	ArrayList<String> keyJustPressedList = new ArrayList<String>();
	
	ArrayList<Sprite> bulletList = new ArrayList<Sprite>();
	
	ArrayList<Enemy> enemyList = new ArrayList<Enemy>();
	ArrayList<RepairShip> repairShipList = new ArrayList<RepairShip>();
	
	public GameViewManager() {
		initializeStage();

	}
	
	private void createKeyListeners() {
		
		gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				String keyName = event.getCode().toString();
				if (!keyPressedList.contains(keyName)) {
					keyPressedList.add(keyName);
					keyJustPressedList.add(keyName);
				}
				
			}
		});
		
		gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				String keyName = event.getCode().toString();
				//not sure if there would actually BE a case where it wouldn't contain it
				if (keyPressedList.contains(keyName))
					keyPressedList.remove(keyName);
				
			}
		});
	}
	
	private void createEndScreenKeyListeners() {
		gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				String keyName = event.getCode().toString();
				if (keyName == "Z") {
					gameStage.close();
					gameTimer.stop();
					menuStage.show();
				} else if (keyName == "C") {
					replayGame();
				}
				
			}
		});
	}
	
	private void replayGame() {
		velocityMultiplier = 1;
		healthbar.setCurrentHealth(healthbar.getMaxHealth());
		score.setCurrentScore(0);
		bulletList.clear();
		enemyList.clear();
		repairShipList.clear();
		elementCreation();
		Screen = "GAME";
		
	}
	private void initializeStage() {
		gamePane = new BorderPane();
		gamePane.setPrefSize(GAME_WIDTH, GAME_HEIGHT);
		canvas = new Canvas(GAME_WIDTH, GAME_HEIGHT);
		context = canvas.getGraphicsContext2D();
		gamePane.setCenter(canvas);
		gameScene = new Scene(gamePane/*, GAME_WIDTH, GAME_HEIGHT*/);
		gameStage = new Stage();
		gameStage.setScene(gameScene);
	}
	
	
	public void createNewGame(Stage menuStage) {
	
		this.menuStage = menuStage;
		this.menuStage.hide();
		elementCreation();
		createGameLoop();
		gameStage.show();
	}
	
	

	private void elementCreation() {
		createBackground();
		createShip();
		createHealthBar();
		createScore();
		createKeyListeners();
	}
	
	private void createBackground() {
		bg = new Sprite("space", "object/resources/spacebg.jpg");
		bg.setPosition(0, 0);
	}
	private void createShip() {
		ship = new Player("player", "object/resources/player.png");
		ship.setPosition(250, 590);
	}
	
	private void createHealthBar() {
		healthbar = new HealthBar(250, 650, 160, 15);
	}
	
	private void createScore() {
		score = new Score(319, 60, 0);
	}
	
	private void updateScore(Score score, GraphicsContext context) {
		context.setFill(Color.WHITE);
		context.setStroke(Color.DARKORCHID);
		Font ft0 = Font.font("arial", FontWeight.BOLD, 30);
		context.setFont(ft0);
		String scoreText = "SCORE: " + score.getCurrentScore();
		context.fillText(scoreText, score.getScoreX(), score.getScoreY());
	    context.strokeText(scoreText, score.getScoreX(), score.getScoreY());
	}
	
	public void keyEvents(Player player) {
		if (keyPressedList.contains("A"))
			player.addVelocity(-500, 0);
		if (keyPressedList.contains("D"))
			player.addVelocity(500, 0);
		if (keyJustPressedList.contains("SPACE")) {
	    	Sprite bullet = new Sprite("player" + "bullet", "object/resources/bulletFinal.png");
	    	bullet.position.set(player.position.x, player.position.y);
	    	bullet.velocity.set(0, -400);
	        bulletList.add(bullet); 
		}
		keyJustPressedList.clear();
	}
	
	public int getRandomNumber(int min, int max) {
	    return (int) ((Math.random() * (max - min)) + min);
	}
	
	//have to do two separate ones because the first has a tendency to only produce basic and warrior class
	//while I was tempted to put these next couple functions in the enemy class, I decided it might be simpler to just put them here instead
	public int getRandomShip(int min, int max) {
		int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
		return randomNum;
	}
	
	// 50% chance of creating basic type, 35% of warrior, 15% of centurion
	public String randomEnemyType (int randNum) {
		int probResult;
		if (1 <= randNum && randNum <= 3) {
			probResult = 3;
		} else if (4 <= randNum && randNum <= 10) {
			probResult = 2;
		} else {
			probResult = 1;
		}
		switch (probResult) {
			case 1:
				return "Basic";
			case 2:
				return "Warrior";
			case 3:
				return "Centurion";
			default:
				return "Basic";
		}
	}
	
	//checks to make sure that enemies that spawn don't overlap the enemies or repairship currently on the screen
	public static boolean doesNewSpriteOverlapEnemy(List<Enemy> arraylist, double randXNum) {
	    for (Enemy spr : arraylist) {
	        if (((spr.position.x + 40 >= randXNum) && (randXNum >= spr.position.x)) || ((spr.position.x - 40 <= randXNum) && (randXNum <= spr.position.x))) {
	        	return true;
	        }
	    }
	    return false;
	}
	
	public static boolean doesNewSpriteOverlapRepairShip(List<RepairShip> repairArrayList, double randXNum) {
		for (RepairShip sprr : repairArrayList) {
	        if (((sprr.position.x + 30 >= randXNum) && (randXNum >= sprr.position.x)) || ((sprr.position.x - 30 <= randXNum) && (randXNum <= sprr.position.x))) {
	        	return true;
	        }
	    }
	    return false;
	}
	

	public void enemyLogic(HealthBar healthBar) {
		if (Screen == "GAME") {
		//check to see how many enemy ships are onscreen/in list
		//if less than 5, spawn a new one at a random position above 0 y
		//if enemy ship has traveled past screen boundary, remove from list
		//subtract hp if enemy ship travels past boundary
		if ((enemyList.size() + repairShipList.size()) < 5) {
			
			Enemy tempEnemy = new Enemy(randomEnemyType(getRandomShip(1, 20)));
			//reason for the values 22 and 471 is because the spaceships are ~43 pixels wide-- it makes sure they dont go off either edge of the screen
			double randXNum = getRandomNumber(22, 471);
			//checks to make sure the new spaceship wont overlap any that are currently on screen
			do {
				randXNum = getRandomNumber(22, 471);
			} while (doesNewSpriteOverlapEnemy(enemyList, randXNum) || doesNewSpriteOverlapRepairShip(repairShipList, randXNum));
			//generate random number- if boundaries of tempEnemy at spawn overlap
			//those of the current position of an enemy ship, generate a new planned coordinate
			tempEnemy.position.set(randXNum, 0);
			tempEnemy.velocity.set(0, (getRandomShip(20, 100) * velocityMultiplier));
	        enemyList.add(tempEnemy); 
		}
			
		for (int m = 0; m < enemyList.size(); m++) {
			Sprite tempEnemy = enemyList.get(m);
			tempEnemy.update(1/60.0);
			//if the enemy ship goes past the bottom of the screen, remove it and decrease health by one;
			if (tempEnemy.position.y > GAME_HEIGHT) {
				enemyList.remove(m);
				removeHealth(healthBar);
				
			}

		}
		}
	}
	public void repairShipLogic(HealthBar healthBar) {
		if(Screen == "GAME") {
		//check to see how many enemy ships are onscreen/in list
		//random generation, if getRandomShip returns a 16, then spawn a new RepairShip
		//if less than 5, spawn a new one at a random position above 0 y
		//if enemy ship has traveled past screen boundary, remove from list
		//subtract hp if enemy ship travels past boundary
		if (((enemyList.size() + repairShipList.size()) < 5) && (getRandomShip(1,20) == 16)){
			//TODO: monitor this. 2 hp too much? too little?
			RepairShip tempShip = new RepairShip(2);
			//reason for the values 35 and 471 is because the repairShip is 70 pixels wide-- it makes sure they dont go off either edge of the screen
			double randXNum = getRandomNumber(35, 471);
			//checks to make sure the new spaceship wont overlap any that are currently on screen
			do {
				randXNum = getRandomNumber(35, 471);
			} while (doesNewSpriteOverlapEnemy(enemyList, randXNum) || doesNewSpriteOverlapRepairShip(repairShipList, randXNum));
			//generate random number- if boundaries of tempShip at spawn overlap
			//those of the current position of another ship, generate a new planned coordinate
			tempShip.position.set(randXNum, 0);
			tempShip.velocity.set(0, getRandomShip(60, 140));
	        repairShipList.add(tempShip); 
		}
			
		for (int m = 0; m < repairShipList.size(); m++) {
			Sprite tempShip = repairShipList.get(m);
			tempShip.update(1/60.0);
			//if the enemy ship goes past the bottom of the screen, remove it and decrease health by one;
			if (tempShip.position.y > GAME_HEIGHT) {
				repairShipList.remove(m);
				//TODO: ADD PROPER METHOD INCREASE HEALTH FOR HEALTHBAR
				healthBar.increaseHealth(10);
			}

		}
		}
	}
	
	public void bulletLogic(/* Score score */) {
		for (int n = 0; n < bulletList.size(); n++) {
			Sprite bullet = bulletList.get(n);
			bullet.update(1/60.0);
			//if bullet has been onscreen for longer than two seconds or goes off the screen, remove from list
			if (bullet.elapsedTime > 2.0)
				bulletList.remove(n);
			if (bullet.position.y < 0)
				bulletList.remove(n);
		}
		

		//if any of the bullets hit the enemy, bullet from bulletList
		//also decrease enemy's hp and if enemy's hp is zero, remove from the list
		for (int n = 0; n < bulletList.size(); n++) {
			Sprite bullet = bulletList.get(n);
			
			
            for (int m = 0; m < enemyList.size(); m++) {
            	Enemy tempEnemy = enemyList.get(m);
            	if (bullet.intersects(tempEnemy)) {
            		//silly that we have to do this, but java throws a fit sometimes if we dont
            		if (bulletList.size() != 0) {
            			bulletList.remove(n);
            		}
            		tempEnemy.hpDecrease();
            		if (tempEnemy.HP == 0) {
						score.increaseScore(tempEnemy.maxHP);
        				enemyList.remove(m);
            		}
            	}
            }
            
            //REPAIRSHIP SECTION HERE
            for (int m = 0; m < repairShipList.size(); m++) {
            	RepairShip tempShip = repairShipList.get(m);
            	if (bullet.intersects(tempShip)) {
            		bulletList.remove(n);
            		tempShip.hpDecrease();
            		if (tempShip.HP == 0) {
        				repairShipList.remove(m);
            		}
            	}
            }
		}
	}
	
	private void changeVelocity(Score score) {
		if (score.getCurrentScore() % 10 == 0) {
			velocityMultiplier = velocityMultiplier + .2;
		}
	}
	public void removeHealth(HealthBar healthBar) {
		healthBar.decreaseHealth();
		//monitors to see if the healthbar hits 0
		if(healthBar.getCurrentHealth() == 0) {
			int passScore = score.getCurrentScore();
			Screen = ("GAME_OVER");
			
		}
	}
	
	public void increaseHealthForHealthBar(HealthBar healthBar, int increaser) {
		healthBar.increaseHealth(increaser);
	}
	
	private void createGameLoop() {
		gameTimer = new AnimationTimer() {

			@Override
			public void handle(long now) {
				if (Screen == "GAME") {
				ship.setVelocity(0, 0);
				
				keyEvents(ship);
				bulletLogic();
				enemyLogic(healthbar);
				repairShipLogic(healthbar);
				
				ship.update(1 / 60.0);
				healthbar.update();
				
				bg.render(context);
				ship.render(context);
				healthbar.render(context);
				
				for (Sprite bullet : bulletList)
					bullet.render(context);
				for (Enemy tempEnemy : enemyList) {
					tempEnemy.render(context);
				}
				for (RepairShip repairShip: repairShipList) {
					repairShip.render(context);
				}
				keyJustPressedList.clear();
				updateScore(score, context);
				} else if (Screen == "GAME_OVER") {
					context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
					context.setFill(Color.INDIGO);
					context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
					Font ft1 = Font.font("arial black", FontWeight.BOLD, 50);
					context.setFont(ft1);
					context.setFill(Color.WHITE);
					context.setStroke( Color.GREY );
					String gg = "GAME OVER!";
					context.fillText(gg, 60, 100 );
					context.strokeText(gg, 60, 100);
					
					
					//Font ft3 = Font.font("arial black", FontWeight.BOLD, 0);
					int finalScore = score.getCurrentScore();
					String endScore = "FINAL SCORE: " + finalScore;
					int endScore_x = 170;
					int endScore_y = 200;
					
					String instruction1  = "PRESS Z TO GO TO MENU SCREEN";
					String instruction2 = "PRESS C TO REPLAY";
					int instructions_x = 80;
					int instruction1_y = 250;
					int instruction2_y = instruction1_y + 25;
					Font ft0 = Font.font("arial", FontWeight.BOLD, 30);
					Font ft3 = Font.font("arial", FontWeight.BOLD, 20);
					context.setFont(ft0);
					context.fillText(endScore, endScore_x, endScore_y);
					context.strokeText(endScore, endScore_x, endScore_y);
					context.setFont(ft3);
					context.setFill(Color.GOLD);
					context.setStroke( Color.GREY );
					context.fillText(instruction1, instructions_x, instruction1_y);
					context.strokeText(instruction1, instructions_x, instruction1_y);
					context.fillText(instruction2, instructions_x, instruction2_y);
					context.strokeText(instruction2, instructions_x, instruction2_y);
					
					String weepingEarth = "view/resources/andtheearthcries.jpg";
					Image i = new Image(weepingEarth);
					context.drawImage(i, 0, 300);
					createEndScreenKeyListeners();
				}

			}
			
		};
		gameTimer.start();
	}
	

	
	
	
	
	
	

	
	
	

}
