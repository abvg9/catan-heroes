package com.ucm.dasi.catan.resource.exception;

public class NotEnoughtResourcesException extends Exception {

    private static final long serialVersionUID = 1L;

    public NotEnoughtResourcesException() {
	super("You dont have enought resources to do that.");
    }

}
