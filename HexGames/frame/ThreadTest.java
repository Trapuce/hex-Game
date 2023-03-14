package frame;

import model.Move;
import model.State;

public class ThreadTest implements  Runnable{

        public State state ;

        public ThreadTest(State state){
            this.state = state ;
        }

    /**
     *
     */
    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Move m = state.getRandomMove(state.getMove());

        state = state.play(m);
    }
}
