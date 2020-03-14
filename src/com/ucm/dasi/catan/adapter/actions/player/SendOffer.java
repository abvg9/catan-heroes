package com.ucm.dasi.catan.adapter.actions.player;

import java.util.Set;
import com.ucm.dasi.catan.adapter.actions.SendMessage;
import com.ucm.dasi.catan.warehouse.Warehouse;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

public class SendOffer extends OneShotBehaviour {

    private static final long serialVersionUID = 1L;
    
    private Set<String> receivers;
    private Agent agent;
    private int typeOfMessage;
    private Warehouse[] offerAndRequest;

    public SendOffer(final Set<String> receivers, Agent agent, final int typeOfMessage, final Warehouse[] offerAndRequest) {
	this.receivers = receivers;
	this.agent = agent;
	this.typeOfMessage = typeOfMessage;
	this.offerAndRequest = offerAndRequest;
    }

    @Override
    public void action() {
	agent.addBehaviour(new SendMessage(receivers, typeOfMessage, offerAndRequest));
    }

}
