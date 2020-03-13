package com.ucm.dasi.catan.adapter.actions.player;

import java.util.Set;

import com.ucm.dasi.catan.adapter.actions.SendMessage;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

public class SendOffer extends OneShotBehaviour {

    private static final long serialVersionUID = 1L;
    Set<String> receivers;
    Agent agent;

    public SendOffer(final Set<String> receivers, Agent agent) {
	this.receivers = receivers;
	this.agent = agent;
    }

    @Override
    public void action() {
	agent.addBehaviour(new SendMessage(receivers, PlayerActions.sendOffer.getValue()));
    }

}
