package com.ucm.dasi.catan.warehouse.exceptions;

public class ENegativeNumber extends Exception {

	private static final long serialVersionUID = 1L;
	
	public ENegativeNumber(String name) {
		super("Value of " + name + "can't be negative.");
	}
}