package com.sparkans.banqi.game;

import com.google.gson.annotations.Expose;
import java.util.ArrayList;

public class Soldier extends BanqiPiece {

	ArrayList<String> legalMoves;

	@Expose
	private final String piece = "Soldier";

	public Soldier(BanqiBoard board, Color color) {
		super(board, color);
		legalMoves = new ArrayList<>();
	}

	@Override
	public String toString() {
		if (this.color.equals(Color.WHITE))
			return "WS";
		else
			return "RS";
	}

	@Override
	public ArrayList<String> legalMoves() {
		legalMoves.clear();
		String fromPosition = this.getPosition();
		String toPosition;

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 8; j++) {
				toPosition = Character.toString((char) (97 + j)) + String.valueOf(i + 1);
				try {
					if (moveSoldier(fromPosition, toPosition))
						legalMoves.add(toPosition);
				} catch (IllegalMoveException e) {
				}
			}
		}
		return legalMoves;
	}

	// Helper Method to determine if the Soldier's move is legal.
	private boolean moveSoldier(String fromPosition, String toPosition) throws IllegalMoveException {
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

			// can capture only a General
			if (!destinationPiece.toString().equals("WG") || !destinationPiece.toString().equals("RG"))
				return inValid;
			// Soldier cannot move diagonally.
			if ((sourceRow != destRow) && (sourceColumn != destColumn))
				return inValid;
			// Soldier can move only one square horizontal or vertical.
			if (Math.abs(destRow - sourceRow) > 1 || Math.abs(destColumn - sourceColumn) > 1)
				return inValid;
			// Soldier can capture only opponent's piece.
			if (destinationColor != null && sourceColor.equals(destinationColor))
				return inValid;

		} catch (IllegalPositionException e) {
			throw new IllegalMoveException("Invalid Move. Moving to a destination outside the board");
		}
		return !inValid;
	}

}
