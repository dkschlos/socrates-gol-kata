/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dsc.gol;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author Dominik Schlosser
 */
class Board {

    private int width;
    private int height;
    private Map<CellCoordinates, Cell> cells = new HashMap<>();

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        initCells();
    }

    public Board(int width, int height, CellCoordinates... cellCoordinates) {
        this(width, height);
        for (CellCoordinates aliveCoords : cellCoordinates) {
            cells.put(aliveCoords, StandardCell.Alive);
        }
    }

    private void initCells() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                cells.put(new CellCoordinates(i, j), StandardCell.Dead);
            }
        }
    }

    public Cell getCellAt(int x, int y) {
        if (x >= width || y >= height) {
            throw new IllegalArgumentException("There is no cell with x:" + x + ", y:" + y);
        }

        return cells.get(new CellCoordinates(x, y));
    }

    void setCell(CellCoordinates cellCoordinates, Cell cell) {
        cells.put(cellCoordinates, cell);
    }

    public void evolve() {
        Map<CellCoordinates, Cell> updates = new HashMap<>();
        for (Entry<CellCoordinates, Cell> cellEntry : cells.entrySet()) {
            CellCoordinates coords = cellEntry.getKey();
            int neighborCount = (int) cells.entrySet().stream()
                    .filter(e -> !e.getKey().equals(coords))
                    .filter(e -> e.getValue().isAlive())
                    .filter(e -> e.getKey().getY() <= coords.getY() + 1 && e.getKey().getY() >= coords.getY() - 1)
                    .filter(e -> e.getKey().getX() <= coords.getX() + 1 && e.getKey().getX() >= coords.getX() - 1)
                    .count();

            updates.put(coords, cellEntry.getValue().evolve(neighborCount));
        }

        cells = updates;
    }

}
