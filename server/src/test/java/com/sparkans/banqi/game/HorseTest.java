package com.sparkans.banqi.game;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import org.junit.jupiter.api.*;

public class HorseTest {

	private BanqiBoard board;

	//creating a  new board.
	@BeforeEach
	void init() {
		board = new BanqiBoard();
	}
	@Test
	void horseKills() throws IllegalPositionException,IllegalMoveException{
		Cannon cW = new Cannon(board, BanqiPiece.Color.WHITE);
		board.placePiece(cW,"b6");
		Horse hR = new Horse(board, BanqiPiece.Color.RED);
		board.placePiece(hR,"b5");
		assertTrue(board.getPiece("b5").legalMoves().contains("b6"));
	}

	@Test
	public void horseDoNotAttackSameColor() throws IllegalMoveException,IllegalPositionException {
		Horse hR = new Horse(board, BanqiPiece.Color.RED);
		board.placePiece(hR,"c3");
		Soldier sR = new Soldier(board, BanqiPiece.Color.RED);
		board.placePiece(sR,"b3");
		assertFalse(board.getPiece("c3").legalMoves().contains("b3"));
	}

	@Test
	public void horseCannotMoveDiagonally() throws IllegalPositionException, IllegalMoveException{
		Horse hR = new Horse(board, BanqiPiece.Color.RED);
		board.placePiece(hR,"b4");
		assertThrows(IllegalMoveException.class, ()->{board.move("b4","a3");});
	}

	@Test
	public void  horseCannotAttackHorse()throws IllegalMoveException,IllegalPositionException{
		Horse aR = new Horse(board, BanqiPiece.Color.RED);
		board.placePiece(aR,"c1");
		Horse aW = new Horse(board, BanqiPiece.Color.WHITE);
		board.placePiece(aW,"c2");
		assertTrue(board.getPiece("c1").legalMoves().contains("c2"));
	}


}
