package com.ucm.dasi.catan.adapter.master;

import java.io.FileNotFoundException;

public interface IMaster {

    /* Methods */

    void createPlayer(final String name);

    void loadArguments() throws FileNotFoundException;
    
    void endGame();

}
