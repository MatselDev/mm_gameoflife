package swingapplication.model;

import swingapplication.gui.interfaces.classLinker;

import java.io.*;
import java.util.Random;

public class World implements classLinker, Serializable {

	public int rows;
	public int columns;

	private boolean[][] grid;
	private boolean[][] buffer;

	public World(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;

		grid = new boolean[rows][columns];
		buffer = new boolean[rows][columns];
	}

	public boolean getCell(int row, int col) {
		return grid[row][col];
	}

	public void setCell(int row, int col, boolean status) {
		grid[row][col] = status;
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}


	public void clear() {

		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				grid[i][j] = false;
			}
		}
	}

	public void randomize() {
		Random random = new Random();
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				int coinFlip = random.nextInt(18);
				if (coinFlip == 1) {
					setCell(i, j, true);
				}
			}
		}

	}


	public void next() {


		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < columns; col++) {

				int neighbours = checkNeighbours(row, col);

				boolean status = false;

				if (neighbours < 2) {
					status = false;
				} else if (neighbours > 3) {
					status = false;
				} else if (neighbours == 3) {
					status = true;
				} else if (neighbours == 2) {
					status = getCell(row, col);
				}

				buffer[row][col] = status;
			}
		}

		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < columns; col++) {

				grid[row][col] = buffer[row][col];

			}
		}
	}

	public int checkNeighbours(int row, int col) {
		int neighbours = 0;

		for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
			for (int colOffset = -1; colOffset <= 1; colOffset++) {

				if (rowOffset == 0 && colOffset == 0) {
					continue;
				}

				int gridRow = row + rowOffset;
				int gridCol = col + colOffset;

				if (gridRow < 0) {
					continue;
				} else if (gridRow == rows) {
					continue;
				}

				if (gridCol < 0) {
					continue;
				} else if (gridCol == columns) {
					continue;
				}

				boolean status = getCell(gridRow, gridCol);

				if (status) {
					neighbours++;
				}


			}
		}
		return neighbours;
	}


	public void getGrid(File selectedfile) {
		try (var dos = new DataOutputStream(new FileOutputStream(selectedfile))) {
			dos.writeInt(rows);
			dos.writeInt(columns);

			for (int row = 0; row < rows; row++) {
				for (int col = 0; col < columns; col++) {
					dos.writeBoolean(grid[row][col]);
				}
			}

		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}


	}

	public void loadGrid(File openFile) {
		try (var dos = new DataInputStream(new FileInputStream(openFile))) {
			int fileRows = dos.readInt();
			int fileColumns = dos.readInt();

			for (int row = 0; row < fileRows; row++) {
				for (int col = 0; col < fileColumns; col++) {
					boolean status = dos.readBoolean();
					if(row >= rows || col >= columns) {
						continue;
				}

				grid[row][col] = status;

				}
			}

			System.out.println("Load completed");
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
