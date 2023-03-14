

import frame.HexagonGui;
import mcts.MonteCarloTreeSearch;
import model.Move;
import model.State;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

public class Main {


    public static void main(String[] args) {
        State initialState = new State(11, 11);
        initialState.setCurrentPlayer(1);



        while (true) {
            MonteCarloTreeSearch currentPlayerMcts;
            if (initialState.getCurrentPlayer() == 1) {
                MonteCarloTreeSearch player1Mcts = new MonteCarloTreeSearch(initialState.deepCopy());
                currentPlayerMcts = player1Mcts;
                currentPlayerMcts.findBestMove(100);

            } else {
                MonteCarloTreeSearch player2Mcts = new MonteCarloTreeSearch(initialState.deepCopy());
                currentPlayerMcts = player2Mcts;
                currentPlayerMcts.findBestMove(1000);

            }

            System.out.println("Player " + initialState.getCurrentPlayer() + " played:");
            currentPlayerMcts.bestMove.printBoard();
            System.out.println("player  "+currentPlayerMcts.bestMove.getCurrentPlayer());
            initialState = currentPlayerMcts.bestMove.deepCopy();
            System.out.println("player  "+initialState.getCurrentPlayer());

            State clonedState = initialState.deepCopy(); // créer une copie de l'état

            if (clonedState.isOver(clonedState.getCurrentPlayer() == 1 ? 2:1)) {
                break;
            }


          //  clonedState.setCurrentPlayer(initialState.getCurrentPlayer() == 1 ? 2 : 1);
            initialState = clonedState.deepCopy();
            //System.out.println(""+initialState.getCurrentPlayer());
        }

        System.out.println("Winner: " + initialState.getCurrentPlayer());
    }



                //HexagonGui hex = new HexagonGui(state , 40);
                //hex.setVisible(true);
               // System.out.println(Thread.currentThread().getName());

}


