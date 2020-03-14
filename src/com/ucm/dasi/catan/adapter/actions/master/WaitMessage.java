package com.ucm.dasi.catan.adapter.actions.master;

import com.ucm.dasi.catan.adapter.actions.player.PlayerActions;
import com.ucm.dasi.catan.adapter.master.Master;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class WaitMessage extends CyclicBehaviour {

    private static final long serialVersionUID = 1L;
    private Master master;
    
    public WaitMessage(final Master master) {
	this.master = master;
    }

    @Override
    public void action() {

	ACLMessage msg = myAgent.receive();

	if (msg != null) {
	    final String sender = msg.getSender().getName();
	    final String content = msg.getContent();

	    ACLMessage reply = msg.createReply();

	    System.out.println(myAgent.getName() + ": " + sender + "send me a message => " + content);

	    switch (PlayerActions.values()[msg.getPerformative()]) {
	    case buildStructure:
		break;
	    case endTurn:
		// Check if the player has 10 points.
		// Give turn to another player.
		break;
	    default:
		break;
	    }

	    reply.setPerformative(ACLMessage.CONFIRM);
	    reply.setContent("Receiver Roger Roger");

	    myAgent.send(reply);
	}
	block();
    }

}
