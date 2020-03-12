package com.ucm.dasi.catan.board;

import com.ucm.dasi.catan.board.element.IBoardElement;
import com.ucm.dasi.catan.board.exception.InvalidBoardDimensionsException;
import com.ucm.dasi.catan.board.exception.InvalidBoardElementException;
import com.ucm.dasi.catan.board.structure.IBoardStructure;
import com.ucm.dasi.catan.board.structure.StructureType;

public class CatanEditableBoard extends CatanBoard implements ICatanEditableBoard {

    public CatanEditableBoard(int width, int height, IBoardElement[][] elements)
	    throws InvalidBoardDimensionsException, InvalidBoardElementException {

	super(width, height, elements);
    }

    @Override
    public void build(IBoardElement element, int x, int y) throws InvalidBoardElementException {

	if (!isValidBuild(element, x, y)) {
	    throw new InvalidBoardElementException();
	}
	this.elements[x][y] = element;
    }

    private boolean isValidBuild(IBoardElement element, int x, int y) {

	boolean isValidElementToBuild;
	if (null == elements[x][y]) {
	    isValidElementToBuild = this.isValidBuildNew(element);
	} else {
	    isValidElementToBuild = this.isValidBuildUpgrade(element);
	}

	if (!isValidElementToBuild) {
	    return false;
	}

	return this.checkElementType(element.getElementType(), x, y);
    }

    private boolean isValidBuildNew(IBoardElement element) {

	return element.getElementType() != BoardElementType.Structure
		|| ((IBoardStructure) element).getType() != StructureType.City;
    }

    private boolean isValidBuildUpgrade(IBoardElement element) {

	return !isValidBuildNew(element);
    }

}
