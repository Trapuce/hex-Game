package src.mcts;

import src.model.Move;
import src.model.State;

import java.util.*;

public class MonteCarloTreeSearchRAVE {

    private State rootNode;
    private State bestMove;
    private Map<State, Map<Move, double[]>> raveTable = new HashMap<>();
    public  Move movePlay ;

    public MonteCarloTreeSearchRAVE(State state) {
        this.rootNode = state;
       // raveStats = new HashMap<>();

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
                    //child.setUCTValue();
                    //child.setUCTValueWithRAVE(raveTable.get(currentNode), currentNode.getNumVisits());
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

                return newState.getCurrentPlayer();

            }
            newState.setCurrentPlayer((newState.getCurrentPlayer() == 1) ? 2 : 1);

        }
    }

    public void backpropagateRolloutResult(State s, int won) { // backpropagate

        State current = s;
        while (current != null) {

            current.setNumVisits(current.getNumVisits() + 1);

            if (rootNode.getCurrentPlayer() == won) {
                current.setVictories(current.getVictories() + 1);

            } else current.setLosses(current.getLosses() + 1);



            // mise à jour des statistiques RAVE
            Map<Move, double[]> raveStats = raveTable.computeIfAbsent(current, k -> new HashMap<>());
            Move lastMove = current.geLastMove();
            double[] raveValues = raveStats.computeIfAbsent(lastMove, k -> new double[2]);
            raveValues[0] += (rootNode.getCurrentPlayer() == won) ? 1 : 0;
            raveValues[1]++;
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
                movePlay = bestMove.geLastMove();
                victories = child.getVictories();
            }
        }
        for (Map.Entry<State, Map<Move, double[]>> stateEntry : raveTable.entrySet()) {
            State state = stateEntry.getKey();
            System.out.println("State: " + state.toString());
            Map<Move, double[]> raveStats = stateEntry.getValue();
            for (Map.Entry<Move, double[]> moveEntry : raveStats.entrySet()) {
                Move move = moveEntry.getKey();
                double[] stats = moveEntry.getValue();
                System.out.println("  Move: " + move.toString() + " RAVE stats: " + Arrays.toString(stats));
            }
        }


    }

    public State getRootNode() {
        return rootNode;
    }

    public void setRootNode(State rootNode) {
        this.rootNode = rootNode;
    }

    public State getBestMove() {
        return bestMove;
    }

    public void setBestMove(State bestMove) {
        this.bestMove = bestMove;
    }
}

