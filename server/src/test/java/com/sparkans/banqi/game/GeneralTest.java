package com.sparkans.banqi.game;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.logging.SimpleFormatter;

import org.junit.jupiter.api.*;

public class GeneralTest {

	private BanqiBoard board;

	//creating a  new board.
	@BeforeEach
	void init() {
		board = new BanqiBoard();
	}

	@Test

	void generalIsOnBoard() throws IllegalMoveException, IllegalPositionException{
		General gW = new General(board, BanqiPiece.Color.RED);
		board.placePiece(gW,"b7");
		gW.isFaceDown = false;
		board.setUser2("red");
		board.setUser1("white");
		board.setRedPlayer("red");
		board.setWhitePlayer("white");
		board.playerTurn = "red";
		board.move("b7","c7");
		assertTrue(board.getPiece("c7").legalMoves().contains("d7"));
	}

	@Test
	void generalKills() throws IllegalPositionException,IllegalMoveException{
		General gW = new General(board, BanqiPiece.Color.WHITE);
		board.placePiece(gW,"b6");
		Chariot cR = new Chariot(board, BanqiPiece.Color.RED);
		board.placePiece(cR,"b5");
		assertTrue(board.getPiece("b6").legalMoves().contains("b5"));
	}

	@Test
	void generalCannotKillPawn() throws IllegalMoveException,IllegalPositionException{
		General gR = new General(board, BanqiPiece.Color.RED);
		board.placePiece(gR,"d2");
		Soldier sW = new Soldier(board, BanqiPiece.Color.WHITE);
		board.placePiece(sW,"d3");
		assertFalse(board.getPiece("d2").legalMoves().contains("d3"));
	}

	@Test
	public void  generalCannotAttackGeneral()throws IllegalMoveException,IllegalPositionException{
		General aR = new General(board, BanqiPiece.Color.RED);
		board.placePiece(aR,"c1");
		General aW = new General(board, BanqiPiece.Color.WHITE);
		board.placePiece(aW,"c2");
		assertTrue(board.getPiece("c1").legalMoves().contains("c2"));
	}

}
