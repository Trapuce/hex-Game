package mcts;

import model.Move;
import model.State;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

public class MonteCarloTreeSearch {


    public State rootNode;
    public State bestMove;

    public MonteCarloTreeSearch(State r) {
        this.rootNode = r;

    }

    public State select(State r) { //select
        State currentNode = r;
        while (true) {
            if (currentNode.isOver(currentNode.getCurrentPlayer())) {
                return currentNode;
            }
            if (currentNode.getChildren().isEmpty()) {
                expand(currentNode);
                if (currentNode.getChildren().isEmpty()) {
                    return currentNode;
                }
                return currentNode.getChildren().get(0);
            } else {
                for (State child : currentNode.getChildren()) {
                    child.setUCTValue();
                }

                Collections.sort(currentNode.getChildren());
                currentNode = currentNode.getChildren().get(0);
                if (currentNode.getNumVisits() == 0) {
                    return currentNode;
                } else {
                    return select(currentNode);
                }
            }
        }

    }

    public void expand(State s) {
        Set<Move> allMovePossibles = s.getMove();
        for (Move m : allMovePossibles) {
            State child = s.play(m);
            child.setCurrentPlayer(s.getCurrentPlayer() == 1 ? 2 : 1);
            s.setChildren(child);
        }
    }

    public int simulate(State s) {
        State newState = s.deepCopy();
        if (newState.isOver(newState.getCurrentPlayer())) {
            return newState.getCurrentPlayer();
        }
        while (true) {
            Move m = newState.getRandomMove(newState.getMove());
            if (m == null) {
                return newState.getCurrentPlayer();
            }
            newState = newState.play(m);
            if (newState.isOver(newState.getCurrentPlayer()) == true) {
              //  System.out.println("gagnant sim: "+ newState.getCurrentPlayer());
                return newState.getCurrentPlayer();

            }
            newState.setCurrentPlayer((newState.getCurrentPlayer() == 1) ? 2 : 1);

        }
    }

    public void backpropagateRolloutResult(State s, int won) { // backpropagate

        State current = s;
        while (current != null) {
            current.setNumVisits(current.getNumVisits() + 1);
            if (current.getCurrentPlayer() == won) {
                current.setVictories(current.getVictories() + 1);
            } else current.setLosses(current.getLosses() + 1);
            current = current.getParent();
        }
    }

    public void findBestMove(int numIterations) {

        for (int i = 0; i < numIterations; i++) {

            State nodePromise = select(rootNode); // selection / expansion phase
            int won = simulate(nodePromise); // rollout phase
            backpropagateRolloutResult(nodePromise, won); // backpropagation phase

        }

        double victories = 0;
        for (State child : rootNode.getChildren()) {
            if (child.getVictories() >= victories) {
                bestMove = child;
                victories = child.getVictories();
            }
        }
       // System.out.println(bestMove);
     //  for (State child: rootNode.getChildren()) System.out.println(Arrays.toString(child.getBoard()) + " " + child.getNumVisits()+ " " +child.getVictories() +" "+" "+ child.getLosses() +" "+ child.getUCTValue() + " " + child.getMove());

    }


}
