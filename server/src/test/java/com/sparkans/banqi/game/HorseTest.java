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
	void horseIsonBoard() throws IllegalMoveException,IllegalPositionException{
		Horse hR = new Horse(board, BanqiPiece.Color.RED);
		board.placePiece(hR,"c1");
		hR.isFaceDown = false;
		board.setUser2("red");
		board.setUser1("white");
		board.setRedPlayer("red");
		board.setWhitePlayer("white");
		board.playerTurn = "red";
		board.move("c1","c2");
		assertTrue(board.getPiece("c2").legalMoves().contains("c3"));
	}
	@Test
	void horseKills() throws IllegalPositionException,IllegalMoveException{
		Cannon cW = new Cannon(board, BanqiPiece.Color.WHITE);
		board.placePiece(cW,"b6");
		Horse hR = new Horse(board, BanqiPiece.Color.RED);
		board.placePiece(hR,"b5");
		assertTrue(board.getPiece("b5").legalMoves().contains("b6"));
	}

	void horseIsOnBoard() throws IllegalMoveException, IllegalPositionException{
		Horse gW = new Horse(board, BanqiPiece.Color.WHITE);
		board.placePiece(gW,"b7");
		board.move("b7","c7");
		assertTrue(board.getPiece("c7").legalMoves().contains("b7"));
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
	public void  horseCanAttackHorse()throws IllegalMoveException,IllegalPositionException{
		Horse aR = new Horse(board, BanqiPiece.Color.RED);
		board.placePiece(aR,"c1");
		Horse aW = new Horse(board, BanqiPiece.Color.WHITE);
		board.placePiece(aW,"c2");
		assertTrue(board.getPiece("c1").legalMoves().contains("c2"));
	}


}
