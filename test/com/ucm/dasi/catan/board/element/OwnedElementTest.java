package com.ucm.dasi.catan.board.element;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

import com.ucm.dasi.catan.board.BoardElementType;
import com.ucm.dasi.catan.player.IPlayer;
import com.ucm.dasi.catan.player.Player;
import com.ucm.dasi.catan.warehouse.Warehouse;
import com.ucm.dasi.catan.warehouse.exception.NegativeNumberException;

public class OwnedElementTest {

    @Test
    public void itMustReturnItsType() throws NegativeNumberException {
	IPlayer player = new Player(0, new Warehouse());
	OwnedElement element = new MinimunOwnedElement(BoardElementType.Structure, player);
	
	assertSame(player, element.getOwner());
    }
}
