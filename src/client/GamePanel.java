package client;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
        int x = 50;
        int y = 50;
        int width = 500;
        int height = 300;
        Shape pokerTable = new Ellipse2D.Float(x, y, width, height);
        g2.rotate(Math.toRadians(180), x + width / 2, y + height / 2);
        g2.setPaint(Color.GREEN);
        g2.fill(pokerTable);
        g2.setStroke(new BasicStroke(4));
        g2.setPaint(Color.BLACK);
        g2.draw(pokerTable);
	}

	public Dimension getMinimumSize() {
        return new Dimension(600, 600);
    }

	public Dimension getMaximumSize() {
        return new Dimension(600, 600);
    }

	public Dimension getPreferredSize() {
        return new Dimension(600, 600);
    }

	public GamePanel() {
	}
	
	public GamePanel(LayoutManager layout){
		super(layout);
	}
}