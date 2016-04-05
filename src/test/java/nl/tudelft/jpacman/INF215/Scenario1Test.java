package nl.tudelft.jpacman.INF215;

/**
 *  
 * As a player
 * I want to start the game
 * so that I can actually playScenario S1.1: Start.
 */

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.game.Game;

public class Scenario1Test {
	private Launcher launcher;

	@Before
	public void setUp() throws Exception {
		launcher = new Launcher();
		launcher.launch();
	}

	@After
	public void tearDown() throws Exception {
		launcher.dispose();
	}

	/**
	 * Scenario S1.1
	 * Given the user has launched the JPacman GUI;
     * When  the user presses the "Start" button;
     * Then  the game should start.
	 */
	
	@Test
	public void testS11() {
		Game game = launcher.getGame();
        assertFalse(game.isInProgress());
        game.start();
        try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        assertTrue(game.isInProgress());
	}

}
