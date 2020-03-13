package com.ucm.dasi.catan.adapter.actions.player;

public enum PlayerActions {
    
    sendOffer(0), buildStructure(1), acceptOffer(2), endTurn(3);

    private final int value;
    
    private PlayerActions(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}