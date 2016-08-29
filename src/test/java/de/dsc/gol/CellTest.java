/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dsc.gol;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Dominik Schlosser
 */
public class CellTest {

    @Test
    public void livingCellWithOneLivingNeighborDies() {
        StandardCell evolved = StandardCell.Alive.evolve(1);

        Assert.assertEquals(StandardCell.Dead, evolved);
    }

    @Test
    public void livingCellWithTwoLivingNeighborsStaysAlive() {
        StandardCell evolved = StandardCell.Alive.evolve(2);

        Assert.assertEquals(StandardCell.Alive, evolved);
    }

    @Test
    public void livingCellWithThreeLivingNeighborsStaysAlive() {
        StandardCell evolved = StandardCell.Alive.evolve(3);

        Assert.assertEquals(StandardCell.Alive, evolved);
    }

    @Test
    public void livingCellWithFourNeighborsDies() {
        StandardCell evolved = StandardCell.Alive.evolve(4);

        Assert.assertEquals(StandardCell.Dead, evolved);
    }

    @Test
    public void deadCellWithTwoLivingNeighborsStaysDead() {
        StandardCell evolved = StandardCell.Dead.evolve(2);

        Assert.assertEquals(StandardCell.Dead, evolved);
    }

    @Test
    public void deadCellWithThreeLivingNeighborsIsResurrected() {
        StandardCell evolved = StandardCell.Dead.evolve(3);

        Assert.assertEquals(StandardCell.Alive, evolved);
    }

    @Test
    public void deadCellWithFourNeighborsStaysDead() {
        StandardCell evolved = StandardCell.Dead.evolve(4);

        Assert.assertEquals(StandardCell.Dead, evolved);
    }
}
