package model;

import view.Cell;

public class Board {
	private int size;
	private Cell[][] componentCells;
	private int[][] matrixCells;

	public Board(int size) {
		this.size = size;
		this.matrixCells = new int[size][size];
		this.componentCells = new Cell[size][size];
		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				Cell cell = new Cell(y, x);
				this.componentCells[y][x] = cell;
			}
		}
	}

	public void resetBoard() {
		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				signCell(y, x, Game.NON_TICK);
			}
		}
	}

	public int getSize() {
		return size;
	}

	public void setSize(int length) {
		this.size = length;
	}

	public void signCell(int y, int x, int tick) {
		this.componentCells[y][x].tick = tick;
		this.matrixCells[y][x] = tick;
	}

	public Cell getCell(int y, int x) {
		return this.componentCells[y][x];
	}

	public int[][] getMatrixCells() {
		return matrixCells;
	}

	public void setMatrixCells(int[][] matrixCells) {
		this.matrixCells = matrixCells;
	}

}
