package com.ucm.dasi.catan.adapter.actions.player;

import java.util.Arrays;
import java.util.HashSet;

import com.ucm.dasi.catan.adapter.actions.SendMessage;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class EndTurn extends OneShotBehaviour {

    private static final long serialVersionUID = 1L;
    private int points;
    private Agent agent;

    public EndTurn(final int points, Agent agent) {
	this.points = points;
	this.agent = agent;
    }

    @Override
    public void action() {
	agent.addBehaviour(new SendMessage(new HashSet<String>(Arrays.asList("master")), ACLMessage.INFORM,
		String.valueOf(points)));
    }

}