package com.ucm.dasi.catan.game.trade;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class ReferenceTest {

  @DisplayName("It must get its id")
  @Tag("Reference")
  @Test
  public void itMustGetItsId() {

    UUID uuid = UUID.randomUUID();

    Reference ref = new Reference(uuid);

    assertEquals(uuid, ref.getId());
  }
}
