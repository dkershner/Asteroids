/* *****************************************
* CSCI205 - Software Engineering and Design
* Spring 2017
*
* Name: Andrew Gray. John Piermatteo, Dylan Zucker, Dan Kershner
* Date: Apr 10, 2017
* Time: 1:19:07 PM
*
* Project: csci205FinalProject
* Package: Models
* File: GamePiece
* Description: Final Project for csci205
*
* ****************************************
 */
package Models;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author dtk008
 */
public class GamePiece {

    protected double width;

    protected double height;

    /**
     * The x-coordinate of the GamePiece
     */
    protected double xPos;

    /**
     * The y-coordinate of the GamePiece
     */
    protected double yPos;

    /**
     * The image used to represent the GamePiece
     */
    protected Image imageView;

    /**
     * The x component of the velocity
     */
    protected double changeX;

    /**
     * The y-component of the velocity
     */
    protected double changeY;

    /**
     * Node containing the imageView and the coordinates of the
     */
    protected ImageView view;

    /**
     * The velocity of the GamePiece
     */
    protected Point2D velocity = new Point2D(0, 0);

    /**
     * Determines whether the GamePiece should be destroyed or not
     */
    protected boolean alive;

    /**
     * The name of the file containing the imageView
     */
    protected String imageFile;

    public GamePiece(ImageView view) {
        //this.imageView = imageView;
        //this.imageView.setViewport(new Rectangle2D(xPos, yPos, width, height));
        //this.getParent().add(imageView);
        this.view = view;
        this.imageView = view.getImage();
        alive = true;
    }

    /**
     * Determines whether the GamePiece is colliding with another GamePiece
     * piece
     *
     * @param piece - the other GamePiece used to check for collisions
     * @return true if the two pieces are colliding
     */
    public boolean isColliding(GamePiece piece) {
        boolean xOverlap = false;
        boolean yOverlap = false;
        if (!(this instanceof Bullet)) {
            if ((piece.getRotate() <= 45 || piece.getRotate() >= 315) || (piece.getRotate() >= 135 && piece.getRotate() <= 225)) {
                if (xPos > piece.getXPos()) {
                    xOverlap = (Math.abs(xPos - piece.getXPos()) < (piece.getView().getImage().getWidth()));
                }
                else {
                    xOverlap = (Math.abs(xPos - piece.getXPos()) < (view.getImage().getWidth()));
                }
                if (yPos > piece.getYPos()) {
                    yOverlap = (Math.abs(yPos - piece.getYPos()) < (piece.getView().getImage().getHeight()));
                }
                else {
                    yOverlap = (Math.abs(yPos - piece.getYPos()) < (view.getImage().getHeight()));
                }

            }
            else {
                if (xPos > piece.getXPos()) {
                    xOverlap = (Math.abs(xPos - piece.getXPos()) < (piece.getView().getImage().getHeight()));
                }
                else {
                    xOverlap = (Math.abs(xPos - piece.getXPos()) < (view.getImage().getWidth()));
                }
                if (yPos < piece.getYPos()) {
                    yOverlap = (Math.abs(yPos - piece.getYPos()) - piece.getView().getImage().getWidth() / 2 < (view.getImage().getWidth()));
                }
                else {
                    yOverlap = (Math.abs(yPos - piece.getYPos()) - piece.getView().getImage().getWidth() / 2 < (piece.getView().getImage().getWidth()));
                }

            }
        }
        else {
            if (yPos < piece.getYPos()) {
                yOverlap = (Math.abs(yPos - piece.getYPos()) < (view.getImage().getHeight()));
            }
            else {
                yOverlap = (Math.abs(yPos - piece.getYPos()) < (piece.getView().getImage().getHeight()));
            }
            if (xPos < piece.getXPos()) {
                xOverlap = (Math.abs(xPos - piece.getXPos()) < (view.getImage().getWidth()));
            }
            else {
                xOverlap = (Math.abs(xPos - piece.getXPos()) < (piece.getView().getImage().getWidth()));
            }

        }

        return xOverlap && yOverlap;
    }

    /**
     * Updates the x and y positions (xPos / yPos) of the GamePiece
     */
    public void update() {
        this.xPos = xPos + velocity.getX() * 1.2;

        this.yPos = yPos + velocity.getY() * 1.2;
        if (xPos <= 0) {
            xPos = 1299;
        }
        if (yPos <= 0) {
            yPos = 799;
        }

        xPos %= 1300;

        yPos %= 800;

    }

    /**
     * Updates the image of the GamePiece
     */
    public void updateUI() {

        view.relocate(xPos, yPos);
    }

    /**
     * Returns the x component of the velocity of the GamePiece
     *
     * @return
     */
    public double getDirectionX() {
        return Math.sin(Math.toRadians(this.getView().getRotate()));
    }

    /**
     * Returns the y component of the velocity of the GamePiece
     *
     * @return
     */
    public double getDirectionY() {
        return -1 * Math.cos(Math.toRadians(this.getView().getRotate()));
    }

    public void setVelocity(Point2D v) {
        this.velocity = v;
    }

    public Point2D getVelocity() {
        return velocity;
    }

    public void setView(ImageView v) {
        this.view = v;
    }

    /**
     * Gets the value representing the rotation of the GamePiece
     *
     * @return
     */
    public double getRotate() {
        return view.getRotate();
    }

    /**
     * Rotates the GamePiece slightly to the right
     */
    public void rotateRight() {

        view.setRotate((view.getRotate() + 7) % 360);
    }

    /**
     * Rotates the GamePiece slightly to the left
     */
    public void rotateLeft() {
        view.setRotate((360 + (view.getRotate() - 7)) % 360);
    }

    public double getPieceWidth() {
        return width;
    }

    public double getPieceHeight() {
        return height;
    }

    public void setPieceWidth(double w) {
        this.width = w;
    }

    public void setPieceHeight(double h) {
        this.height = h;
    }

    public double getXPos() {
        return xPos;
    }

    public double getYPos() {
        return yPos;
    }

    public ImageView getView() {
        return view;
    }

    public boolean isAlive() {
        return alive;
    }

    /**
     * Returns true if alive == false and false if alive == true
     *
     * @return !alive
     */
    public boolean isNotAlive() {
        return !alive;
    }

    public void setAlive(boolean aliveOrDead) {
        alive = aliveOrDead;
    }

    public Image getImageView() {
        return imageView;
    }

    /**
     * Sets both the x and y coordinates
     *
     * @param xPos - new x coordinate
     * @param yPos - new y coordinate
     */
    public void setPos(double xPos, double yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public void setXPos(double i) {
        xPos = i;
    }

    public void setYPos(double i) {
        yPos = i;
    }

    public void setImageFile(String fileName) {
        imageFile = fileName;
    }

    public String getImageFile() {
        return imageFile;
    }
}
