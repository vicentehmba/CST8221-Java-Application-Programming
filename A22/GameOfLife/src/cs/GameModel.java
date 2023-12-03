/**
 * File Name: GameView.java
 * Identification: Vicente Mba Engung 041029226 // Ken Dekpor 041054266
 * Course: CST 8221 – JAP, Lab Section: [300, 302]
 * Assignment: A12
 * Professor: Daniel Cormier
 * Date: November 6, 2023.
 * Compiler: Eclipse IDE for Java Developers – Version: 2023-09 (4.29.0)
 *
 * Purpose: This class represents the model for a game. It manages the game grid, generation count,
 * cell states, and game logic.
 */

package cs;

/**
 * The class GameModel represents the model for the game.
 */
class GameModel {

  // Variables
  private int[][] grid;
  private int numRows;
  private int numCols;
  private boolean isRunning;
  private boolean[][] nextGeneration;
  private int generation;

  /**
   * Initializes a new instance of the GameModel class.
   *
   * @param numRows The number of rows in the game grid.
   * @param numCols The number of columns in the game grid.
   */
  public GameModel(int numRows, int numCols) {
    /**
     * Initializes the 'numRows' instance variable with the provided 'numRows' parameter value.
     */
    this.numRows = numRows;

    /**
     * Initializes the 'numCols' instance variable with the provided 'numCols' parameter value.
     */
    this.numCols = numCols;

    /**
     * Creates a 2D integer array 'grid' with 'numRows' rows and 'numCols' columns.
     */
    grid = new int[numRows][numCols];

    /**
     * Creates a 2D boolean array 'nextGeneration' with 'numRows' rows and 'numCols' columns.
     */
    nextGeneration = new boolean[numRows][numCols];

    /**
     * Sets the 'isRunning' instance variable to 'false' by default.
     */
    isRunning = false;

    /**
     * Initializes the 'generation' instance variable to 0, indicating the current generation.
     */
    generation = 0;
  }

  /**
   * Gets the number of rows in the game grid.
   *
   * @return The number of rows in the grid.
   */
  public int getNumRows() {
    return numRows;
  }

  /**
   * Gets the number of columns in the game grid.
   *
   * @return The number of columns in the grid.
   */
  public int getNumCols() {
    return numCols;
  }

  /**
   * Gets the state of a cell at the specified row and column.
   *
   * @param row The row of the cell.
   * @param col The column of the cell.
   * @return The state of the cell (1 for alive, 0 for dead).
   */
  public int getCellState(int row, int col) {
    return grid[row][col];
  }

  /**
   * Gets the current generation count.
   *
   * @return The current generation count.
   */
  public int getGeneration() {
    return generation;
  }

  /**
   * Checks if the game is running.
   *
   * @return true if the game is running, false otherwise.
   */
  public boolean isRunning() {
    return isRunning;
  }

  /**
   * Toggles the state of a cell at the specified row and column.
   *
   * @param row The row of the cell.
   * @param col The column of the cell.
   */
  public void toggleCellState(int row, int col) {
    grid[row][col] = grid[row][col] == 1 ? 0 : 1;
  }

  /**
   * Toggles the game's running state.
   */
  public void togglePlayMode() {
    isRunning = !isRunning;
  }

  /**
   * Handles the manual toggle of a cell at the specified row and column.
   *
   * @param x The row of the cell.
   * @param y The column of the cell.
   */
  public void handleManualCellToggle(int x, int y) {
    if (isValidCell(x, y)) {
      grid[x][y] = grid[x][y] == 1 ? 0 : 1;
    }
  }

  /**
   * Sets up a random cell configuration for the game.
   */
  public void setupRandomConfiguration() {
    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < numCols; j++) {
        grid[i][j] = Math.random() < 0.3 ? 1 : 0;
      }
      generation = 0;
    }
  }

  /**
   * Sets up a manual cell configuration for the game.
   */
  public void setupManualConfiguration() {
    isRunning = false;
    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < numCols; j++) {
        grid[i][j] = 0;
      }
    }
  }

  public void setupCustomConfiguration() {}

  /**
   * Advances the game by one generation based on the rules of the game.
   */
  public void step() {
    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < numCols; j++) {
        int neighbors = countAliveNeighbors(i, j);

        if (grid[i][j] == 1) {
          nextGeneration[i][j] = neighbors >= 2 && neighbors <= 3;
        } else {
          nextGeneration[i][j] = neighbors == 3;
        }
      }
    }

    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < numCols; j++) {
        grid[i][j] = nextGeneration[i][j] ? 1 : 0;
      }
    }

    generation++;
  }

  /**
   * Counts the number of live neighbors for a cell at the specified row and column.
   *
   * @param x The row of the cell.
   * @param y The column of the cell.
   * @return The count of live neighbors for the cell.
   */
  private int countAliveNeighbors(int x, int y) {
    int count = 0;
    int[][] neighbors = {
      { -1, -1 },
      { -1, 0 },
      { -1, 1 },
      { 0, -1 },
      { 0, 1 },
      { 1, -1 },
      { 1, 0 },
      { 1, 1 },
    };

    for (int[] neighbor : neighbors) {
      int newX = x + neighbor[0];
      int newY = y + neighbor[1];

      if (isValidCell(newX, newY) && grid[newX][newY] == 1) {
        count++;
      }
    }

    return count;
  }

  /**
   * Checks if the specified cell coordinates are valid within the game grid.
   *
   * @param x The row of the cell.
   * @param y The column of the cell.
   * @return true if the cell coordinates are valid, false otherwise.
   */
  private boolean isValidCell(int x, int y) {
    return x >= 0 && x < numRows && y >= 0 && y < numCols;
  }
}
