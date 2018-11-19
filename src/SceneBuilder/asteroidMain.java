/* *****************************************
* CSCI205 - Software Engineering and Design
* Spring 2017
*
* Name: Andrew Gray. John Piermatteo, Dylan Zucker, Dan Kershner
* Date: Apr 10, 2017
* Time: 1:35:26 PM
*
* Project: csci205FinalProject
* Package: SceneBuilder
* File: asteroidMain
* Description: Final Project for csci205
*
* ****************************************
 */
package SceneBuilder;

import Models.Ship;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author ayg001
 */
public class asteroidMain extends Application {

    private Ship ship;
    private ImageView img;
    Stage theStage;
    VBox main;
    VBox options;
    VBox controls;
    HBox mainButtons;
    HBox optionButtons;
    Button ok;
    Button optionsButton;
    Button quit;
    Button backBtn;
    Button optionQuit;
    Button playAgain;
    Button endQuit;
    Button goToMain;
    Scene mainScene;
    Scene optionsScene;
//    HBox ships;

    //private ANNModel model;
    @Override
    public void start(Stage primaryStage) throws IOException, InterruptedException {

        theStage = primaryStage;
        main = new VBox(10);
        Label mainTitle = new Label("Welcome To Asteroids");
        main.getChildren().add(mainTitle);
        mainTitle.setTranslateY(150);
        mainTitle.setFont(new Font(60));
        mainTitle.setTextFill(Color.WHITE);
        main.setPrefHeight(800);
        main.setPrefWidth(1300);
        ok = new Button("Play");
        ok.setOnAction(e -> {
            try {
                ButtonClicked(e);
            } catch (IOException ex) {
                Logger.getLogger(asteroidMain.class.getName()).log(Level.SEVERE,
                                                                   null, ex);
            }
        });
        optionsButton = new Button("Options");
        optionsButton.setOnAction(e -> {
            try {
                ButtonClicked(e);
            } catch (IOException ex) {
                Logger.getLogger(asteroidMain.class.getName()).log(Level.SEVERE,
                                                                   null, ex);
            }
        });
        quit = new Button("Quit");
        quit.setOnAction(e -> {
            try {
                ButtonClicked(e);
            } catch (IOException ex) {
                Logger.getLogger(asteroidMain.class.getName()).log(Level.SEVERE,
                                                                   null, ex);
            }
        });

        mainButtons = new HBox(10);

        mainButtons.getChildren().addAll(ok, optionsButton, quit);
        main.getChildren().add(mainButtons);

        BackgroundImage back = new BackgroundImage(
                new Image("MainMenu.jpg"),
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);
        BackgroundImage[] images = {back};
        main.setBackground(new Background(images));
        main.setAlignment(Pos.CENTER);
        mainButtons.setAlignment(Pos.BOTTOM_CENTER);
        mainButtons.setTranslateY(200);

        //System.exit(0);
        options = new VBox(10);
        Label optionsTitle = new Label("Options");
        options.getChildren().add(optionsTitle);
        optionsTitle.setAlignment(Pos.TOP_CENTER);
        optionsTitle.setTranslateX(550);
        optionsTitle.setFont(new Font(60));
        optionsTitle.setTextFill(Color.WHITE);
        options.setPrefSize(1300, 800);
        controls = new VBox(10);
        controls.setMinSize(300, 500);
        controls.setPrefSize(400, 600);
        controls.setAlignment(Pos.BOTTOM_CENTER);
        Label controlTitle = new Label("Controls");
        controlTitle.setTextFill(Color.WHITE);
        controls.getChildren().add(controlTitle);
        Label forward = new Label("Forward : up arrow key");
        forward.setTextFill(Color.WHITE);
        controls.getChildren().add(forward);
        Label left = new Label("Rotate left : left arrow key");
        left.setTextFill(Color.WHITE);
        controls.getChildren().add(left);
        Label right = new Label("Rotate right : right arrow key");
        right.setTextFill(Color.WHITE);
        controls.getChildren().add(right);
        Label shoot = new Label("Shoot : w");
        shoot.setTextFill(Color.WHITE);
        controls.getChildren().add(shoot);
        Label superMove = new Label(
                "Activate super (destroy all asteroids on screen when progress reaches 100) : e");
        superMove.setTextFill(Color.WHITE);
        controls.getChildren().add(superMove);
        options.getChildren().add(controls);
        optionButtons = new HBox(10);
        backBtn = new Button("Back");
        backBtn.setOnAction(e -> {
            try {
                ButtonClicked(e);
            } catch (IOException ex) {
                Logger.getLogger(asteroidMain.class.getName()).log(Level.SEVERE,
                                                                   null, ex);
            }
        });
        optionQuit = new Button("Quit");
        optionQuit.setOnAction(e -> {
            try {
                ButtonClicked(e);
            } catch (IOException ex) {
                Logger.getLogger(asteroidMain.class.getName()).log(Level.SEVERE,
                                                                   null, ex);
            }
        });
        optionButtons.getChildren().addAll(backBtn, optionQuit);
        options.getChildren().add(optionButtons);
        optionButtons.setAlignment(Pos.BOTTOM_RIGHT);
        options.setBackground(new Background(images));

        mainScene = new Scene(main);
        optionsScene = new Scene(options);

        primaryStage.setTitle("Asteroids");
        primaryStage.setScene(mainScene);
        primaryStage.sizeToScene();

        primaryStage.show();

    }

    /**
     * Helper method that changes scenes, called when a button is pressed
     *
     * @param e - the ActionEvent
     * @throws IOException
     */
    public void ButtonClicked(ActionEvent e) throws IOException {
        if (e.getSource() == ok) {
            FXMLLoader loader = new FXMLLoader();
            Class thisClass = asteroidMain.class;
            InputStream in = thisClass.getResourceAsStream("asteroidFXML.fxml");
            loader.setBuilderFactory(new JavaFXBuilderFactory());
            loader.setLocation(thisClass.getResource("asteroidFXML.fxml"));
            Parent root = loader.load(in);
            asteroidController ctrl = loader.getController();
            img = new ImageView();
            //Image i = new Image("file:~/csci205FinalProject/src/ship.png");
            Image i = new Image("ship.png", 68, 40, true, true);
            img.setImage(i);
            ship = new Ship(img);
            ship.setVelocity(new Point2D(0, 0));
            ship.setXPos(600);
            ship.setYPos(400);
            ctrl.addPiece(ship);
            ctrl.instantiateShip(ship);

            AnimationTimer theTimer = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    ctrl.onUpdate();
                    if (ctrl.getGameOn() == false) {
                        stop();
                        VBox endGame = new VBox(10);
                        endGame.setPrefHeight(800);
                        endGame.setPrefWidth(1300);
                        BackgroundImage over = new BackgroundImage(
                                new Image("gameOver.jpg"),
                                BackgroundRepeat.REPEAT,
                                BackgroundRepeat.REPEAT,
                                BackgroundPosition.CENTER,
                                BackgroundSize.DEFAULT);
                        BackgroundImage[] images = {over};
                        endGame.setBackground(new Background(images));
                        endQuit = new Button("Quit");
                        endQuit.setOnAction(x -> {
                            try {
                                ButtonClickedInner(x);
                            } catch (IOException ex) {
                                Logger.getLogger(asteroidMain.class.getName()).log(
                                        Level.SEVERE,
                                        null, ex);
                            }
                        });
                        goToMain = new Button("Main Menu");
                        goToMain.setOnAction(x -> {
                            try {
                                ButtonClickedInner(x);
                            } catch (IOException ex) {
                                Logger.getLogger(asteroidMain.class.getName()).log(
                                        Level.SEVERE,
                                        null, ex);
                            }
                        });
                        Label score = new Label("Score : " + ctrl.getScore());
                        score.setFont(new Font(60));
                        score.setTextFill(Color.WHITE);
                        Label placeholder = new Label();
                        endGame.getChildren().add(score);
                        endGame.getChildren().addAll(goToMain,
                                                     endQuit, placeholder);
                        endGame.setAlignment(Pos.BOTTOM_CENTER);
                        Scene endScreen = new Scene(endGame);
                        theStage.setScene(endScreen);
                        theStage.show();
                    }
                }
            };

            Scene scene = new Scene(root);

            scene.setOnKeyPressed(ctrl);
            scene.setOnKeyReleased(ctrl);
            ctrl.generateAsteroids();
            theTimer.start();

            theStage.setScene(scene);
            theStage.show();

        }

        else if (e.getSource() == optionsButton) {
            theStage.setScene(optionsScene);
            theStage.show();
        }

        else if (e.getSource() == quit) {
            System.exit(0);
        }
        else if (e.getSource() == optionQuit) {
            System.exit(0);
        }
        else if (e.getSource() == backBtn) {
            theStage.setScene(mainScene);
            theStage.show();
        }
    }

    /**
     * Helper method that changes scenes
     *
     * @param x
     * @throws IOException
     */
    public void ButtonClickedInner(ActionEvent x) throws IOException {
        if (x.getSource() == endQuit) {
            System.exit(0);
        }

        else if (x.getSource() == goToMain) {
            theStage.setScene(mainScene);
            theStage.show();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
