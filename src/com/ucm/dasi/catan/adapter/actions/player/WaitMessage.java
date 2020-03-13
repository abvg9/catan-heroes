package com.ucm.dasi.catan.adapter.actions.player;

import com.ucm.dasi.catan.adapter.actions.master.MasterActions;
import com.ucm.dasi.catan.adapter.player.Player;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class WaitMessage extends CyclicBehaviour {

    private static final long serialVersionUID = 1L;
    
    Player player;
    
    public WaitMessage(final Player player) {
	this.player = player;
    }

    @Override
    public void action() {

	System.out.println(myAgent.getName() + ": I'm waiting for messages");

	ACLMessage msg = myAgent.receive();

	if (msg != null) {
	    final String sender = msg.getSender().getName();

	    if (sender.equals("master")) {
				
		switch (MasterActions.values()[msg.getPerformative()]) {
		case endGame:
		    // The game finished.
		    player.endGame();
		    break;
		case giveTurn:
		    // The player starts to think about his strategy.
		    break;
		default:
		    break;
		}

	    } else {
		
		switch (PlayerActions.values()[msg.getPerformative()]) {
		case acceptOffer:
		    // Player receive ACK confirming an offer, lets make the treat.
		    break;
		case sendOffer:
		    // Player receive some offer, he needs to think.
		    break;
		default:
		    break;
		}

	    }
	}
	block();
    }

}
