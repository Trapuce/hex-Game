package src.mcts;

import src.model.Move;
import src.model.State;

import java.util.Collections;
import java.util.Set;

public class MonteCarloTreeSearch {


    private State rootNode;



    private State bestMove;
    public  Move movePlay ;
    public MonteCarloTreeSearch(State state ) {
        this.rootNode = state;
    }

    /*
    *@param State state
    * @return State
    *
     */
    public State select(State state) {
        State currentNode = state;
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
    /*
    *@param State s
    * create node tree
     */
    public void expand(State state) {
        Set<Move> allMovePossibles = state.getMove();
        for (Move m : allMovePossibles) {
            State child = state.play(m);
            child.setCurrentPlayer(state.getCurrentPlayer() == 1 ? 2 : 1);
            state.setChildren(child);


        }
    }

    /*
    *@param State s
    * @return winner of simulation
     */
    public int simulation(State s) {
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

    /*
    *@param State s
    * @param int won
    * update all spanning tree
     */
    public void backpropagateRolloutResult(State s, int won) { // backpropagate

        State current = s;
        while (current != null) {
            current.setNumVisits(current.getNumVisits() + 1);
            if (rootNode.getCurrentPlayer() == won) {
                current.setVictories(current.getVictories() + 1);
            } else current.setLosses(current.getLosses() + 1);
            current = current.getParent();
        }
    }
    /*
    *@param the number iteration
    * given bestMove
     */
    public void findBestMove(int numIterations) {

        for (int i = 0; i < numIterations; i++) {

            State nodePromise = select(rootNode); // selection / expansion phase
            int won = simulation(nodePromise); // rollout phase
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

    }
    public Move MoveLast(){
        for(int i = 0 ; i<rootNode.getRows() ; i++){
            for (int j = 0 ; j<rootNode.getCols();j++){
                    if(rootNode.getBoard()[i][j] != bestMove.getBoard()[i][j]){
                               return  new Move(i , j);
                    }
            }
        }
        return  null ;
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
