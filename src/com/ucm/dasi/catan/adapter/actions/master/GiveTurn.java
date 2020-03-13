package com.ucm.dasi.catan.adapter.actions.master;

import java.util.Arrays;
import java.util.HashSet;

import com.ucm.dasi.catan.adapter.actions.SendMessage;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

public class GiveTurn extends OneShotBehaviour {

    private static final long serialVersionUID = 1L;
    String receiver;
    int turn;
    Agent agent;

    public GiveTurn(final String receiver, final int turn, Agent agent) {
	this.receiver = receiver;
	this.turn = turn;
	this.agent = agent;
    }

    @Override
    public void action() {
	agent.addBehaviour(
		new SendMessage(new HashSet<String>(Arrays.asList(receiver)), MasterActions.giveTurn.getValue()));
    }

}
