package view;

import java.util.List;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.GalaxyHuntButton;
import model.GalaxyHuntSubscene;
import model.InfoLabel;

public class ViewManager {

	private static final int WIDTH = 1024;
	private static final int HEIGHT = 768;
	private AnchorPane mainPane;
	private Scene mainScene;
	private Stage mainStage;

	private final static int MENU_BUTTON_START_X = 100;
	private final static int MENU_BUTTON_START_Y = 150;

	private GalaxyHuntSubscene creditsSubscene;
	private GalaxyHuntSubscene helpSubscene;
	private GalaxyHuntSubscene scoreSubscene;
	private GalaxyHuntSubscene gameOverSubScene;

	private GalaxyHuntSubscene currentSubScene;

	List<GalaxyHuntButton> menuButtons;

	public ViewManager() {
		menuButtons = new ArrayList<>();
		mainPane = new AnchorPane();
		mainScene = new Scene(mainPane, WIDTH, HEIGHT);
		mainStage = new Stage();
		mainStage.setScene(mainScene);
		createSubscenes();
		CreateButtons();
		createBackground();
		createLogo();
	}

	private void showSubScene(GalaxyHuntSubscene subSceneToShow) {
		//hides current subscene and moves parameter subSceneToShow to proper position
		if (currentSubScene != null) {
			currentSubScene.moveSubScene();
		}

		subSceneToShow.moveSubScene();
		currentSubScene = subSceneToShow;
	}

	private void createSubscenes() {
		creditsSubscene = new GalaxyHuntSubscene();
		mainPane.getChildren().add(creditsSubscene);
		helpSubscene = new GalaxyHuntSubscene();
		mainPane.getChildren().add(helpSubscene);
		scoreSubscene = new GalaxyHuntSubscene();
		mainPane.getChildren().add(scoreSubscene);
		createCreditsSubScene();
	}

	private void addMenuButton(GalaxyHuntButton button) {
		//adds buttons to list of menuButtons, then places them on the screeen
		button.setLayoutX(MENU_BUTTON_START_X);
		button.setLayoutY(MENU_BUTTON_START_Y + menuButtons.size() * 70);
		menuButtons.add(button);
		mainPane.getChildren().add(button);
	}

	private void CreateButtons() {
		//creates all buttons
		createStartButton();
		createScoresButton();
		createHelpButton();
		createCreditsButton();
		createExitButton();
	}

	public Stage getMainStage() {
		return mainStage;
	}

	private void createStartButton() {
		GalaxyHuntButton startButton = new GalaxyHuntButton("PLAY");
		addMenuButton(startButton);
		
		startButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				/*try {
					//SoundEffects.playSound(new URI(BUTTON_SFX));
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
					mainStage.hide();
					GameViewManager gameViewManagger = new GameViewManager();
					gameViewManagger.createNewGame(mainStage);
			}
		});
	}

	private void createScoresButton() {
		//creates scores button and event handler
		GalaxyHuntButton scoresButton = new GalaxyHuntButton("SCORES");
		addMenuButton(scoresButton);

		scoresButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			//when clicked, show scoreSubscene
			public void handle(ActionEvent event) {
				showSubScene(scoreSubscene);
			}
		});

	}

	private void createHelpButton() {
		//creates help button and event handler
		GalaxyHuntButton helpButton = new GalaxyHuntButton("HELP");
		addMenuButton(helpButton);
		
		helpButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				showSubScene(helpSubscene);
				
			}
		});
	}

	private void createCreditsButton() {
		//creates credits button and event handler
		GalaxyHuntButton creditsButton = new GalaxyHuntButton("CREDITS");
		addMenuButton(creditsButton);

		creditsButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				showSubScene(creditsSubscene);

			}
		});
	}

	private void createExitButton() {
		GalaxyHuntButton exitButton = new GalaxyHuntButton("EXIT");
		addMenuButton(exitButton);

		exitButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				mainStage.close();
			}
		});

	}

	private void createBackground() {
		//sets background to image
		Image backgroundImage = new Image("view/resources/spacebg2.png", 1200, 1200, false, true);
		BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT,
				BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
		mainPane.setBackground(new Background(background));
	}

	private void createLogo() {
		//creates galaxyhunt button
		ImageView logo = new ImageView("view/resources/gh_transparent.png");
		logo.setFitHeight(62);
		logo.setFitWidth(375);
		logo.setLayoutX(380);
		logo.setLayoutY(50);

		logo.setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				logo.setEffect(new DropShadow());

			}
		});

		logo.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				logo.setEffect(null);

			}
		});

		mainPane.getChildren().add(logo);

	}
	

	//CREDITS SUBSCENE
	//TODO: ADD RELEVANT LINKS/ASSET REFERENCES
	private void createCreditsSubScene() {
		creditsSubscene = new GalaxyHuntSubscene();
		mainPane.getChildren().add(creditsSubscene);
		
		InfoLabel credits = new InfoLabel("  <<< Credits >>>");
		credits.setLayoutX(120);
		credits.setLayoutY(20);
		Label team = new Label("Programming by Team9 (Mason Tipton, Corrin Schuelke, Lucas Hauge, Michael Arndt)");
		Label assetCredit = new Label("Sounds and images added by ");
		Label masonCredit = new Label("Mason Tipton");
		
		String[]link    = new String[6];
				link[0] = "https://kenney.nl/";
				link[1] = "https://freesound.org/";
				link[2] = "http://soundbible.com/";
				link[3] = "https://www.freesoundslibrary.com/";
				link[4] = "https://www.google.com/";

				
		Hyperlink link0 = new Hyperlink("Kenney");
		Hyperlink link1 = new Hyperlink("FreeSound");
		Hyperlink link2 = new Hyperlink("SoundBible");
		Hyperlink link3 = new Hyperlink("FreeSoundsLibrary");
		
				
		VBox creditsBox = new VBox(10, team, assetCredit, link0, link1, link2, link3, masonCredit);
		creditsBox.setLayoutX(50);
		creditsBox.setLayoutY(80);
		
		creditsSubscene.getPane().getChildren().addAll(credits, creditsBox);
		
		/*Application app = new Application() {@Override public void start(Stage primaryStage) throws Exception{}};
		HostServices services = app.getHostServices();*/
		
		link0.setOnAction(e -> {
		    if(Desktop.isDesktopSupported())
		    {
		        try {
		            Desktop.getDesktop().browse(new URI(link[0]));
		        } catch (IOException e1) {
		            e1.printStackTrace();
		        } catch (URISyntaxException e1) {
		            e1.printStackTrace();
		        }
		    }
		});
		link1.setOnAction(e -> {
		    if(Desktop.isDesktopSupported())
		    {
		        try {
		            Desktop.getDesktop().browse(new URI(link[1]));
		        } catch (IOException e1) {
		            e1.printStackTrace();
		        } catch (URISyntaxException e1) {
		            e1.printStackTrace();
		        }
		    }
		});
		link2.setOnAction(e -> {
		    if(Desktop.isDesktopSupported())
		    {
		        try {
		            Desktop.getDesktop().browse(new URI(link[2]));
		        } catch (IOException e1) {
		            e1.printStackTrace();
		        } catch (URISyntaxException e1) {
		            e1.printStackTrace();
		        }
		    }
		});
		link3.setOnAction(e -> {
		    if(Desktop.isDesktopSupported())
		    {
		        try {
		            Desktop.getDesktop().browse(new URI(link[3]));
		        } catch (IOException e1) {
		            e1.printStackTrace();
		        } catch (URISyntaxException e1) {
		            e1.printStackTrace();
		        }
		    }
		});
	}
}
