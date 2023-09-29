package src;

import src.gui.HexagonGui;
import src.mcts.MonteCarloTreeSearch;
import src.mcts.MonteCarloTreeSearchRAVE;
import src.model.State;

import java.util.ArrayList;

public class Main {


    public static void main(String[] args) {
        State initialState = new State(11, 11);
        initialState.setCurrentPlayer(1);
        HexagonGui hex = new HexagonGui(initialState, 40);
         hex.setVisible(true);

    }
    }



