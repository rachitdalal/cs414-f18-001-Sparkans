package com.sparkans.banqi.game;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import org.junit.jupiter.api.*;

public class MinisterTest {
	private BanqiBoard board;

	//creating a  new board.
	@BeforeEach
	void init() {
		board = new BanqiBoard();
	}

	@Test
	void ministerPlacedOnBoard() throws IllegalPositionException,IllegalMoveException{
		Minister cR = new Minister(board, BanqiPiece.Color.RED);
		board.placePiece(cR,"c2");
		cR.isFaceDown = false;
		board.move("c2","c3");
		assertTrue(board.getPiece("c3").legalMoves().contains("c4"));

	}
	@Test
	public void MinisterDoNotAttackSameColor() throws IllegalMoveException,IllegalPositionException {
		Minister cR = new Minister(board, BanqiPiece.Color.RED);
		board.placePiece(cR,"c3");
		Soldier sR = new Soldier(board, BanqiPiece.Color.RED);
		board.placePiece(sR,"b3");
		assertFalse(board.getPiece("c3").legalMoves().contains("b3"));
	}

	@Test
	public void MinisterCannotAttackHigherRankThatOfItself() throws IllegalMoveException,IllegalPositionException{
		Minister cR = new Minister(board, BanqiPiece.Color.RED);
		board.placePiece(cR,"a5");
		Horse hW = new Horse(board, BanqiPiece.Color.WHITE);
		board.placePiece(hW,"a4");
		assertFalse(board.getPiece("a5").legalMoves().contains("a4"));
	}
}
