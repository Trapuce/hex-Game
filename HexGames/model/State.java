package model;

import java.util.*;

public class State implements Comparable<State> {

    private final static int PLAYER1 = 1;
    private final static int PLAYER2 = 2;
    private final static int BLANK = 0;
    private int[][] board;
    private int cols, rows;
    private int currentPlayer;
    private int numberOfPiecesPlayer1;
    private int numberOfPiecesPlayer2;
    private boolean visited[][];
    private ArrayList<State> children;
    private State parent;
    private double numVisits, UCTValue, victories, losses = 0;


    /*
     * @param cols rows the board size
     * @param board the board
     * @param currentPlayer
     */
    public State(int cols, int rows, int[][] board, int currentPlayer, State parent) {
        this.cols = cols;
        this.rows = rows;
        this.board = board;
        this.visited = new boolean[rows][cols];
        this.currentPlayer = currentPlayer;
        // this.numberOfPiecesPlayer1 = numberOfPiecesPlayer1;
        // this.numberOfPiecesPlayer2 = numberOfPiecesPlayer2;
        this.parent = parent;
        this.children = new ArrayList<>();

    }

    /*
     * @param cols rows the board size
     */
    public State(int cols, int rows) {
        this.numberOfPiecesPlayer1 = 0;
        this.numberOfPiecesPlayer2 = 0;
        this.currentPlayer = PLAYER1;
        this.cols = cols;
        this.rows = rows;
        this.board = new int[cols][rows];

        this.visited = new boolean[cols][rows];
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {

                board[i][j] = BLANK;
            }
        }
        // initialisation du tableau des cases visitées
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {

                visited[i][j] = false;
            }
        }
        this.children = new ArrayList<>();
        this.parent = null;
    }

    /*
     * @return Set<Move> all possible moves
     */
    public Set<Move> getMove() {
        Set<Move> allMovePossible = new HashSet<Move>();
        List<Move> list = new ArrayList<>();
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < cols; j++) {
                if (board[i][j] == BLANK) {
                    list.add(new Move(i, j));
                }
            }
        }
        Collections.shuffle(list);
        allMovePossible.addAll(list);
        return allMovePossible;
    }

    /*
     * @param Move
     * @return state
     */
    public State play(Move m) {
        // if(m == null) return new State(cols , rows , board , currentPlayer , this);
        int x = m.getX();
        int y = m.getY();
        int[][] copyBoard = new int[rows][cols];
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                copyBoard[i][j] = this.board[i][j];
            }
        }

        copyBoard[x][y] = currentPlayer;

        if (currentPlayer == PLAYER1) {
            numberOfPiecesPlayer1++;

        } else if (currentPlayer == PLAYER2) {
            numberOfPiecesPlayer2++;

        }

        State newState = new State(cols, rows, copyBoard, currentPlayer, this);
        return newState;
    }

    /*
     * @param x the x coordinate
     * @param y the y coordinate
     * @return the state of the cell (x, y) if (x, y) is in the bounds of the board.
     *  Otherwise return -1
     */
    public int GetCell(int x, int y) {
        if (x < 0 || x >= rows || y < 0 || y >= cols)
            return -1;
        else
            return board[x][y];
    }

    /*
     * @param x the x coordinate
     *
     * @param y the y coordinate
     *
     * @param currentPlayer the current player
     *
     * @return true if  the sides opposite connected
     * true Check if player has a winning path. A winning path is horizontal
     * if player is player 1, vertical otherwise.
     */
    public boolean dfs(int x, int y, int currentPlayer) {

        if (currentPlayer == PLAYER1 && x == rows - 1)
            return true;
        else if (currentPlayer == PLAYER2 && y == cols - 1)
            return true;

        visited[x][y] = true;
        // Check cell (x, y) hexagonal neighbours
        if (GetCell(x - 1, y) == currentPlayer && visited[x - 1][y] == false)
            if (dfs(x - 1, y, currentPlayer) == true)
                return true;

        if (GetCell(x - 1, y + 1) == currentPlayer && visited[x - 1][y + 1] == false)
            if (dfs(x - 1, y + 1, currentPlayer) == true)
                return true;

        if (GetCell(x, y - 1) == currentPlayer && visited[x][y - 1] == false)
            if (dfs(x, y - 1, currentPlayer) == true)
                return true;

        if (GetCell(x, y + 1) == currentPlayer && visited[x][y + 1] == false)
            if (dfs(x, y + 1, currentPlayer) == true)
                return true;

        if (GetCell(x + 1, y - 1) == currentPlayer && visited[x + 1][y - 1] == false)
            if (dfs(x + 1, y - 1, currentPlayer) == true)
                return true;

        if (GetCell(x + 1, y) == currentPlayer && visited[x + 1][y] == false)
            if (dfs(x + 1, y, currentPlayer) == true)
                return true;

        return false;
    }

    /*
     * @param currentPlayer Current player
     *
     * @return true if a winning path is found, false otherwise
     */
    public boolean isOver(int currentPlayer) {
        boolean gameOver = false;
        int y = 0, x = 0;
        int end = (currentPlayer == PLAYER1 ? cols : rows);
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                visited[i][j] = false;
            }

        }

        for (int i = 0; i < end; ++i) {
            if (GetCell(x, y) == currentPlayer && dfs(x, y, currentPlayer) == true) {
                gameOver = true;
                break;
            }

            if (currentPlayer == PLAYER1) {
                y++;
            } else {
                x++;
            }
        }

        return gameOver;
    }

    public State deepCopy() {
        int[][] copyBoard = new int[this.rows][this.cols];
        for (int i = 0; i < this.cols; i++) {
            for (int j = 0; j < this.rows; j++) {
                copyBoard[i][j] = this.board[i][j];
            }
        }
        return new State(this.rows, this.cols, copyBoard, this.currentPlayer, this);
    }

    /*
     * @param Set<Move> setOfMoves
     * @return  a random move
     */
    public Move getRandomMove(Set<Move> setOfMoves) {

        Move[] arrayOfMoves = setOfMoves.toArray(new Move[setOfMoves.size()]);
        Random rnd = new Random();
        if (setOfMoves.isEmpty()) {
            return null;
        }
        int i = rnd.nextInt(setOfMoves.size());
        return arrayOfMoves[i];
    }

    // affichage du plateau en console
    public void printBoard() {
        for (int i = 0; i < cols; i++) {
            for (int space = 0; space < i; space++) {
                System.out.print(" ");
            }
            for (int j = 0; j < rows; j++) {
                if (this.board[i][j] == 0) {
                    System.out.print(" - ");
                } else if (this.board[i][j] == 1) {
                    System.out.print(" 1 ");
                } else if (this.board[i][j] == 2) {
                    System.out.print(" 2 ");
                }
            }
            System.out.println();
        }
    }

    /*
     * @retun currentPLayer
     */
    public int getCurrentPlayer() {
        return this.currentPlayer;
    }

    /*
     *@param currentPlayer
     */
    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /*
     * @return the board
     */
    public int[][] getBoard() {
        return this.board;
    }

    public int getRows() {
        return this.cols;
    }

    public int getCols() {
        return this.rows;
    }

    public ArrayList<State> getChildren() {
        return this.children;
    }

    public void setChildren(State child) {
        this.children.add(child);
    }

    public double getUCTValue() {
        return UCTValue;
    }


    public State getParent() {
        return parent;
    }

    public void setParent(State parent) {
        this.parent = parent;
    }

    public double getVictories() {
        return victories;
    }

    public double getLosses() {
        return losses;
    }

    public void setLosses(double losses) {
        this.losses = losses;
    }

    public void setVictories(double victories) {
        this.victories = victories;
    }

    public double getNumVisits() {
        return numVisits;
    }

    public void setNumVisits(double numVisits) {
        this.numVisits = numVisits;
    }

    /**
     * @param o the object to be compared.
     * @return
     */
    @Override
    public int compareTo(State o) {
        return Double.compare(o.UCTValue, getUCTValue());
    }

    public void setUCTValue() {

        if (numVisits == 0) UCTValue = Double.MAX_VALUE;
        else UCTValue = (victories / numVisits) + Math.sqrt(2) * Math.sqrt(Math.log(parent.numVisits) / numVisits);
    }


}