/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dsc.gol;

/**
 *
 * @author Dominik Schlosser
 */
public enum StandardCell implements Cell {
    Alive(n -> n == 2 || n == 3, true),
    Dead(n -> n == 3, false);

    private final CellEvolver cellEvolver;
    private final boolean alive;

    private StandardCell(CellEvolver cellEvolver, boolean alive) {
        this.cellEvolver = cellEvolver;
        this.alive = alive;
    }

    @Override
    public StandardCell evolve(int neighbors) {
        return cellEvolver.evolve(neighbors) ? Alive : Dead;
    }

    public boolean isAlive() {
        return alive;
    }

    @FunctionalInterface
    private interface CellEvolver {

        boolean evolve(int neighbors);
    }
}
