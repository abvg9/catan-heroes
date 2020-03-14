package com.ucm.dasi.catan.adapter.actions.player;

import java.util.Arrays;
import java.util.HashSet;

import com.ucm.dasi.catan.adapter.actions.SendMessage;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

public class BuildStructure extends OneShotBehaviour {

    private static final long serialVersionUID = 1L;
    private Agent agent;

    public BuildStructure(Agent agent) {
	this.agent = agent;
    }

    @Override
    public void action() {
	agent.addBehaviour(
		new SendMessage(new HashSet<String>(Arrays.asList("master")), PlayerActions.sendOffer.getValue()));
    }

}
