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

	//Helper Method to determine if the Cannon's move is legal.
	private boolean moveCannon(String fromPosition, String toPosition) throws IllegalMoveException {
		// TODO Auto-generated method stub
		return false;
	}


}
