package com.sparkans.banqi.game;

import com.google.gson.annotations.Expose;
import java.util.ArrayList;

public class Minister extends BanqiPiece {

	ArrayList<String> legalMoves;

	@Expose
	private final String piece = "Minister";

	public Minister(BanqiBoard board, Color color) {
		super(board, color);
		legalMoves = new ArrayList<>();
	}

	@Override
	public String toString() {
		if (this.color.equals(Color.WHITE))
			return "WM";
		else
			return "RM";
	}

	@Override
	public ArrayList<String> legalMoves() {
		legalMoves.clear();
		String fromPosition = this.getPosition();
		String toPosition;

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 8; j++) {
				toPosition = Character.toString((char) (97 + i)) + String.valueOf(j + 1);
				try {
					if (moveMinister(fromPosition, toPosition))
						legalMoves.add(toPosition);
				} catch (IllegalMoveException e) {
				}
			}
		}
		return legalMoves;
	}

	// Helper Method to determine if the Minister's move is legal.
	private boolean moveMinister(String fromPosition, String toPosition) throws IllegalMoveException {

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

			// Minister can capture only opponent's piece.
			if (destinationColor != null && sourceColor.equals(destinationColor))
				return inValid;
			if(destinationPiece != null){
				// can capture only a Soldier and Minister
				if (!(destinationPiece.toString().equals("WS") || destinationPiece.toString().equals("RS"))||
						!(destinationPiece.toString().equals("WM") || destinationPiece.toString().equals("RM")))
					return inValid;
			}
			// Minister cannot move diagonally.
			if ((sourceRow != destRow) && (sourceColumn != destColumn))
				return inValid;

			// Minister can move only one square horizontal or vertical.
			if (Math.abs(destRow - sourceRow) > 1 || Math.abs(destColumn - sourceColumn) > 1)
				return inValid;
		} catch (IllegalPositionException e) {
			throw new IllegalMoveException("Invalid Move. Moving to a destination outside the board");
		}
		return !inValid;
	}
}
