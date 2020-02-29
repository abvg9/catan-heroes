package com.ucm.dasi.catan.board.exception;

public class InvalidBoardElementException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -514121800286703032L;

	public InvalidBoardElementException() {
		super();
	}
	
	public InvalidBoardElementException(String message) {
		super(message);
	}
}
