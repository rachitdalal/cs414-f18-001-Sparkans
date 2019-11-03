package com.sparkans.banqi.game;

import com.google.gson.annotations.Expose;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class BanqiPiece {

	public enum Color { RED, WHITE };

	protected BanqiBoard board;

	@Expose
	protected int row;
	@Expose
	protected int column;
	@Expose
	protected Color color;
	@Expose
	protected boolean isFaceDown;

	public BanqiPiece(BanqiBoard board, Color color) {
		this.board = board;
		this.color = color;
		this.isFaceDown = true;
	}

	public Color getColor() {
		return color;
	}

	public String getPosition() {
		return Character.toString((char) (row + 97)) + String.valueOf(column + 1);
	}

	public void setPosition(String position) throws IllegalPositionException {

		Pattern regex = Pattern.compile("[a-d][1-8]");
		Matcher matcher = regex.matcher(position);
		if (!matcher.matches())
			throw new IllegalPositionException("Illegal Position. Position should be between a1 through d8");

		column = Integer.parseInt(String.valueOf(position.charAt(1))) - 1;
		row = position.charAt(0) - 'a';
	}

	// Helper Method to store row and column indexes as Key-Value Pair.
	public Map<String, Integer> parsePosition(String position) {
		
		Map<String, Integer> parsedPositions = new HashMap<>();
		int column = 0;
		char col = position.charAt(0);
		int row = Integer.parseInt(String.valueOf(position.charAt(1))) - 1;
		column = col - 'a';
		parsedPositions.put("row", row);
		parsedPositions.put("column", column);
		return parsedPositions;
	}
	
	abstract public String toString();

	abstract public ArrayList<String> legalMoves();
}
