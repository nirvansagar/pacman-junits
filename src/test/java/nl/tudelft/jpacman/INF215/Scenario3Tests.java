package nl.tudelft.jpacman.INF215;

//As a ghost;
//I get automatically moved around;
//So that I can try to kill the player.
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.game.GameFactory;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.LevelFactory;
import nl.tudelft.jpacman.level.MapParser;
import nl.tudelft.jpacman.level.Pellet;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.level.PlayerFactory;
import nl.tudelft.jpacman.npc.ghost.Ghost;
import nl.tudelft.jpacman.npc.ghost.GhostFactory;
import nl.tudelft.jpacman.sprite.PacManSprites;
import nl.tudelft.jpacman.ui.PacManUI;
import nl.tudelft.jpacman.ui.PacManUiBuilder;

public class Scenario3Tests {
	
	private MapParser parser;
	private Level l;
	private PacManUI pacManUI;
	private Game game;
	private Player player;
	private Ghost ghost;
	PacManSprites sprites;
	
	@Before
	public void setUp(){
		sprites = new PacManSprites();
		parser = new MapParser(new LevelFactory(sprites, new GhostFactory(sprites)), new BoardFactory(sprites));	
		l = parser.parseMap(Lists.newArrayList(	"#######", 
				   								"#G . P#", 
				   								"#######"));
		game = makeGame(sprites);
		PacManUiBuilder builder = new PacManUiBuilder().withDefaultButtons();
		pacManUI = builder.build(game);
		pacManUI.start();	
		player = game.getPlayers().get(0);
		ghost = (Ghost) l.getBoard().squareAt(1, 1).getOccupants().get(0);
		game.start();
	}
	
	public Game makeGame(PacManSprites sprites) {
		GameFactory gf = new GameFactory(new PlayerFactory(sprites));
		return gf.createSinglePlayerGame(l);
	}

	@After
	public void tearDown() throws Exception {
		game.stop();
		pacManUI.dispose();
	}

//	Scenario S3.1: A ghost moves.
//	Given the game has started, and a ghost is next to an empty cell;
//	When  a tick event occurs; Then the ghost can move to that cell.
	@Test
	public void testS31() throws Exception {
		Board b = l.getBoard();
		Square s2 = b.squareAt(2, 1);
//		System.out.println("s1="+s1.toString() + " s2="+s2.toString());
		assertNotNull(ghost);
//		System.out.println(g.getInterval() );
		Thread.sleep(ghost.getInterval());
		Square s3 = ghost.getSquare();
//		System.out.println(s3.toString());
		assertEquals(s3,s2);
	}
	
	
//	Scenario S3.2: The ghost moves over a square with a pellet.
//	Given the game has started, and a ghost is next to a cell containing a pellet;
//	When  a tick event occurs; Then the ghost can move to the cell with the pellet, and the pellet on that cell is not visible anymore.
	@Test
	public void testS32() throws InterruptedException {
		Board b = l.getBoard();
		Square s2 = b.squareAt(3, 1);
		assertNotNull(ghost);
		Thread.sleep(ghost.getInterval()*2);
		Square s3 = ghost.getSquare();
		Pellet p = mock(Pellet.class);
		assertFalse(s3.getOccupants().contains(p));
		assertEquals(s3,s2);
	}
	
	
//	Scenario S3.3: The ghost leaves a cell with a pellet.
//	Given a ghost is on a cell with a pellet (see S3.2);
//	When  a tick even occurs; Then  the ghost can move away from the cell with the pellet, and the pellet on that cell is is visible again.
	@Test
	public void testS33() throws InterruptedException {
		Board b = l.getBoard();
		Square s2 = b.squareAt(3, 1);
		Square s3 = b.squareAt(4, 1);
		assertNotNull(ghost);
		Thread.sleep(ghost.getInterval()*3);
		Square s4 = ghost.getSquare();
		Unit unit = s2.getOccupants().get(0);
		assertTrue(unit instanceof Pellet);
		assertEquals(s4,s3);
	}
	
	
//	Scenario S3.4: The player dies.
//	Given the game has started, and a ghost is next to a cell containing the player;
//	When a tick event occurs;
//	Then the ghost can move to the player, and the game is over.
	@Test
	public void testS34() throws InterruptedException {
		Board b = l.getBoard();
		Square s2 = b.squareAt(5, 1);
		assertNotNull(ghost);
		Thread.sleep(ghost.getInterval()*4);
		Square s3 = ghost.getSquare();
		assertEquals(s3,s2);
		assertFalse(player.isAlive());
		assertFalse(game.isInProgress());
	}

}
