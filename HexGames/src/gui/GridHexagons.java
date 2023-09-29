
package src.gui;

import src.model.State;
import src.util.Listener;

import java.awt.*;

import javax.swing.*;

public class GridHexagons extends JPanel implements Listener {
    private final Polygon hexagon = new Polygon();

    private final Dimension dimension;
    private final int side;
    private State state;

    /*
     *@param the side
     * @param State state
     */
    public GridHexagons(State state, final int side) {
        this.state = state;
        this.side = side;
        dimension = getHexagon(0, 0).getBounds().getSize();
    }

    @Override
    public void paintComponent(final Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.black);
        int decalage = 0;
        for (int row = 0; row < state.getRows(); row += 2) {
            for (int column = 0; column < state.getCols(); column++) {
                getHexagon(decalage + column * dimension.width, (int) (row * side * 1.5));
                g2d.setColor(Color.black);
                g2d.draw(hexagon);
                if (state.getBoard()[row][column] == 1) {
                    g2d.setColor(Color.red);
                    g2d.fillPolygon(hexagon);
                }
                if (state.getBoard()[row][column] == 2) {
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
                g2d.setColor(Color.black);
                g2d.draw(hexagon);
                if (state.getBoard()[row][column] == 1) {
                    g2d.setColor(Color.red);
                    g2d.fillPolygon(hexagon);
                }
                if (state.getBoard()[row][column] == 2) {
                    g2d.setColor(Color.blue);
                    g2d.fillPolygon(hexagon);
                }
                g2d.setColor(Color.black);
            }
            decalage += dimension.width;

        }


    }

    /*
     *@param the coordinate x
     * @param the coordinate y
     * @ return a polygon
     */
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

    public void update(State s) {
        this.state = s;
    }


    /**
     * @param s
     */
    @Override
    public void updateModel(Object s) {
        this.state = (State) s;
        repaint();
    }
}

