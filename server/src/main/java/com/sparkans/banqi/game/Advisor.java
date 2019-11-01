package com.sparkans.banqi.game;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;

public class Advisor extends BanqiPiece {

	ArrayList<String> legalMoves;

	@Expose
	private static final String name = "Advisor";

	public Advisor(BanqiBoard board, Color color) {
		super(board, color);
		legalMoves = new ArrayList<>();
	}

	@Override
	public String toString() {
		if (this.color.equals(Color.WHITE))
			return "WA";
		else
			return "RA";
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
					if (moveAdvisor(fromPosition, toPosition))
						legalMoves.add(toPosition);
				} catch (IllegalMoveException e) {
				}
			}
		}
		return legalMoves;
	}

	//Helper Method to determine if the Advisor's move is legal.
	private boolean moveAdvisor(String fromPosition, String toPosition) throws IllegalMoveException {

		boolean inValid = false;
		try 
		{
			int sourceRow = this.row;
			int sourceColumn = this.column;

			// to avoid Null pointer Exception if destination piece is null.
			BanqiPiece destinationPiece = board.getPiece(toPosition);
			int destRow = destinationPiece != null ? destinationPiece.row : parsePosition(toPosition).get("row");
			int destColumn = destinationPiece != null ? destinationPiece.column : parsePosition(toPosition).get("column");

			//cannot capture a General or a Chariot or a Horse
			if(destinationPiece.toString().equals("WG") || destinationPiece.toString().equals("RG") 
					|| destinationPiece.toString().equals("WCh") || destinationPiece.toString().equals("RCh")
					|| destinationPiece.toString().equals("WH") || destinationPiece.toString().equals("RH"))
				return inValid;
			//Advisor cannot move diagonally.
			if ((sourceRow != destRow) && (sourceColumn != destColumn))
				return inValid;
			//Advisor can move only one square horizontal or vertical.
			if(Math.abs(destRow - sourceRow) > 1 || Math.abs(destColumn - sourceColumn) > 1 )
				return inValid;				

		} catch (IllegalPositionException e) {
			throw new IllegalMoveException("Invalid Move. Moving to a destination outside the board");
		}
		return !inValid;
	}
}
