package com.sparkans.banqi.game;

import com.google.gson.annotations.Expose;
import java.util.ArrayList;

public class Horse extends BanqiPiece {
	@Expose
	ArrayList<String> legalMoves = new ArrayList<>();

	@Expose
	private final String piece = "Horse";

	public Horse(BanqiBoard board, Color color) {
		super(board, color);
	}

	@Override
	public String toString() {
		return "Horse";
	}

	@Override
	public ArrayList<String> legalMoves() {
		legalMoves = new ArrayList<>();
		String fromPosition = this.getPosition();
		String toPosition;

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 8; j++) {
				toPosition = Character.toString((char) (97 + i)) + String.valueOf(j + 1);
				try {
					if (moveHorse(fromPosition, toPosition))
						legalMoves.add(toPosition);
				} catch (IllegalMoveException e) {
				}
			}
		}
		return legalMoves;
	}

	// Helper Method to determine if the Horse's move is legal.
	private boolean moveHorse(String fromPosition, String toPosition) throws IllegalMoveException {

		boolean inValid = false;
		try {

			int sourceRow = this.row;
			int sourceColumn = this.column;
			Color sourceColor = this.color;

			// to avoid Null pointer Exception if destination piece is null.
			BanqiPiece destinationPiece = board.getPiece(toPosition);
			int destRow = destinationPiece != null ? destinationPiece.row : parsePosition(toPosition).get("row");
			int destColumn = destinationPiece != null ? destinationPiece.column
					: parsePosition(toPosition).get("column");
			Color destinationColor = destinationPiece != null ? destinationPiece.color : null;

			// Horse can capture only opponent's piece.
			if (destinationColor != null && sourceColor.equals(destinationColor))
				return inValid;
			// Cannot capture a General or a Chariot
			if(destinationPiece != null){
				if (destinationPiece.toString().equals("General") || destinationPiece.toString().equals("Chariot"))
					return inValid;
			}

			// Horse cannot move diagonally.
			if ((sourceRow != destRow) && (sourceColumn != destColumn))
				return inValid;
			// Horse can move only one square horizontal or vertical.
			if (Math.abs(destRow - sourceRow) > 1 || Math.abs(destColumn - sourceColumn) > 1)
				return inValid;


		} catch (IllegalPositionException e) {
			throw new IllegalMoveException("Invalid Move. Moving to a destination outside the board");
		}
		return !inValid;
	}
}