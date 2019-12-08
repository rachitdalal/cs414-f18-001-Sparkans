package com.sparkans.banqi.game;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import org.junit.jupiter.api.*;

public class CannonTest {
	private BanqiBoard board;

	//creating a  new board.
	@BeforeEach
	void init() {
		board = new BanqiBoard();
	}
	@Test
	void cannonPlacedOnBoard() throws IllegalPositionException,IllegalMoveException{
		Cannon cR = new Cannon(board, BanqiPiece.Color.RED);
		board.placePiece(cR,"c2");
		board.move("c2","c7");
		assertTrue(board.getPiece("c2").legalMoves().containsAll(Arrays.asList("c3","c4","c5","c6","c7")));

	}
	@Test
	public void cannonDoNotAttackSameColor() throws IllegalMoveException,IllegalPositionException {
		Cannon cR = new Cannon(board, BanqiPiece.Color.RED);
		board.placePiece(cR,"c3");
		Soldier sR = new Soldier(board, BanqiPiece.Color.RED);
		board.placePiece(sR,"b3");
		assertFalse(board.getPiece("c3").legalMoves().contains("b3"));
	}

	@Test
	public void cannonCannotAttackHigherRankThatOfItself() throws IllegalMoveException,IllegalPositionException{
		Cannon cR = new Cannon(board, BanqiPiece.Color.RED);
		board.placePiece(cR,"a5");
		Horse hW = new Horse(board, BanqiPiece.Color.WHITE);
		board.placePiece(hW,"a4");
		assertFalse(board.getPiece("a5").legalMoves().contains("a4"));
	}
	@Test
	public void  cannonCannotAttackCannon()throws IllegalMoveException,IllegalPositionException{
		Cannon aR = new Cannon(board, BanqiPiece.Color.RED);
		board.placePiece(aR,"c1");
		Cannon aW = new Cannon(board, BanqiPiece.Color.WHITE);
		board.placePiece(aW,"c2");
		assertTrue(board.getPiece("c1").legalMoves().contains("c2"));
	}


}
