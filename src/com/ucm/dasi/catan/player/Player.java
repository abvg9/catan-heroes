package com.ucm.dasi.catan.player;

import com.ucm.dasi.catan.resource.IResourceManager;
import com.ucm.dasi.catan.resource.ResourceManager;
import com.ucm.dasi.catan.resource.exception.NegativeNumberException;

public class Player implements IPlayer {

  protected int id;

  protected IResourceManager warehouse;

  public Player(int id, IResourceManager warehouse) throws NegativeNumberException {
    this.id = id;
    this.warehouse = new ResourceManager(warehouse);
  }
  
  @Override
  public int compareTo(IPlayer other) {
    return getId() - other.getId();
  }

  @Override
  public int getId() {
    return id;
  }

  @Override
  public IResourceManager getWarehouse() {
    return warehouse;
  }
}
