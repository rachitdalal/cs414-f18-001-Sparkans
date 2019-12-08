package com.sparkans.banqi.game;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import org.junit.jupiter.api.*;

public class ChariotTest {
	private BanqiBoard board;

	//creating a  new board.
	@BeforeEach
	void init() {
		board = new BanqiBoard();
	}

	@Test
	void chariotIsOnBoard() throws IllegalMoveException,IllegalPositionException{
		Chariot cW = new Chariot(board, BanqiPiece.Color.WHITE);
		board.placePiece(cW,"d1");
		board.move("d1","d2");
		assertTrue(board.getPiece("d1").legalMoves().contains("d2"));
	}

	@Test

	void chariotKills() throws IllegalMoveException,IllegalPositionException{
		Chariot cW = new Chariot(board, BanqiPiece.Color.WHITE);
		board.placePiece(cW,"d2");
		Cannon cR = new Cannon(board, BanqiPiece.Color.RED);
		board.placePiece(cR,"c2");
		assertTrue(board.getPiece("d2").legalMoves().contains("c2"));
	}

	@Test

	void chariotDontAttackSameColor() throws IllegalPositionException,IllegalMoveException{
		Chariot cR = new Chariot(board, BanqiPiece.Color.RED);
		board.placePiece(cR,"a1");
		Horse hR = new Horse(board, BanqiPiece.Color.RED);
		board.placePiece(hR,"a2");
		assertFalse(board.getPiece("a1").legalMoves().contains("a2"));
	}

	@Test
	public void  chariotCannotAttackChariot()throws IllegalMoveException,IllegalPositionException{
		Chariot aR = new Chariot(board, BanqiPiece.Color.RED);
		board.placePiece(aR,"c1");
		Chariot aW = new Chariot(board, BanqiPiece.Color.WHITE);
		board.placePiece(aW,"c2");
		assertTrue(board.getPiece("c1").legalMoves().contains("c2"));
	}
}
