/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dsc.gol;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 *
 * @author Dominik Schlosser
 */
public class BoardTest {

    @Test
    public void boardWithOneRowAndOneColumnIsPopulatedAutomatically() {
        Board board = new Board(1, 1);

        Assert.assertEquals(StandardCell.Dead, board.getCellAt(0, 0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void boardWithOneRowAndOneColumnThrowsIfCellOutsideRowRangeIsAccessed() {
        Board board = new Board(1, 1);

        board.getCellAt(1, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void boardWithOneRowAndOneColumnThrowsIfCellOutsideColumnRangeIsAccessed() {
        Board board = new Board(1, 1);

        board.getCellAt(0, 1);
    }

    // property-based testing for random board-size -> max x,y coordinates?
    @Test
    public void initiallyAliveCellIsReturnedAsAlive() {
        Board board = new Board(2, 1, new CellCoordinates(0, 0));

        Assert.assertEquals(StandardCell.Alive, board.getCellAt(0, 0));
    }

    @Test
    public void deadCellIsReturnedAsDeadEvenIfThereIsAnotherAliveCell() {
        Board board = new Board(2, 1, new CellCoordinates(0, 0));

        Assert.assertEquals(StandardCell.Dead, board.getCellAt(1, 0));
    }

    @Test
    public void twoAliveCellsAreReturnedAsAlive() {
        Board board = new Board(2, 1, new CellCoordinates(0, 0), new CellCoordinates(1, 0));

        Assert.assertEquals(StandardCell.Alive, board.getCellAt(0, 0));
        Assert.assertEquals(StandardCell.Alive, board.getCellAt(1, 0));
    }

    @Test
    public void getCellReturnsSetCell() {
        Board board = new Board(2, 1);

        board.setCell(new CellCoordinates(0, 0), StandardCell.Alive);

        Assert.assertEquals(StandardCell.Alive, board.getCellAt(0, 0));
    }

    @Test
    public void cellWithSingleLivingNeighborIsEvolvedAccordingly() {
        Cell cellMock = createCellMock();
        Board board = new Board(2, 1, new CellCoordinates(1, 0));
        board.setCell(new CellCoordinates(0, 0), cellMock);

        board.evolve();

        Mockito.verify(cellMock).evolve(1);
    }

    @Test
    public void cellWithTwoLivinNeighborsIsEvolvedAccordingly() {
        Cell cellMock = createCellMock();
        Board board = new Board(2, 2, new CellCoordinates(1, 0), new CellCoordinates(0, 1));
        board.setCell(new CellCoordinates(0, 0), cellMock);

        board.evolve();

        Mockito.verify(cellMock).evolve(2);
    }

    @Test
    public void livingNonNeighborCellBelowIsIgnoredWhileEvolving() {
        Cell cellMock = createCellMock();
        Board board = new Board(3, 3, new CellCoordinates(1, 0), new CellCoordinates(0, 2));
        board.setCell(new CellCoordinates(0, 0), cellMock);

        board.evolve();

        Mockito.verify(cellMock).evolve(1);
    }

    @Test
    public void livingNonNeighborCellAboveIsIgnoredWhileEvolving() {
        Cell cellMock = createCellMock();
        Board board = new Board(3, 3, new CellCoordinates(1, 2), new CellCoordinates(0, 0));
        board.setCell(new CellCoordinates(0, 2), cellMock);

        board.evolve();

        Mockito.verify(cellMock).evolve(1);
    }

    @Test
    public void livingNonNeighborCellOnFarLeftIsIgnoredWhileEvolving() {
        Cell cellMock = createCellMock();
        Board board = new Board(3, 3, new CellCoordinates(1, 0), new CellCoordinates(0, 0));
        board.setCell(new CellCoordinates(2, 0), cellMock);

        board.evolve();

        Mockito.verify(cellMock).evolve(1);
    }

    @Test
    public void livingNonNeighborCellOnFarRightIsIgnoredWhileEvolving() {
        Cell cellMock = createCellMock();
        Board board = new Board(3, 3, new CellCoordinates(1, 0), new CellCoordinates(2, 0));
        board.setCell(new CellCoordinates(0, 0), cellMock);

        board.evolve();

        Mockito.verify(cellMock).evolve(1);
    }

    @Test
    public void cellItselfIsIgnoredWhileCountingNeighors() {
        Cell cellMock = createCellMock();
        Mockito.when(cellMock.isAlive()).thenReturn(true);
        Board board = new Board(3, 3, new CellCoordinates(1, 0), new CellCoordinates(1, 1));
        board.setCell(new CellCoordinates(0, 0), cellMock);

        board.evolve();

        Mockito.verify(cellMock).evolve(2);
    }

    @Test
    public void evolveUpdatesCell() {
        Cell cellMock = Mockito.mock(Cell.class);
        Cell evolved = Mockito.mock(Cell.class);
        Mockito.when(cellMock.evolve(Mockito.any(Integer.class))).thenReturn(evolved);
        Board board = new Board(3, 3, new CellCoordinates(1, 0), new CellCoordinates(2, 0));
        board.setCell(new CellCoordinates(0, 0), cellMock);

        board.evolve();

        Assert.assertSame(evolved, board.getCellAt(0, 0));
    }

    private static Cell createCellMock() {
        Cell cellMock = Mockito.mock(Cell.class);
        Cell evolved = Mockito.mock(Cell.class);
        Mockito.when(cellMock.evolve(Mockito.any(Integer.class))).thenReturn(evolved);

        return cellMock;
    }
}
