package com.sparkans.banqi.game;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import com.sparkans.banqi.game.BanqiPiece.Color;

public class BanqiPieceTest {

	private BanqiPiece piece;
	private BanqiBoard board = new BanqiBoard();

	@BeforeEach
	void init() {
		piece = new General(board, Color.WHITE);
	}
	
	//Testing if the color returned is true.
	@Test
	public void getColorShouldReturnColor()
	{
		assertTrue(piece.getColor().equals(Color.WHITE));
	}
	
	//Testing if the position is correctly returned.
	@Test
	public void setPositionShouldSetPosition() throws IllegalPositionException
	{
		piece.setPosition("c5");
		assertTrue(piece.getPosition().equals("c5"));
	}
	
	//Testing if exception is thrown for the position outside chess board boundary.
	@Test
	public void setPositionShouldThrowExceptionWhenPositionIsOutOfBounds() throws IllegalPositionException
	{
		assertThrows(IllegalPositionException.class, () -> {piece.setPosition("z5");});
		assertThrows(IllegalPositionException.class, () -> {piece.setPosition("d9");});
	}
}
