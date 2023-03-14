package frame;

import model.Move;
import model.State;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HexagonGui extends JFrame implements ActionListener{
    private GridHexagons pan;
    private State state ;
    private int side ;
    private JButton btnStart , btnReset;
    private JPanel btnPanel;
    public HexagonGui(State state , int side){
        this.state = state ;
        Container c = getContentPane();
        btnPanel = new JPanel();
        btnPanel.setLayout(new GridLayout(1, 5));
        setLayout(new BorderLayout());
        pan = new GridHexagons( state  ,  side);
          setTitle("Hex Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 900);

         btnStart = new JButton(" Start");
         btnReset = new JButton("reset");
        btnStart.setBounds(800 , 100 , 50 , 100);
        btnReset.setBounds(800 , 200 , 50 , 100);
        btnPanel.add(btnStart);
        btnPanel.add(btnReset);
        c.add(pan , BorderLayout.CENTER);
        c.add(btnPanel, BorderLayout.SOUTH);

        btnStart.addActionListener(this);
        btnReset.addActionListener(this);


    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        System.out.println(Thread.currentThread().getName());
              if(actionEvent.getSource() == btnStart){
                  while(true) {

                            ThreadTest  test=  new ThreadTest(state);
                            test.run();
                            state = test.state;
                          pan.update(state);

                        //repaint();
                        pan.paintComponent(pan.getGraphics());
                      state.printBoard();
                      if (state.isOver(state.getCurrentPlayer()) == true) {

                          break;
                      }
                      state.setCurrentPlayer((state.getCurrentPlayer() == 1) ? 2 : 1);


                  }
                  if (state.getCurrentPlayer() == 1) {
                      JOptionPane.showMessageDialog(null, "RED" + " has won!!", "Winner",
                              JOptionPane.INFORMATION_MESSAGE);
                  }
                  if(state.getCurrentPlayer() == 2){
                      JOptionPane.showMessageDialog(null, "BLUE" + " has won!!", "Winner",
                              JOptionPane.INFORMATION_MESSAGE);

                  }
              }if(actionEvent.getSource() == btnReset){

                   pan.removeAll();
                        this.state = new State(this.state.getCols() , this
                                .state.getRows());
                        repaint();
        }



    }


}
