package src.gui;

import src.mcts.MonteCarloTreeSearch;
import src.model.State;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HexagonGui extends JFrame implements ActionListener {
    private GridHexagons pan;
    private State initialState;
    private int side;
    private JButton btnStart, btnReset;
    private JPanel btnPanel;
    private  int p1 , p2 ;
    public HexagonGui(State initialState, int side) {
        this.initialState = initialState;
        Container c = getContentPane();
        btnPanel = new JPanel();
        btnPanel.setLayout(new GridLayout(1, 5));
        setLayout(new BorderLayout());
        pan = new GridHexagons(initialState, side);
        setTitle("Hex Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 900);
        btnStart = new JButton(" Start");
        btnReset = new JButton("reset");
        btnPanel.add(btnStart);
        btnPanel.add(btnReset);
        c.add(pan, BorderLayout.CENTER);
        c.add(btnPanel, BorderLayout.SOUTH);
        btnStart.addActionListener(this);

        String player1 = JOptionPane.showInputDialog("Enter your number iteration pour le joueur 1");
         p1 = Integer.parseInt(player1);
        String player2 = JOptionPane.showInputDialog("Enter your number iteration pour le joueur 2");
         p2 = Integer.parseInt(player2);



    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        System.out.println(Thread.currentThread().getName());
        if (actionEvent.getSource() == btnStart) {
            Thread thread = new Thread(() -> {
                while (true) {
                    MonteCarloTreeSearch currentPlayerMcts;

                    //ThreadTest test = new ThreadTest(state);
                    if (initialState.getCurrentPlayer() == 1) {
                        MonteCarloTreeSearch player1Mcts = new MonteCarloTreeSearch(initialState.deepCopy());
                        currentPlayerMcts = player1Mcts;

                        currentPlayerMcts.findBestMove(p1);



                    } else {
                        MonteCarloTreeSearch player2Mcts = new MonteCarloTreeSearch(initialState.deepCopy());
                        currentPlayerMcts = player2Mcts;
                        currentPlayerMcts.findBestMove(p2);

                    }

                    currentPlayerMcts.getBestMove().printBoard();
                   // pan.repaint();
                    pan.updateModel(initialState);
                    System.out.println("getLast :"+ currentPlayerMcts.movePlay);
                    System.out.println("player  "+initialState.getCurrentPlayer());
                    initialState = currentPlayerMcts.getBestMove().deepCopy();
                    pan.updateModel(initialState);
                    if (initialState.isOver(initialState.getCurrentPlayer()==1 ? 2 : 1 )) {
                        initialState.setCurrentPlayer(initialState.getCurrentPlayer()==1 ? 2 :1);
                        break;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                String winnerName = initialState.getCurrentPlayer() == 1 ? "RED" : "BLUE";
                JOptionPane.showMessageDialog(null, winnerName + " has won!!", "Winner",
                        JOptionPane.INFORMATION_MESSAGE);

                pan.updateModel(initialState);
            });

            thread.start();

        }
        if (actionEvent.getSource() == btnReset) {

            pan.removeAll();
            this.initialState = new State(this.initialState.getCols(), this
                    .initialState.getRows());
            repaint();
        }


    }


}
