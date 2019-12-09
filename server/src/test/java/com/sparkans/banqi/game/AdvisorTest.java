package com.sparkans.banqi.game;

import static org.junit.jupiter.api.Assertions.*;
//
//import java.lang.reflect.Array;
//import java.net.SocketImpl;
import java.util.Arrays;
import org.junit.jupiter.api.*;
//import sun.java2d.pipe.SolidTextRenderer;

public class AdvisorTest {
	private BanqiBoard board;
	//creating a  new board.
	@BeforeEach
	void init() {
		board = new BanqiBoard();
	}


	@Test
	public  void  moveAdvisor() throws IllegalMoveException, IllegalPositionException{
		Advisor aR = new Advisor(board, BanqiPiece.Color.RED);
		board.placePiece(aR,"b1");
		aR.isFaceDown = false;
		board.setUser2("red");
		board.setUser1("white");
		board.setRedPlayer("red");
		board.setWhitePlayer("white");
		board.playerTurn = "red";
		board.move("b1","a1");
		assertTrue(board.getPiece("a1").legalMoves().contains("b1"));
	}

	@Test
	public void advisorDiagonalMove() throws IllegalPositionException, IllegalMoveException{
		Advisor aR = new Advisor(board, BanqiPiece.Color.RED);
		board.placePiece(aR,"b4");
		assertThrows(IllegalMoveException.class, ()->{board.move("b4","a3");});
	}

	@Test
	public void  advisorCannotAttackSameColor()throws IllegalMoveException,IllegalPositionException{
		Advisor aR = new Advisor(board, BanqiPiece.Color.RED);
		board.placePiece(aR,"c1");
		Soldier sR = new Soldier(board, BanqiPiece.Color.RED);
		board.placePiece(sR,"c2");
		assertThrows(IllegalMoveException.class,()->{board.move("c1","c2");});
	}

	@Test
	public void  advisorCanAttackAdvisor()throws IllegalMoveException,IllegalPositionException{
		Advisor aR = new Advisor(board, BanqiPiece.Color.RED);
		board.placePiece(aR,"c1");
		Advisor aW = new Advisor(board, BanqiPiece.Color.WHITE);
		board.placePiece(aW,"c2");
		assertTrue(board.getPiece("c1").legalMoves().contains("c2"));
	}


}
