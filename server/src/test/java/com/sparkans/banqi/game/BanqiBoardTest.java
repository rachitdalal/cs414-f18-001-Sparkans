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
		board.initialize();
	}

	/*
	 * Testing if an exception is thrown when the Source Piece is null.
	 * Testing if an exception is thrown, if the moves are not according to the rules.
	 */
	@Test
	public void testMoveThrowsExceptionWhenSourceIsNullorMoveisInvalid() throws IllegalMoveException {
		board.initialize();
	}	

	//Testing if there is no capture of same color piece.
	@Test
	public void testPlacePieceShouldNotPlacePieceIfColorIsSame() throws IllegalPositionException {

		board.initialize();
		board.placePiece(board.getPiece("a2"), "a4");
		assertFalse(board.placePiece(board.getPiece("a1"), "a4"));		
	}

	//Testing if the pieces are placed, when the move is legal.
	@Test
	public void testPlacePieceShouldPlacePieceIfValidMove() throws IllegalPositionException {

		board.initialize();

	}
}
