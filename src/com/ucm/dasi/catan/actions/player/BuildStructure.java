package com.ucm.dasi.catan.actions.player;

import java.util.Arrays;
import java.util.HashSet;
import com.ucm.dasi.catan.actions.ActionTypes;
import com.ucm.dasi.catan.actions.SendMessage;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

public class BuildStructure extends OneShotBehaviour {

    private static final long serialVersionUID = 1L;
    String winner;
    Agent agent;

    public BuildStructure(final String winner, Agent agent) {
	this.winner = winner;
	this.agent = agent;
    }

    @Override
    public void action() {
	agent.addBehaviour(
		new SendMessage(new HashSet<String>(Arrays.asList("master")), ActionTypes.sendOffer.getValue(), ""));
    }

}
