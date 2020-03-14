package com.ucm.dasi.catan.adapter.actions.player;

import java.util.Arrays;
import java.util.HashSet;

import com.ucm.dasi.catan.adapter.actions.SendMessage;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

public class AcceptOffer extends OneShotBehaviour {

    private static final long serialVersionUID = 1L;
    private String receiver;
    private Agent agent;

    public AcceptOffer(final String receiver, Agent agent) {
	this.receiver = receiver;
	this.agent = agent;
    }

    @Override
    public void action() {
	agent.addBehaviour(new SendMessage(new HashSet<String>(Arrays.asList(receiver)),
		PlayerActions.acceptOffer.getValue()));
    }

}
