/* *****************************************
* CSCI205 - Software Engineering and Design
* Spring 2017
*
* Name: Andrew Gray. John Piermatteo, Dylan Zucker, Dan Kershner
* Date: Apr 30, 2017
* Time: 6:28:26 PM
*
* Project: csci205FinalProject
* Package: Models
* File: GamePieceTest
* Description: Final Project for csci205
*
* ****************************************
 */
package Models;

import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author jsp029
 */
public class GamePieceTest extends TestCase {

    GamePiece piece;

    public GamePieceTest(String testName) {
        super(testName);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        ImageView imgv = new ImageView();
//        imgv.setImage(
//                new Image("newBullet.png", 30, 30, true, true));
        piece = new GamePiece(imgv);
        piece.setPieceWidth(30);
        piece.setPieceHeight(30);
        piece.setVelocity(new Point2D(1, 1));
        piece.setXPos(5);
        piece.setYPos(5);
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of update method, of class GamePiece.
     */
    @Test
    public void testUpdate() {
        assertEquals(piece.getXPos(), 5.0);
        assertEquals(piece.getYPos(), 5.0);
        piece.update();
        assertEquals(piece.getXPos(), 6.2);
        assertEquals(piece.getYPos(), 6.2);
    }

    /**
     * Test of isNotAlive method, of class GamePiece.
     */
    @Test
    public void testIsNotAlive() {
        assertFalse(piece.isNotAlive());
    }

    /**
     * Test of rotateRight method, of class GamePiece
     */
    @Test
    public void testRotateRight() {
        assertEquals(piece.getView().getRotate(), 0.0);
        piece.rotateRight();
        assertEquals(piece.getView().getRotate(), 7.0);
    }

    /**
     * Test of rotateLeft method, of class GamePiece
     */
    @Test
    public void testRotateLeft() {
        assertEquals(piece.getView().getRotate(), 0.0);
        piece.rotateLeft();
        assertEquals(piece.getView().getRotate(), 353.0);
    }
}
