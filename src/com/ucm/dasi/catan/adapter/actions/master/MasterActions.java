package com.ucm.dasi.catan.adapter.actions.master;

public enum MasterActions {
    
    endGame(0), giveTurn(1);

    private final int value;
    
    private MasterActions(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}