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

	    // CODIGO DE PRUEBAA
	    try {
		Thread.sleep(5000);
	    } catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }

	    Map<ResourceType, Integer> offer = Map.of(ResourceType.Ore, 2, ResourceType.Brick, 2, ResourceType.Wool,
		    0, ResourceType.Lumber, 0, ResourceType.Gain, 0);

	    Map<ResourceType, Integer> request = Map.of(ResourceType.Ore, 0, ResourceType.Brick, 0, ResourceType.Wool,
		    0, ResourceType.Lumber, 2, ResourceType.Gain, 2);

	    Warehouse[] offerAndRequest = new Warehouse[2];
	    try {
		offerAndRequest[0] = new Warehouse(offer);
		offerAndRequest[1] = new Warehouse(request);
	    } catch (NegativeNumberException e) {
		e.printStackTrace();
	    }
	   
	    if (name.equals("gamer1")) {
		addBehaviour(new SendOffer(new HashSet<>(Arrays.asList("gamer2")), this, PlayerActions.sendOffer.getValue(), offerAndRequest));
	    }

	} else {

	    // Some error has ocurred.
	    System.err.println("Player " + name + " received an invalid arguments.");
	    endGame();
	}

    }

    @Override
    public void endGame() {
	doDelete();
    }

}
