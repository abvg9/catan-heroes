package com.ucm.dasi.catan.adapter.master;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import com.ucm.dasi.catan.warehouse.Warehouse;
import jade.core.Agent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

public class Master extends Agent implements IMaster {

    private static final long serialVersionUID = 1L;

    /* Atributes */
    final String argumentsPath = "/arguments/arguments";
    Map<String, Warehouse> playersInformation;

    @Override
    protected void setup() {

	System.out.println("Hello, my name is " + getAID().getName() + "and i am the master of the game.");
	playersInformation = new TreeMap<String, Warehouse>();

	try {
	    
	    // Load names of players.
	    loadArguments();

	    // Create list of players.
	    for (Map.Entry<String, Warehouse> player : playersInformation.entrySet()) {
		createPlayer(player.getKey());
	    }
	    
	    // Give turns and shuffle.
	    
	    // Give turn to player and wait.

	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	}
    }

    @Override
    public void createPlayer(final String name) {

	// Get the JADE runtime interface (singleton)
	jade.core.Runtime runtime = jade.core.Runtime.instance();
	// Create a Profile, where the launch arguments are stored
	Profile profile = new ProfileImpl();
	profile.setParameter(Profile.CONTAINER_NAME, "Catan-heroes");
	profile.setParameter(Profile.MAIN_HOST, "localhost");
	// Create a non-main agent container
	ContainerController container = runtime.createAgentContainer(profile);
	try {

	    AgentController ag = container.createNewAgent(name, "com.ucm.dasi.catan.player.Player",
		    new Object[] { playersInformation });// arguments
	    ag.start();

	} catch (StaleProxyException e) {
	    e.printStackTrace();
	}

    }

    @Override
    public void loadArguments() throws FileNotFoundException {

	File file = new File(new File("").getAbsolutePath() + argumentsPath);
	Scanner sc = new Scanner(file);
	String line;

	while (sc.hasNextLine()) {
	    line = sc.nextLine();
	    playersInformation.put(line, new Warehouse());
	}

	sc.close();
    }

    @Override
    public void endGame() {
	doDelete();
    }

}
