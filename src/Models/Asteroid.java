/* *****************************************
* CSCI205 - Software Engineering and Design
* Spring 2017
*
* Name: Andrew Gray. John Piermatteo, Dylan Zucker, Dan Kershner
* Date: Apr 7, 2017
* Time: 1:05:30 PM
*
* Project: csci205FinalProject
* Package: Models
* File: Asteroid
* Description: Final Project for csci205
*
* ****************************************
 */
package Models;

import javafx.scene.image.ImageView;

/**
 *
 * @author dtk008
 */
public class Asteroid extends GamePiece {

    public Asteroid(ImageView imageView) {
        super(imageView);
    }

    /**
     * Changes the coordinates of the image of the asteroid
     */
    public void move() {
        this.view.setX(view.getX() + changeX);
        this.view.setY(view.getY() + changeY);
    }
}
