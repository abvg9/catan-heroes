package com.ucm.dasi.catan.warehouse.exceptions;

public class ENotEnoughtResources extends Exception {

	private static final long serialVersionUID = 1L;
	
	public ENotEnoughtResources() {
		super("You dont have enought resources to do that.");
	}
}
