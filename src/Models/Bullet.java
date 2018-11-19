/* *****************************************
* CSCI205 - Software Engineering and Design
* Spring 2017
*
* Name: Andrew Gray. John Piermatteo, Dylan Zucker, Dan Kershner
* Date: Apr 12, 2017
* Time: 4:29:23 PM
*
* Project: csci205FinalProject
* Package: Models
* File: Bullet
* Description: Final Project for csci205
*
* ****************************************
 */
package Models;

import javafx.scene.image.ImageView;

/**
 *
 * @author jsp029
 */
public class Bullet extends GamePiece {

    /**
     * How far the bullet has traveled
     */
    private double distanceTravelled;

    /**
     * How far the bullet can travel before it gets destroyed
     */
    private final double maxDistance = 650;

    public Bullet(ImageView imageView) {
        super(imageView);
        distanceTravelled = 0;
    }

    /**
     * Updates the coordinates and distanceTraveled of the bullet
     */
    @Override
    public void update() {
        double xOffset = velocity.getX() * 1.2;
        this.xPos += xOffset;
        double yOffset = velocity.getY() * 1.2;
        this.yPos += yOffset;
        this.distanceTravelled += Math.sqrt(Math.pow(xOffset, 2) + Math.pow(
                yOffset,
                2));
        if (xPos <= 0) {
            xPos = 1299;
        }
        if (yPos <= 0) {
            yPos = 799;
        }

        xPos %= 1300;

        yPos %= 800;
        if (distanceTravelled >= maxDistance) {
            this.setAlive(false);
        }

    }

}
