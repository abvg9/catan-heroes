package com.ucm.dasi.catan.adapter.actions.master;

import java.util.Set;

import com.ucm.dasi.catan.adapter.actions.SendMessage;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class EndGame extends OneShotBehaviour {

    private static final long serialVersionUID = 1L;
    Set<String> players;
    String winner;
    Agent agent;

    public EndGame(final Set<String> players, final String winner, Agent agent) {
	this.players = players;
	this.winner = winner;
	this.agent = agent;
    }

    @Override
    public void action() {
	agent.addBehaviour(new SendMessage(players, ACLMessage.INFORM, "The game finish, winner is: " + winner));
    }

}
