package com.sparkans.banqi.game;

import com.google.gson.annotations.Expose;
import com.sparkans.banqi.user.UserBean;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BanqiBoard {

	@Expose
	private BanqiPiece[][] board;

	private UserBean user1;
	private UserBean user2;

	// initialize the board to 4x8 array
	public BanqiBoard() {
		if (this.board == null) {
			this.board = new BanqiPiece[4][8];
			initialize();
		}
	}

	public BanqiBoard(UserBean user1, UserBean user2) {
		this.user1 = user1;
		this.user2 = user2;

		if (this.board == null) {
			this.board = new BanqiPiece[4][8];
			initialize();
		}
	}

	public BanqiPiece[][] getBoard() {
		return board;
	}

	// initialize the board to standard Banqi opening state
	private void initialize() {
		BanqiPiece[] pieces = new BanqiPiece[32];
		Random r = new Random();

		pieces[0] = new General(this, BanqiPiece.Color.RED);
		pieces[1] = new General(this, BanqiPiece.Color.WHITE);

		pieces[2] = new Advisor(this, BanqiPiece.Color.RED);
		pieces[3] = new Advisor(this, BanqiPiece.Color.WHITE);
		pieces[4] = new Advisor(this, BanqiPiece.Color.RED);
		pieces[5] = new Advisor(this, BanqiPiece.Color.WHITE);

		pieces[6] = new Minister(this, BanqiPiece.Color.RED);
		pieces[7] = new Minister(this, BanqiPiece.Color.WHITE);
		pieces[8] = new Minister(this, BanqiPiece.Color.RED);
		pieces[9] = new Minister(this, BanqiPiece.Color.WHITE);

		pieces[10] = new Chariot(this, BanqiPiece.Color.RED);
		pieces[11] = new Chariot(this, BanqiPiece.Color.WHITE);
		pieces[12] = new Chariot(this, BanqiPiece.Color.RED);
		pieces[13] = new Chariot(this, BanqiPiece.Color.WHITE);

		pieces[14] = new Horse(this, BanqiPiece.Color.RED);
		pieces[15] = new Horse(this, BanqiPiece.Color.WHITE);
		pieces[16] = new Horse(this, BanqiPiece.Color.RED);
		pieces[17] = new Horse(this, BanqiPiece.Color.WHITE);

		pieces[18] = new Soldier(this, BanqiPiece.Color.RED);
		pieces[19] = new Soldier(this, BanqiPiece.Color.WHITE);
		pieces[20] = new Soldier(this, BanqiPiece.Color.RED);
		pieces[21] = new Soldier(this, BanqiPiece.Color.WHITE);
		pieces[22] = new Soldier(this, BanqiPiece.Color.RED);
		pieces[23] = new Soldier(this, BanqiPiece.Color.WHITE);
		pieces[24] = new Soldier(this, BanqiPiece.Color.RED);
		pieces[25] = new Soldier(this, BanqiPiece.Color.WHITE);
		pieces[26] = new Soldier(this, BanqiPiece.Color.RED);
		pieces[27] = new Soldier(this, BanqiPiece.Color.WHITE);

		pieces[28] = new Cannon(this, BanqiPiece.Color.RED);
		pieces[29] = new Cannon(this, BanqiPiece.Color.WHITE);
		pieces[30] = new Cannon(this, BanqiPiece.Color.RED);
		pieces[31] = new Cannon(this, BanqiPiece.Color.WHITE);

		// assign each piece to a random spot on the board
		for (int i = 0; i < 32; i++) {

			int randr = r.nextInt(4);
			int randc = r.nextInt(8);
			// if a spot is already assigned try a different one
			while (board[randr][randc] != null) {
				randr = r.nextInt(4);
				randc = r.nextInt(8);
			}

			board[randr][randc] = pieces[i];
			pieces[i].row = randr;
			pieces[i].column = randc;
		}

	}

	// Helper Method to store row and column indexes as Key-Value Pair.
	private Map<String, Integer> parsePosition(String position) {
		Map<String, Integer> parsedPositions = new HashMap<>();
		int column = 0;
		char col = position.charAt(0);
		int row = Integer.parseInt(String.valueOf(position.charAt(1))) - 1;
		column = col - 'a';
		parsedPositions.put("row", row);
		parsedPositions.put("column", column);
		return parsedPositions;
	}

	/*
	 * Column and Row coordinates are separated from the given position. Banqi Piece
	 * present in the board in the given position is returned. If the passed
	 * position is not within the Banqi board boundaries, an
	 * IllegalPositionException is thrown.
	 */
	public BanqiPiece getPiece(String position) throws IllegalPositionException {

		Pattern regex = Pattern.compile("[a-d][1-8]");
		Matcher matcher = regex.matcher(position);
		if (!matcher.matches()) {
			throw new IllegalPositionException("Illegal Position. Position should be between a1 through d8");
		}

		int row = parsePosition(position).get("row");
		int column = parsePosition(position).get("column");

		return board[row][column];
	}

	/*
	 * Places the passed BanqiPiece in the given position on Banqi board.
	 */
	public boolean placePiece(BanqiPiece piece, String position) {

		int row = parsePosition(position).get("row");
		int column = parsePosition(position).get("column");

		BanqiPiece existingPiece;

		try {
			existingPiece = getPiece(position);

			if (null == existingPiece) {
				piece.setPosition(position);
				board[row][column] = piece;
				return true;
			} else {
				if (existingPiece.color.equals(piece.color)) {
					return false;
				} else {
					piece.setPosition(position);
					board[row][column] = piece;
					return true;
				}
			}
		} catch (IllegalPositionException e) {
			return false;
		}
	}

	/*
	 * A given move is compared against the list of legal moves and the piece is
	 * moved accordingly. An IllegalMoveException is thrown in case of an illegal
	 * move.
	 */
	public void move(String fromPosition, String toPosition) throws IllegalMoveException {

		try {
			BanqiPiece sourcePiece = getPiece(fromPosition);
			BanqiPiece destinationPiece = getPiece(toPosition);

			if (sourcePiece == null)
				throw new IllegalMoveException("No piece present at the source");

			if (!faceUp(sourcePiece))
				throw new IllegalMoveException("No move allowed for a face-down piece");

			if (!faceUp(destinationPiece)
					&& (!sourcePiece.toString().equals("RCa") || !sourcePiece.toString().equals("WCa")))
				throw new IllegalMoveException("Cannot capture a face-down piece");

			int sourceRow = sourcePiece.row;
			int sourceColumn = sourcePiece.column;
			if (sourcePiece.legalMoves().contains(toPosition)) {
				if (placePiece(sourcePiece, toPosition)) {
					board[sourceRow][sourceColumn] = null;
				}
			} else
				throw new IllegalMoveException("Illegal Move.");
		} catch (IllegalPositionException e) {
			throw new IllegalMoveException("Illegal Move due to invalid position. " + e.getMessage());
		}
	}

	private boolean faceUp(BanqiPiece piece) {
		return false;
	}

	public UserBean getUser1() {
		return user1;
	}

	public UserBean getUser2() {
		return user2;
	}
}
