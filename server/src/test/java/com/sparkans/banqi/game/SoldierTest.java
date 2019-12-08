package com.sparkans.banqi.game;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import org.junit.jupiter.api.*;

public class SoldierTest {
	private BanqiBoard board;

	//creating a  new board.
	@BeforeEach
	void init() {
		board = new BanqiBoard();
	}

	@Test
	void soldierPlacedOnBoard() throws IllegalPositionException,IllegalMoveException{
		Soldier cR = new Soldier(board, BanqiPiece.Color.RED);
		board.placePiece(cR,"c2");
		board.move("c2","c3");
		assertTrue(board.getPiece("c2").legalMoves().containsAll(Arrays.asList("c3")));
	}

	@Test
	void anyOneCanKillSoldierExceptGeneral() throws IllegalMoveException,IllegalPositionException{
		General gR = new General(board, BanqiPiece.Color.RED);
		board.placePiece(gR,"d2");
		Soldier sW = new Soldier(board, BanqiPiece.Color.WHITE);
		board.placePiece(sW,"d3");
		assertFalse(board.getPiece("d2").legalMoves().contains("d3"));
	}

	@Test
	void onlySoldierCanKillGeneral() throws IllegalPositionException,IllegalMoveException{
		General gR = new General(board, BanqiPiece.Color.RED);
		board.placePiece(gR,"d5");
		Soldier sW = new Soldier(board, BanqiPiece.Color.WHITE);
		board.placePiece(sW,"d6");
		assertFalse(board.getPiece("d6").legalMoves().contains("d5"));
	}

	@Test
	public void  soldierCannotAttackSoldier()throws IllegalMoveException,IllegalPositionException{
		Soldier aR = new Soldier(board, BanqiPiece.Color.RED);
		board.placePiece(aR,"c1");
		Soldier aW = new Soldier(board, BanqiPiece.Color.WHITE);
		board.placePiece(aW,"c2");
		assertTrue(board.getPiece("c1").legalMoves().contains("c2"));
	}

	
	
}
