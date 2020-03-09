package com.ucm.dasi.catan.actions;

public enum ActionTypes {
    
    endGame(0), sendOffer(1), buildStructure(2);

    private final int value;
    
    private ActionTypes(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}