package com.sparkans.banqi.game;

import com.google.gson.annotations.Expose;
import java.util.ArrayList;

public class Cannon extends BanqiPiece {

	ArrayList<String> legalMoves;
	@Expose
	private final String piece = "Cannon";

	public Cannon(BanqiBoard board, Color color) {
		super(board, color);
		legalMoves = new ArrayList<>();
	}

	@Override
	public String toString() {
		if (this.color.equals(Color.WHITE))
			return "WCa";
		else
			return "RCa";
	}

	@Override
	public ArrayList<String> legalMoves() {
		legalMoves.clear();
		String fromPosition = this.getPosition();
		String toPosition;

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 4; j++) {
				toPosition = Character.toString((char) (97 + j)) + String.valueOf(i + 1);
				try {
					if (moveCannon(fromPosition, toPosition))
						legalMoves.add(toPosition);
				} catch (IllegalMoveException e) {
				}
			}
		}
		return legalMoves;
	}

	// Helper Method to determine if the Cannon's move is legal.
	private boolean moveCannon(String fromPosition, String toPosition) throws IllegalMoveException {

		boolean inValid = false;
		try {
			int sourceRow = this.row;
			int sourceColumn = this.column;

			// to avoid Null pointer Exception if destination piece is null.
			BanqiPiece destinationPiece = board.getPiece(toPosition);
			int destRow = destinationPiece != null ? destinationPiece.row : parsePosition(toPosition).get("row");
			int destColumn = destinationPiece != null ? destinationPiece.column : parsePosition(toPosition).get("column");

			// Cannon cannot move diagonally.
			if ((sourceRow != destRow) && (sourceColumn != destColumn))
				return inValid;
			// Cannon cannot move adjacent squares.
			if (Math.abs(destRow - sourceRow) == 1 || Math.abs(destColumn - sourceColumn) == 1)
				return inValid;
			// Cannon can leap over only one piece.
			if (leapOver(fromPosition, toPosition) != 1)
				return inValid;

		} catch (IllegalPositionException e) {
			throw new IllegalMoveException("Invalid Move. Moving to a destination outside the board");
		}
		return !inValid;
	}

	// Helper method to check how many pieces are between
	private int leapOver(String fromPosition, String toPosition) {

		int count = 0;
		int rowIndex, colIndex;
		try {
			if (fromPosition.charAt(1) != toPosition.charAt(1)) {
				if (fromPosition.charAt(1) < toPosition.charAt(1))
					rowIndex = 1;
				else
					rowIndex = -1;
				for (char x = (char) (fromPosition.charAt(1) + rowIndex); x != toPosition.charAt(1); x += rowIndex) {
					if (board.getPiece(fromPosition.charAt(0) + Character.toString(x)) != null)
						count++;
				}
			}
			if (fromPosition.charAt(0) != toPosition.charAt(0)) {
				if (fromPosition.charAt(0) < toPosition.charAt(0))
					colIndex = 1;
				else
					colIndex = -1;

				for (char x = (char) (fromPosition.charAt(0) + colIndex); x != toPosition.charAt(0); x += colIndex) {
					if (board.getPiece(Character.toString(x) + fromPosition.charAt(1)) != null)
						count++;
				}
			}
		} catch (IllegalPositionException e) {
			e.printStackTrace();
		}
		return count;
	}
}
