package com.ucm.dasi.catan.actions.master;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class WaitMessage extends CyclicBehaviour {

    private static final long serialVersionUID = 1L;

    @Override
    public void action() {

	System.out.println(myAgent.getName() + ": I'm waiting for messages");

	ACLMessage msg = myAgent.receive();

	if (msg != null) {
	    final String sender = msg.getSender().getName();
	    final String content = msg.getContent();

	    ACLMessage reply = msg.createReply();

	    System.out.println(myAgent.getName() + ": " + sender + "send me a message => " + content);

	    reply.setPerformative(ACLMessage.CONFIRM);
	    reply.setContent("Receiver Roger Roger");

	    myAgent.send(reply);
	}
	block();
    }

}
