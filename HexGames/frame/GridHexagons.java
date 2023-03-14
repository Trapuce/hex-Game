package frame;

import model.State;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.ArrayList;
import java.util.List;
public class GridHexagons extends JPanel {
    private final Polygon hexagon = new Polygon();
    private final BasicStroke bs1 = new BasicStroke(1);
    private final BasicStroke bs3 = new BasicStroke(3);
    private final Point focusedHexagonLocation = new Point();
    private final Dimension dimension;
    private final int  side;
    private State state ;
    private Point mousePosition;
    private int number;
    private List<Polygon> polygons = new ArrayList<>();
    private List<Integer> polygonsPos = new ArrayList<>();

    public GridHexagons(State state, final int side) {
        this.state = state;
        this.side = side;
        dimension = getHexagon(0, 0).getBounds().getSize();
    }
    @Override
    public void paintComponent(final Graphics g ) {
        System.out.println("ok repaint");
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.black);
        g2d.setStroke(bs1);
        number = -1;
        int decalage = 0;
        for (int row = 0; row < state.getRows(); row += 2) {
            for (int column = 0; column < state.getCols(); column++) {
                getHexagon(decalage + column * dimension.width, (int) (row * side * 1.5));
                //if (mousePosition !=null && hexagon.contains(mousePosition)){
                    focusedHexagonLocation.x = decalage + column * dimension.width;
                    focusedHexagonLocation.y = (int) (row * side * 1.5);
                    number = row * state.getCols() + column;
             //  }
                g2d.draw(hexagon);
                g2d.setColor(Color.black);
                g2d.setStroke(bs3);
                Polygon focusedHexagon = getHexagon(focusedHexagonLocation.x,
                        focusedHexagonLocation.y);
                g2d.draw(focusedHexagon);
                if(state.getBoard()[row][column] == 1){
                    g2d.setColor(Color.red);
                    g2d.fillPolygon(hexagon);
                }if(state.getBoard()[row][column] == 2){
                    g2d.setColor(Color.blue);
                    g2d.fillPolygon(hexagon);
                }
            }
            decalage += dimension.width;
        }
        decalage = 0;
        for (int row = 1; row < state.getRows(); row += 2) {
            for (int column = 0; column < state.getCols(); column++) {
                getHexagon(decalage + column * dimension.width + dimension.width / 2, (int) (row * side * 1.5 + 0.5));
               // if (mousePosition!= null && hexagon.contains(mousePosition)){
                    focusedHexagonLocation.x = decalage + column * dimension.width + dimension.width / 2;
                    focusedHexagonLocation.y =(int) (row * side * 1.5 + 0.5);
                    number = row * state.getCols() + column;
             //   }
                 g2d.draw(hexagon);

                if(state.getBoard()[row][column] == 1){
                    g2d.setColor(Color.red);
                    g2d.fillPolygon(hexagon);
                }if(state.getBoard()[row][column] == 2){
                    g2d.setColor(Color.blue);
                    g2d.fillPolygon(hexagon);
                }
                   //

               // }
               // g2d.draw(hexagon);
                g2d.setColor(Color.black);
                g2d.setStroke(bs3);
                Polygon focusedHexagon = getHexagon(focusedHexagonLocation.x,
                        focusedHexagonLocation.y);
                g2d.draw(focusedHexagon);

            }
            decalage += dimension.width;

        }


    }

    public Polygon getHexagon(final int x, final int y) {
        hexagon.reset();
        int h = side / 2;
        int w = (int) (side * (Math.sqrt(3) / 2));
        hexagon.addPoint(x, y + h);
        hexagon.addPoint(x + w, y);
        hexagon.addPoint(x + 2 * w, y + h);
        hexagon.addPoint(x + 2 * w, y + (int) (1.5 * side));
        hexagon.addPoint(x + w, y + 2 * side);
        hexagon.addPoint(x, y + (int) (1.5 * side));
        return hexagon;
    }
    public void update(State s ){
        System.out.println("update :  ok" );
        this.state = s ;
        repaint();
    }

   
}
