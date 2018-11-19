/* *****************************************
* CSCI205 - Software Engineering and Design
* Spring 2017
*
* Name: Andrew Gray. John Piermatteo, Dylan Zucker, Dan Kershner
* Date: Apr 10, 2017
* Time: 1:47:19 PM
*
* Project: csci205FinalProject
* Package: SceneBuilder
* File: asteroidController
* Description: Final Project for csci205
*
* ****************************************
 */
package SceneBuilder;

import Models.Asteroid;
import Models.Bullet;
import Models.GamePiece;
import Models.Ship;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Rotate;

/**
 * FXML Controller class
 *
 * @author ayg001
 */
public class asteroidController implements EventHandler<KeyEvent> {

    /**
     * ArrayList of Bullet objects that should be displayed onscreen
     */
    private ArrayList<Bullet> bullets = new ArrayList<>();

    /**
     * ArrayList of asteroid objects that should be displayed onscreen
     */
    private ArrayList<Asteroid> asteroids = new ArrayList<>();

    private Ship ship;

    /**
     * Whether the ship should be rotating to the right
     */
    boolean right = false;

    /**
     * Whether the ship should be moving forward
     */
    boolean up = false;

    /**
     * Whether the ship should be rotating left
     */
    boolean left = false;

    /**
     * Whether the game should be paused
     */
    boolean isPaused = false;

    /**
     * Whether the game is active, gets set to false if ship is destroyed
     */
    boolean gameOn = true;

    private int score;

    /**
     * Thread for creating Asteroid objects
     */
    private Thread th;

    private int maxAsteroids = 7;

    /**
     * Whether the key for moving forward is released
     */
    private boolean release = true;

    /**
     * Array containing the possible Asteroid object sizes
     */
    private int[] sizes = {40, 100, 130};

    /**
     * Boolean to prevent holding w to shoot repeatedly
     */
    private boolean shoot = false;

    /**
     * Value used to delay the creation of new Asteroid objects after the Ship
     * object's super is used
     */
    int superCount = 30;
    @FXML
    private Button btnPause;
    @FXML
    private Label lblScore;

    @FXML
    private Label superNum;

    @FXML
    private Canvas games;
    private GraphicsContext gc;
    private AnimationTimer aTimer;

    /**
     * Handles the pause button being clicked, pauses the game
     *
     * @param event
     */
    @FXML
    void btnPauseMethod(MouseEvent event) {

        if (isPaused == true) {
            isPaused = false;
        }
        else {
            isPaused = true;
        }
    }

    public boolean getGameOn() {
        return gameOn;
    }

    public int getScore() {
        return score;
    }

    public boolean getPaused() {
        return isPaused;
    }

    /**
     * Instantiates a new Ship object
     *
     * @param theShip
     * @param theModel
     */
    public void instantiateShip(Ship theShip) {
        ship = theShip;
        //lblScore.textProperty().bind(new SimpleIntegerProperty(score).asString());
    }

    public Ship getShip() {
        return this.ship;
    }

    public ArrayList<Bullet> getBullets() {
        return this.bullets;
    }

    public ArrayList<Asteroid> getAsteroids() {
        return this.asteroids;
    }

    /**
     * Adds a Bullet object to bullets
     *
     * @param bullet - the Bullet object to be added
     */
    public void addBullet(Bullet bullet) {
        bullets.add(bullet);
        if (bullet.getView().getImage() != null) {
            addPiece(bullet);
        }
    }

    /**
     * Adds an Asteroid object to asteroids
     *
     * @param asteroid - the Asteroid object to be added
     */
    public void addAsteroid(Asteroid asteroid) {
        // GENERATE RANDOM X/Y here and then add piece.
        asteroids.add(asteroid);
        if (asteroid.getView().getImage() != null) {
            addPiece(asteroid);
        }
    }

    /**
     * Adds a GamePiece object to the screen
     *
     * @param piece - the GamePiece to be added
     */
    void addPiece(GamePiece piece) {
        gc = games.getGraphicsContext2D();
        piece.getView().setX(piece.getXPos());
        piece.getView().setY(piece.getYPos());
        gc.drawImage(piece.getView().getImage(), piece.getXPos(),
                     piece.getYPos());
    }

    /**
     * Updates the display and checks for collisions between the ship,
     * asteroids, and bullets
     */
    public void onUpdate() {

        if (isPaused == false) {

            if (gameOn) {

                gc.clearRect(500, 500, 100000, 100000);
                gc.drawImage(new Image("space1.jpg", 1500, 1000, true, true), 0,
                             0);
                //th.stop();
                for (GamePiece bullet : bullets) {
                    //for (GamePiece asteroid : asteroids) {
                    Object[] asteroidsArray = asteroids.toArray();
                    Iterator<Asteroid> iter = asteroids.iterator();
                    for (int i = 0; i < asteroidsArray.length; i++) {
                        Asteroid asteroid = (Asteroid) asteroidsArray[i];
                        if (bullet.isColliding(asteroid)) {
                            //.println("hello");
                            bullet.setAlive(false);
                            asteroid.setAlive(false);
                            score += 100;
                            lblScore.setText(Integer.toString(score));//Update score
                            ship.incrementSuperMeter(5);
                            superNum.setText(
                                    Integer.toString(ship.getSuperMeter()) + "%");
                            if (asteroid.getPieceWidth() != sizes[0]) {
                                int childSize;
                                if (asteroid.getPieceWidth() == sizes[1]) {
                                    childSize = sizes[0];
                                }
                                else {
                                    childSize = sizes[1];
                                }
                                ImageView imgv = new ImageView();

                                imgv.setImage(new Image(asteroid.getImageFile(),
                                                        childSize, childSize,
                                                        true, true)
                                );
                                Asteroid child1 = new Asteroid(imgv);
                                Asteroid child2 = new Asteroid(imgv);
                                child1.setPieceWidth(childSize);
                                child1.setPieceHeight(childSize);
                                child1.setXPos(asteroid.getXPos());
                                child1.setYPos(asteroid.getYPos());
                                child1.setVelocity(asteroid.getVelocity());
                                child2.setXPos(asteroid.getXPos());
                                child2.setYPos(asteroid.getYPos());
                                child2.setPieceWidth(childSize);
                                child2.setPieceHeight(childSize);
                                child1.setImageFile(asteroid.getImageFile());
                                child2.setImageFile(asteroid.getImageFile());
                                //double child2XVel = asteroid.getVelocity().getX();
                                //double child2YVel = asteroid.getVelocity().getY();
                                child2.setVelocity(new Point2D(0.5, 0.5));
                                asteroids.add(child1);
                                asteroids.add(child2);
                            }
                        }
//                        if (asteroid.isNotAlive()) {
//                            .remove();
//                        }
                    }
                    //}
                }
                asteroids.removeIf(GamePiece::isNotAlive);
                bullets.removeIf(GamePiece::isNotAlive);

                //System.out.println(asteroids.size());
                for (GamePiece as : asteroids) {
                    if (as.isColliding(ship)) {
                        endGame();
                    }
                }

                for (Bullet bullet : bullets) {
//            if (bullet.getXPos() > 1300 || bullet.getXPos() < 0 || bullet.getYPos() > 800 || bullet.getXPos() < 0) {
//                bullets.remove(bullet);
//            }
//            else {
                    redraw(bullet);

                    //}
                }

                for (GamePiece a : asteroids) {
                    redraw(a);
                }
                //th.resume();
            }
        }
//            else {
////                gc.drawImage(new Image("gameOver.jpg", 1300, 1500, true, true),
////                             0,
////                             0);
//                return;
//            }

        if (up) {
            ship.move();
            gc.save();
            Rotate r = new Rotate(ship.getView().getRotate(),
                                  ship.getXPos() + ship.getView().getImage().getWidth() / 2,
                                  ship.getYPos() + ship.getView().getImage().getHeight() / 2);
            gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(),
                            r.getTx(), r.getTy());
            gc.drawImage(ship.getView().getImage(), ship.getXPos(),
                         ship.getYPos());
            gc.restore();
        }
        else {
            ship.slowDown();
            ship.keepMoving();
        }
        if (right) {
            ship.rotateRight();
            gc.save();
            Rotate r = new Rotate(ship.getView().getRotate(),
                                  ship.getXPos() + ship.getView().getImage().getWidth() / 2,
                                  ship.getYPos() + ship.getView().getImage().getHeight() / 2);
            gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(),
                            r.getTx(), r.getTy());
            gc.drawImage(ship.getView().getImage(), ship.getXPos(),
                         ship.getYPos());
            gc.restore();
        }
        else if (left) {
            ship.rotateLeft();
            gc.save();
            Rotate r = new Rotate(ship.getView().getRotate(),
                                  ship.getXPos() + ship.getView().getImage().getWidth() / 2,
                                  ship.getYPos() + ship.getView().getImage().getHeight() / 2);
            gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(),
                            r.getTx(), r.getTy());
            gc.drawImage(ship.getView().getImage(), ship.getXPos(),
                         ship.getYPos());
            gc.restore();
        }

        gc.save();
        Rotate r = new Rotate(ship.getView().getRotate(),
                              ship.getXPos() + ship.getView().getImage().getWidth() / 2,
                              ship.getYPos() + ship.getView().getImage().getHeight() / 2);
        gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(),
                        r.getTx(), r.getTy());
        gc.drawImage(ship.getView().getImage(), ship.getXPos(),
                     ship.getYPos());
        gc.restore();

        asteroids.forEach(GamePiece::update);

        superCount += 1;
        //gc.drawImage(new Image("newBullet.png", 10, 10, true, true),
        //           ship.getXPos(), ship.getYPos());

    }

    /**
     * Called from a task, generates new Asteroid objects
     */
    public void generateAsteroids() {
        th = new Thread(task);
        th.setDaemon(true);
        th.start();

    }
    Task<Void> task = new Task<Void>() {
        @Override
        protected Void call() throws Exception {

            while (gameOn) {

//                if (asteroids.size() > 70) { //Make it a max asteroids that collisionCheck number goes up as you play longer
//                    continue;
//                }
                if (score % 1000 == 0 && score != 0 && maxAsteroids < 17) {
                    score += 100;
                    maxAsteroids += 4;
                }
                if (score % 1500 == 0 && score != 0) {
                    ImageView img = new ImageView();
                    img.setImage(new Image("Dancy.png", 250, 200, true, true));
                    Asteroid a = new Asteroid(img);
                    a.setImageFile("Dancy.png");
                    a.setXPos(0);
                    a.setYPos((Math.random() * (games.getHeight() + 1)));
                    double xv = Math.sin(Math.toRadians(
                            Math.random() * 110 + 1));
                    double yv = -1 * Math.cos(
                            Math.toRadians(Math.random() * 110 + 1));
                    a.setVelocity(new Point2D(xv * 2.5, yv * 2.5));
                    addAsteroid(a);
                    score += 100;
                }
                if (score % 2000 == 0 && score != 0) {
                    ImageView img = new ImageView();
                    img.setImage(new Image("ben.png", 250, 200, true, true));
                    Asteroid a = new Asteroid(img);
                    a.setImageFile("ben.png");
                    a.setXPos(0);
                    a.setYPos((Math.random() * (games.getHeight() + 1)));
                    double xv = Math.sin(Math.toRadians(
                            Math.random() * 110 + 1));
                    double yv = -1 * Math.cos(
                            Math.toRadians(Math.random() * 110 + 1));
                    a.setVelocity(new Point2D(xv * 2.5, yv * 2.5));
                    addAsteroid(a);
                    score += 100;
                }
                if (Math.random() < 0.02 && asteroids.size() < maxAsteroids && isPaused == false && superCount >= 30) {
                    Random generator = new Random();
                    int i = generator.nextInt(sizes.length);
                    int size = sizes[i];
                    ImageView imgv = new ImageView();
                    imgv.setImage(
                            new Image("newAsteroid.png", size, size, true, true));

                    Asteroid a = new Asteroid(imgv);
                    //a.setPieceHeight(size);
                    //a.setPieceWidth(size);
                    double x = Math.random();
                    if (x <= 0.25) {
                        a.setXPos(0);
                        a.setYPos((Math.random() * (games.getHeight() + 1)));
                        double xv = Math.sin(Math.toRadians(
                                Math.random() * 110 + 1));
                        double yv = -1 * Math.cos(
                                Math.toRadians(Math.random() * 110 + 1));
                        a.setVelocity(new Point2D(xv * 1.3, yv * 1.3));
                    }
                    else if (x > .25 && x <= .5) {
                        a.setXPos(1200);
                        a.setYPos((Math.random() * (games.getHeight() + 1)));
                        double xv = Math.sin(Math.toRadians(
                                Math.random() * -110 - 1));
                        double yv = -1 * Math.cos(
                                Math.toRadians(Math.random() * 110 + 1));
                        a.setVelocity(new Point2D(xv * 1.4, yv * 1.4));
                    }
                    else if (x > .5 && x <= .75) {
                        a.setYPos(0);
                        a.setXPos((Math.random() * (games.getWidth() + 1)));
                        double xv = Math.sin(Math.toRadians(
                                Math.random() * 110 + 1));
                        double yv = -1 * Math.cos(
                                Math.toRadians(Math.random() * -110 - 1));
                        a.setVelocity(new Point2D(xv * 1.3, yv * 1.3));
                    }
                    else {
                        a.setYPos(700);
                        a.setXPos((Math.random() * (games.getWidth() + 1)));
                        double xv = Math.sin(Math.toRadians(
                                Math.random() * -110 - 1));
                        double yv = -1 * Math.cos(
                                Math.toRadians(Math.random() * 110 + 1));
                        a.setVelocity(new Point2D(xv * 1.1, yv * 1.1));
                    }
                    a.setImageFile("newAsteroid.png");
                    addAsteroid(a);
                }
            }

            return null;
        }
    };

    /**
     * Ends the game
     */
    public void endGame() {
        gameOn = false;
        //aTimer.stop(); //TODO if we do this we have to delete collisionCheck ship object

        // TODO: put in code to end collisionCheck game. Maybe a pass in ATimer and do timer.stop()
    }

    /**
     * Helper method that updates a GamePiece object onscreen
     *
     * @param piece - the GamePiece object
     */
    public void redraw(GamePiece piece) {
        piece.update();

        gc.drawImage(piece.getView().getImage(), piece.getXPos(),
                     piece.getYPos());

    }

    /**
     * Handler for different key presses that moves the Ship, shoots a bullet,
     * or uses the super
     *
     * @param event - the KeyEvent
     */
    @Override
    public void handle(KeyEvent event) { //add if collisionCheck fire button is pressed with another button
        if (event.getEventType() == KeyEvent.KEY_PRESSED) {
            if (event.getCode() == KeyCode.W) {
                if (!shoot) {
                    shoot = true;
                    addBullet(ship.shoot());
                }
            }

            if (event.getCode() == KeyCode.RIGHT) {
                right = true;

            }
            if (event.getCode() == KeyCode.UP) {
                if (!up) {
                    ship.resetAcc();
                }
                up = true;
            }
            if (event.getCode() == KeyCode.LEFT) {
                left = true;
            }
            if (event.getCode() == KeyCode.E) {
                if (ship.getSuperMeter() == 100) {
                    asteroids.clear();
                    ship.useSuper();
                    superNum.setText(
                            Integer.toString(ship.getSuperMeter()) + "%");
                    superCount = 0;
                }
            }
        }
        else if (event.getEventType() == KeyEvent.KEY_RELEASED) {
            if (event.getCode() == KeyCode.UP) {

                up = false;
                release = true;
            }
            if (event.getCode() == KeyCode.RIGHT) {
                right = false;
            }
            if (event.getCode() == KeyCode.LEFT) {
                left = false;
            }
            if (event.getCode() == KeyCode.W) {
                shoot = false;
            }
        }

    }
}
