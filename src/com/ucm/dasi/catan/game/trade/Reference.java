package com.ucm.dasi.catan.game.trade;

import java.util.UUID;

public class Reference implements IReference {

  private UUID id;

  public Reference(UUID id) {

    this.id = id;
  }

  @Override
  public UUID getId() {
    return id;
  }
}
