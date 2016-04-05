package nl.tudelft.jpacman.INF215;

/**
 * #### Story 4: Suspend the Game
 * As a player,
 * I want to be able to suspend the game;
 * So  that I can pause and do something else.
 */

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.game.Game;

public class Scenario4Tests {

	private Launcher launcher;
	private Game game;

	@Before
	public void setUp() throws Exception {
		launcher = new Launcher();
		launcher.launch();
		game = launcher.getGame();
	}

	@After
	public void tearDown() throws Exception {
		launcher.dispose();
	}

//	Scenario S4.1: Suspend the game.
//	Given the game has started; When  the player clicks the "Stop" button;
//	Then all moves from ghosts and the player are suspended.	
	@Test
	public void testS41() {		
		game.start();
        assertTrue(game.isInProgress());
        game.stop();
        assertFalse(game.isInProgress());
	}
	
	
//	Scenario S4.2: Restart the game.
//	Given the game is suspended; When  the player hits the "Start" button;
//	Then  the game is resumed.
	@Test
	public void testS42() {		
		game.start();
        assertTrue(game.isInProgress());
	}

}
