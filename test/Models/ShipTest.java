/* *****************************************
* CSCI205 - Software Engineering and Design
* Spring 2017
*
* Name: Andrew Gray. John Piermatteo, Dylan Zucker, Dan Kershner
* Date: Apr 30, 2017
* Time: 9:00:55 PM
*
* Project: csci205FinalProject
* Package: Models
* File: ShipTest
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
public class ShipTest extends TestCase {

    Ship ship;

    public ShipTest(String testName) {
        super(testName);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        ImageView imgv = new ImageView();
        ship = new Ship(imgv);
        ship.setPieceWidth(40);
        ship.setPieceHeight(68);
        ship.setXPos(600);
        ship.setYPos(400);
        ship.setVelocity(new Point2D(1, 1));
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of move method, of class Ship.
     */
    @Test
    public void testMove() {
        assertEquals(ship.getXPos(), 600.0);
        assertEquals(ship.getYPos(), 400.0);
        ship.move();
        assertEquals(ship.getXPos(), 600.0);
        assertEquals(ship.getYPos(), 399.2);
    }

    /**
     * Test of keepMoving method, of class Ship.
     */
    @Test
    public void testKeepMoving() {
        assertEquals(ship.getXPos(), 600.0);
        assertEquals(ship.getYPos(), 400.0);
        ship.keepMoving();
        assertEquals(ship.getXPos(), 601.0);
        assertEquals(ship.getYPos(), 401.0);
    }

    /**
     * Test of slowDown method, of class Ship.
     */
    @Test
    public void testSlowDown() {
        assertEquals(ship.getAcc(), 1.0);
        ship.slowDown();
        assertEquals(ship.getAcc(), 0.94);
    }

    /**
     * Test of resetAcc method, of class Ship.
     */
    @Test
    public void testResetAcc() {
        ship.slowDown();
        assertEquals(ship.getAcc(), 0.94);
        ship.resetAcc();
        assertEquals(ship.getAcc(), 1.0);
    }

    /**
     * Test of incrementSuperMeter method, of class Ship.
     */
    @Test
    public void testIncrementSuperMeter() {
        assertEquals(ship.getSuperMeter(), 0);
        ship.incrementSuperMeter(10);
        assertEquals(ship.getSuperMeter(), 10);
        ship.incrementSuperMeter(130);
        assertEquals(ship.getSuperMeter(), 100);
    }

    /**
     * Test of useSuper method, of class Ship.
     */
    @Test
    public void testUseSuper() {
        ship.incrementSuperMeter(130);
        assertEquals(ship.getSuperMeter(), 100);
        ship.useSuper();
        assertEquals(ship.getSuperMeter(), 0);
    }

}
