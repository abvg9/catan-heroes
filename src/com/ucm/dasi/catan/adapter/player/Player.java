package com.ucm.dasi.catan.adapter.player;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;
import com.ucm.dasi.catan.adapter.actions.player.PlayerActions;
import com.ucm.dasi.catan.adapter.actions.player.SendOffer;
import com.ucm.dasi.catan.adapter.actions.player.WaitMessage;
import com.ucm.dasi.catan.resource.ResourceType;
import com.ucm.dasi.catan.warehouse.Warehouse;
import com.ucm.dasi.catan.warehouse.exception.NegativeNumberException;

import jade.core.Agent;

public class Player extends Agent implements IPlayer {

    private static final long serialVersionUID = 1L;

    /* Atributes */
    Warehouse resources;
    Map<String, Warehouse> playersInformation;
    String name;
    int points;

    /* Methods */

    @SuppressWarnings("unchecked")
    @Override
    protected void setup() {

	name = getAID().getName().split("@")[0];
	points = 0;

	Object[] args = getArguments();
	if (args != null && args.length != 0) {

	    // Copy the general information of the players.
	    playersInformation = new TreeMap<String, Warehouse>((Map<String, Warehouse>) args[0]);

	    // Initialize his warehouse.
	    resources = playersInformation.get(name);

	    // Remove his information.
	    playersInformation.remove(name);

	    // Our player always waits for messages.
	    addBehaviour(new WaitMessage(this));

	} else {

	    // Some error has occurred.
	    System.err.println("Player " + name + " received an invalid arguments.");
	    endGame();
	}

    }

    @Override
    public void endGame() {
	doDelete();
    }

}
