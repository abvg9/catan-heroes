package com.ucm.dasi.catan.player;

import java.util.Map;
import java.util.TreeMap;
import com.ucm.dasi.catan.actions.player.WaitMessage;
import com.ucm.dasi.catan.warehouse.Warehouse;
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

	// TO DELETE
	System.out.println("Hello, my name is " + name);

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
	    addBehaviour(new WaitMessage());

	} else {
	    // NO PUEDO HACERLO MEDIANTE EXCEPCIONES :(
	    System.err.println("Player " + name + " received an invalid arguments.");
	    // Some error has ocurred.
	    doDelete();
	}

    }

}
