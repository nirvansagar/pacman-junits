package nl.tudelft.jpacman.INF215;
/**
 * As a player, 
 * I want to move my Pacman arround on the board;
 * So that I can earn all points and win the game.
 */

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.collect.Lists;

import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.game.GameFactory;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.LevelFactory;
import nl.tudelft.jpacman.level.MapParser;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.level.PlayerFactory;
import nl.tudelft.jpacman.npc.ghost.GhostFactory;
import nl.tudelft.jpacman.sprite.PacManSprites;
import nl.tudelft.jpacman.ui.PacManUI;
import nl.tudelft.jpacman.ui.PacManUiBuilder;

public class Scenario2Tests {
	
	private MapParser parser;
	private Level l;
	private PacManUI pacManUI;
	private Game game;
	private Player player;

	public void setUp(boolean hasGhost) throws Exception {
		PacManSprites sprites = new PacManSprites();
		parser = new MapParser(new LevelFactory(sprites, new GhostFactory(
				sprites)), new BoardFactory(sprites));
		if(hasGhost){
			System.out.println("ghost level");
			l = parser.parseMap(Lists.newArrayList("####", 
												   "#PG#", 
												   "####"));	
		} else {
			l = parser.parseMap(Lists.newArrayList("#####", 
												   "# P.#", 
												   "#####"));
		}
		
		game = makeGame(sprites);
		PacManUiBuilder builder = new PacManUiBuilder().withDefaultButtons();
		pacManUI = builder.build(game);
		pacManUI.start();	
		player = game.getPlayers().get(0);
		game.start();
	}
	
	public Game makeGame(PacManSprites sprites) {
		GameFactory gf = new GameFactory(new PlayerFactory(sprites));
		return gf.createSinglePlayerGame(l);
	}

	public void tearDown() throws Exception {
		game.stop();
		pacManUI.dispose();
	}

//	Scenario S2.1: The player consumes
//	Given the game has started, and  my Pacman is next to a square containing a pellet;
//	When  I press an arrow key towards that square;
//	Then  my Pacman can move to that square, and  I earn the points for the pellet,and  the pellet disappears from that square.
	@Test
	public void testS21() throws Exception {
		setUp(false);
		assertEquals(0, player.getScore());
		game.move(player, Direction.EAST);
		assertEquals(10, player.getScore());
		Thread.sleep(1000);
		tearDown();
	}
	
	
//	Scenario S2.2: The player moves on empty square
//	Given the game has started, my Pacman is next to an empty square;
//	When  I press an arrow key towards that square;
//	Then  my Pacman can move to that square ands my points remain the same.
	@Test
	public void testS22() throws Exception {
		setUp(false);
		assertEquals(0, player.getScore());
		game.move(player, Direction.WEST);
		assertEquals(0, player.getScore());
		Thread.sleep(1000);
		tearDown();
	}
	
//	Scenario S2.3: The player dies
//	Given the game has started,and my Pacman is next to a cell containing a ghost;
//	When  I press an arrow key towards that square; Then my Pacman dies, and  the game is over.
	@Test
	public void testS23() throws Exception {
		setUp(true);
		assertTrue(player.isAlive());
		game.move(player, Direction.EAST);
		System.out.println(player.isAlive());
		assertFalse(player.isAlive());
		Thread.sleep(2000);
		tearDown();
	}
	
//	Scenario S2.4: The move fails
//	Given the game has started, and my Pacman is next to a cell containing a wall;
//	When  I press an arrow key towards that cell; Then  the move is not conducted.
	@Test
	public void testS24() throws Exception {
		setUp(false);
		Square prev = player.getSquare();
		game.move(player, Direction.NORTH);
		Square current = player.getSquare();
		assertEquals(prev, current);
		Thread.sleep(1000);
		tearDown();
	}
	
//	Scenario S2.5: Player wins, extends S2.2
//	When  I have eaten the last pellet; Then  I win the game.
	@Test
	public void testS25() throws Exception {
		setUp(false);
		game.move(player, Direction.EAST);
		int pellets = l.remainingPellets();
		assertEquals(0, pellets);
		assertFalse(game.isInProgress());
		Thread.sleep(1000);
		tearDown();
	}

}
