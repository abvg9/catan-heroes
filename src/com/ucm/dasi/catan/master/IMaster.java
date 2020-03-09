package com.ucm.dasi.catan.master;

import java.io.FileNotFoundException;

public interface IMaster {

    /* Methods */

    void createPlayer(final String name);

    void loadArguments() throws FileNotFoundException;

}
