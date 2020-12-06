/*
 * package view;
 * 
 * import java.util.ArrayList; import java.util.List; import java.util.Random;
 * import java.util.concurrent.ThreadLocalRandom;
 * 
 * import javafx.animation.AnimationTimer; import javafx.event.EventHandler;
 * import javafx.scene.Scene; import javafx.scene.canvas.Canvas; import
 * javafx.scene.canvas.GraphicsContext; import javafx.scene.image.ImageView;
 * import javafx.scene.input.KeyCode; import javafx.scene.input.KeyEvent; import
 * javafx.scene.layout.AnchorPane; import javafx.scene.layout.BorderPane; import
 * javafx.scene.layout.GridPane; import javafx.stage.Stage; import
 * model.SmallInfoLabel; import object.Enemy; import object.HealthBar; import
 * object.Player; import object.Sprite;
 * 
 * public class GameViewManager {
 * 
 * private BorderPane gamePane; private Scene gameScene; private Stage
 * gameStage;
 * 
 * private static final int GAME_WIDTH = 500; private static final int
 * GAME_HEIGHT = 666;
 * 
 * private Stage menuStage; private Player spaceship;
 * 
 * private int score; private SmallInfoLabel scoreLabel;
 * 
 * 
 * 
 * // indiscrete input ArrayList<String> keyPressedList = new
 * ArrayList<String>();
 * 
 * // discrete input ArrayList<String> keyJustPressedList = new
 * ArrayList<String>();
 * 
 * ArrayList<Sprite> bulletList = new ArrayList<Sprite>(); ArrayList<Enemy>
 * enemyList = new ArrayList<Enemy>(); private Canvas canvas = new
 * Canvas(GAME_WIDTH, GAME_HEIGHT); private GraphicsContext context; private
 * AnimationTimer gameTimer;
 * 
 * public GameViewManager() {
 * 
 * initializeStage(); createKeyHandlers(); }
 * 
 * private void initializeStage() { context = canvas.getGraphicsContext2D();
 * gamePane = new BorderPane(); gameScene = new Scene(gamePane, GAME_WIDTH,
 * GAME_HEIGHT); gameStage = new Stage(); gameStage.setTitle("GALAXY HUNT");
 * gameStage.setScene(gameScene); gameStage.setResizable(false);
 * gamePane.setCenter(canvas); }
 * 
 * private int getRandomNumber(int min, int max) { return (int) ((Math.random()
 * * (max - min)) + min); }
 * 
 * // have to do two separate ones because the first has a tendency to only
 * produce // basic and warrior class // while I was tempted to put these next
 * couple functions in the enemy class, I // decided it might be simpler to just
 * put them here instead private int getRandomEnemy(int min, int max) { int
 * randomNum = ThreadLocalRandom.current().nextInt(min, max + 1); return
 * randomNum; }
 * 
 * // 50% chance of creating basic type, 35% of warrior, 15% of centurion public
 * String randomEnemyType(int randNum) { int probResult; if (1 <= randNum &&
 * randNum <= 3) { probResult = 3; } else if (4 <= randNum && randNum <= 10) {
 * probResult = 2; } else { probResult = 1; } switch (probResult) { case 1:
 * return "Basic"; case 2: return "Warrior"; case 3: return "Centurion";
 * default: return "Basic"; } }
 * 
 * // checks to make sure that enemies that spawn don't overlap those currently
 * on // the screen public static boolean doesNewEnemyOverlap(List<Enemy>
 * arraylist, double randXNum) { for (Enemy spr : arraylist) { if
 * (((spr.position.x + 40 >= randXNum) && (randXNum >= spr.position.x)) ||
 * ((spr.position.x - 40 <= randXNum) && (randXNum <= spr.position.x))) { return
 * true; } } return false; }
 * 
 * public void bulletLogic() { for (int n = 0; n < bulletList.size(); n++) {
 * Sprite bullet = bulletList.get(n); bullet.update(1 / 60.0); // if bullet has
 * been onscreen for longer than two seconds or goes off the // screen, remove
 * from list if (bullet.elapsedTime > 2.0) bulletList.remove(n); if
 * (bullet.position.y < 0) bulletList.remove(n); }
 * 
 * // if any of the bullets hit the enemy, bullet from bulletList // also
 * decrease enemy's hp and if enemy's hp is zero, remove from the list for (int
 * n = 0; n < bulletList.size(); n++) { Sprite bullet = bulletList.get(n); for
 * (int m = 0; m < enemyList.size(); m++) { Enemy tempEnemy = enemyList.get(m);
 * if (bullet.intersects(tempEnemy)) { bulletList.remove(n);
 * tempEnemy.hpDecrease(); if (tempEnemy.HP == 0) { score += (tempEnemy.maxHP);
 * String textToSet = "Points: "; if (score < 10) textToSet += "0";
 * scoreLabel.setText(textToSet + score); enemyList.remove(m); } } } } }
 * 
 * public void createKeyHandlers() { gameScene.setOnKeyPressed( (KeyEvent event)
 * -> { String keyName = event.getCode().toString(); if
 * (!keyPressedList.contains(keyName)) { keyPressedList.add(keyName);
 * keyJustPressedList.add(keyName); } } );
 * 
 * gameScene.setOnKeyReleased( (KeyEvent event) -> { String keyName =
 * event.getCode().toString(); //not sure if there would actually BE a case
 * where it wouldn't contain it if (keyPressedList.contains(keyName))
 * keyPressedList.remove(keyName); } ); }
 * 
 * private void keyEvents() { if (keyPressedList.contains("A"))
 * spaceship.addVelocity(-500, 0); if (keyPressedList.contains("D"))
 * spaceship.addVelocity(500, 0); if (keyJustPressedList.contains("SPACE")) {
 * Sprite bullet = new Sprite("player" + "bullet",
 * "/object/resources/bulletFinal.png");
 * bullet.position.set(spaceship.position.x, spaceship.position.y);
 * bullet.velocity.set(0, -400); bulletList.add(bullet); }
 * keyJustPressedList.clear(); }
 * 
 * public void createNewGame(Stage menuStage) {
 * 
 * this.menuStage = menuStage; this.menuStage.hide(); // createBackground();
 * createShip(); // createGameElements(); createGameLoop(); gameStage.show(); }
 * 
 * private void createShip() { spaceship = new Player("player",
 * "/object/resources/player.png"); spaceship.setPosition(250, 590); }
 * 
 * //TO-DO: figure out how to make healthbar work private void createHealthBar()
 * { HealthBar healthBar = new HealthBar(250, 650, 160, 15);
 * gamePane.getChildren().add(healthBar); }
 * 
 * private void createGameLoop() { gameTimer = new AnimationTimer() {
 * 
 * @Override public void handle(long now) { // moveBackground(); //
 * moveGameElements(); // checkIfElementAreBehindTheShipAndRelocated(); //
 * checkIfElementsCollide(); // moveShip(); keyEvents();
 * spaceship.setVelocity(0, 0); spaceship.update(1 / 60.0);
 * spaceship.render(context); bulletLogic();
 * 
 * for (Sprite bullet : bulletList) bullet.render(context); for (Enemy tempEnemy
 * : enemyList) { tempEnemy.render(context); }
 * 
 * keyJustPressedList.clear(); }
 * 
 * }; gameTimer.start(); } }
 */


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
	
	private SmallInfoLabel pointsLabel;
	private int points;
	private Score score;

	ArrayList<String> keyPressedList = new ArrayList<String>();
	
	//discrete input
	ArrayList<String> keyJustPressedList = new ArrayList<String>();
	
	ArrayList<Sprite> bulletList = new ArrayList<Sprite>();
	
	ArrayList<Enemy> enemyList = new ArrayList<Enemy>();
	
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
		gameStage.show();
	}
	
	

	private void elementCreation() {
		createBackground();
		createShip();
		createHealthBar();
		createScore();
		createKeyListeners();
		createGameLoop();
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
	public int getRandomEnemy(int min, int max) {
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
	
	//checks to make sure that enemies that spawn don't overlap those currently on the screen
	public static boolean doesNewEnemyOverlap(List<Enemy> arraylist, double randXNum) {
	    for (Enemy spr : arraylist) {
	        if (((spr.position.x + 40 >= randXNum) && (randXNum >= spr.position.x)) || ((spr.position.x - 40 <= randXNum) && (randXNum <= spr.position.x))) {
	            return true;
	        }
	    }
	    return false;
	}
	public void enemyLogic(HealthBar healthBar) {
		//check to see how many enemy ships are onscreen/in list
		//if less than 5, spawn a new one at a random position above 0 y
		//if enemy ship has traveled past screen boundary, remove from list
		//subtract hp if enemy ship travels past boundary
		if (enemyList.size() < 5) {
			
			Enemy tempEnemy = new Enemy(randomEnemyType(getRandomEnemy(1, 20)));
			//reason for the values 22 and 471 is because the spaceships are ~43 pixels wide-- it makes sure they dont go off either edge of the screen
			double randXNum = getRandomNumber(22, 471);
			//checks to make sure the new spaceship wont overlap any that are currently on screen
			do {
				randXNum = getRandomNumber(22, 471);
			} while (doesNewEnemyOverlap(enemyList, randXNum) == true);
			//generate random number- if boundaries of tempEnemy at spawn overlap
			//those of the current position of an enemy ship, generate a new planned coordinate
			tempEnemy.position.set(randXNum, 0);
			tempEnemy.velocity.set(0, getRandomNumber(60, 140));
	        enemyList.add(tempEnemy); 
		}
			
		for (int m = 0; m < enemyList.size(); m++) {
			Sprite tempEnemy = enemyList.get(m);
			tempEnemy.update(1/60.0);
			//if the enemy ship goes past the bottom of the screen, remove it and decrease health by one;
			if (tempEnemy.position.y > 666) {
				enemyList.remove(m);
				removeHealth(healthBar);
				
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
            		bulletList.remove(n);
            		tempEnemy.hpDecrease();
            		if (tempEnemy.HP == 0) {
						score.increaseScore(tempEnemy.maxHP);
        				enemyList.remove(m);
            		}
            	}
            	
            }
		}
	}
	
	public void removeHealth(HealthBar healthBar) {
		healthBar.decreaseHealth();
		if(healthBar.getCurrentHealth() == 0) {
			gameStage.close();
			gameTimer.stop();
			menuStage.show();
		}
	}
	
	private void createGameLoop() {
		gameTimer = new AnimationTimer() {

			@Override
			public void handle(long now) {
				
				ship.setVelocity(0, 0);
				
				keyEvents(ship);
				bulletLogic();
				enemyLogic(healthbar);
				
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
				keyJustPressedList.clear();
				updateScore(score, context);

			}
			
		};
		gameTimer.start();
	}
	

	
	
	
	
	
	

	
	
	

}
