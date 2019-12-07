package com.sparkans.banqi.game;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

public class BanqiBoardTest {

	BanqiBoard board;

	//creating a  new board.
	@BeforeEach
	void init() {
		board=new BanqiBoard();
	}	

	/*  Testing if code throws an IllegalPositionException, if the piece position mentioned is outside the chess
	 *  board boundary.
	 */
	@Test
	public void testGetPieceThrowsExceptionWhenInvalidPosition() throws IllegalPositionException {
		assertThrows(IllegalPositionException.class, () -> { board.getPiece("b9");});
		assertThrows(IllegalPositionException.class, () -> { board.getPiece("j4");});
	}

	//Testing if after a legal move, the piece in the destination position is equal to the one that should actually be there.
	@Test
	public void testGetPieceReturnsPiece() throws IllegalPositionException, IllegalMoveException {

	}

	/*
	 * Testing if an exception is thrown when the Source Piece is null.
	 * Testing if an exception is thrown, if the moves are not according to the rules.
	 */
	@Test
	public void testMoveThrowsExceptionWhenSourceIsNullorMoveisInvalid() throws IllegalMoveException {

	}	

	/* I'm pretty sure this is not required. place piece does not check for valid moves
	//Testing if there is no capture of same color piece.
	@Test
	public void testPlacePieceShouldNotPlacePieceIfColorIsSame() throws IllegalPositionException {

		board.placePiece(board.getPiece("a2"), "a4");
		assertFalse(board.placePiece(board.getPiece("a1"), "a4"));		
	}
*/
	//Testing if the pieces are placed, when the move is legal.
	@Test
	public void testPlacePieceShouldPlacePieceIfValidMove() throws IllegalPositionException {


	}

	@Test
	public void testForWin() throws IllegalPositionException, IllegalMoveException {
		Soldier sW = new Soldier(board, BanqiPiece.Color.WHITE);
		Minister mR = new Minister(board, BanqiPiece.Color.RED);
		board.placePiece(sW,"a1");
		board.placePiece(mR,"a2");
		assert (board.winner.equals("none"));
		board.flip("a1");
		board.flip("a2");
		board.move("a2","a1");
		assert (board.winner.equals("red"));
	}

}
