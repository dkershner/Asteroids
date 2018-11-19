/* *****************************************
* CSCI205 - Software Engineering and Design
* Spring 2017
*
* Name: Andrew Gray. John Piermatteo, Dylan Zucker, Dan Kershner
* Date: Apr 30, 2017
* Time: 9:01:02 PM
*
* Project: csci205FinalProject
* Package: SceneBuilder
* File: asteroidControllerTest
* Description: Final Project for csci205
*
* ****************************************
 */
package SceneBuilder;

import Models.Asteroid;
import Models.Bullet;
import Models.Ship;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import junit.framework.TestCase;
import static junit.framework.TestCase.assertTrue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author jsp029
 */
public class asteroidControllerTest extends TestCase {

    asteroidController ctrl;

    public asteroidControllerTest(String testName) {
        super(testName);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        ctrl = new asteroidController();
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of btnPauseMethod method, of class asteroidController.
     */
    @Test
    public void testBtnPauseMethod() {
        assertFalse(ctrl.getPaused());
        MouseEvent mockEvent = new MouseEvent(MouseEvent.MOUSE_CLICKED, 0,
                                              0, 0, 0, MouseButton.PRIMARY, 1,
                                              true, true, true, true,
                                              true, true, true, true, true, true,
                                              null);
        ctrl.btnPauseMethod(mockEvent);
        assertTrue(ctrl.getPaused());
    }

    /**
     * Test of instantiateShip method, of class asteroidController.
     */
    @Test
    public void testInstantiateShip() {
        assertNull(ctrl.getShip());
        ImageView imgv = new ImageView();
        ctrl.instantiateShip(new Ship(imgv));
        assertNotNull(ctrl.getShip());
    }

    /**
     * Test of addBullet method, of class asteroidController.
     */
    @Test
    public void testAddBullet() {
        assertTrue(ctrl.getBullets().isEmpty());
        ImageView imgv = new ImageView();
        ctrl.addBullet(new Bullet(imgv));
        assertFalse(ctrl.getBullets().isEmpty());
    }

    /**
     * Test of addAsteroid method, of class asteroidController.
     */
    @Test
    public void testAddAsteroid() {
        assertTrue(ctrl.getAsteroids().isEmpty());
        ImageView imgv = new ImageView();
        ctrl.addAsteroid(new Asteroid(imgv));
        assertFalse(ctrl.getAsteroids().isEmpty());
    }

    /**
     * Test of endGame method, of class asteroidController.
     */
    @Test
    public void testEndGame() {
        assertTrue(ctrl.getGameOn());
        ctrl.endGame();
        assertFalse(ctrl.getGameOn());
    }

    /**
     * Test of handle method, of class asteroidController.
     */
    @Test
    public void testHandle() {

    }

}
