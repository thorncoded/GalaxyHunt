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
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.SmallInfoLabel;
import object.Enemy;
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
	private AnimationTimer gameTimer;
	Canvas canvas;
	GraphicsContext context; 
	
	
	private ImageView[] brownMeteors;
	private ImageView[] greyMeteors;
	Random randomPositionGenerator;
	
	
	private ImageView star;
	private SmallInfoLabel pointsLabel;
	private ImageView[] playerLifes;
	private int playerLife;
	private int points;

	ArrayList<String> keyPressedList = new ArrayList<String>();
	
	//discrete input
	ArrayList<String> keyJustPressedList = new ArrayList<String>();
	
	ArrayList<Sprite> bulletList = new ArrayList<Sprite>();
	
	ArrayList<Enemy> enemyList = new ArrayList<Enemy>();
	
	public GameViewManager() {
		initializeStage();
		
		//randomPositionGenerator = new Random();
		
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
		createBackground();
		createShip();
		createKeyListeners();
		//createGameElements(choosenShip);
		createGameLoop();
		gameStage.show();
	}
	
	

	
	private void createBackground() {
		bg = new Sprite("space", "object/resources/spacebg.jpg");
		bg.setPosition(0, 0);
	}
	private void createShip() {
		ship = new Player("player", "object/resources/player.png");
		ship.setPosition(250, 590);
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
	
	private void createGameLoop() {
		gameTimer = new AnimationTimer() {

			@Override
			public void handle(long now) {
				/*moveBackground();
				moveGameElements();
				checkIfElementAreBehindTheShipAndRelocated();
				checkIfElementsCollide();
				moveShip();*/
				
				ship.setVelocity(0, 0);
				keyEvents(ship);
				ship.update(1 / 60.0);
				bg.render(context);
				ship.render(context);
				keyJustPressedList.clear();
			}
			
		};
		gameTimer.start();
		//gameStage.show();
	}
	

	
	
	
	
	
	

	
	
	

}
