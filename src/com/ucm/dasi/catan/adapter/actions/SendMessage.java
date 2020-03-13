package com.ucm.dasi.catan.adapter.actions;

import java.util.Set;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class SendMessage extends OneShotBehaviour {

    private static final long serialVersionUID = 1L;

    /* Atributes */
    Set<String> receivers;
    int typeOfMessage;
    String message;

    public SendMessage(final Set<String> receivers, final int typeOfMessage, final String message) {
	this.receivers = receivers;
	this.typeOfMessage = typeOfMessage;
	this.message = message;
    }
    
    public SendMessage(final Set<String> receivers, final int typeOfMessage) {
	this.receivers = receivers;
	this.typeOfMessage = typeOfMessage;
	this.message = "";
    }

    public void action() {

	ACLMessage msg = new ACLMessage(typeOfMessage);
	
	for(final String receiver : receivers) {
	    msg.addReceiver(new AID(receiver, AID.ISLOCALNAME));
	}
	
	msg.setLanguage("English");
	msg.setContent(message);
	myAgent.send(msg);
    }

}
