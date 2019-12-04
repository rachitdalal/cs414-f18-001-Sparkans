package com.sparkans.banqi.game;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import org.junit.jupiter.api.*;

public class AdvisorTest {
	private BanqiBoard board;

	//creating a  new board.
	@BeforeEach
	void init() {
		board = new BanqiBoard();
	}

	@Test
    void advisorTest() {

	    Advisor aR = new Advisor(board, BanqiPiece.Color.RED);
	    board.placePiece(aR, "a1");
	   // IllegalMoveException.class, () -> board.move("a1","a2");


    }

}
