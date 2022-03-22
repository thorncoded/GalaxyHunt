package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.text.Font;

public class InfoLabel extends Label{
	public final static String FONT_PATH = "src/model/resources/kenvector_future.ttf";
	public final static String BG_IMAGE = "model/resources/yellow_button.png";
	
	public InfoLabel(String text) {
		setPrefWidth(600);
		setPrefHeight(49);
		setPadding(new Insets(10, 40, 40, 50));
		setText(text);
		setWrapText(true);
		setLabelFont();
		
		BackgroundImage bgImage = new BackgroundImage(new Image(BG_IMAGE, 380, 50, false, true), 
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
		
		setBackground(new Background(bgImage));
				
	}
	
	private void setLabelFont() {
		try {
			setFont(Font.loadFont(new FileInputStream(FONT_PATH), 25));
		} catch (FileNotFoundException e) {
			System.out.println("Could not load font. Using defaults...");
			setFont(Font.font("Verdana", 25));
		}
	}
}