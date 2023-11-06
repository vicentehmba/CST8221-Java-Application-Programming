package cs;

class GameModel {
    private int[][] grid;
    private int numRows;
    private int numCols;
    private boolean isRunning;
    private boolean[][] nextGeneration;
    private int generation;

    public GameModel(int numRows, int numCols) {
        this.numRows = numRows;
        this.numCols = numCols;
        grid = new int[numRows][numCols];
        nextGeneration = new boolean[numRows][numCols];
        isRunning = false;
        generation = 0;
    }

    public int getNumRows() {
        return numRows;
    }

    public int getNumCols() {
        return numCols;
    }

    public int getCellState(int row, int col) {
        return grid[row][col];
    }

    public int getGeneration() {
        return generation;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void toggleCellState(int row, int col) {
        grid[row][col] = grid[row][col] == 1 ? 0 : 1;
    }

    public void togglePlayMode() {
        isRunning = !isRunning;
    }

    public void handleManualCellToggle(int x, int y) {
        if (isValidCell(x, y)) {
            grid[x][y] = grid[x][y] == 1 ? 0 : 1;
        }
    }

    public void setupRandomConfiguration() {
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                grid[i][j] = Math.random() < 0.3 ? 1 : 0;
            }
            generation = 0;
        }
    }

    public void setupManualConfiguration() {
        isRunning = false;
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                grid[i][j] = 0;
            }
        }
    }

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

    private int countAliveNeighbors(int x, int y) {
        int count = 0;
        int[][] neighbors = {
            {-1, -1}, {-1, 0}, {-1, 1},
            {0, -1},           {0, 1},
            {1, -1}, {1, 0},  {1, 1}
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

    private boolean isValidCell(int x, int y) {
        return x >= 0 && x < numRows && y >= 0 && y < numCols;
    }
}
