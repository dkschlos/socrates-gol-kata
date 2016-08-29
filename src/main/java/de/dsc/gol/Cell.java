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
public interface Cell {

    Cell evolve(int neighbors);

    boolean isAlive();
}
